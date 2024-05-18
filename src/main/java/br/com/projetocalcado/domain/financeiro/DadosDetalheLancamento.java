package br.com.projetocalcado.domain.financeiro;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalheLancamento(
        @NotNull
        Long id,
        @NotNull
        TipoLancamento tipoLancamento,
        @NotNull
        Long idTabLancamento,
        @NotBlank
        String descricao,
        @NotBlank
        String formaPto,
        @NotNull
        @Positive
        @Digits(integer = 10 , fraction = 2)
        BigDecimal valor,

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataVencimento,
        @NotNull
        boolean pago,
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataPagamento
    ) {


    public DadosDetalheLancamento(Lancamento lancamento) {
        this(lancamento.getId(), lancamento.getTipoLancamento(), lancamento.getTabelaLancamento().getId(), lancamento.getDescricao(), lancamento.getFormaPgto(), lancamento.getValor(), lancamento.getDataVencimento(), lancamento.isPago(), lancamento.getDataPagamento());
    }
}
