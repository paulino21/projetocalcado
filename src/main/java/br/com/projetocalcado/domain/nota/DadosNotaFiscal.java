package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.domain.xmlnota.Duplicata;

import java.time.LocalDateTime;
import java.util.List;

public record DadosNotaFiscal(Long numeroNF, LocalDateTime dataEmissao, Long idFornecedor,
                              Long idProduto, int quantidade, List<Duplicata> duplicatas) {

}


