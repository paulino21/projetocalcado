package br.com.projetocalcado.domain.Usuario;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastraLogin(@NotBlank
                                 String login ,
                                 @NotBlank
                                 String senha,
                                 @NotNull
                                 @Valid
                                 UserRole role   ) {
}
