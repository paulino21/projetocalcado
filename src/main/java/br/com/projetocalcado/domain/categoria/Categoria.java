package br.com.projetocalcado.domain.categoria;

import br.com.projetocalcado.domain.produto.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categoria {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull
    private String nome;
    @OneToMany(mappedBy = "categoria")
    private List<Produto> produto;

    public Categoria(DadosCategoria dadosCategoria) {
        this.nome = dadosCategoria.nome();
    }

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void atualizaCategoria(DadosDetalheCategoria dadosCategoria) {
        this.nome = dadosCategoria.nome();
    }
}
