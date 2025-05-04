package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.converter.ConverterData;
import br.com.projetocalcado.converter.ConverterInt;
import br.com.projetocalcado.domain.Pedido.ItemDaCompra;
import br.com.projetocalcado.domain.categoria.CategoriaRepository;
import br.com.projetocalcado.domain.estoque.Estoque;
import br.com.projetocalcado.domain.estoque.EstoqueRepository;
import br.com.projetocalcado.domain.fornecedor.FornecedorRepository;
import br.com.projetocalcado.domain.metodoPagamentoPadraoNota.FormaPgtoPadraoNotaRepository;
import br.com.projetocalcado.domain.movimentacaoEstoque.MovimentacaoEstoqueService;
import br.com.projetocalcado.domain.movimentacaoEstoque.TipoMovimentacao;
import br.com.projetocalcado.domain.pagamentos.DadosPagamentoEfetuadoNota;
import br.com.projetocalcado.domain.pagamentos.DadosRetornoPagamentoNota;
import br.com.projetocalcado.domain.produto.Produto;
import br.com.projetocalcado.domain.produto.ProdutoRepository;
import br.com.projetocalcado.domain.produto.ProdutoService;
import br.com.projetocalcado.domain.xmlnota.*;
import br.com.projetocalcado.infra.exception.ValidacaoException;
import br.com.projetocalcado.utilConfig.XstreamConfig;
import com.thoughtworks.xstream.security.AnyTypePermission;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NotaService {
    private Integer numeroDeParcela = 0;
    @Autowired
    MovimentacaoEstoqueService movimentacaoEstoqueService;
    @Autowired
    NotaFiscalRepository notaFiscalRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    FornecedorRepository fornecedorRepository;
    @Autowired
    FormaPgtoPadraoNotaRepository formaPgtoPadraoNotaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    private XstreamConfig xStream;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    ProdutoService produtoService;
    @Getter
    private BigDecimal totalPago = BigDecimal.ZERO;
    public List<Duplicata> duplicatas = new ArrayList<>();
    public List<ItemDaCompra> produtos = new ArrayList<>();
    private BigDecimal totalNota = BigDecimal.ZERO;
    public List<DadosPagamentoEfetuadoNota> pagamentosEfetuadosNotas = new ArrayList<>();

    public InfNFe devolveDadosXml(MultipartFile arquivo) {

        try {
            File tempFile = File.createTempFile("xml_nfe", "xml");
            arquivo.transferTo(tempFile);

            xStream.xStream().registerConverter(new ConverterData());
            xStream.xStream().registerConverter(new ConverterInt());
            xStream.xStream().ignoreUnknownElements();
            xStream.xStream().autodetectAnnotations(true);
            xStream.xStream().addPermission(AnyTypePermission.ANY);
            xStream.xStream().processAnnotations(NfeProc.class);
            NfeProc nfeProc = (NfeProc) xStream.xStream().fromXML(tempFile);
            var infXml = nfeProc.getNfe().getInfNFe();

            if (nfeProc == null || nfeProc.getNfe() == null || nfeProc.getNfe().getInfNFe() == null) {
                throw new ValidacaoException("Erro ao ler o conteúdo do XML. Estrutura inválida.");
            }

            tempFile.delete();

            return infXml;

        } catch (Exception e) {
            throw new ValidacaoException("Falha ao processar o arquivo XML: " + e.getMessage());
        }

    }

    public DadosXml carregaNotaXml(InfNFe infXml) {
        Long idFornecedor;
        ItemDaCompra itemDaCompra = null;

        if (!fornecedorRepository.existsByCnpj(infXml.getFornecedor().getCnpj())) {
            var fornecedor = fornecedorRepository.save(infXml.getFornecedor());
            idFornecedor = fornecedor.getId();
        } else {
            var fornecedor = fornecedorRepository.findByCnpj(infXml.getFornecedor().getCnpj());
            idFornecedor = fornecedor.getId();
        }
        var numeroNota = infXml.getIdentifNota().getNumeroNF();
        LocalDate dataEmissao = infXml.getIdentifNota().getDataEmiss();
        var formaPagamentoPadraNota = formaPgtoPadraoNotaRepository.findByCodigo(infXml.getPag().getDetPag().getNumPagtoTpag());

        for (Duplicata dup : infXml.getCobranca().getDuplicatas()) {
            numeroDeParcela++;
            var pgtoEfetuadoNota = new DadosPagamentoEfetuadoNota(numeroDeParcela, dup.getDataVenc(), dup.getValorDup(), formaPagamentoPadraNota.getDescricaoPagamento());
            pagamentosEfetuadosNotas.add(pgtoEfetuadoNota);
            this.totalPago = totalPago.add(dup.getValorDup());
        }

        for (DetItens detItens : infXml.getDetList()) {

            for (ProdDetalheNota prodD : detItens.getProdutos()) {
                var categoria = categoriaRepository.getReferenceById(1L);
                var estoque = new Estoque(0);
                var produto = new Produto(prodD.getCodProd(), prodD.getCodEan(), prodD.getNomeProd(), prodD.getValorUnit(), prodD.getValorUnit().multiply(new BigDecimal("2.20").setScale(2, RoundingMode.HALF_UP)), LocalDateTime.now(), categoria, estoque);

                if (!produtoRepository.existsByCodEan(prodD.getCodEan())) {
                    produtoRepository.save(produto);
                    itemDaCompra = new ItemDaCompra(prodD.getQuantidade(), produto);
                    produtos.add(itemDaCompra);
                } else {
                    produto = produtoRepository.findByCodEan(prodD.getCodEan());
                    produto.setCustoProd(prodD.getValorUnit());
                    itemDaCompra = new ItemDaCompra(prodD.getQuantidade(), produto);
                    produtos.add(itemDaCompra);
                }
            }
            this.totalNota = this.totalNota.add(itemDaCompra.getProduto().getCustoProd().multiply(new BigDecimal(itemDaCompra.getQuantidade())));
        }
        return new DadosXml(idFornecedor, numeroNota, dataEmissao);
    }

    public DadosNota adicionaProdutoNota(String ean, Integer quantidade) {

        var produto = produtoRepository.findByCodEan(ean);
        boolean produtoJaExistente = false;
        for (ItemDaCompra itemDaCompra : produtos) {
            if (itemDaCompra.getProduto().getId() == produto.getId()) {
                itemDaCompra.setQuantidade(itemDaCompra.getQuantidade() + quantidade);
                itemDaCompra.setSubTotalProdCompra(itemDaCompra.getSubTotalProdCompra().add(produto.getCustoProd().multiply(new BigDecimal(quantidade))));
                produtoJaExistente = true;
                break;
            }
        }
        if (!produtoJaExistente) {
            var item = new ItemDaCompra(quantidade, produto);
            item.setSubTotalProdVenda(new BigDecimal(0));
            produtos.add(item);
        }
        this.totalNota = this.totalNota.add(produto.getCustoProd().multiply(new BigDecimal(quantidade)));
        return new DadosNota(produtos, totalNota);
    }

    public void alteraQuantidadeItens(Long id, String acao) {
        Iterator<ItemDaCompra> iterator = produtos.iterator();
        BigDecimal novoTotal = BigDecimal.ZERO;

        while (iterator.hasNext()) {
            ItemDaCompra itemDaCompra = iterator.next();

            if (itemDaCompra.getProduto().getId().equals(id)) {
                if (acao.equals("+")) {
                    itemDaCompra.setQuantidade(itemDaCompra.getQuantidade() + 1);
                } else if (acao.equals("-")) {
                    itemDaCompra.setQuantidade(itemDaCompra.getQuantidade() - 1);
                    if (itemDaCompra.getQuantidade() <= 0) {
                        iterator.remove();
                        continue;
                    }
                }
                itemDaCompra.setSubTotalProdCompra(itemDaCompra.getProduto().getCustoProd().multiply(new BigDecimal(itemDaCompra.getQuantidade())));
            }
            novoTotal = novoTotal.add(itemDaCompra.getSubTotalProdCompra());
        }
        this.totalNota = novoTotal;
    }

    public void alterarPrecoItens(Long id, BigDecimal precoCusto, BigDecimal precoVenda) {
        Iterator<ItemDaCompra> iterator = produtos.iterator();
        BigDecimal novoTotal = BigDecimal.ZERO;

        while (iterator.hasNext()) {
            ItemDaCompra itemDaCompra = iterator.next();

            if (itemDaCompra.getProduto().getId().equals(id)) {
                itemDaCompra.getProduto().setCustoProd(precoCusto);
                itemDaCompra.getProduto().setPrecoVenda(precoVenda);
                produtoService.atualizaPrecoProduto(itemDaCompra.getProduto().getId(), itemDaCompra.getProduto().getCustoProd(), itemDaCompra.getProduto().getPrecoVenda());
                itemDaCompra.setSubTotalProdCompra(itemDaCompra.getProduto().getCustoProd().multiply(new BigDecimal(itemDaCompra.getQuantidade())));
            }
            novoTotal = novoTotal.add(itemDaCompra.getSubTotalProdCompra());
        }
        this.totalNota = novoTotal;
    }

    public DadosNota retornaItensComQdeAlterada() {
        return new DadosNota(produtos, totalNota);
    }

    public void removeProduto(Long id) {
        Iterator<ItemDaCompra> iterator = produtos.iterator();
        BigDecimal novoTotal = BigDecimal.ZERO;

        while (iterator.hasNext()) {
            ItemDaCompra itemDaCompra = iterator.next();
            if (itemDaCompra.getProduto().getId().equals(id)) {
                iterator.remove();
            } else {
                novoTotal = novoTotal.add(itemDaCompra.getSubTotalProdCompra());
            }
        }
        this.totalNota = novoTotal;
    }

    public void adicionaPagamento(DadosDuplicata dadosDuplicata) {
        numeroDeParcela++;
        var formaPgtoPadraoNota = formaPgtoPadraoNotaRepository.getReferenceById(dadosDuplicata.idFormaPagto());
        this.totalPago = totalPago.add(dadosDuplicata.valorDup());
        criaDtoRetornoPgto(numeroDeParcela, dadosDuplicata.dataVenc(), dadosDuplicata.valorDup(), formaPgtoPadraoNota.getDescricaoPagamento());
    }

    public void criaDtoRetornoPgto(Integer numParcela, LocalDate dataVenc, BigDecimal valorDup, String descricaoPgto) {
        var pgtoEfetuadoNota = new DadosPagamentoEfetuadoNota(numParcela, dataVenc, valorDup, descricaoPgto);
        pagamentosEfetuadosNotas.add(pgtoEfetuadoNota);
    }

    public void removerPagamento(int numParcela) {

        Iterator<DadosPagamentoEfetuadoNota> iteratorPgto = pagamentosEfetuadosNotas.iterator();

        while (iteratorPgto.hasNext()) {
            DadosPagamentoEfetuadoNota pagamento = iteratorPgto.next();
            if (pagamento.numParcela() == numParcela) {
                iteratorPgto.remove();
                break;
            }
        }

        int novaParcela = 1;
        List<DadosPagamentoEfetuadoNota> listaAtualizada = new ArrayList<>();

        for (DadosPagamentoEfetuadoNota pagamento : pagamentosEfetuadosNotas) {

            DadosPagamentoEfetuadoNota parcela = new DadosPagamentoEfetuadoNota(
                    novaParcela++,
                    pagamento.dataVenc(),
                    pagamento.valorDup(),
                    pagamento.descricaoPagamento()
            );
            listaAtualizada.add(parcela);
        }
        pagamentosEfetuadosNotas.clear();
        pagamentosEfetuadosNotas.addAll(listaAtualizada);
        BigDecimal novoTotal = BigDecimal.ZERO;
        for (DadosPagamentoEfetuadoNota pagamento : pagamentosEfetuadosNotas) {
            novoTotal = novoTotal.add(pagamento.valorDup());
        }
        this.totalPago = novoTotal;
    }

    public DadosRetornoPagamentoNota retornaValorPago() {
        return new DadosRetornoPagamentoNota(pagamentosEfetuadosNotas, totalPago);
    }

    public DadosRetornoNotaFiscal finalizaNota(DadosCabecalhoNota dadosCabecalhoNota) {

        var fornecedor = fornecedorRepository.getReferenceById(dadosCabecalhoNota.idFornecedor());
        LocalDate dataEmissao = dadosCabecalhoNota.data();
        var numeroNota = dadosCabecalhoNota.numeroNota();
        var nota = new NotaFiscal(numeroNota, dataEmissao, fornecedor);

        for (DadosPagamentoEfetuadoNota pg : pagamentosEfetuadosNotas) {
            Duplicata dup = new Duplicata(pg.numParcela(), pg.dataVenc(), pg.valorDup(), nota);
            duplicatas.add(dup);
        }

        if (nota != null && !produtos.isEmpty() && !duplicatas.isEmpty() && fornecedor != null) {
            if (totalPago.compareTo(totalNota) == -1) {
                throw new ValidacaoException("O valor informado para o pagamento é inferior ao valor total da nota.");
            }
            for (ItemDaCompra item : produtos) {
                int novaQuantidade = item.getProduto().getEstoque().getQuantidade() + item.getQuantidade();
                var estoque = estoqueRepository.getReferenceById(item.getProduto().getEstoque().getId());
                estoque.setQuantidade(novaQuantidade);
                var itensNota = new ItensNota(item.getQuantidade(), nota, item.getProduto());
                nota.adiconarItem(itensNota);
                movimentacaoEstoqueService.registraEntradaMovimentacao(item.getProduto(), TipoMovimentacao.SAIDA, item.getQuantidade());
            }
            for (Duplicata dupli : duplicatas) {
                nota.adicionaPagamento(dupli);
            }
            notaFiscalRepository.save(nota);
            resetaDadosNota();
        } else {
            throw new ValidacaoException("Você precisa adicionar o fornecedor, os produtos e as condições de pagamento antes de finalizar a nota.");
        }
        return new DadosRetornoNotaFiscal(nota);
    }

    public void resetaDadosNota() {
        duplicatas.clear();
        produtos.clear();
        pagamentosEfetuadosNotas.clear();
        totalNota = BigDecimal.ZERO;
        totalPago = BigDecimal.ZERO;
        numeroDeParcela = 0;
    }
}