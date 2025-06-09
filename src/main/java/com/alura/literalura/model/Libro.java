package com.alura.literalura.model;

import com.alura.literalura.dto.DatosLibro;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    @JsonProperty("descargas")
    private Integer numeroDescargas;

    public Libro(DatosLibro datosLibro) {}
    public Libro() {
        // Constructor vacío requerido por Jackson
    }

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    public Idioma getIdioma() { return idioma; }

    // Permite que Jackson asigne el valor recibido en el JSON (ej: "es", "en", etc.)
    @JsonProperty("idioma")
    public void setIdioma(String idiomaStr) {
        this.idioma = Idioma.fromString(idiomaStr);
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDescargas() { return numeroDescargas; }
    public void setNumeroDescargas(Integer numeroDescargas) { this.numeroDescargas = numeroDescargas; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Libro libro = (Libro) obj;
        return Objects.equals(titulo, libro.titulo);
    }

    @Override
    public int hashCode() { return Objects.hash(titulo); }

    @Override
    public String toString() {
        return "------------- LIBRO -------------" +
                "\nTítulo: " + titulo +
                "\nAutor: " + (autor != null ? autor.getNombre() : "Desconocido") +
                "\nIdioma: " + (idioma != null ? idioma.getNombreIdioma() : "Desconocido") +
                "\nNúmero de descargas: " + (numeroDescargas != null ? numeroDescargas : 0) +
                "\n---------------------------------";
    }
}
