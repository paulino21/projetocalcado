package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@XStreamAlias("fat")
public class Fatura {

    @XStreamAlias("nFat")
    private Long numFatura;
    @XStreamAlias("vOrig")
    private BigDecimal valorOriginal;
    @XStreamAlias("vDesc")
    private BigDecimal valorDesc;
    @XStreamAlias("vLiq")
    private  BigDecimal valorLiq;

}
