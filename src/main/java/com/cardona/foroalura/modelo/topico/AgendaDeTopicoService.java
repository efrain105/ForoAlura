package com.cardona.foroalura.modelo.topico;
import com.cardona.foroalura.infra.errores.ValidacionDeIntegridad;
import com.cardona.foroalura.modelo.curso.Curso;
import com.cardona.foroalura.modelo.topico.validaciones.ValidadorCancelamientoDeTopico;
import com.cardona.foroalura.modelo.topico.validaciones.ValidadorDeTopicos;
import com.cardona.foroalura.repository.CursoRepository;
import com.cardona.foroalura.repository.TopicoRepository;
import com.cardona.foroalura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
@SuppressWarnings("all")
public class AgendaDeTopicoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    private List<ValidadorDeTopicos> validadores = new ArrayList<>();

    private List<ValidadorCancelamientoDeTopico> validadoresCancelamiento = new ArrayList<>();

    public DatosDetalleTopico agregar(DatosAgregarTopico datos){

        if(!usuarioRepository.findById(datos.idAutor()).isPresent()){
            throw new ValidacionDeIntegridad("este id para el usuario no fue encontrado");
        }

        if(datos.idCurso()!=null && !cursoRepository.existsById(datos.idCurso())){
            throw new ValidacionDeIntegridad("este id para el curso no fue encontrado");
        }

        validadores.forEach(v-> v.validar(datos));

        var autor = usuarioRepository.findById(datos.idAutor()).get();

        var curso = cursoRepository.findById(datos.idCurso()).get();

        if(curso==null){
            throw new ValidacionDeIntegridad("no existen topicos disponibles de este curso");
        }

        var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);

        topicoRepository.save(topico);

        return new DatosDetalleTopico(topico);

    }

    public void cancelar(DatosCancelamientoTopico datos) {
        if(!topicoRepository.existsById(datos.idTopico())){
            throw new ValidacionDeIntegridad("Id del topico introducido no existe");
        }

        validadoresCancelamiento.forEach(v-> v.validar(datos));

        var topico = topicoRepository.getReferenceById(datos.idTopico());
        topico.cancelar(datos.motivo());
    }

    private Curso seleccionarCurso(DatosAgregarTopico datos) {
        if(datos.idCurso() != null){
            return cursoRepository.getReferenceById(datos.idCurso());
        }
        if(datos.categoria() == null){
            throw new ValidacionDeIntegridad("debe seleccionarse una categoria para el curso");
        }
        return cursoRepository.seleccionarCursoConCategoria(datos.categoria());
    }

    public Page<DatosDetalleTopico> consultar(Pageable paginacion){
        return topicoRepository.findAll(paginacion).map(DatosDetalleTopico::new);
    }
}