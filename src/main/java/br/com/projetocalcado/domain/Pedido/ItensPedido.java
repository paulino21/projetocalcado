package br.com.projetocalcado.domain.Pedido;

import br.com.projetocalcado.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItensPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal precoVenda;
    private int quantidade;
    private BigDecimal subTotalProd;
    @ManyToOne
    private Pedido pedido;
    @ManyToOne
    private Produto produto;
    public ItensPedido(int quantidade, Pedido pedido, Produto produto) {
        this.quantidade = quantidade;
        this.pedido = pedido;
        this.produto = produto;
        this.precoVenda = produto.getPrecoVenda();
        this.subTotalProd = getValor();
    }
    public BigDecimal getValor() {
        return precoVenda.multiply(new BigDecimal(quantidade));
    }

}
