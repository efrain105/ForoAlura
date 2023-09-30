package com.cardona.foroalura.modelo.topico.validaciones;
import com.cardona.foroalura.modelo.topico.DatosAgregarTopico;
import com.cardona.foroalura.repository.TopicoRepository;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;

@Component
public class TopicoMismoTitulo implements ValidadorDeTopicos {

	private TopicoRepository repository;
	
	public void validar(DatosAgregarTopico datos) {
        if(datos.idCurso()==null)
            return;

        var topicoMismoTitulo = repository.existsByTitulo(datos.titulo());
        if(topicoMismoTitulo){
            throw new ValidationException("ya hay un tópico con el mismo título");
        }
    }
}
