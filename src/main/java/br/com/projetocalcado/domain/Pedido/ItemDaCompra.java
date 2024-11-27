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
    private BigDecimal subTotalProdVenda;
    private BigDecimal subTotalProdCompra;

    public ItemDaCompra( Integer quantidade, Produto produto){
        this.quantidade = quantidade;
        this.produto = produto;
        this.subTotalProdVenda = produto.getPrecoVenda().multiply(new BigDecimal(quantidade));
        this.subTotalProdCompra = produto.getCustoProd().multiply(new BigDecimal(quantidade));

    }

}
