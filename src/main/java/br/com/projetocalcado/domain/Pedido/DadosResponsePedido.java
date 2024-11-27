package br.com.projetocalcado.domain.Pedido;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosResponsePedido(
        Long id,
        BigDecimal valorTotal,
        @JsonFormat(pattern =" dd/MM/yyyy")
        LocalDate dataPedido) {
       public DadosResponsePedido(Pedido pedido) {
           this(pedido.getId(), pedido.getValorTotal(), pedido.getDataPedido());
        }
}