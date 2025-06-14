package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE " +
            "(a.fechaNacimiento IS NULL OR a.fechaNacimiento <= :ano) AND " +
            "(a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :ano)")
    List<Autor> autoresVivosEnAno(@Param("ano") Integer ano);

    @Query("SELECT a FROM Autor a ORDER BY a.nombre")
    List<Autor> findAllOrderByNombre();
}
