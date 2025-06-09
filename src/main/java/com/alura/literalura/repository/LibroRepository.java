package com.alura.literalura.repository;

import com.alura.literalura.model.Idioma;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTitulo(String titulo);

    List<Libro> findByIdioma(Idioma idioma);

    List<Libro> findByTituloContainingIgnoreCase(String palabraClave);

    @Query("SELECT l FROM Libro l ORDER BY l.titulo")
    List<Libro> findAllOrderByTitulo();

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma ORDER BY l.titulo")
    List<Libro> findByIdiomaOrderByTitulo(@Param("idioma") Idioma idioma);

    @Query("SELECT l FROM Libro l ORDER BY l.numeroDescargas DESC")
    List<Libro> findTop10ByOrderByNumeroDescargasDesc();
}