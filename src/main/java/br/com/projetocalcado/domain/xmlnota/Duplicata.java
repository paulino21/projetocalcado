package br.com.projetocalcado.domain.xmlnota;

import br.com.projetocalcado.domain.nota.NotaFiscal;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private Long numParcelaDup;
    @XStreamAlias("dVenc")
    private LocalDate dataVenc;
    @XStreamAlias("vDup")
    private BigDecimal valorDup;

    public Duplicata(Long numParcelaDup, LocalDate dataVenc, BigDecimal valorDup , NotaFiscal notaFiscal ){
        this.numParcelaDup = numParcelaDup;
        this.dataVenc = dataVenc;
        this.valorDup = valorDup;
        this.notaFiscal = notaFiscal;

    }

}
