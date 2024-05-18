package br.com.projetocalcado.domain.financeiro.tipoLancamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosDetalheTabelaLancamento(
        @NotNull
        Long id,
        @NotBlank
        String nome
       ) {
       public DadosDetalheTabelaLancamento(TabelaLancamento tabelaLancamento){

              this(tabelaLancamento.getId(), tabelaLancamento.getNome());
       }

}
