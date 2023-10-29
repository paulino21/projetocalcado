package br.com.projetocalcado.domain.xmlnota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
@XStreamAlias("Nfe")
public class Nfe {

    @XStreamAlias("infNFe")
    private InfNFe infNFe;
}
