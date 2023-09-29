package br.com.projetocalcado.domain.produto;

import br.com.projetocalcado.domain.categoria.Categoria;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
public record DadosCadastroProduto(
        @NotBlank
        String codProd,
        @NotBlank
        String codEan,
        @NotBlank
        String nomeProd,
        @NotNull
        @Positive
        @Digits( integer = 10, fraction = 2)
        BigDecimal custoProd,
        @NotNull
        @Valid
        Categoria categoria

) {

}
