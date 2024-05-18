package br.com.projetocalcado.domain.financeiro.tipoLancamento;

import br.com.projetocalcado.domain.financeiro.Lancamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TabelaLancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @OneToMany(mappedBy = "tabelaLancamento")
    private List<Lancamento> lancamentos;

    public TabelaLancamento(DadosTabelaLancamento dadosTabelaLancamento) {
        this.nome = dadosTabelaLancamento.nome();
    }

    public void atualizaCategoria(DadosDetalheTabelaLancamento dadosDetalheTabelaLancamento) {
        this.nome = dadosDetalheTabelaLancamento.nome();
    }
}
