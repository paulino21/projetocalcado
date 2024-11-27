package br.com.projetocalcado.domain.xmlnota;

import br.com.projetocalcado.domain.nota.NotaFiscal;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XStreamAlias("dup")
@Entity
public class Duplicata {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notaFiscal_id")
    private NotaFiscal notaFiscal;
    @XStreamAlias("nDup")
    @NotNull
    private Integer numParcelaDup;
    @XStreamAlias("dVenc")
    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dataVenc;
    @XStreamAlias("vDup")
    @NotNull
    @Digits( integer = 10, fraction = 2)
    @Positive
    private BigDecimal valorDup;

    public Duplicata(Integer numParcelaDup, LocalDate dataVenc, BigDecimal valorDup , NotaFiscal notaFiscal ){
        this.numParcelaDup = numParcelaDup;
        this.dataVenc = dataVenc;
        this.valorDup = valorDup;
        this.notaFiscal = notaFiscal;

    }

}
