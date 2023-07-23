package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
@Getter
@XStreamAlias("ide")
public class IdentifNota {

    @XStreamAlias("nNF")
    private Long numeroNF;
    @XStreamAlias("dhEmi")
    private String dataHoraEmiss;


}
