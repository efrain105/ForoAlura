package com.cardona.foroalura.controller;
import com.cardona.foroalura.infra.errores.ValidacionDeIntegridad;
import com.cardona.foroalura.modelo.topico.*;
import com.cardona.foroalura.repository.TopicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

	@Autowired
	private TopicoRepository topicoRepository;

	@Autowired
	private AgendaDeTopicoService service;
	
	@GetMapping
	@Operation(summary = "Obtiene el listado de topicos")
	public ResponseEntity<Page<DatosDetalleTopico>> listar (@PageableDefault(size = 10) Pageable paginacion) {
        var response = service.consultar(paginacion);
        return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@Transactional
    @Operation(
            summary = "registra un topico en la base de datos",
            description = "",
            tags = { "topico", "post" })
	public ResponseEntity agregar(@RequestBody @Valid DatosAgregarTopico datos) throws ValidacionDeIntegridad {
        var response = service.agregar(datos);
        return ResponseEntity.ok(response);
	}

	@PutMapping
	@Transactional
	@Operation(summary = "Actualiza los datos de un topico existente")
	public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
		Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
		topico.actualizarDatos(datosActualizarTopico);
		return ResponseEntity.ok(new DatosDetalleTopico(topico.getId(),topico.getTitulo(), topico.getMensaje(),
		 	topico.getfechaCreacion(), topico.getStatus(), topico.getAutor().getId(),topico.getCurso().getId()));
	}

	@DeleteMapping
    @Transactional
    @Operation(
            summary = "elimina un topico",
            description = "requiere motivo",
            tags = { "consulta", "delete" })
    public ResponseEntity eliminar(@RequestBody @Valid DatosCancelamientoTopico datos) throws ValidacionDeIntegridad {
        service.cancelar(datos);
        return ResponseEntity.noContent().build();
    }

	@GetMapping("/{id}")
	@Operation(summary = "Obtiene los registros del topico con ID")
	public ResponseEntity<DatosDetalleTopico> retornaDatosTopico(@PathVariable Long id) {
		Topico topico = topicoRepository.getReferenceById(id);
		var datosTopico = new DatosDetalleTopico(topico.getId(),topico.getTitulo(), topico.getMensaje(),
				topico.getfechaCreacion(), topico.getStatus(), topico.getAutor().getId(),topico.getCurso().getId());
		return ResponseEntity.ok(datosTopico);
	}
}
