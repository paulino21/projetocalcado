package br.com.projetocalcado.domain.financeiro;

import br.com.projetocalcado.domain.financeiro.tipoLancamento.TabelaLancamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lancamento{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoLancamento tipoLancamento;
    @JoinColumn( name = "tabelaLancamento_id")
    @JsonIgnore
    @ManyToOne
    private TabelaLancamento tabelaLancamento;
    private String descricao;
    private String formaPgto;
    private BigDecimal valor;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataVencimento;
    private boolean pago;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPagamento;

    public Lancamento(TipoLancamento tipoLancamento, TabelaLancamento tabelaLancamento, String descricao, String formaPgto, BigDecimal valor, LocalDate dataVencimento, Boolean pago, LocalDate dataPagamento) {
        this.tipoLancamento = tipoLancamento;
        this.tabelaLancamento = tabelaLancamento;
        this.descricao =  descricao;
        this.formaPgto = formaPgto;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.pago = pago;
        this.dataPagamento = dataPagamento;
    }
}

