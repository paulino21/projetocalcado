package br.com.projetocalcado.domain.financeiro;

import br.com.projetocalcado.domain.financeiro.tipoLancamento.TabelaLancamentoRepository;
import br.com.projetocalcado.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {
    @Autowired
    TabelaLancamentoRepository tabelaLancamentoRepository;
    @Autowired
    LancamentoRepository lancamentoRepository;
    public Lancamento cadastra(DadosLacamento dadosLacamento) {

        if(!tabelaLancamentoRepository.existsById(dadosLacamento.idTabLancamento())) {
            throw new ValidacaoException(" Tabela lançamento não encontrada");
        }
        var tabelaLancamento = tabelaLancamentoRepository.getReferenceById(dadosLacamento.idTabLancamento());
        var lancamento = new Lancamento(dadosLacamento.tipoLancamento(), tabelaLancamento, dadosLacamento.descricao(), dadosLacamento.formaPto(), dadosLacamento.valor(), dadosLacamento.dataVencimento(), dadosLacamento.pago(), dadosLacamento.dataPagamento());
        lancamentoRepository.save(lancamento);
        return lancamento;
    }
    public Lancamento atualiza(DadosDetalheLancamento dadosDetalheLancamento) {
        var tabLancamento = tabelaLancamentoRepository.getReferenceById(dadosDetalheLancamento.idTabLancamento());
        var lancamento = lancamentoRepository.getReferenceById(dadosDetalheLancamento.id());
        lancamento.setTipoLancamento(dadosDetalheLancamento.tipoLancamento());
        lancamento.setTabelaLancamento(tabLancamento);
        lancamento.setDescricao(dadosDetalheLancamento.descricao());
        lancamento.setFormaPgto(dadosDetalheLancamento.formaPto());
        lancamento.setValor(dadosDetalheLancamento.valor());
        lancamento.setDataVencimento(dadosDetalheLancamento.dataVencimento());
        lancamento.setPago(dadosDetalheLancamento.pago());
        lancamento.setDataPagamento(dadosDetalheLancamento.dataPagamento());
        return lancamento;
    }
}
