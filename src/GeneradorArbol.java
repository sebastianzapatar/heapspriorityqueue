import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Generador de árbol a partir de una PriorityQueue.
 *
 * PriorityQueue guarda sus elementos en un arreglo con orden por niveles
 * (el heap binario). toArray() copia ese arreglo tal cual en OpenJDK, así
 * que basta aplicar las fórmulas de índices para reconstruir el árbol:
 *
 *   padre(i) = (i - 1) / 2      hijos(i) = 2i + 1, 2i + 2
 *
 * La cola NO se modifica: no se hace poll(), solo se copia.
 * Funciona con cualquier tipo (Integer, String, objetos con toString()).
 */
public class GeneradorArbol {

    /** Genera el árbol de la cola como texto, con la raíz arriba y ramas / \. */
    public static <T> String generarArbol(PriorityQueue<T> cola) {
        if (cola.isEmpty()) return "(cola vacía)\n";

        Object[] nodos = cola.toArray();     // orden por niveles del heap interno
        int n = nodos.length;

        // Ancho de celda según el elemento más largo, para que nada se encime
        int ancho = 2;
        for (Object o : nodos) {
            ancho = Math.max(ancho, String.valueOf(o).length());
        }

        int niveles = 32 - Integer.numberOfLeadingZeros(n);   // ⌊log₂(n)⌋ + 1
        int hojas = 1 << (niveles - 1);                        // nodos del último nivel
        int anchoTotal = hojas * (ancho + 2);                  // ancho del "lienzo"

        StringBuilder salida = new StringBuilder();
        for (int nivel = 0; nivel < niveles; nivel++) {
            int primero = (1 << nivel) - 1;                    // índice del 1er nodo del nivel
            int enNivel = 1 << nivel;                          // capacidad del nivel

            StringBuilder lineaNodos = lineaVacia(anchoTotal);
            StringBuilder lineaRamas = lineaVacia(anchoTotal);

            for (int j = 0; j < enNivel && primero + j < n; j++) {
                int i = primero + j;
                // Centro del nodo: reparte el nivel uniformemente en el lienzo
                int centro = anchoTotal * (2 * j + 1) / (2 * enNivel);
                escribirCentrado(lineaNodos, String.valueOf(nodos[i]), centro);

                // Rama hacia el padre: '/' si es hijo izquierdo, '\' si es derecho
                if (i > 0) {
                    int jPadre = (i - 1) / 2 - (enNivel / 2 - 1);
                    int centroPadre = anchoTotal * (2 * jPadre + 1) / enNivel;
                    lineaRamas.setCharAt((centro + centroPadre) / 2, (i % 2 == 1) ? '/' : '\\');
                }
            }
            if (nivel > 0) salida.append(recortar(lineaRamas)).append('\n');
            salida.append(recortar(lineaNodos)).append('\n');
        }
        return salida.toString();
    }

    /** Imprime directamente el árbol generado. */
    public static <T> void imprimirArbol(PriorityQueue<T> cola) {
        System.out.print(generarArbol(cola));
    }

    // ---------- Auxiliares de dibujo ----------

    /** Línea de `ancho` espacios sobre la cual se "pintan" nodos y ramas. */
    private static StringBuilder lineaVacia(int ancho) {
        return new StringBuilder(" ".repeat(ancho));
    }

    /** Escribe el texto centrado en la posición dada, sin salirse de la línea. */
    private static void escribirCentrado(StringBuilder linea, String texto, int centro) {
        int inicio = centro - texto.length() / 2;
        inicio = Math.max(0, Math.min(inicio, linea.length() - texto.length()));
        linea.replace(inicio, inicio + texto.length(), texto);
    }

    /** Quita los espacios sobrantes al final de la línea. */
    private static String recortar(StringBuilder linea) {
        int fin = linea.length();
        while (fin > 0 && linea.charAt(fin - 1) == ' ') fin--;
        return linea.substring(0, fin);
    }

    // ---------- Demostración ----------

    public static void main(String[] args) {
        int[] valores = {50, 30, 40, 10, 20, 35, 25};

        // Min-Heap de enteros
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int v : valores) minHeap.offer(v);
        System.out.println("Min-Heap tras insertar 50, 30, 40, 10, 20, 35, 25:\n");
        imprimirArbol(minHeap);

        // Max-Heap con los mismos valores
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int v : valores) maxHeap.offer(v);
        System.out.println("\nMax-Heap con los mismos valores:\n");
        imprimirArbol(maxHeap);

        // También funciona con Strings (o cualquier objeto con toString())
        PriorityQueue<String> nombres =
                new PriorityQueue<>(List.of("Zara", "Miguel", "Ana", "Carlos", "Diana"));
        System.out.println("\nMin-Heap de nombres:\n");
        imprimirArbol(nombres);

        // La cola queda intacta: generar el árbol no extrae elementos
        System.out.println("\nLa cola no se modificó → peek() = " + nombres.peek()
                + ", size() = " + nombres.size());
    }
}
