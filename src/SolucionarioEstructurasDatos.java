
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Solucionario de ejercicios de Estructuras de Datos y Programación Funcional.
 *
 * Contiene ejemplos funcionales de:
 * - Árboles Binarios de Búsqueda (BST)
 * - HashMap
 * - Recursividad
 * - Comparator
 * - Streams y Collectors
 *
 * Compilación:
 *   javac SolucionarioEstructurasDatos.java
 *
 * Ejecución:
 *   java SolucionarioEstructurasDatos
 */
public class SolucionarioEstructurasDatos {

    /**
     * Ejecuta los 10 ejercicios en orden. Cada ejercicio es independiente
     * y crea sus propios datos de prueba.
     */
    public static void main(String[] args) {
        ejercicio1();
        ejercicio2();
        ejercicio3();
        ejercicio4();
        ejercicio5();
        ejercicio6();
        ejercicio7();
        ejercicio8();
        ejercicio9();
        ejercicio10();
    }

    // =========================================================
    // EJERCICIO 1: INVENTARIO DE BIBLIOTECA (BST + STREAMS)
    //
    // Demuestra: un BST ordenado por ISBN (insertar, buscar,
    // recorrido inorden) combinado con Streams para filtrar,
    // promediar y encontrar máximos.
    // =========================================================

    /** Modelo inmutable de un libro; la clave del BST es el ISBN. */
    static class Libro {
        private final String isbn;
        private final String titulo;
        private final String autor;
        private final int anio;
        private final int prestamos;

        public Libro(String isbn, String titulo, String autor, int anio, int prestamos) {
            this.isbn = isbn;
            this.titulo = titulo;
            this.autor = autor;
            this.anio = anio;
            this.prestamos = prestamos;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getAutor() {
            return autor;
        }

        public int getAnio() {
            return anio;
        }

        public int getPrestamos() {
            return prestamos;
        }

        @Override
        public String toString() {
            return titulo + " | ISBN: " + isbn + " | Año: " + anio
                    + " | Préstamos: " + prestamos;
        }
    }

    /** Nodo del BST de libros: guarda el dato y sus dos hijos. */
    static class NodoLibro {
        Libro libro;
        NodoLibro izquierdo;
        NodoLibro derecho;

        NodoLibro(Libro libro) {
            this.libro = libro;
        }
    }

    /**
     * BST de libros ordenado por ISBN.
     * Menores a la izquierda, mayores a la derecha; no admite duplicados.
     */
    static class ArbolLibros {
        private NodoLibro raiz;

        /** Inserta un libro manteniendo el orden del BST. O(log n) promedio. */
        public void insertar(Libro libro) {
            raiz = insertarRec(raiz, libro);
        }

        private NodoLibro insertarRec(NodoLibro actual, Libro libro) {
            // Caso base: posición libre encontrada → crear el nodo aquí
            if (actual == null) {
                return new NodoLibro(libro);
            }

            int comparacion = libro.getIsbn().compareTo(actual.libro.getIsbn());

            if (comparacion < 0) {
                actual.izquierdo = insertarRec(actual.izquierdo, libro);
            } else if (comparacion > 0) {
                actual.derecho = insertarRec(actual.derecho, libro);
            } else {
                // comparacion == 0 → el ISBN ya existe en el árbol
                throw new IllegalArgumentException("Ya existe un libro con ISBN " + libro.getIsbn());
            }

            return actual;
        }

        /** Busca por ISBN; Optional.empty() si no existe. O(log n) promedio. */
        public Optional<Libro> buscar(String isbn) {
            return Optional.ofNullable(buscarRec(raiz, isbn));
        }

        private Libro buscarRec(NodoLibro actual, String isbn) {
            if (actual == null) {
                return null; // se llegó a una hoja sin encontrarlo
            }

            int comparacion = isbn.compareTo(actual.libro.getIsbn());

            if (comparacion == 0) {
                return actual.libro;
            }

            // Descender por la rama correcta según la comparación
            return comparacion < 0
                    ? buscarRec(actual.izquierdo, isbn)
                    : buscarRec(actual.derecho, isbn);
        }

        /** Recorrido inorden: retorna los libros ordenados por ISBN. */
        public List<Libro> inorden() {
            List<Libro> resultado = new ArrayList<>();
            inordenRec(raiz, resultado);
            return resultado;
        }

        private void inordenRec(NodoLibro actual, List<Libro> resultado) {
            if (actual == null) {
                return;
            }

            // izquierda → nodo → derecha = orden ascendente
            inordenRec(actual.izquierdo, resultado);
            resultado.add(actual.libro);
            inordenRec(actual.derecho, resultado);
        }
    }

    /** Ejercicio 1: carga el BST y lo consulta con streams. */
    static void ejercicio1() {
        System.out.println("\n========== EJERCICIO 1 ==========");

        ArbolLibros arbol = new ArbolLibros();
        arbol.insertar(new Libro("978-03", "Clean Code", "Robert C. Martin", 2008, 40));
        arbol.insertar(new Libro("978-01", "Effective Java", "Joshua Bloch", 2018, 35));
        arbol.insertar(new Libro("978-05", "Modern Java in Action", "Urma", 2021, 60));
        arbol.insertar(new Libro("978-04", "Java: The Complete Reference", "Schildt", 2022, 25));
        arbol.insertar(new Libro("978-06", "Spring in Action", "Walls", 2023, 55));

        System.out.println("Libros en inorden:");
        arbol.inorden().forEach(System.out::println);

        System.out.println("\nBúsqueda por ISBN:");
        System.out.println(arbol.buscar("978-05").orElse(null));

        // filter: conserva solo los libros que cumplen la condición
        System.out.println("\nPublicados después de 2020:");
        arbol.inorden().stream()
                .filter(libro -> libro.getAnio() > 2020)
                .forEach(System.out::println);

        // mapToInt + average: convierte a IntStream y promedia;
        // orElse(0.0) cubre el caso de un árbol vacío
        double promedioPrestamos = arbol.inorden().stream()
                .mapToInt(Libro::getPrestamos)
                .average()
                .orElse(0.0);

        System.out.println("\nPromedio de préstamos: " + promedioPrestamos);

        // max con Comparator: encuentra el libro con más préstamos
        arbol.inorden().stream()
                .max(Comparator.comparingInt(Libro::getPrestamos))
                .ifPresent(libro -> System.out.println("Libro más prestado: " + libro));
    }

    // =========================================================
    // EJERCICIO 2: DIRECTORIO DE EMPLEADOS
    //
    // Demuestra: HashMap como índice por documento (acceso O(1))
    // y Collectors.groupingBy / averagingDouble para agrupar.
    // =========================================================

    /** Modelo inmutable de un empleado; la clave del HashMap es el documento. */
    static class Empleado {
        private final String documento;
        private final String nombre;
        private final String departamento;
        private final double salario;

        public Empleado(String documento, String nombre, String departamento, double salario) {
            this.documento = documento;
            this.nombre = nombre;
            this.departamento = departamento;
            this.salario = salario;
        }

        public String getDocumento() {
            return documento;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDepartamento() {
            return departamento;
        }

        public double getSalario() {
            return salario;
        }

        @Override
        public String toString() {
            return nombre + " | " + departamento + " | $" + salario;
        }
    }

    /** Ejercicio 2: consultas sobre un HashMap de empleados. */
    static void ejercicio2() {
        System.out.println("\n========== EJERCICIO 2 ==========");

        // La clave es el documento → get() responde en O(1)
        Map<String, Empleado> empleados = new HashMap<>();
        empleados.put("1001", new Empleado("1001", "Ana", "Sistemas", 4_500_000));
        empleados.put("1002", new Empleado("1002", "Luis", "Finanzas", 5_200_000));
        empleados.put("1003", new Empleado("1003", "Marta", "Sistemas", 6_000_000));
        empleados.put("1004", new Empleado("1004", "Carlos", "Talento Humano", 3_800_000));

        System.out.println("Empleado por documento 1003: " + empleados.get("1003"));

        System.out.println("\nEmpleados de Sistemas:");
        empleados.values().stream()
                .filter(e -> e.getDepartamento().equalsIgnoreCase("Sistemas"))
                .forEach(System.out::println);

        // groupingBy + averagingDouble: agrupa por departamento y
        // calcula el promedio de salario de cada grupo en una sola pasada
        Map<String, Double> promedioPorDepartamento = empleados.values().stream()
                .collect(Collectors.groupingBy(
                        Empleado::getDepartamento,
                        Collectors.averagingDouble(Empleado::getSalario)
                ));

        System.out.println("\nPromedio salarial por departamento:");
        promedioPorDepartamento.forEach((departamento, promedio) ->
                System.out.println(departamento + ": $" + promedio)
        );

        empleados.values().stream()
                .max(Comparator.comparingDouble(Empleado::getSalario))
                .ifPresent(e -> System.out.println("\nMayor salario: " + e));

        // map + distinct + sorted: lista de departamentos sin repetir, ordenada
        System.out.println("\nDepartamentos:");
        empleados.values().stream()
                .map(Empleado::getDepartamento)
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }

    // =========================================================
    // EJERCICIO 3: ESTADÍSTICAS DE VENTAS
    //
    // Demuestra: sum, max, average, filter y agrupaciones
    // (groupingBy con lista y con counting) sobre una lista.
    // =========================================================

    /** Modelo inmutable de una venta; el total se calcula, no se guarda. */
    static class Venta {
        private final String producto;
        private final String categoria;
        private final double valorUnitario;
        private final int cantidad;

        public Venta(String producto, String categoria, double valorUnitario, int cantidad) {
            this.producto = producto;
            this.categoria = categoria;
            this.valorUnitario = valorUnitario;
            this.cantidad = cantidad;
        }

        public String getProducto() {
            return producto;
        }

        public String getCategoria() {
            return categoria;
        }

        public double getValorUnitario() {
            return valorUnitario;
        }

        public int getCantidad() {
            return cantidad;
        }

        /** Total de la venta = valor unitario × cantidad. */
        public double getTotal() {
            return valorUnitario * cantidad;
        }

        @Override
        public String toString() {
            return producto + " | " + categoria + " | Total: $" + getTotal();
        }
    }

    /** Ejercicio 3: estadísticas de ventas con streams. */
    static void ejercicio3() {
        System.out.println("\n========== EJERCICIO 3 ==========");

        // List.of crea una lista inmutable de datos de prueba
        List<Venta> ventas = List.of(
                new Venta("Portátil", "Tecnología", 3_000_000, 2),
                new Venta("Mouse", "Tecnología", 80_000, 10),
                new Venta("Silla", "Hogar", 650_000, 3),
                new Venta("Escritorio", "Hogar", 900_000, 2)
        );

        double totalVendido = ventas.stream()
                .mapToDouble(Venta::getTotal)
                .sum();

        System.out.println("Total vendido: $" + totalVendido);

        ventas.stream()
                .max(Comparator.comparingInt(Venta::getCantidad))
                .ifPresent(v -> System.out.println("Producto con más unidades: " + v.getProducto()));

        double promedio = ventas.stream()
                .mapToDouble(Venta::getTotal)
                .average()
                .orElse(0.0);

        System.out.println("Promedio por venta: $" + promedio);

        System.out.println("\nVentas superiores a $500.000:");
        ventas.stream()
                .filter(v -> v.getTotal() > 500_000)
                .forEach(System.out::println);

        // groupingBy simple: Map de categoría → lista de sus ventas
        Map<String, List<Venta>> porCategoria = ventas.stream()
                .collect(Collectors.groupingBy(Venta::getCategoria));

        System.out.println("\nVentas agrupadas por categoría:");
        porCategoria.forEach((categoria, lista) ->
                System.out.println(categoria + ": " + lista)
        );

        // groupingBy + counting: cuántos registros hay por categoría
        Map<String, Long> cantidadPorCategoria = ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::getCategoria,
                        Collectors.counting()
                ));

        System.out.println("\nCantidad de registros por categoría:");
        System.out.println(cantidadPorCategoria);
    }

    // =========================================================
    // EJERCICIO 4: AGENDA TELEFÓNICA (BST)
    //
    // Demuestra: BST completo con eliminación (3 casos),
    // altura, conteo de hojas y los 3 recorridos clásicos
    // (preorden, inorden, postorden).
    // =========================================================

    /** Modelo inmutable de un contacto; la clave del BST es el nombre. */
    static class Contacto {
        private final String nombre;
        private final String telefono;
        private final String correo;

        public Contacto(String nombre, String telefono, String correo) {
            this.nombre = nombre;
            this.telefono = telefono;
            this.correo = correo;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre + " | " + telefono + " | " + correo;
        }
    }

    /** Nodo del BST de contactos. */
    static class NodoContacto {
        Contacto contacto;
        NodoContacto izquierdo;
        NodoContacto derecho;

        NodoContacto(Contacto contacto) {
            this.contacto = contacto;
        }
    }

    /**
     * Agenda implementada como BST ordenado por nombre
     * (comparación sin distinguir mayúsculas/minúsculas).
     */
    static class AgendaBST {
        private NodoContacto raiz;

        /** Inserta un contacto en orden alfabético. */
        public void insertar(Contacto contacto) {
            raiz = insertarRec(raiz, contacto);
        }

        private NodoContacto insertarRec(NodoContacto actual, Contacto contacto) {
            if (actual == null) {
                return new NodoContacto(contacto);
            }

            int comparacion = contacto.getNombre()
                    .compareToIgnoreCase(actual.contacto.getNombre());

            if (comparacion < 0) {
                actual.izquierdo = insertarRec(actual.izquierdo, contacto);
            } else if (comparacion > 0) {
                actual.derecho = insertarRec(actual.derecho, contacto);
            } else {
                throw new IllegalArgumentException("El contacto ya existe");
            }

            return actual;
        }

        public Optional<Contacto> buscar(String nombre) {
            return Optional.ofNullable(buscarRec(raiz, nombre));
        }

        private Contacto buscarRec(NodoContacto actual, String nombre) {
            if (actual == null) {
                return null;
            }

            int comparacion = nombre.compareToIgnoreCase(actual.contacto.getNombre());

            if (comparacion == 0) {
                return actual.contacto;
            }

            return comparacion < 0
                    ? buscarRec(actual.izquierdo, nombre)
                    : buscarRec(actual.derecho, nombre);
        }

        /**
         * Elimina un contacto por nombre.
         * Maneja los 3 casos clásicos de eliminación en un BST:
         *   1. Nodo sin hijo izquierdo  → lo reemplaza su hijo derecho.
         *   2. Nodo sin hijo derecho    → lo reemplaza su hijo izquierdo.
         *   3. Nodo con dos hijos       → se copia el SUCESOR (el menor del
         *      subárbol derecho) y luego se elimina ese sucesor.
         */
        public void eliminar(String nombre) {
            raiz = eliminarRec(raiz, nombre);
        }

        private NodoContacto eliminarRec(NodoContacto actual, String nombre) {
            if (actual == null) {
                return null; // el nombre no está en el árbol
            }

            int comparacion = nombre.compareToIgnoreCase(actual.contacto.getNombre());

            if (comparacion < 0) {
                actual.izquierdo = eliminarRec(actual.izquierdo, nombre);
            } else if (comparacion > 0) {
                actual.derecho = eliminarRec(actual.derecho, nombre);
            } else {
                // Encontrado. Casos 1 y 2: a lo sumo un hijo
                if (actual.izquierdo == null) {
                    return actual.derecho;
                }

                if (actual.derecho == null) {
                    return actual.izquierdo;
                }

                // Caso 3: dos hijos → copiar el sucesor y eliminarlo de la rama derecha
                NodoContacto sucesor = menor(actual.derecho);
                actual.contacto = sucesor.contacto;
                actual.derecho = eliminarRec(actual.derecho, sucesor.contacto.getNombre());
            }

            return actual;
        }

        /** El menor de un subárbol: bajar por la izquierda hasta el final. */
        private NodoContacto menor(NodoContacto actual) {
            while (actual.izquierdo != null) {
                actual = actual.izquierdo;
            }
            return actual;
        }

        /** Altura del árbol: -1 si está vacío, 0 si solo tiene la raíz. */
        public int altura() {
            return alturaRec(raiz);
        }

        private int alturaRec(NodoContacto actual) {
            if (actual == null) {
                return -1;
            }

            // 1 (este nivel) + la altura del hijo más profundo
            return 1 + Math.max(
                    alturaRec(actual.izquierdo),
                    alturaRec(actual.derecho)
            );
        }

        /** Cuenta los nodos hoja (sin hijos). */
        public int cantidadHojas() {
            return cantidadHojasRec(raiz);
        }

        private int cantidadHojasRec(NodoContacto actual) {
            if (actual == null) {
                return 0;
            }

            // Una hoja no tiene hijos
            if (actual.izquierdo == null && actual.derecho == null) {
                return 1;
            }

            return cantidadHojasRec(actual.izquierdo)
                    + cantidadHojasRec(actual.derecho);
        }

        /** Preorden: nodo → izquierda → derecha. */
        public void preorden() {
            preordenRec(raiz);
        }

        private void preordenRec(NodoContacto actual) {
            if (actual == null) return;
            System.out.println(actual.contacto);
            preordenRec(actual.izquierdo);
            preordenRec(actual.derecho);
        }

        /** Inorden: izquierda → nodo → derecha (sale en orden alfabético). */
        public void inorden() {
            inordenRec(raiz);
        }

        private void inordenRec(NodoContacto actual) {
            if (actual == null) return;
            inordenRec(actual.izquierdo);
            System.out.println(actual.contacto);
            inordenRec(actual.derecho);
        }

        /** Postorden: izquierda → derecha → nodo. */
        public void postorden() {
            postordenRec(raiz);
        }

        private void postordenRec(NodoContacto actual) {
            if (actual == null) return;
            postordenRec(actual.izquierdo);
            postordenRec(actual.derecho);
            System.out.println(actual.contacto);
        }
    }

    /** Ejercicio 4: operaciones completas sobre la agenda BST. */
    static void ejercicio4() {
        System.out.println("\n========== EJERCICIO 4 ==========");

        AgendaBST agenda = new AgendaBST();
        agenda.insertar(new Contacto("Laura", "3001111111", "laura@email.com"));
        agenda.insertar(new Contacto("Andrés", "3002222222", "andres@email.com"));
        agenda.insertar(new Contacto("Sofía", "3003333333", "sofia@email.com"));
        agenda.insertar(new Contacto("Carlos", "3004444444", "carlos@email.com"));

        System.out.println("Recorrido inorden:");
        agenda.inorden();

        System.out.println("Altura: " + agenda.altura());
        System.out.println("Cantidad de hojas: " + agenda.cantidadHojas());

        agenda.eliminar("Andrés");

        System.out.println("\nDespués de eliminar a Andrés:");
        agenda.inorden();
    }

    // =========================================================
    // EJERCICIO 5: UNIVERSIDAD
    //
    // Demuestra: sorted + limit (top N), agrupaciones por carrera
    // y búsqueda del máximo sobre las entradas de un Map.
    // =========================================================

    /** Modelo inmutable de un estudiante con su promedio académico. */
    static class Estudiante {
        private final String codigo;
        private final String nombre;
        private final String carrera;
        private final double promedio;

        public Estudiante(String codigo, String nombre, String carrera, double promedio) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.carrera = carrera;
            this.promedio = promedio;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public String getCarrera() {
            return carrera;
        }

        public double getPromedio() {
            return promedio;
        }

        @Override
        public String toString() {
            return nombre + " | " + carrera + " | " + promedio;
        }
    }

    /** Ejercicio 5: rankings y agrupaciones de estudiantes. */
    static void ejercicio5() {
        System.out.println("\n========== EJERCICIO 5 ==========");

        List<Estudiante> estudiantes = List.of(
                new Estudiante("E01", "Ana", "Sistemas", 4.8),
                new Estudiante("E02", "Luis", "Industrial", 4.2),
                new Estudiante("E03", "Andrea", "Sistemas", 4.5),
                new Estudiante("E04", "Carlos", "Civil", 3.9),
                new Estudiante("E05", "Sofía", "Industrial", 4.7),
                new Estudiante("E06", "Miguel", "Sistemas", 4.0)
        );

        // sorted descendente + limit: los 5 mejores promedios
        System.out.println("Top 5:");
        estudiantes.stream()
                .sorted(Comparator.comparingDouble(Estudiante::getPromedio).reversed())
                .limit(5)
                .forEach(System.out::println);

        double promedioGeneral = estudiantes.stream()
                .mapToDouble(Estudiante::getPromedio)
                .average()
                .orElse(0.0);

        System.out.println("\nPromedio general: " + promedioGeneral);

        Map<String, List<Estudiante>> porCarrera = estudiantes.stream()
                .collect(Collectors.groupingBy(Estudiante::getCarrera));

        System.out.println("\nAgrupados por carrera:");
        porCarrera.forEach((carrera, lista) ->
                System.out.println(carrera + ": " + lista)
        );

        Map<String, Double> promedioPorCarrera = estudiantes.stream()
                .collect(Collectors.groupingBy(
                        Estudiante::getCarrera,
                        Collectors.averagingDouble(Estudiante::getPromedio)
                ));

        // Map.Entry.comparingByValue: compara las entradas por su valor
        // (el promedio) para hallar la carrera ganadora
        promedioPorCarrera.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(entry -> System.out.println(
                        "\nCarrera con mayor promedio: "
                                + entry.getKey() + " (" + entry.getValue() + ")"
                ));

        Map<String, Long> cantidadPorCarrera = estudiantes.stream()
                .collect(Collectors.groupingBy(
                        Estudiante::getCarrera,
                        Collectors.counting()
                ));

        System.out.println("\nCantidad por carrera: " + cantidadPorCarrera);

        System.out.println("\nNombres que empiezan por A:");
        estudiantes.stream()
                .filter(e -> e.getNombre().startsWith("A"))
                .forEach(System.out::println);
    }

    // =========================================================
    // EJERCICIO 6: RED SOCIAL
    //
    // Demuestra: conteos y sumas por usuario con groupingBy
    // (counting y summingInt) sobre publicaciones.
    // =========================================================

    /** Modelo inmutable de una publicación con sus métricas. */
    static class Publicacion {
        private final String usuario;
        private final int likes;
        private final int comentarios;
        private final LocalDate fecha;

        public Publicacion(String usuario, int likes, int comentarios, LocalDate fecha) {
            this.usuario = usuario;
            this.likes = likes;
            this.comentarios = comentarios;
            this.fecha = fecha;
        }

        public String getUsuario() {
            return usuario;
        }

        public int getLikes() {
            return likes;
        }

        public int getComentarios() {
            return comentarios;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        @Override
        public String toString() {
            return usuario + " | Likes: " + likes + " | Comentarios: " + comentarios;
        }
    }

    /** Ejercicio 6: métricas de publicaciones agrupadas por usuario. */
    static void ejercicio6() {
        System.out.println("\n========== EJERCICIO 6 ==========");

        List<Publicacion> publicaciones = List.of(
                new Publicacion("ana", 1200, 80, LocalDate.of(2026, 1, 10)),
                new Publicacion("luis", 600, 25, LocalDate.of(2026, 1, 11)),
                new Publicacion("ana", 3000, 150, LocalDate.of(2026, 1, 12)),
                new Publicacion("marta", 900, 40, LocalDate.of(2026, 1, 13)),
                new Publicacion("ana", 400, 10, LocalDate.of(2026, 1, 14))
        );

        Map<String, Long> cantidadPorUsuario = publicaciones.stream()
                .collect(Collectors.groupingBy(
                        Publicacion::getUsuario,
                        Collectors.counting()
                ));

        cantidadPorUsuario.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(e -> System.out.println(
                        "Usuario con más publicaciones: " + e.getKey()
                ));

        double promedioLikes = publicaciones.stream()
                .mapToInt(Publicacion::getLikes)
                .average()
                .orElse(0.0);

        System.out.println("Promedio de likes: " + promedioLikes);

        System.out.println("\nPublicaciones con más de 1000 likes:");
        publicaciones.stream()
                .filter(p -> p.getLikes() > 1000)
                .forEach(System.out::println);

        Map<String, List<Publicacion>> porUsuario = publicaciones.stream()
                .collect(Collectors.groupingBy(Publicacion::getUsuario));

        System.out.println("\nAgrupadas por usuario: " + porUsuario);

        // groupingBy + summingInt: suma los comentarios de cada usuario
        Map<String, Integer> comentariosPorUsuario = publicaciones.stream()
                .collect(Collectors.groupingBy(
                        Publicacion::getUsuario,
                        Collectors.summingInt(Publicacion::getComentarios)
                ));

        System.out.println("\nComentarios por usuario: " + comentariosPorUsuario);
    }

    // =========================================================
    // EJERCICIO 7: ÁRBOL DE PERSONAS
    //
    // Demuestra: métricas estructurales de un BST — altura,
    // nodos internos vs hojas, nivel de un nodo y el camino
    // desde la raíz hasta un nodo (con backtracking).
    // =========================================================

    /** Modelo inmutable de una persona; la clave del BST es el documento. */
    static class Persona {
        private final String documento;
        private final String nombre;
        private final int edad;

        public Persona(String documento, String nombre, int edad) {
            this.documento = documento;
            this.nombre = nombre;
            this.edad = edad;
        }

        public String getDocumento() {
            return documento;
        }

        public String getNombre() {
            return nombre;
        }

        public int getEdad() {
            return edad;
        }

        @Override
        public String toString() {
            return nombre + " (" + documento + ")";
        }
    }

    /** Nodo del BST de personas. */
    static class NodoPersona {
        Persona persona;
        NodoPersona izquierdo;
        NodoPersona derecho;

        NodoPersona(Persona persona) {
            this.persona = persona;
        }
    }

    /** BST ordenado por documento, enfocado en métricas del árbol. */
    static class ArbolPersonas {
        private NodoPersona raiz;

        /** Inserta por documento; los duplicados se ignoran en silencio. */
        public void insertar(Persona persona) {
            raiz = insertarRec(raiz, persona);
        }

        private NodoPersona insertarRec(NodoPersona actual, Persona persona) {
            if (actual == null) {
                return new NodoPersona(persona);
            }

            int comparacion = persona.getDocumento()
                    .compareTo(actual.persona.getDocumento());

            if (comparacion < 0) {
                actual.izquierdo = insertarRec(actual.izquierdo, persona);
            } else if (comparacion > 0) {
                actual.derecho = insertarRec(actual.derecho, persona);
            }

            return actual;
        }

        /** Altura del árbol: -1 si está vacío. */
        public int altura() {
            return alturaRec(raiz);
        }

        private int alturaRec(NodoPersona actual) {
            if (actual == null) {
                return -1;
            }

            return 1 + Math.max(
                    alturaRec(actual.izquierdo),
                    alturaRec(actual.derecho)
            );
        }

        /** Cuenta los nodos internos (los que tienen al menos un hijo). */
        public int contarInternos() {
            return contarInternosRec(raiz);
        }

        private int contarInternosRec(NodoPersona actual) {
            if (actual == null) {
                return 0;
            }

            // Las hojas no cuentan como internos
            if (actual.izquierdo == null && actual.derecho == null) {
                return 0;
            }

            return 1
                    + contarInternosRec(actual.izquierdo)
                    + contarInternosRec(actual.derecho);
        }

        /** Cuenta los nodos hoja (sin hijos). */
        public int contarHojas() {
            return contarHojasRec(raiz);
        }

        private int contarHojasRec(NodoPersona actual) {
            if (actual == null) {
                return 0;
            }

            if (actual.izquierdo == null && actual.derecho == null) {
                return 1;
            }

            return contarHojasRec(actual.izquierdo)
                    + contarHojasRec(actual.derecho);
        }

        /** Nivel (profundidad) del nodo con ese documento; -1 si no existe. */
        public int nivel(String documento) {
            return nivelRec(raiz, documento, 0);
        }

        private int nivelRec(NodoPersona actual, String documento, int nivelActual) {
            if (actual == null) {
                return -1;
            }

            int comparacion = documento.compareTo(actual.persona.getDocumento());

            if (comparacion == 0) {
                return nivelActual;
            }

            return comparacion < 0
                    ? nivelRec(actual.izquierdo, documento, nivelActual + 1)
                    : nivelRec(actual.derecho, documento, nivelActual + 1);
        }

        /**
         * Camino desde la raíz hasta el nodo con ese documento.
         * Retorna lista vacía si el documento no está en el árbol.
         */
        public List<Persona> caminoHasta(String documento) {
            List<Persona> camino = new ArrayList<>();
            boolean encontrado = construirCamino(raiz, documento, camino);

            return encontrado ? camino : List.of();
        }

        private boolean construirCamino(
                NodoPersona actual,
                String documento,
                List<Persona> camino
        ) {
            if (actual == null) {
                return false;
            }

            // Agregar el nodo al camino de forma optimista
            camino.add(actual.persona);

            int comparacion = documento.compareTo(actual.persona.getDocumento());

            if (comparacion == 0) {
                return true; // destino alcanzado: el camino queda completo
            }

            boolean encontrado = comparacion < 0
                    ? construirCamino(actual.izquierdo, documento, camino)
                    : construirCamino(actual.derecho, documento, camino);

            // Backtracking: si por esta rama no se encontró, deshacer el paso
            if (!encontrado) {
                camino.remove(camino.size() - 1);
            }

            return encontrado;
        }
    }

    /** Ejercicio 7: métricas estructurales del árbol de personas. */
    static void ejercicio7() {
        System.out.println("\n========== EJERCICIO 7 ==========");

        ArbolPersonas arbol = new ArbolPersonas();
        arbol.insertar(new Persona("50", "Laura", 40));
        arbol.insertar(new Persona("30", "Ana", 20));
        arbol.insertar(new Persona("70", "Luis", 45));
        arbol.insertar(new Persona("20", "Carlos", 18));
        arbol.insertar(new Persona("40", "Marta", 30));

        System.out.println("Altura: " + arbol.altura());
        System.out.println("Nodos internos: " + arbol.contarInternos());
        System.out.println("Hojas: " + arbol.contarHojas());
        System.out.println("Nivel del documento 40: " + arbol.nivel("40"));
        System.out.println("Camino hasta 40: " + arbol.caminoHasta("40"));
    }

    // =========================================================
    // EJERCICIO 8: COMPARADORES
    //
    // Demuestra: las 4 formas típicas de construir un Comparator —
    // comparingInt, reversed(), thenComparing (criterio de desempate)
    // y un comparador con lambda sobre un campo derivado.
    // =========================================================

    /** Modelo inmutable de una película para practicar comparadores. */
    static class Pelicula {
        private final String nombre;
        private final String director;
        private final int anio;
        private final double calificacion;

        public Pelicula(String nombre, String director, int anio, double calificacion) {
            this.nombre = nombre;
            this.director = director;
            this.anio = anio;
            this.calificacion = calificacion;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDirector() {
            return director;
        }

        public int getAnio() {
            return anio;
        }

        public double getCalificacion() {
            return calificacion;
        }

        @Override
        public String toString() {
            return nombre + " | " + director + " | " + anio + " | " + calificacion;
        }
    }

    /** Ejercicio 8: ordenar la misma lista con distintos comparadores. */
    static void ejercicio8() {
        System.out.println("\n========== EJERCICIO 8 ==========");

        List<Pelicula> peliculas = List.of(
                new Pelicula("Interstellar", "Christopher Nolan", 2014, 8.7),
                new Pelicula("Inception", "Christopher Nolan", 2010, 8.8),
                new Pelicula("Parasite", "Bong Joon-ho", 2019, 8.5),
                new Pelicula("Dune", "Denis Villeneuve", 2021, 8.0)
        );

        System.out.println("Por año:");
        peliculas.stream()
                .sorted(Comparator.comparingInt(Pelicula::getAnio))
                .forEach(System.out::println);

        System.out.println("\nPor calificación descendente:");
        peliculas.stream()
                .sorted(Comparator.comparingDouble(Pelicula::getCalificacion).reversed())
                .forEach(System.out::println);

        // thenComparing: desempata por nombre cuando el director coincide
        System.out.println("\nPor director y nombre:");
        peliculas.stream()
                .sorted(
                        Comparator.comparing(Pelicula::getDirector)
                                .thenComparing(Pelicula::getNombre)
                )
                .forEach(System.out::println);

        // El comparador puede usar cualquier expresión, no solo getters
        System.out.println("\nPor longitud del nombre:");
        peliculas.stream()
                .sorted(Comparator.comparingInt(p -> p.getNombre().length()))
                .forEach(System.out::println);
    }

    // =========================================================
    // EJERCICIO 9: ANÁLISIS DE TRANSACCIONES
    //
    // Demuestra: agregaciones por cliente/ciudad y
    // DoubleSummaryStatistics (máximo, mínimo y total en una pasada).
    // =========================================================

    /** Modelo inmutable de una transacción de compra. */
    static class Transaccion {
        private final String cliente;
        private final String ciudad;
        private final double valor;
        private final LocalDate fecha;

        public Transaccion(String cliente, String ciudad, double valor, LocalDate fecha) {
            this.cliente = cliente;
            this.ciudad = ciudad;
            this.valor = valor;
            this.fecha = fecha;
        }

        public String getCliente() {
            return cliente;
        }

        public String getCiudad() {
            return ciudad;
        }

        public double getValor() {
            return valor;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        @Override
        public String toString() {
            return cliente + " | " + ciudad + " | $" + valor;
        }
    }

    /** Ejercicio 9: agregaciones y estadísticas de transacciones. */
    static void ejercicio9() {
        System.out.println("\n========== EJERCICIO 9 ==========");

        List<Transaccion> transacciones = List.of(
                new Transaccion("Ana", "Medellín", 300_000, LocalDate.of(2026, 1, 1)),
                new Transaccion("Luis", "Bogotá", 700_000, LocalDate.of(2026, 1, 2)),
                new Transaccion("Ana", "Medellín", 500_000, LocalDate.of(2026, 1, 3)),
                new Transaccion("Marta", "Cali", 200_000, LocalDate.of(2026, 1, 4)),
                new Transaccion("Luis", "Bogotá", 900_000, LocalDate.of(2026, 1, 5))
        );

        Map<String, Long> comprasPorCliente = transacciones.stream()
                .collect(Collectors.groupingBy(
                        Transaccion::getCliente,
                        Collectors.counting()
                ));

        comprasPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(e -> System.out.println(
                        "Cliente con más compras: " + e.getKey()
                ));

        Map<String, Double> ventasPorCiudad = transacciones.stream()
                .collect(Collectors.groupingBy(
                        Transaccion::getCiudad,
                        Collectors.summingDouble(Transaccion::getValor)
                ));

        ventasPorCiudad.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(e -> System.out.println(
                        "Ciudad con mayor volumen: " + e.getKey()
                ));

        Map<String, Double> promedioPorCiudad = transacciones.stream()
                .collect(Collectors.groupingBy(
                        Transaccion::getCiudad,
                        Collectors.averagingDouble(Transaccion::getValor)
                ));

        System.out.println("Promedio por ciudad: " + promedioPorCiudad);

        // summaryStatistics: calcula max, min, suma, promedio y conteo
        // en una sola pasada sobre el stream
        DoubleSummaryStatistics estadisticas = transacciones.stream()
                .mapToDouble(Transaccion::getValor)
                .summaryStatistics();

        System.out.println("Máximo: $" + estadisticas.getMax());
        System.out.println("Mínimo: $" + estadisticas.getMin());
        System.out.println("Total: $" + estadisticas.getSum());
    }

    // =========================================================
    // EJERCICIO 10: INTEGRADOR
    //
    // Combina todo: un BST ordenado por NOMBRE + un HashMap
    // indexado por DOCUMENTO sobre los mismos pacientes.
    // Cada estructura responde rápido a un tipo de consulta:
    //   - HashMap  → búsqueda por documento en O(1)
    //   - BST      → búsqueda/listado por nombre en O(log n)
    // =========================================================

    /** Modelo inmutable de un paciente (distinto al Paciente.java de urgencias). */
    static class Paciente {
        private final String documento;
        private final String nombre;
        private final int edad;
        private final String ciudad;

        public Paciente(String documento, String nombre, int edad, String ciudad) {
            this.documento = documento;
            this.nombre = nombre;
            this.edad = edad;
            this.ciudad = ciudad;
        }

        public String getDocumento() {
            return documento;
        }

        public String getNombre() {
            return nombre;
        }

        public int getEdad() {
            return edad;
        }

        public String getCiudad() {
            return ciudad;
        }

        @Override
        public String toString() {
            return nombre + " | " + documento + " | " + edad + " | " + ciudad;
        }
    }

    /** Nodo del BST de pacientes. */
    static class NodoPaciente {
        Paciente paciente;
        NodoPaciente izquierdo;
        NodoPaciente derecho;

        NodoPaciente(Paciente paciente) {
            this.paciente = paciente;
        }
    }

    /**
     * Sistema con doble índice: el mismo paciente se guarda en
     * un BST (por nombre) y en un HashMap (por documento).
     */
    static class SistemaPacientes {
        private NodoPaciente raiz;
        private final Map<String, Paciente> porDocumento = new HashMap<>();

        /** Registra en ambas estructuras; rechaza documentos repetidos. */
        public void registrar(Paciente paciente) {
            if (porDocumento.containsKey(paciente.getDocumento())) {
                throw new IllegalArgumentException("Documento repetido");
            }

            raiz = insertarRec(raiz, paciente);
            porDocumento.put(paciente.getDocumento(), paciente);
        }

        private NodoPaciente insertarRec(NodoPaciente actual, Paciente paciente) {
            if (actual == null) {
                return new NodoPaciente(paciente);
            }

            int comparacion = paciente.getNombre()
                    .compareToIgnoreCase(actual.paciente.getNombre());

            if (comparacion < 0) {
                actual.izquierdo = insertarRec(actual.izquierdo, paciente);
            } else {
                actual.derecho = insertarRec(actual.derecho, paciente);
            }

            return actual;
        }

        /** Búsqueda por documento: usa el HashMap → O(1). */
        public Optional<Paciente> buscarPorDocumento(String documento) {
            return Optional.ofNullable(porDocumento.get(documento));
        }

        /** Búsqueda por nombre: usa el BST → O(log n) promedio. */
        public Optional<Paciente> buscarPorNombre(String nombre) {
            return Optional.ofNullable(buscarNombreRec(raiz, nombre));
        }

        private Paciente buscarNombreRec(NodoPaciente actual, String nombre) {
            if (actual == null) {
                return null;
            }

            int comparacion = nombre.compareToIgnoreCase(actual.paciente.getNombre());

            if (comparacion == 0) {
                return actual.paciente;
            }

            return comparacion < 0
                    ? buscarNombreRec(actual.izquierdo, nombre)
                    : buscarNombreRec(actual.derecho, nombre);
        }

        /** Inorden del BST → pacientes en orden alfabético por nombre. */
        public List<Paciente> pacientesOrdenados() {
            List<Paciente> resultado = new ArrayList<>();
            inordenRec(raiz, resultado);
            return resultado;
        }

        private void inordenRec(NodoPaciente actual, List<Paciente> resultado) {
            if (actual == null) {
                return;
            }

            inordenRec(actual.izquierdo, resultado);
            resultado.add(actual.paciente);
            inordenRec(actual.derecho, resultado);
        }

        /** Promedio de edad de todos los pacientes registrados. */
        public double edadPromedio() {
            return porDocumento.values().stream()
                    .mapToInt(Paciente::getEdad)
                    .average()
                    .orElse(0.0);
        }

        /** Paciente con la mayor edad. */
        public Optional<Paciente> pacienteMayor() {
            return porDocumento.values().stream()
                    .max(Comparator.comparingInt(Paciente::getEdad));
        }

        /** Agrupa los pacientes por ciudad con groupingBy. */
        public Map<String, List<Paciente>> agruparPorCiudad() {
            return porDocumento.values().stream()
                    .collect(Collectors.groupingBy(Paciente::getCiudad));
        }

        /** Ciudades únicas en orden alfabético. */
        public List<String> ciudadesOrdenadas() {
            return porDocumento.values().stream()
                    .map(Paciente::getCiudad)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    /** Ejercicio 10: consultas combinando BST, HashMap y streams. */
    static void ejercicio10() {
        System.out.println("\n========== EJERCICIO 10 ==========");

        SistemaPacientes sistema = new SistemaPacientes();
        sistema.registrar(new Paciente("100", "Laura", 28, "Medellín"));
        sistema.registrar(new Paciente("101", "Carlos", 45, "Bogotá"));
        sistema.registrar(new Paciente("102", "Ana", 35, "Medellín"));
        sistema.registrar(new Paciente("103", "Sofía", 60, "Cali"));

        System.out.println("Buscar por documento:");
        System.out.println(sistema.buscarPorDocumento("102").orElse(null));

        System.out.println("\nBuscar por nombre:");
        System.out.println(sistema.buscarPorNombre("Carlos").orElse(null));

        System.out.println("\nPacientes en orden alfabético:");
        sistema.pacientesOrdenados().forEach(System.out::println);

        System.out.println("\nEdad promedio: " + sistema.edadPromedio());

        sistema.pacienteMayor()
                .ifPresent(p -> System.out.println("Paciente de mayor edad: " + p));

        System.out.println("\nAgrupados por ciudad:");
        sistema.agruparPorCiudad().forEach((ciudad, lista) ->
                System.out.println(ciudad + ": " + lista)
        );

        System.out.println("\nCiudades ordenadas:");
        sistema.ciudadesOrdenadas().forEach(System.out::println);
    }
}
