package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;

@Getter
@XStreamAlias("Nfe")
public class Nfe {

    @XStreamAlias("infNFe")
    private InfNFe infNFe;
}
