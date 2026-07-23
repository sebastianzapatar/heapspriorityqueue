import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Ejercicio 11 — K puntos más cercanos al origen.
 *
 * Max-Heap acotado de tamaño k ordenado por distancia al origen
 * (x² + y², sin raíz cuadrada: comparar cuadrados preserva el orden).
 * La raíz es el punto MÁS LEJANO de los k guardados: si llega uno más
 * cercano, se expulsa la raíz. Al final quedan los k más cercanos.
 *
 * Complejidad: O(n log k) en tiempo, O(k) en memoria.
 */
public class Ejercicio11 {

    /** Distancia al origen al cuadrado (evita Math.sqrt). */
    private static int dist(int[] p) {
        return p[0] * p[0] + p[1] * p[1];
    }

    public static int[][] kClosest(int[][] points, int k) {
        // Max-Heap por distancia: la raíz es el más lejano de los candidatos
        PriorityQueue<int[]> maxHeap =
                new PriorityQueue<>((a, b) -> dist(b) - dist(a));

        for (int[] punto : points) {
            maxHeap.offer(punto);
            if (maxHeap.size() > k) {
                maxHeap.poll();   // expulsa el más lejano: sobreviven los k cercanos
            }
        }

        // Pasar el heap al arreglo resultado y ordenarlo por distancia
        // (el heap no garantiza orden al iterar)
        int[][] resultado = maxHeap.toArray(new int[0][]);
        Arrays.sort(resultado, Comparator.comparingInt(Ejercicio11::dist));
        return resultado;
    }

    public static void main(String[] args) {
        int[][] points = {{1, 3}, {-2, 2}, {5, -1}};
        int[][] result = kClosest(points, 2);
        System.out.println("Los 2 más cercanos son:");
        for (int[] p : result) {
            System.out.println(Arrays.toString(p));
        }
        // Esperado: [-2, 2], [1, 3]
    }
}
