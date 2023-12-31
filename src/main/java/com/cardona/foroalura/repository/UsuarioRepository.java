package com.cardona.foroalura.repository;

import com.cardona.foroalura.modelo.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	UserDetails findByNombre(String username);
	
}
