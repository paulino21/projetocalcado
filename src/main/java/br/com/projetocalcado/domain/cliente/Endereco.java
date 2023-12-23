package br.com.projetocalcado.domain.cliente;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Endereco {
    @NotBlank
    private  String rua;
    @NotBlank
    private String numero;
    private String complemento;
    @NotBlank
    @Pattern(regexp = "\\d{8}")
    private String cep;
    @NotBlank
    private String bairro;
    @NotBlank
    private String municipio;
    @NotBlank
    private String uf;




}
