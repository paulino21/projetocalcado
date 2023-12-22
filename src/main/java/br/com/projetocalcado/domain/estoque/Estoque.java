package br.com.projetocalcado.domain.estoque;

import br.com.projetocalcado.domain.nota.ItensNota;
import br.com.projetocalcado.domain.nota.NotaFiscal;
import br.com.projetocalcado.domain.produto.Produto;
import br.com.projetocalcado.domain.produto.ProdutoRepository;
import br.com.projetocalcado.domain.xmlnota.Duplicata;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.Lint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Estoque {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private Integer quantidade;

    @OneToMany(mappedBy = "estoque" , cascade = CascadeType.ALL)
    private List<Produto> produtos = new ArrayList<>();

    public Estoque(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void adicionarProduto(Produto produto) {

       produto.setEstoque(this);
       this.getProdutos().add(produto);

    }
}