package br.com.projetocalcado.domain.xmlnota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@Setter
@Getter
@XStreamAlias("prod")
public class ProdDetalheNota {

        @XStreamAlias("cProd")
        private String codProd;
        @XStreamAlias("cEAN")
        private String codEan;
        @XStreamAlias("xProd")
        private String nomeProd;
        @XStreamAlias("NCM")
        private Long ncm;
        @XStreamAlias("qCom")
        private int quantidade;
        @XStreamAlias("vUnCom")
        private BigDecimal valorUnit;
        @XStreamAlias("vProd")
        private BigDecimal valorTotalProd;

}



