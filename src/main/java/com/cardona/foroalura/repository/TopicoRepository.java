package com.cardona.foroalura.repository;

import com.cardona.foroalura.modelo.curso.Curso;
import com.cardona.foroalura.modelo.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Boolean existsByCursoAndFechaCreacion(Curso curso, LocalDateTime fechaCreacion);
    
    Boolean existsByTitulo(String titulo);
}