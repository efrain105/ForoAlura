package com.cardona.foroalura.modelo.respuesta;
import com.cardona.foroalura.infra.errores.ValidacionDeIntegridad;
import com.cardona.foroalura.repository.CursoRepository;
import com.cardona.foroalura.repository.RespuestaRepository;
import com.cardona.foroalura.repository.TopicoRepository;
import com.cardona.foroalura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("all")
public class AgendaDeRespuestaService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;

    private List<ValidadorDeRespuestas> validadores = new ArrayList<>();

    private List<ValidadorCancelamientoDeRespuesta> validadoresCancelamiento = new ArrayList<>();

    public DatosDetalleRespuesta agregar(DatosAgregarRespuesta datos){

        if(!usuarioRepository.findById(datos.idAutor()).isPresent()){
            throw new ValidacionDeIntegridad("este id para el autor no fue encontrado");
        }
        
        if(datos.idTopico()!=null && !topicoRepository.existsById(datos.idTopico())){
            throw new ValidacionDeIntegridad("este id para el topico no fue encontrado");
        }

        validadores.forEach(v-> v.validar(datos));

        var topico = topicoRepository.findById(datos.idTopico()).get();

        var autor = usuarioRepository.findById(datos.idAutor()).get();

        if(topico==null){
            throw new ValidacionDeIntegridad("no existen respuestas disponibles del tópico");
        }

        var respuesta = new Respuesta(datos.mensaje(),topico,autor);

        respuestaRepository.save(respuesta);

        return new DatosDetalleRespuesta(respuesta);

    }

	public void eliminar(DatosCancelamientoRespuesta datos) {
        if(!respuestaRepository.existsById(datos.idRespuesta())){
            throw new ValidacionDeIntegridad("Id de respuesta introducido no existe");
        }

        validadoresCancelamiento.forEach(v-> v.validar(datos));

        var respuesta = respuestaRepository.getReferenceById(datos.idRespuesta());
        respuesta.eliminar(datos.motivo());
    }

    public Page<DatosDetalleRespuesta> consultar(Pageable paginacion){
        return respuestaRepository.findAll(paginacion).map(DatosDetalleRespuesta::new);
    }
}