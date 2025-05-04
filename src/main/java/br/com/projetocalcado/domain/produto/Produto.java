package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.categoria.Categoria;
import br.com.projetocalcado.domain.estoque.Estoque;
import br.com.projetocalcado.domain.movimentacaoEstoque.MovimentacaoEstoque;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
        private BigDecimal precoVenda;
        private LocalDateTime data = LocalDateTime.now();
        @JsonIgnore
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "categoria_id")
        private Categoria categoria;
        @JsonIgnore
        @JoinColumn(name = "estoque_id")
        @ManyToOne(cascade = CascadeType.ALL)
        private Estoque estoque;
        @JsonIgnore
        @OneToMany(mappedBy = "produto")
        private List<MovimentacaoEstoque> movimentacoes;


        public Produto(String codProd, String codEan, String nomeProd, BigDecimal custoProd, BigDecimal precoVenda, LocalDateTime data, Categoria categoria, Estoque estoque) {
                this.codProd = codProd;
                this.codEan = codEan;
                this.nomeProd = nomeProd;
                this.custoProd = custoProd;
                this.precoVenda = precoVenda;
                this.data = data;
                this.categoria = categoria;
                this.estoque = estoque;
        }

        public Produto(String codProd, String codEan, String nomeProd, BigDecimal custoProd, Estoque estoque){
                this.codProd = codProd;
                this.codEan = codEan;
                this.nomeProd = nomeProd;
                this.custoProd = custoProd;
                this.estoque = estoque;
        }

}



