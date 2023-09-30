package com.cardona.foroalura.modelo.topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.cardona.foroalura.modelo.curso.Curso;
import com.cardona.foroalura.modelo.respuesta.Respuesta;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Table(name = "topicos")
@Entity(name = "Topico")
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensaje;
	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	
	@Column(name = "status_topico")
	@Enumerated(EnumType.STRING)
	private StatusTopico status = StatusTopico.NO_RESPONDIDO;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
	private Usuario autor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curso_id")
	private Curso curso;
	
	@Column(name = "motivo_cancelamiento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivoCancelamiento;
	
	@OneToMany
	private List<Respuesta> respuestas = new ArrayList<>();

	public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.fechaCreacion = LocalDateTime.now();
		this.status = StatusTopico.NO_RESPONDIDO;
		this.autor = autor;
		this.curso = curso;
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
		Topico other = (Topico) obj;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getfechaCreacion() {
		return fechaCreacion;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public void setStatus(StatusTopico status) {
		this.status = status;
	}

	public Usuario getAutor() {
		return autor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Respuesta> getRespuestas() {
		return this.respuestas;
	}

	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
		if (datosActualizarTopico.titulo() != null) {
			this.titulo = datosActualizarTopico.titulo();
		}
		if (datosActualizarTopico.mensaje() != null) {
			this.mensaje = datosActualizarTopico.mensaje();
		}
	}
	
	public void cancelar(MotivoCancelamiento motivo) {
        this.motivoCancelamiento = motivo;
    }

}
