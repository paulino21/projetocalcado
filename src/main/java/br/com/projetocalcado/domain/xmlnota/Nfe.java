package br.com.projetocalcado.domain.xmlnota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;

@Getter
@XStreamAlias("Nfe")
public class Nfe {

    @XStreamAlias("infNFe")
    private InfNFe infNFe;
}
