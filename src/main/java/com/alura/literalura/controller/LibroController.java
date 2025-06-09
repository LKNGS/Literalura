package com.alura.literalura.controller;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    @Autowired
    public LibroController(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @PostMapping
    public Libro crearLibro(@RequestBody Libro libro) {
        // Buscar si el autor ya existe
        Autor autor = libro.getAutor();
        Autor autorExistente = autorRepository.findByNombre(autor.getNombre())
                .orElse(null); // Si no existe, se creará uno nuevo

        if (autorExistente != null) {
            // Si el autor existe, reutilízalo
            libro.setAutor(autorExistente);
        } else {
            // Si no existe, guarda el nuevo autor
            autor = autorRepository.save(autor);
            libro.setAutor(autor);
        }

        return libroRepository.save(libro);
    }
}