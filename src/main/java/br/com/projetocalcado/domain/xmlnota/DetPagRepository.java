package br.com.projetocalcado.domain.xmlnota;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DetPagRepository extends JpaRepository < DetPag , Long> {

    DetPag findBynumPagtoTpag(Long numPagtoTpag);
}
