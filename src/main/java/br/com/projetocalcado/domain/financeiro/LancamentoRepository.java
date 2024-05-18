package br.com.projetocalcado.domain.financeiro;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository <Lancamento, Long> {
}
