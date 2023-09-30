package com.cardona.foroalura.modelo.respuesta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelamientoRespuesta(
		@NotNull
	    Long idRespuesta,
	    MotivoCancelamientoRespuesta motivo) {

}
