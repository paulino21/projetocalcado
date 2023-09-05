package br.com.projetocalcado.domain.xmlnota;

import br.com.projetocalcado.domain.nota.NotaFiscal;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
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
    private Long numParcelaDup;
    @XStreamAlias("dVenc")
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate dataVenc;
    @XStreamAlias("vDup")
    @NotNull
    @Digits( integer = 10, fraction = 2)
    @Positive
    private BigDecimal valorDup;

    public Duplicata(Long numParcelaDup, LocalDate dataVenc, BigDecimal valorDup , NotaFiscal notaFiscal ){
        this.numParcelaDup = numParcelaDup;
        this.dataVenc = dataVenc;
        this.valorDup = valorDup;
        this.notaFiscal = notaFiscal;

    }

}
