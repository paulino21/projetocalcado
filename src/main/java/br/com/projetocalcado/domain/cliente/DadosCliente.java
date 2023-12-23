package br.com.projetocalcado.domain.cliente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCliente(
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        @NotBlank
        String telefone,
        @NotNull
        @Valid
        Endereco endereco) {

    DadosCliente(Cliente cliente){

        this(cliente.getNome(), cliente.getCpf(), cliente.getTelefone(), cliente.getEndereco());
    }
}
