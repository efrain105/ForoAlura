package com.cardona.foroalura.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException {
	public ValidacionDeIntegridad(String s) {
        super(s);
    }
}
