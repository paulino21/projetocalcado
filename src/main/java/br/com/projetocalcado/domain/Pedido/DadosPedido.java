package br.com.projetocalcado.domain.Pedido;

import java.math.BigDecimal;
import java.util.List;

public record DadosPedido(
         List<ItemDaCompra> produtos,
         BigDecimal total) {
    }
