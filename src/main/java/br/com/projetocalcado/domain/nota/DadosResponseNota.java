package br.com.projetocalcado.domain.nota;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosResponseNota(
        @NotNull
        Long id,
        @NotNull
        BigDecimal valorTotal,
        @JsonFormat(pattern =" dd/MM/yyyy")
        LocalDateTime dataEmissao) {
            public DadosResponseNota(NotaFiscal notaFiscal) {
            this(notaFiscal.getId(), notaFiscal.getValorTotal(), notaFiscal.getDataEmissao());
        }
}