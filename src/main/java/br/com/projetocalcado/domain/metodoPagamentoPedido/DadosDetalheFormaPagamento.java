package br.com.projetocalcado.domain.metodoPagamentoPedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosDetalheFormaPagamento(
        @NotNull
        Long id,
        @NotBlank
        TipoPagamento tipoPagamento,
        @NotBlank
        String descricao) {

   public DadosDetalheFormaPagamento(FormaPagamento formaPagto){
       this(formaPagto.getId(), formaPagto.getTipoPagamento(), formaPagto.getDescricao());
    }
}
