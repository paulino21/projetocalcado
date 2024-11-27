package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.domain.fornecedor.Fornecedor;

import java.time.LocalDateTime;

public record DadosResposeCabecalhoNota(Fornecedor fornecedor, LocalDateTime data, Long numeroNota ){
}
