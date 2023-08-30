package br.com.projetocalcado.domain.fornecedor;

import br.com.projetocalcado.domain.xmlnota.EnderFornecedor;



public record DadosFornecedor(String cnpj, String razaoSocial, EnderFornecedor enderFornecedor, String inscricaoEstadual ) {
}
