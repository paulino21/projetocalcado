package br.com.projetocalcado.domain.nota;

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
public class ItensNota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal precoCusto;
    private int quantidade;
    private BigDecimal subTotalProd;
    @ManyToOne
    private NotaFiscal notaFiscal;
    @ManyToOne
    private Produto produto;

    public ItensNota(int quantidade, NotaFiscal notaFiscal, Produto produto) {
        this.quantidade = quantidade;
        this.notaFiscal = notaFiscal;
        this.produto = produto;
        this.precoCusto = produto.getCustoProd().setScale(2);
        this.subTotalProd = getValor();
    }
    public BigDecimal getValor()
    {
        return precoCusto.multiply(new BigDecimal(quantidade));
    }

}
