package com.cardona.foroalura.modelo.respuesta;

import java.time.LocalDateTime;

import com.cardona.foroalura.modelo.topico.Topico;
import com.cardona.foroalura.modelo.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mensaje;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
	private Topico topico;
	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
	private Usuario autor;
	private Boolean solucion = false;
	
	@Column(name = "motivo_cancelamiento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamientoRespuesta motivoCancelamiento;

	public Respuesta(String mensaje, Topico topico, Usuario autor) {
		this.mensaje = mensaje;
		this.topico = topico;
		this.fechaCreacion = LocalDateTime.now();
		this.autor = autor;
		this.solucion = false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Respuesta other = (Respuesta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	public LocalDateTime getfechaCreacion() {
		return fechaCreacion;
	}

	public void setfechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Boolean getSolucion() {
		return solucion;
	}

	public void solucionado(Boolean solucion) {
		this.solucion = true;
	}

	public void actualizarDatos(DatosActualizarRespuesta datosActualizarRespuesta) {
		if (datosActualizarRespuesta.mensaje() != null) {
			this.mensaje = datosActualizarRespuesta.mensaje();
		}
	}
	
	public void eliminar(MotivoCancelamientoRespuesta motivo) {
        this.motivoCancelamiento = motivo;
    }

}
