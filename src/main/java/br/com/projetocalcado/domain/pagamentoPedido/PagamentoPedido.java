package br.com.projetocalcado.domain.pagamentoPedido;

import br.com.projetocalcado.domain.Pedido.Pedido;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PagamentoPedido {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private LocalDateTime dataLancamento = LocalDateTime.now();
    @NotNull
    private Integer numParcela;
    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dataVenc;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    @NotBlank
    private String formaPagamento;
    @NotNull
    @Digits( integer = 10, fraction = 2)
    @Positive
    private BigDecimal valor;

    public PagamentoPedido(Integer numParcela, BigDecimal valor, LocalDate dataVenc, Pedido pedido, String formaPagamento ){
        this.numParcela = numParcela;
        this.valor = valor;
        this.dataVenc = dataVenc;
        this.pedido = pedido;
        this.formaPagamento = formaPagamento;


    }


}
