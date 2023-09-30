package com.cardona.foroalura.modelo.respuesta;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarRespuesta(@NotNull Long id, String mensaje) {
}
