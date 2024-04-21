package br.com.projetocalcado.domain.metodoPagamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosDetalheFormaPagamento(
        @NotNull
        Long id,
        @NotBlank
        TipoPagamento tpoPagamento,
        @NotBlank
        String descricao) {

   public DadosDetalheFormaPagamento(FormaPagamento formaPagto){
       this(formaPagto.getId(), formaPagto.getTipoPagamento(), formaPagto.getDescricao());
    }
}
