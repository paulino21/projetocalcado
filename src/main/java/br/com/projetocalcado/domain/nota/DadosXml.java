package br.com.projetocalcado.domain.nota;

import java.time.LocalDate;

public record DadosXml(
        Long idFornecedor,
        Long numeroNota,
        LocalDate dataEmissao
) {
}
