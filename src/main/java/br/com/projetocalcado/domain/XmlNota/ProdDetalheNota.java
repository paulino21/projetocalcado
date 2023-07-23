package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@XStreamAlias("prod")
public class ProdDetalheNota {

        @XStreamAlias("cProd")
        private String codProd;
        @XStreamAlias("cEAN")
        private String codEAN;
        @XStreamAlias("xProd")
        private String nomeProd;
        @XStreamAlias("NCM")
        private Long ncm;
        @XStreamAlias("qCom")
        private String quantidade;
        @XStreamAlias("vUnCom")
        private BigDecimal valorUnit;
        @XStreamAlias("vProd")
        private BigDecimal valorTotalProd;

}



