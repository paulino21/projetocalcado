package br.com.projetocalcado.domain.Pedido;

import br.com.projetocalcado.domain.cliente.Cliente;
import br.com.projetocalcado.domain.cliente.ClienteRepository;
import br.com.projetocalcado.domain.cliente.DadosDetalheCliente;
import br.com.projetocalcado.domain.estoque.EstoqueRepository;
import br.com.projetocalcado.domain.financeiro.Lancamento;
import br.com.projetocalcado.domain.financeiro.LancamentoRepository;
import br.com.projetocalcado.domain.financeiro.LancamentoService;
import br.com.projetocalcado.domain.financeiro.TipoLancamento;
import br.com.projetocalcado.domain.financeiro.tipoLancamento.TabelaLancamentoRepository;
import br.com.projetocalcado.domain.metodoPagamento.FormaPagamentoRepository;
import br.com.projetocalcado.domain.metodoPagamento.TipoPagamento;
import br.com.projetocalcado.domain.movimentacaoEstoque.MovimentacaoEstoqueService;
import br.com.projetocalcado.domain.movimentacaoEstoque.TipoMovimentacao;
import br.com.projetocalcado.domain.pagamentoPedido.DadosPagamento;
import br.com.projetocalcado.domain.pagamentoPedido.DadosPagamentoEfetuado;
import br.com.projetocalcado.domain.pagamentoPedido.DadosRetornoPagamento;
import br.com.projetocalcado.domain.pagamentoPedido.PagamentoPedido;
import br.com.projetocalcado.domain.produto.ProdutoRepository;
import br.com.projetocalcado.infra.exception.ValidacaoException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    LancamentoService lancamentoService;
    @Autowired
    TabelaLancamentoRepository tabelaLancamentoRepository;
    private Cliente cliente;
    private PagamentoPedido pagamentoPedido;
    public List<ItemDaCompra> produtos = new ArrayList<>();
    public List<PagamentoPedido> pagamentos = new ArrayList<>();
    public List<Lancamento> lancamentos = new ArrayList<>();
    private BigDecimal totalPedido = BigDecimal.ZERO;
    @Getter
    private BigDecimal totalPago = BigDecimal.ZERO;
    private BigDecimal subTotal = BigDecimal.ZERO;
    private int numpagto = 0;
    public List<DadosPagamentoEfetuado> pagamentosEfetuados = new ArrayList<>();
    @Autowired
    MovimentacaoEstoqueService movimentacaoEstoqueService;

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    EstoqueRepository estoqueRepository;

    public DadosDetalheCliente buscaCliente(Long id) {
        cliente = clienteRepository.getReferenceById(id);
        return new DadosDetalheCliente(cliente);
    }

    public DadosPedido adicionaProdutoPedido(String ean, Integer quantidade) {

        var produto = produtoRepository.findByCodEan(ean);
        boolean produtoJaExistente = false;
        for (ItemDaCompra itemDaCompra : produtos) {
            if (itemDaCompra.getProduto().getId() == produto.getId()) {
                itemDaCompra.setQuantidade(itemDaCompra.getQuantidade() + quantidade);
                itemDaCompra.setSubTotalProd(itemDaCompra.getSubTotalProd().add(produto.getPrecoVenda().multiply(new BigDecimal(quantidade))));
                produtoJaExistente = true;
                break;
            }
        }
        if (!produtoJaExistente) {
            var item = new ItemDaCompra(quantidade, produto);
            produtos.add(item);
        }
        this.totalPedido = this.totalPedido.add(produto.getPrecoVenda().multiply(new BigDecimal(quantidade)));
        return new DadosPedido(produtos, totalPedido);
    }

    public void alteraQuantidadeItens(Long id, String acao) {

        for (ItemDaCompra itemDaCompra : produtos) {
            if (itemDaCompra.getProduto().getId() == id && acao.equals("+")) {
                itemDaCompra.setQuantidade(itemDaCompra.getQuantidade() + 1);
            } else if (itemDaCompra.getProduto().getId() == id && acao.equals("-")) {
                itemDaCompra.setQuantidade(itemDaCompra.getQuantidade() - 1);
                if (itemDaCompra.getQuantidade() <= 0) {
                    produtos.remove(itemDaCompra);
                }
            }
        }
    }

    public void removeProduto(Long id) {
        for (ItemDaCompra itemDaCompra : produtos) {
            if (itemDaCompra.getProduto().getId() == id) {
                produtos.remove(itemDaCompra);
            }
        }
    }

    public void adicionaPagamento(DadosPagamento dadosPagamento) {

        var formaPgamanto = formaPagamentoRepository.getReferenceById(dadosPagamento.idPagamento());

        if (formaPgamanto.getTipoPagamento().equals(TipoPagamento.CREDITO) && dadosPagamento.numParcela() == 1) {
            var pagto = new PagamentoPedido(dadosPagamento.numParcela(), dadosPagamento.valor(), LocalDate.now().plusMonths(1), new Pedido(cliente), formaPgamanto.getDescricao());
            pagamentos.add(pagto);
            var tabelaLancamento = tabelaLancamentoRepository.getReferenceByNome("PEDIDO");
            var lancamento = new Lancamento(TipoLancamento.RECEITA, tabelaLancamento, "PEDIDO", formaPgamanto.getDescricao(), dadosPagamento.valor(), LocalDate.now().plusMonths(1), false, null);
            lancamentos.add(lancamento);
        } else if (formaPgamanto.getTipoPagamento().equals(TipoPagamento.CREDITO) && dadosPagamento.numParcela() > 1) {
            var valorDaParcela = dadosPagamento.valor().divide(BigDecimal.valueOf(dadosPagamento.numParcela()), 2, RoundingMode.HALF_UP);
            for (int i = 1; i <= dadosPagamento.numParcela(); i++) {
                LocalDate vencimento = LocalDate.now().plusMonths(i);
                var pagto = new PagamentoPedido(i, valorDaParcela, vencimento, new Pedido(cliente), formaPgamanto.getDescricao());
                pagamentos.add(pagto);
                var tabelaLancamento = tabelaLancamentoRepository.getReferenceByNome("PEDIDO");
                var lancamento = new Lancamento(TipoLancamento.RECEITA, tabelaLancamento, "PEDIDO", formaPgamanto.getDescricao(), valorDaParcela, vencimento, false, null);
                lancamentos.add(lancamento);
            }
        } else if (formaPgamanto.getTipoPagamento().equals(TipoPagamento.PIX) && dadosPagamento.numParcela() == 1) {
            var pagto = new PagamentoPedido(dadosPagamento.numParcela(), dadosPagamento.valor(), LocalDate.now(), new Pedido(cliente), formaPgamanto.getDescricao());
            pagamentos.add(pagto);
            var tabelaLancamento = tabelaLancamentoRepository.getReferenceByNome("PEDIDO");
            var lancamento = new Lancamento(TipoLancamento.RECEITA, tabelaLancamento, "PEDIDO", formaPgamanto.getDescricao(), dadosPagamento.valor(), LocalDate.now(), true, LocalDate.now());
            lancamentos.add(lancamento);
        } else if (formaPgamanto.getTipoPagamento().equals(TipoPagamento.DEBITO) && dadosPagamento.numParcela() == 1) {
            var pagto = new PagamentoPedido(dadosPagamento.numParcela(), dadosPagamento.valor(), LocalDate.now(), new Pedido(cliente), formaPgamanto.getDescricao());
            pagamentos.add(pagto);
            var tabelaLancamento = tabelaLancamentoRepository.getReferenceByNome("PEDIDO");
            var lancamento = new Lancamento(TipoLancamento.RECEITA, tabelaLancamento, "PEDIDO", formaPgamanto.getDescricao(), dadosPagamento.valor(), LocalDate.now(), true, LocalDate.now());
            lancamentos.add(lancamento);
        } else if (formaPgamanto.getTipoPagamento().equals(TipoPagamento.DINHEIRO) && dadosPagamento.numParcela() == 1) {
            var pagto = new PagamentoPedido(dadosPagamento.numParcela(), dadosPagamento.valor(), LocalDate.now(), new Pedido(cliente), formaPgamanto.getDescricao());
            pagamentos.add(pagto);
            var tabelaLancamento = tabelaLancamentoRepository.getReferenceByNome("PEDIDO");
            var lancamento = new Lancamento(TipoLancamento.RECEITA, tabelaLancamento, "PEDIDO", formaPgamanto.getDescricao(), dadosPagamento.valor(), LocalDate.now(), true, LocalDate.now());
            lancamentos.add(lancamento);
        }
        this.totalPago = this.totalPago.add(dadosPagamento.valor());
        criaDtoRetornoPgto(dadosPagamento.numParcela(), formaPgamanto.getDescricao(), dadosPagamento.valor());
    }

    public void criaDtoRetornoPgto(Integer numParcela, String formaPagamento, BigDecimal valor) {
        numpagto++;
        var pgtoEfetuado = new DadosPagamentoEfetuado(numpagto, numParcela, formaPagamento, valor);
        pagamentosEfetuados.add(pgtoEfetuado);
    }

    public DadosRetornoPagamento retornaValorPago() {
        return new DadosRetornoPagamento(pagamentosEfetuados, totalPago);
    }

    public DadosResponsePedido finalizaPedido() {

        var pedido = new Pedido(cliente);
        if (cliente != null && !produtos.isEmpty() && !pagamentos.isEmpty()) {
            if (totalPago.compareTo(totalPedido) == -1) {
                throw new ValidacaoException("O valor do pagamento é menor que o valor do pedido ");
            }
            for (ItemDaCompra item : produtos) {
                int novaQuantidade = item.getProduto().getEstoque().getQuantidade() - item.getQuantidade();
                var estoque = estoqueRepository.getReferenceById(item.getProduto().getEstoque().getId());
                estoque.setQuantidade(novaQuantidade);
                var itemPedido = new ItensPedido(item.getQuantidade(), pedido, item.getProduto());
                pedido.adiconarItem(itemPedido);
                movimentacaoEstoqueService.registraEntradaMovimentacao(item.getProduto(), TipoMovimentacao.SAIDA, item.getQuantidade());
            }
            for (PagamentoPedido pagto : pagamentos) {
                pedido.adicionaPagamentoPedido(pagto);
            }
           for(Lancamento lancamento : lancamentos){
               lancamentoRepository.save(lancamento);


            }
            pedidoRepository.save(pedido);
            produtos.clear();
            pagamentos.clear();
            pagamentosEfetuados.clear();
            totalPedido = BigDecimal.ZERO;
            totalPago = BigDecimal.ZERO;
            subTotal = BigDecimal.ZERO;
            cliente = null;
            numpagto = 0;
            lancamentos.clear();
        } else {
            throw new ValidacaoException("Você precisa adicionar cliente, produto e pagamento antes de finalizar o pedido");
        }
        return new DadosResponsePedido(pedido);
    }

}
