package br.com.projetocalcado.domain.metodoPagamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FormaPagamento {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long Id;
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;
    private  String descricao;

    public FormaPagamento(DadosFormaPgto dadosFormaPgto){
        this.descricao = dadosFormaPgto.descricao();
        this.tipoPagamento = dadosFormaPgto.tipoPagamento();
    }

    public void atualuzaFormaPagamento(DadosDetalheFormaPagamento dadosDetalheFormaPagamento) {
        this.descricao = dadosDetalheFormaPagamento.descricao();
        this.tipoPagamento = tipoPagamento;
    }
}
