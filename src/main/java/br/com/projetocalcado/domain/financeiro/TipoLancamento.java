package br.com.projetocalcado.domain.financeiro;

import br.com.projetocalcado.infra.exception.ValidacaoException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum TipoLancamento {

    DESPESA,
    RECEITA;


    @JsonCreator
    public static TipoLancamento fromString(String value) {
        for (TipoLancamento tipoLancamento : TipoLancamento.values()) {
            if (tipoLancamento.name().equalsIgnoreCase(value)) {
                return tipoLancamento;
            }
        }
        throw new ValidacaoException("Tipo lançamento inválido: " + value);
    }
}
