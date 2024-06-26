package br.com.projetocalcado.domain.movimentacaoEstoque;

import br.com.projetocalcado.domain.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovimentacaoEstoque {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private LocalDateTime dataMovimentacao =  LocalDateTime.now();
    private Integer QuantidadeMovimentaco;
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;
    @JoinColumn( name = "produto_id")
    @ManyToOne
    private Produto produto;


    public MovimentacaoEstoque(Produto produto ,TipoMovimentacao tipoMovimentacao, Integer quantidade) {
        this.produto = produto;
        this.tipoMovimentacao = tipoMovimentacao;
        this.QuantidadeMovimentaco = quantidade;

    }
}
