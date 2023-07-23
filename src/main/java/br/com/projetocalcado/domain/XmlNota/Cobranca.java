package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;

import java.util.List;
@Getter
@XStreamAlias("cobr")
public class Cobranca {


    @XStreamAlias("fat")
    private Fatura fatura;
    @XStreamImplicit
    private List<Duplicata> duplicatas;
}
