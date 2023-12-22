package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.categoria.Categoria;
import br.com.projetocalcado.domain.estoque.Estoque;
import br.com.projetocalcado.domain.movimentacaoEstoque.MovimentacaoEstoque;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String codProd;
        private String codEan;
        private String nomeProd;
        private BigDecimal custoProd;
        private LocalDateTime data = LocalDateTime.now();
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "categoria_id")
        private Categoria categoria;
        @JoinColumn(name = "estoque_id")
        @ManyToOne
        private Estoque estoque;
        @OneToMany(mappedBy = "produto")
        private List<MovimentacaoEstoque> movimentacoes;


        public Produto(String codProd, String codEan, String nomeProd, BigDecimal custoProd, LocalDateTime data, Categoria categoria) {
                this.codProd = codProd;
                this.codEan = codEan;
                this.nomeProd = nomeProd;
                this.custoProd = custoProd;
                this.data = data;
                this.categoria = categoria;
        }

        public Produto(String codProd, String codEan, String nomeProd, BigDecimal custoProd, Estoque estoque){
                this.codProd = codProd;
                this.codEan = codEan;
                this.nomeProd = nomeProd;
                this.custoProd = custoProd;
                this.estoque = estoque;
        }

}



