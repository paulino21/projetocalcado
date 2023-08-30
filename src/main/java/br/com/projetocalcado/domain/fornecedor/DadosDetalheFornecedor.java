package br.com.projetocalcado.domain.fornecedor;

import br.com.projetocalcado.domain.xmlnota.EnderFornecedor;

public record DadosDetalheFornecedor(Long id, String cnpj, String razaoSocial, EnderFornecedor enderFornecedor, String inscricaoEstadual) {

    public DadosDetalheFornecedor(Fornecedor fornecedor){
        this(fornecedor.getId(), fornecedor.getCnpj(), fornecedor.getRazaoSocial(), fornecedor.getEnderFornecedor(),fornecedor.getInscricaoEstadual());
    }
}
