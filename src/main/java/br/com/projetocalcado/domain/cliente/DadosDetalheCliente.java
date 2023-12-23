package br.com.projetocalcado.domain.cliente;

import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosDetalheCliente(
        @NotNull
        Long id,
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        @NotBlank
        String telefone,
        @NotNull
        @Valid
        Endereco endereco) {

    public DadosDetalheCliente(Cliente cliente) {
        this(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getTelefone(), cliente.getEndereco());
    }
}
