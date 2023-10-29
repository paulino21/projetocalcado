package br.com.projetocalcado.domain.xmlnota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Getter
@XStreamAlias("cobr")
public class Cobranca {

    @XStreamImplicit
    private List<Duplicata> duplicatas;

}