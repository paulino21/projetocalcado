package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.domain.xmlnota.Duplicata;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;


public record DadosNotaFiscal(
        @NotNull
        Long numeroNF,
        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataEmissao,
        @NotNull
        Long idFornecedor,
        @NotNull
        Long idProduto,
        @NotNull
        Integer quantidade,
        @Valid
        @NotNull
        List<Duplicata> duplicatas

) {

}


