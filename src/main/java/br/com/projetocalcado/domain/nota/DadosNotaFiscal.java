package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.domain.xmlnota.Duplicata;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

public record DadosNotaFiscal(
        @NotNull
        Long numeroNF,
        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dataEmissao,
        @NotNull
        Long idFornecedor,
        @NotNull
        Long idProduto,
        @NotNull
        int quantidade,
        @Valid
        @NotNull
        List<Duplicata> duplicatas
) {

}


