package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.domain.fornecedor.Fornecedor;
import br.com.projetocalcado.domain.xmlnota.Duplicata;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DadosDetalheNotaFiscal(
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
        @JsonIgnore
        @JsonFormat(pattern =" dd/MM/yyyy")
        @NotNull
        LocalDate dataLancamento,
        @NotNull
        @Valid
        Fornecedor fornecedor,
        @JsonIgnore
        @NotNull
        List<ItensNota> itens,
        @JsonIgnore
        @NotNull
        List<Duplicata> duplicatas ) {

    public DadosDetalheNotaFiscal(NotaFiscal nota) {

        this(nota.getId(), nota.getNumeroNF(), nota.getDataEmissao(), nota.getValorTotal(), nota.getDataLancamento(),
                nota.getFornecedor(), nota.getItens(), nota.getDuplicatas());

    }


}
