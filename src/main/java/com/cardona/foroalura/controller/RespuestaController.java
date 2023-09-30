package com.cardona.foroalura.controller;
import com.cardona.foroalura.infra.errores.ValidacionDeIntegridad;
import com.cardona.foroalura.modelo.respuesta.*;
import com.cardona.foroalura.repository.RespuestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {
	
	@Autowired
    RespuestaRepository respuestaRepository;

    @Autowired
	private AgendaDeRespuestaService service;
	
	@GetMapping
	@Operation(summary = "Obtiene el listado de respuestas")
	public ResponseEntity<Page<DatosDetalleRespuesta>> listar (@PageableDefault(size = 10) Pageable paginacion) {
        var response = service.consultar(paginacion);
        return ResponseEntity.ok(response);
	}
	
	@PostMapping
	@Transactional
    @Operation(
            summary = "registra una respuesta en la base de datos",
            description = "",
            tags = { "topico", "post" })
	public ResponseEntity agregar(@RequestBody @Valid DatosAgregarRespuesta datos) throws ValidacionDeIntegridad {
        var response = service.agregar(datos);
        return ResponseEntity.ok(response);
	}

    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza el texto de una respuesta existente")
    public ResponseEntity actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta) {
        Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
        respuesta.actualizarDatos(datosActualizarRespuesta);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta.getId(), respuesta.getMensaje(),
                respuesta.getTopico().getId(), respuesta.getfechaCreacion(), respuesta.getAutor().getId()));
    }
	
	@DeleteMapping
    @Transactional
    @Operation(
            summary = "elimina una respuesta",
            description = "requiere motivo",
            tags = { "consulta", "delete" })
    public ResponseEntity eliminar(@RequestBody @Valid DatosCancelamientoRespuesta datos) throws ValidacionDeIntegridad {
        service.eliminar(datos);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/topico/{topico_id}")
    @Operation(summary = "Obtiene las respuestas con ID del topico")
    public ResponseEntity<List<DatosDetalleRespuesta>> retornaDatosRespuestaPorTopico(@PathVariable Long topico_id) {
        List<Respuesta> respuestas = respuestaRepository.findAllByTopicoId(topico_id);
        List<DatosDetalleRespuesta> datosRespuestas = new ArrayList<>();
        for (Respuesta respuesta : respuestas) {
            DatosDetalleRespuesta datosRespuesta = new DatosDetalleRespuesta(respuesta.getId(), respuesta.getMensaje(),
                    respuesta.getTopico().getId(), respuesta.getfechaCreacion(), respuesta.getAutor().getId());
            datosRespuestas.add(datosRespuesta);
        }
        return ResponseEntity.ok(datosRespuestas);
    }
}
