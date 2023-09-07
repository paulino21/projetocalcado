package br.com.projetocalcado.domain.fornecedor;

import br.com.projetocalcado.domain.xmlnota.EnderFornecedor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

public record DadosDetalheFornecedor(
        @NotNull
        Long id,
        @NotBlank
        @CNPJ
        String cnpj,
        @NotBlank
        String razaoSocial,
        @NotNull
        @Valid
        EnderFornecedor enderFornecedor,
        @NotBlank
        String inscricaoEstadual) {

    public DadosDetalheFornecedor(Fornecedor fornecedor){
        this(fornecedor.getId(), fornecedor.getCnpj(), fornecedor.getRazaoSocial(), fornecedor.getEnderFornecedor(),fornecedor.getInscricaoEstadual());
    }
}
