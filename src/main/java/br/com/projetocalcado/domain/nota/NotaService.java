package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.converter.ConverterData;
import br.com.projetocalcado.converter.ConverterInt;
import br.com.projetocalcado.domain.Pedido.ItemDaCompra;
import br.com.projetocalcado.domain.categoria.Categoria;
import br.com.projetocalcado.domain.estoque.Estoque;
import br.com.projetocalcado.domain.estoque.EstoqueRepository;
import br.com.projetocalcado.domain.financeiro.Lancamento;
import br.com.projetocalcado.domain.financeiro.LancamentoRepository;
import br.com.projetocalcado.domain.financeiro.TipoLancamento;
import br.com.projetocalcado.domain.financeiro.tipoLancamento.TabelaLancamentoRepository;
import br.com.projetocalcado.domain.fornecedor.Fornecedor;
import br.com.projetocalcado.domain.fornecedor.FornecedorRepository;
import br.com.projetocalcado.domain.metodoPagamentoPadraoNota.FormaPgtoPadraoNotaRepository;
import br.com.projetocalcado.domain.metodoPagamentoPedido.FormaPagamentoRepository;
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
    private Estoque estoque;
    private Fornecedor fornecedor;
    private Duplicata duplicata;
    private Produto produto;
    private ItensNota itensNota;
    private Integer numeroDeParcela = 0;
    @Autowired
    MovimentacaoEstoqueService movimentacaoEstoqueService;
    @Autowired
    NotaFiscalRepository notaFiscalRepository;
    @Autowired
    LancamentoRepository lancamentoRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    FornecedorRepository fornecedorRepository;
    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    FormaPgtoPadraoNotaRepository formaPgtoPadraoNotaRepository;
    @Autowired
    DetPagRepository repository;
    @Autowired
    private XstreamConfig xStream;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    TabelaLancamentoRepository tabelaLancamentoRepository;
    @Autowired
    DetPagRepository detPagRepository;
    @Autowired
    ProdutoService produtoService;

    @Getter
    private BigDecimal totalPago = BigDecimal.ZERO;
    public List<Duplicata> duplicatas = new ArrayList<>();
    public List<Lancamento> lancamentos = new ArrayList<>();
    public List<ItemDaCompra> produtos = new ArrayList<>();
    private BigDecimal totalPedido = BigDecimal.ZERO;
    private LocalDate dataEmissao;
    private Long numeroNota;
    private NotaFiscal nota;
    public List<DadosPagamentoEfetuadoNota> pagamentosEfetuadosNotas = new ArrayList<>();

    public InfNFe devolveDadosXml(String caminho) {

        File file = new File("c:\\arq\\" + caminho);
        xStream.xStream().registerConverter(new ConverterData());
        xStream.xStream().registerConverter(new ConverterInt());
        xStream.xStream().ignoreUnknownElements();
        xStream.xStream().autodetectAnnotations(true);
        xStream.xStream().addPermission(AnyTypePermission.ANY);
        xStream.xStream().processAnnotations(NfeProc.class);
        NfeProc nfeProc = (NfeProc) xStream.xStream().fromXML(file);
        var infXml = nfeProc.getNfe().getInfNFe();

        return infXml;
    }

        public DadosRetornoNotaFiscal salvaNotaXml(InfNFe infXml) {

        if (!fornecedorRepository.existsByCnpj(infXml.getFornecedor().getCnpj())) {
            fornecedor = fornecedorRepository.save(infXml.getFornecedor());
        }
        else{
            fornecedor = fornecedorRepository.findByCnpj(infXml.getFornecedor().getCnpj());
        }
        var nota = new NotaFiscal(infXml.getIdentifNota().getNumeroNF(), infXml.getIdentifNota().getDataEmiss(), fornecedor);
            var tabelaLancamento = tabelaLancamentoRepository.getReferenceByNome("FORNECEDOR");
            var formaPagamentoPadraNota = formaPgtoPadraoNotaRepository.getReferenceById(infXml.getPag().getDetPag().getNumPagtoTpag());
        for(Duplicata dup: infXml.getCobranca().getDuplicatas() ){
              duplicata = new Duplicata(dup.getNumParcelaDup(), dup.getDataVenc(), dup.getValorDup(), nota);
              nota.adicionaPagamento(duplicata);
              var lancamento = new Lancamento(TipoLancamento.DESPESA, tabelaLancamento, tabelaLancamento.getNome(), formaPagamentoPadraNota.getDescricaoPagamento(), dup.getValorDup(), dup.getDataVenc(), false, null);
            lancamentos.add(lancamento);
        }
           for(DetItens detItens : infXml.getDetList()){

               for (ProdDetalheNota prodD : detItens.getProdutos()){

                   produto = new Produto( prodD.getCodProd(), prodD.getCodEan(), prodD.getNomeProd(), prodD.getValorUnit(), prodD.getValorUnit().multiply(new BigDecimal("2.20").setScale(2, RoundingMode.HALF_UP)), LocalDateTime.now(), new Categoria(1L, "SEM CATEGORIA"), estoque);
                   estoque = new Estoque(prodD.getQuantidade());

                   if(!produtoRepository.existsByCodEan(prodD.getCodEan())){
                       estoqueRepository.save(estoque);
                       estoque.adicionarProduto(produto);
                       produtoRepository.save(produto);
                       itensNota = new ItensNota(prodD.getQuantidade(),nota, produto);
                       nota.adiconarItem(itensNota);
                       movimentacaoEstoqueService.registraEntradaMovimentacao(produto , TipoMovimentacao.ENTRADA, prodD.getQuantidade());
                   }
                   else {
                       produto = produtoRepository.findByCodEan(prodD.getCodEan());
                       int novaQuantidade = produto.getEstoque().getQuantidade() + prodD.getQuantidade();
                       var estoque = estoqueRepository.getReferenceById(produto.getEstoque().getId());
                       estoque.setQuantidade(novaQuantidade);
                       produto.setEstoque(estoque);
                       produto.setCustoProd(prodD.getValorUnit());
                       itensNota = new ItensNota(prodD.getQuantidade(),nota, produto);
                       nota.adiconarItem(itensNota);
                       movimentacaoEstoqueService.registraEntradaMovimentacao(produto ,TipoMovimentacao.ENTRADA, prodD.getQuantidade());
                   }
               }
           }
            for(Lancamento lancamento : lancamentos){
                lancamentoRepository.save(lancamento);
            }
            notaFiscalRepository.save(nota);
            resetaDadosNota();
            return new DadosRetornoNotaFiscal(nota);
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
        this.totalPedido = this.totalPedido.add(produto.getCustoProd().multiply(new BigDecimal(quantidade)));
        return new DadosNota(produtos, totalPedido);
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
        this.totalPedido = novoTotal;
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
        this.totalPedido = novoTotal;
    }
    public DadosNota retornaItensComQdeAlterada() {
        return new DadosNota(produtos, totalPedido);
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
        this.totalPedido = novoTotal;
    }

    public void adicionaPagamento(DadosDuplicata dadosDuplicata) {
        numeroDeParcela++;
        duplicata = new Duplicata( numeroDeParcela, dadosDuplicata.dataVenc(), dadosDuplicata.valorDup(), nota );
        duplicatas.add(duplicata);
        var tabelaLancamento = tabelaLancamentoRepository.getReferenceByNome("FORNECEDOR");
        var formaPgtoPadraoNota = formaPgtoPadraoNotaRepository.getReferenceById(dadosDuplicata.idFormaPagto());
        var lancamento = new Lancamento(TipoLancamento.DESPESA, tabelaLancamento, tabelaLancamento.getNome(), formaPgtoPadraoNota.getDescricaoPagamento(), dadosDuplicata.valorDup(), dadosDuplicata.dataVenc(), false, null);
        lancamentos.add(lancamento);
        this.totalPago = totalPago.add(dadosDuplicata.valorDup());
        criaDtoRetornoPgto(numeroDeParcela, dadosDuplicata.dataVenc(), dadosDuplicata.valorDup(), formaPgtoPadraoNota.getDescricaoPagamento());
    }
    public void criaDtoRetornoPgto(Integer numParcela, LocalDate dataVenc, BigDecimal valorDup, String descricaoPgto ) {
        var pgtoEfetuadoNota = new DadosPagamentoEfetuadoNota(numParcela, dataVenc, valorDup, descricaoPgto);
        pagamentosEfetuadosNotas.add(pgtoEfetuadoNota);
    }
    public void removerPagamento(int numParcela) {
        Iterator<Duplicata> iteratorDup = duplicatas.iterator();
        while (iteratorDup.hasNext()) {
            Duplicata dup = iteratorDup.next();
            if (dup.getNumParcelaDup() == numParcela) {
                iteratorDup.remove();
                break;
            }
        }
        Iterator<DadosPagamentoEfetuadoNota> iteratorPgto = pagamentosEfetuadosNotas.iterator();
        while (iteratorPgto.hasNext()) {
            DadosPagamentoEfetuadoNota pagamento = iteratorPgto.next();
            if (pagamento.numParcela() == numParcela) {
                iteratorPgto.remove();
                numeroDeParcela--;
                break;
            }
        }

        BigDecimal novoTotal = BigDecimal.ZERO;
        for (Duplicata dup : duplicatas) {
            novoTotal = novoTotal.add(dup.getValorDup());
        }
        this.totalPago = novoTotal;
    }



    public DadosRetornoPagamentoNota retornaValorPago() {
        return new DadosRetornoPagamentoNota(pagamentosEfetuadosNotas, totalPago);
    }
    public DadosRetornoNotaFiscal finalizaNota( DadosCabecalhoNota dadosCabecalhoNota) {
         fornecedor = fornecedorRepository.getReferenceById(dadosCabecalhoNota.idFornecedor());
         dataEmissao = dadosCabecalhoNota.data();
         numeroNota = dadosCabecalhoNota.numeroNota();
         nota = new NotaFiscal(numeroNota, dataEmissao, fornecedor);
        if (totalPago.compareTo(totalPedido) == -1) {
            throw new ValidacaoException("O valor do pagamento é menor que o valor do total da nota ");
        }
        if (nota != null && !produtos.isEmpty() && !duplicatas.isEmpty() && fornecedor != null) {
            if (totalPago.compareTo(totalPedido) == -1) {
                throw new ValidacaoException("O valor do pagamento é menor que o valor total da nota");
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
            for(Lancamento lancamento : lancamentos){
                lancamentoRepository.save(lancamento);
            }
            notaFiscalRepository.save(nota);
            resetaDadosNota();
        } else {
            throw new ValidacaoException("Você precisa adicionar o fornecedor, produto e pagamento antes de finalizar o pedido");
        }
        return new DadosRetornoNotaFiscal(nota);
    }
    public void resetaDadosNota(){
        duplicatas.clear();
        produtos.clear();
        pagamentosEfetuadosNotas.clear();
        totalPedido = BigDecimal.ZERO;
        totalPago = BigDecimal.ZERO;
        numeroDeParcela = 0;
        fornecedor = null;
        lancamentos.clear();
    }
}