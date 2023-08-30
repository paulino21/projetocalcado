package br.com.projetocalcado.domain.fornecedor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor , Long > {
    boolean existsByCnpj(String cnpj);
    Fornecedor findByCnpj(String cnpj);
}
