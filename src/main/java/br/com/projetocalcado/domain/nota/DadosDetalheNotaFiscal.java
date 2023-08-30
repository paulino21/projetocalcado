package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.domain.xmlnota.Duplicata;
import br.com.projetocalcado.domain.fornecedor.Fornecedor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DadosDetalheNotaFiscal(
        Long id,
        Long numeroNF,
        LocalDateTime dataEmissao,
        BigDecimal valorTotal,
        @JsonIgnore
        LocalDate dataLacamento,
        @JsonIgnore
        Fornecedor fornecedor,
        @JsonIgnore
        List<ItensNota> itens,
        @JsonIgnore
        List<Duplicata> duplicatas ) {

    public DadosDetalheNotaFiscal(NotaFiscal nota) {

        this(nota.getId(), nota.getNumeroNF(), nota.getDataEmissao(), nota.getValorTotal(), nota.getDataLacamento(),
                nota.getFornecedor(), nota.getItens(), nota.getDuplicatas());

    }


}
