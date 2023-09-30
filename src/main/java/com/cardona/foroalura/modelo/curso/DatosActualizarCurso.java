package com.cardona.foroalura.modelo.curso;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(@NotNull Long id, String nombre) {
}