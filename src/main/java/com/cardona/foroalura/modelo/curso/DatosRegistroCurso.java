package com.cardona.foroalura.modelo.curso;

import jakarta.validation.constraints.*;

public record DatosRegistroCurso(
	@NotBlank
    String nombre,
    @NotNull
    Categoria categoria) {
}
