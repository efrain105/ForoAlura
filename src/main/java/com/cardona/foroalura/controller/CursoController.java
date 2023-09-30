package com.cardona.foroalura.controller;
import com.cardona.foroalura.modelo.curso.*;
import com.cardona.foroalura.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.net.URI;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@PostMapping
	@Transactional
	@Operation(summary = "Registra un nuevo curso en la base de datos")
	public ResponseEntity<DatosRespuestaCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datosRegistroCurso,
															  UriComponentsBuilder uriComponentsBuilder) {
		Curso curso = cursoRepository.save(new Curso(datosRegistroCurso));
		DatosRespuestaCurso datosRespuestaCurso = new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().toString());

		URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuestaCurso);
	}

	@GetMapping
	@Operation(summary = "Obtiene el listado de cursos")
	public ResponseEntity<Page<DatosListadoCurso>> listadoCursos(@PageableDefault(size = 2) Pageable paginacion) {
		 return ResponseEntity.ok(cursoRepository.findByActivoTrue(paginacion).map(DatosListadoCurso::new));
	}

	@PutMapping
	@Transactional
	@Operation(summary = "Actualiza los datos de un curso existente")
	public ResponseEntity actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso) {
		Curso curso = cursoRepository.getReferenceById(datosActualizarCurso.id());
		curso.actualizarDatos(datosActualizarCurso);
		return ResponseEntity.ok(new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().toString()));
	}

	// DELETE LOGICO
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un curso registrado - inactivo")
    public ResponseEntity desactivarCurso(@PathVariable Long id) {
        Curso curso = cursoRepository.getReferenceById(id);
        curso.desactivarCurso();
        return ResponseEntity.noContent().build();
    }
    
    /*DELETE EN BASE DE DATOS
     public void eliminarCurso(@PathVariable Long id) {
     	Curso curso = cursoRepository.getReferenceById(id);
     	cursoRepository.delete(curso);
     }
     */

	@GetMapping("/{id}")
	@Operation(summary = "Obtiene los registros del curso con ID")
	public ResponseEntity<DatosRespuestaCurso> retornaDatosCurso(@PathVariable Long id) {
		Curso curso = cursoRepository.getReferenceById(id);
		var datosCurso = new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria().toString());
		var cursoActivo=cursoRepository.findActivoById(datosCurso.id());
		if(!cursoActivo){
			throw new ValidationException("No se pueden mostrar registros de cursos inactivos en el sistema");
		}
		return ResponseEntity.ok(datosCurso);
	}
}
