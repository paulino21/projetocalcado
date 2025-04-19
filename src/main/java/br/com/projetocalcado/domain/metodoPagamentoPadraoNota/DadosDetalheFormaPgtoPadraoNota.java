package br.com.projetocalcado.domain.metodoPagamentoPadraoNota;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosDetalheFormaPgtoPadraoNota(
        @NotNull
        Long id,
        @NotNull
        Long codigo,
        @NotBlank
        String descricaoPagamento) {

   public DadosDetalheFormaPgtoPadraoNota(FormaPgtoPadraoNota formaPgtoPadraoNota){
       this(formaPgtoPadraoNota.getId(), formaPgtoPadraoNota.getCodigo(), formaPgtoPadraoNota.getDescricaoPagamento());
    }
}
