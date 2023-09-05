package br.com.projetocalcado.domain.categoria;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String nome;

    public Categoria(DadosCategoria dadosCategoria) {

        this.nome = dadosCategoria.nome();
    }
    public void atualizaCategoria(DadosDetalheCategoria dadosCategoria) {
        this.id = dadosCategoria.id();
        this.nome = dadosCategoria.nome();
    }
}
