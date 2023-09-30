package com.cardona.foroalura.repository;
import com.cardona.foroalura.modelo.respuesta.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    @Query("""
            select r from Respuesta r
            where 
            topico.id=:idTopico
            """)
    List<Respuesta> findAllByTopicoId(Long idTopico);

}