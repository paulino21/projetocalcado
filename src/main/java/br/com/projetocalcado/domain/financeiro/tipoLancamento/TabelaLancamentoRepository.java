package br.com.projetocalcado.domain.financeiro.tipoLancamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TabelaLancamentoRepository extends JpaRepository <TabelaLancamento, Long> {
    TabelaLancamento getReferenceByNome(String Nome);
}
