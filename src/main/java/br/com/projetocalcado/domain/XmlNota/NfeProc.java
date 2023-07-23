package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
@Component
@XStreamAlias("nfeProc")
public class NfeProc {

    @XStreamAlias("NFe")
    private Nfe nfe;

}
