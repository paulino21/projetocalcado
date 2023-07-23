package br.com.projetocalcado.domain.XmlNota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
@Embeddable
@Setter
@Getter
@XStreamAlias("EnderEmit")
public class EnderFornecedor {

    @XStreamAlias("xLgr")
    private String logradouro;
    @XStreamAlias("nro")
    private Long numero;
    @XStreamAlias("xBairro")
    private String bairro;
    @XStreamAlias("xMun")
    private String municipio;
    @XStreamAlias("UF")
    private String uf;
    @XStreamAlias("CEP")
    private Long cep;
    @XStreamAlias("xPais")
    private String pais;
    @XStreamAlias("fone")
    private Long telefone;
}
