package br.com.projetocalcado.domain.Pedido;

import br.com.projetocalcado.domain.produto.Produto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class ItemDaCompra {

    private Integer quantidade;
    private Produto produto;
    private BigDecimal subTotalProd;

    ItemDaCompra( Integer quantidade, Produto produto){
        this.quantidade = quantidade;
        this.produto = produto;
        this.subTotalProd = produto.getPrecoVenda().multiply(new BigDecimal(quantidade));
    }

}
