package br.com.projetocalcado.domain.produto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeProdContainingIgnoreCase(String nome);

    boolean existsByCodEan(String codEAN);

    Produto findByCodEan(String codEAN);
}
