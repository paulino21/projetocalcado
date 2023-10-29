package br.com.projetocalcado.domain.xmlnota;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Embeddable
@Component
@Setter
@Getter
@XStreamAlias("EnderEmit")
public class EnderFornecedor {
    @NotBlank
    @XStreamAlias("xLgr")
    private String logradouro;
    @NotBlank
    @XStreamAlias("nro")
    private String numero;
    @NotBlank
    @XStreamAlias("xBairro")
    private String bairro;
    @NotBlank
    @XStreamAlias("xMun")
    private String municipio;
    @NotBlank
    @XStreamAlias("UF")
    private String uf;
    @NotBlank
    @Pattern(regexp = "\\d{8}")
    @XStreamAlias("CEP")
    private String cep;
    @NotBlank
    @XStreamAlias("xPais")
    private String pais;
    @NotBlank
    @XStreamAlias("fone")
    private String telefone;
}
