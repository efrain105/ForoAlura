package com.cardona.foroalura.modelo.topico.validaciones;
import com.cardona.foroalura.modelo.topico.DatosAgregarTopico;
import com.cardona.foroalura.repository.CursoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoActivo implements ValidadorDeTopicos{
    @Autowired
    private CursoRepository repository;

    public void validar(DatosAgregarTopico datos) {
        if(datos.idCurso()==null){
            return;
        }
        var cursoActivo = repository.findActivoById(datos.idCurso());
        if(!cursoActivo){
            throw new ValidationException("No se puede agregar topicos de cursos inactivos en el sistema");
        }
    }
}