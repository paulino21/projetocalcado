package br.com.projetocalcado.domain.xmlnota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
@Getter
@XStreamAlias("ide")
public class IdentifNota {

    @XStreamAlias("nNF")
    private Long numeroNF;
    @XStreamAlias("dhEmi")
    private LocalDateTime dataEmiss;


}
