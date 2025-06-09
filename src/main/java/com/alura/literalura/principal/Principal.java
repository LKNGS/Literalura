package com.alura.literalura.principal;


import com.alura.literalura.dto.DatosAutor;
import com.alura.literalura.dto.DatosLibro;
import com.alura.literalura.dto.DatosRespuestaGutendex;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Idioma;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.IConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI;
    private final IConvierteDatos conversor;
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final String URL_BASE = "https://gutendex.com/books";

    @Autowired
    public Principal(ConsumoAPI consumoAPI, IConvierteDatos conversor,
                     LibroRepository libroRepository, AutorRepository autorRepository) {
        this.consumoAPI = consumoAPI;
        this.conversor = conversor;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    
                    ===============================================
                              LITERALURA - Challenge Alura
                    ===============================================
                    
                    1 - Buscar libro por palabra clave
                    2 - Listar libros registrados
                    3 - Listar autores registrados  
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    
                    ===============================================
                    Elija una opción válida: 
                    """);

            String input = teclado.nextLine();
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorPalabraClave();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosEnAno();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void buscarLibroPorPalabraClave() {
        System.out.print("Escribe una palabra o frase para buscar en los títulos: ");
        String palabraClave = teclado.nextLine();

        // Búsqueda flexible en la base de datos
        List<Libro> librosEncontrados = libroRepository.findByTituloContainingIgnoreCase(palabraClave);
        if (!librosEncontrados.isEmpty()) {
            System.out.println("\n=== Libros encontrados en la base de datos ===");
            int i = 1;
            for (Libro libro : librosEncontrados) {
                System.out.printf("\nLibro #%d:\n", i++);
                imprimirLibro(libro, null);
            }
            System.out.println("=========================");
            return;
        }

        // Si no hay resultados, consulta la API y registra el primero
        String json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + palabraClave.replace(" ", "%20"));
        if (json == null || json.isEmpty()) {
            System.out.println("No se pudo obtener información de la API");
            return;
        }

        DatosRespuestaGutendex datosBusqueda = conversor.obtenerDatos(json, DatosRespuestaGutendex.class);
        if (datosBusqueda == null || datosBusqueda.resultados().isEmpty()) {
            System.out.println("Libro no encontrado en la API.");
            return;
        }

        DatosLibro datosLibro = datosBusqueda.resultados().get(0);

        try {
            Autor autor = null;
            if (datosLibro.autores() != null && !datosLibro.autores().isEmpty()) {
                DatosAutor datosAutor = datosLibro.autores().get(0);

                Optional<Autor> autorExistente = autorRepository.findByNombre(datosAutor.nombre());
                if (autorExistente.isPresent()) {
                    autor = autorExistente.get();
                } else {
                    autor = new Autor(datosAutor);
                    autor = autorRepository.save(autor);
                }
            }

            Libro libro = new Libro(datosLibro);
            libro.setAutor(autor);
            libro = libroRepository.save(libro);

            System.out.println("Libro guardado exitosamente desde la API:");
            imprimirLibro(libro, "Libro guardado");
        } catch (Exception e) {
            System.out.println("Error al guardar el libro: " + e.getMessage());
        }
    }

    private void listarLibrosRegistrados() {
        System.out.println("\n=== Libros registrados ===");
        var libros = libroRepository.findAllOrderByTitulo();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        int i = 1;
        for (Libro libro : libros) {
            System.out.printf("\nLibro #%d:\n", i++);
            imprimirLibro(libro, null);
        }
        System.out.println("=========================");
    }

    private void listarAutoresRegistrados() {
        System.out.println("\n=== Autores registrados ===");
        var autores = autorRepository.findAllOrderByNombre();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }
        int i = 1;
        for (Autor autor : autores) {
            System.out.printf("\nAutor #%d:\n", i++);
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Nacimiento: " + (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Desconocido"));
            System.out.println("Fallecimiento: " + (autor.getFechaFallecimiento() != null ? autor.getFechaFallecimiento() : "Desconocido"));
        }
        System.out.println("==========================");
    }

    private void listarAutoresVivosEnAno() {
        System.out.print("Ingrese el año a consultar: ");
        String input = teclado.nextLine();
        try {
            int ano = Integer.parseInt(input);
            var autores = autorRepository.autoresVivosEnAno(ano);
            System.out.println("\n=== Autores vivos en el año " + ano + " ===");
            if (autores.isEmpty()) {
                System.out.println("No hay autores vivos en ese año.");
            } else {
                int i = 1;
                for (Autor autor : autores) {
                    System.out.printf("\nAutor #%d:\n", i++);
                    System.out.println("Nombre: " + autor.getNombre());
                    System.out.println("Nacimiento: " + (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Desconocido"));
                    System.out.println("Fallecimiento: " + (autor.getFechaFallecimiento() != null ? autor.getFechaFallecimiento() : "Desconocido"));
                }
            }
            System.out.println("===============================");
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un año válido.");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.print("Ingrese el código de idioma (es, en, fr, pt, otros): ");
        String idiomaStr = teclado.nextLine();
        try {
            Idioma idioma = Idioma.fromString(idiomaStr);
            var libros = libroRepository.findByIdiomaOrderByTitulo(idioma);
            System.out.println("\n=== Libros en idioma: " + idioma.getNombreIdioma() + " ===");
            if (libros.isEmpty()) {
                System.out.println("No hay libros registrados en ese idioma.");
            } else {
                int i = 1;
                for (Libro libro : libros) {
                    System.out.printf("\nLibro #%d:\n", i++);
                    imprimirLibro(libro, null);
                }
            }
            System.out.println("===============================");
        } catch (Exception e) {
            System.out.println("Código de idioma no válido.");
        }
    }

    // Método auxiliar para imprimir datos de un libro de forma elegante
    private void imprimirLibro(Libro libro, String tituloBloque) {
        if (tituloBloque != null) {
            System.out.println("\n=== " + tituloBloque + " ===");
        }
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
        System.out.println("Idioma: " + (libro.getIdioma() != null ? libro.getIdioma().getNombreIdioma() : "Desconocido"));
        System.out.println("Descargas: " + (libro.getNumeroDescargas() != null ? libro.getNumeroDescargas() : 0));
        System.out.println("=========================");
    }
}