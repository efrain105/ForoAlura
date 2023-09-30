package com.cardona.foroalura.modelo.topico;
import com.cardona.foroalura.modelo.curso.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosAgregarTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        Long idAutor,
        Long idCurso,

        Categoria categoria) {

}
