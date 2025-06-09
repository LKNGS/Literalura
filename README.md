==========================================
Literalura 
==========================================

Literalura es una aplicación de consola en Java que permite gestionar una base de datos de libros y autores de manera interactiva. El usuario puede buscar, registrar y consultar libros y autores a través de un menú amigable en la terminal.

------------------------------------------
Características principales
------------------------------------------
- Búsqueda flexible de libros por palabra clave en el título.
- Registro automático de libros y autores desde la API de Gutendex si no existen en la base de datos.
- Listado de libros y autores registrados, ordenados y con información detallada.
- Consulta de autores vivos en un año específico.
- Filtrado de libros por idioma.

------------------------------------------
Tecnologías y herramientas
------------------------------------------
- Java 17
- Spring Boot (solo modo consola, sin servidor web)
- JPA/Hibernate para persistencia
- PostgreSQL como base de datos
- Maven para gestión de dependencias y ejecución
- IntelliJ IDEA recomendado como IDE

------------------------------------------
Instalación y ejecución
------------------------------------------
1. Clona el repositorio:
   git clone https://github.com/TU_USUARIO/literalura.git
   cd literalura

2. Configura tu base de datos PostgreSQL y ajusta las credenciales en src/main/resources/application.properties.

3. Compila y ejecuta en modo consola:
   mvn clean install
   mvn spring-boot:run -Dspring-boot.run.profiles=console

   O ejecuta desde IntelliJ configurando el perfil activo como 'console'.

------------------------------------------
Uso
------------------------------------------
- Al iniciar, verás un menú interactivo en la terminal.
- Elige la opción deseada (por número) y sigue las instrucciones para buscar, listar o registrar libros y autores.
- Si buscas un libro y no existe, se consultará la API Gutendex y se registrará automáticamente el primer resultado.

------------------------------------------
Estructura del menú
------------------------------------------
1 - Buscar libro por palabra clave
2 - Listar libros registrados
3 - Listar autores registrados
4 - Listar autores vivos en un determinado año
5 - Listar libros por idioma
0 - Salir

------------------------------------------
Notas
------------------------------------------
- El proyecto está enfocado en la experiencia de consola.
- No incluye endpoints web ni interfaz gráfica.
- Ideal para prácticas de Java, gestión de bases de datos y aplicaciones de consola.
