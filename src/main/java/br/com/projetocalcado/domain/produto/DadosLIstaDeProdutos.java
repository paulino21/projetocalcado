package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.Pedido.ItemDaCompra;

import java.util.List;

public record DadosLIstaDeProdutos(
        List<ItemDaCompra> produtos
) {

}
