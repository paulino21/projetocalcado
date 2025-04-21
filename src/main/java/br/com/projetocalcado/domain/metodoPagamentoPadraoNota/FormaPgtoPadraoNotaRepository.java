package br.com.projetocalcado.domain.metodoPagamentoPadraoNota;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FormaPgtoPadraoNotaRepository extends JpaRepository<FormaPgtoPadraoNota , Long> {
    FormaPgtoPadraoNota findByCodigo(Long numPagtoTpag);
}
