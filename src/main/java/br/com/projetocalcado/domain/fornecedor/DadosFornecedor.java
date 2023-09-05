package br.com.projetocalcado.domain.fornecedor;

import br.com.projetocalcado.domain.xmlnota.EnderFornecedor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;


public record DadosFornecedor(
        @CNPJ
        @NotBlank
        String cnpj,
        @NotBlank
        String razaoSocial,
        @NotBlank
        String inscricaoEstadual,
        @Valid
        @NotNull
        EnderFornecedor enderFornecedor

) {
}
