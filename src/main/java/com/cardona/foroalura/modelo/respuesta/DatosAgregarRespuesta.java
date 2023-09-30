package com.cardona.foroalura.modelo.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAgregarRespuesta(
        @NotBlank
        String mensaje,
        @NotNull
        Long idTopico,
        @NotNull
        Long idAutor) {

}
