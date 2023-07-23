package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@XStreamAlias("dup")
public class Duplicata {

    @XStreamAlias("nDup")
    private Long numParcelaDup;
    @XStreamAlias("dVenc")
    private LocalDate dataVenc;
    @XStreamAlias("vDup")
    private BigDecimal valorDup;
}
