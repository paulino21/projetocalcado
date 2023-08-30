package br.com.projetocalcado.domain.categoria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categoria {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String nome;


    public Categoria(DadosCategoria dadosCategoria) {

        this.nome = dadosCategoria.nome();
    }
    public void atualizaCategoria(DadosDetalheCategoria dadosCategoria) {
        this.id = dadosCategoria.id();
        this.nome = dadosCategoria.nome();
    }
}
