package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.categoria.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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


        public Produto(String codProd, String codEan, String nomeProd, BigDecimal custoProd, LocalDateTime data, Categoria categoria) {
                this.codProd = codProd;
                this.codEan = codEan;
                this.nomeProd = nomeProd;
                this.custoProd = custoProd;
                this.data = data;
                this.categoria = categoria;
        }

        public Produto( String codProd, String codEan, String nomeProd, BigDecimal custoProd){
                this.codProd = codProd;
                this.codEan = codEan;
                this.nomeProd = nomeProd;
                this.custoProd = custoProd;
        }


}



