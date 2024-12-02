package br.com.projetocalcado.domain.xmlnota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
@XStreamAlias("pag")
public class Pag {

    @XStreamAlias("detPag")
    private DetPag detPag;
}
