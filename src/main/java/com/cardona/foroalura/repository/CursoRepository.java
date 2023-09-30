package com.cardona.foroalura.repository;
import com.cardona.foroalura.modelo.curso.Categoria;
import com.cardona.foroalura.modelo.curso.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
	Page<Curso> findByActivoTrue(Pageable paginacion);

	@Query("""
			select c from Curso c
			where
			c.categoria=:categoria
			order by rand()
			limit 1
			""")
	Curso seleccionarCursoConCategoria(Categoria categoria);
    
	@Query("""
            select c.activo 
            from Curso c
            where c.id=:idCurso
            """)
    Boolean findActivoById(Long idCurso);
}
