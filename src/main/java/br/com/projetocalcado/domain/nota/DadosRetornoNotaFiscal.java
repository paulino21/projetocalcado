package br.com.projetocalcado.domain.nota;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosRetornoNotaFiscal(
        @NotNull
        Long id,
        @NotNull
        Long numeroNF,
        @JsonFormat(pattern = "dd/MM/yyyy")
        @NotNull
        LocalDate dataEmissao,
        @Digits(integer = 10 , fraction = 2)
        @NotNull
        BigDecimal valorTotal,
        @JsonFormat(pattern =" dd/MM/yyyy")
        @NotNull
        LocalDate dataLancamento,
        @NotBlank
        Long idFornecedor
) {

    public DadosRetornoNotaFiscal(NotaFiscal nota) {

        this(nota.getId(), nota.getNumeroNF(), nota.getDataEmissao(), nota.getValorTotal(), nota.getDataLancamento(),
                nota.getFornecedor().getId());

    }


}
