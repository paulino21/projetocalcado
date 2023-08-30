package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.categoria.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
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

        public Produto(DadosCadastroProduto dadosProduto) {

                this.codProd = dadosProduto.codProd();
                this.codEan = dadosProduto.codEan();
                this.nomeProd = dadosProduto.nomeProd();
                this.custoProd = dadosProduto.custoProd();
                this.categoria = dadosProduto.categoria();

        }
        public Produto( String codProd, String codEan, String nomeProd, BigDecimal custoProd){
                this.codProd = codProd;
                this.codEan = codEan;
                this.nomeProd = nomeProd;
                this.custoProd = custoProd;
        }
        
        public void atualizaProduto(DadosDetalheDoproduto dadosProduto){

                this.id = dadosProduto.id();
                this.codProd = dadosProduto.codProd();
                this.codEan = dadosProduto.codEan();
                this.nomeProd = dadosProduto.nomeProd();
                this.custoProd = dadosProduto.custoProd();
                this.categoria = dadosProduto.categoria();

        }

}



