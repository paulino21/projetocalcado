package br.com.projetocalcado.domain.nota;

import br.com.projetocalcado.domain.Pedido.ItemDaCompra;

import java.math.BigDecimal;
import java.util.List;

public record DadosNota(
        List<ItemDaCompra> produtos,
        BigDecimal total){
}
