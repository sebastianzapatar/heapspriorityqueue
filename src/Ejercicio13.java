import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Ejercicio 13 — K pares con las sumas más pequeñas.
 *
 * Los arreglos vienen ordenados, así que el espacio de pares (i, j) se
 * explora como un "grafo" desde (0,0): el Min-Heap (ordenado por
 * nums1[i] + nums2[j]) entrega siempre el par pendiente de menor suma,
 * y de cada par extraído se expanden sus vecinos (i+1, j) y (i, j+1).
 * Un registro de visitados evita encolar el mismo par dos veces.
 *
 * Complejidad: O(k log k) — solo se exploran O(k) pares.
 */
public class Ejercicio13 {

    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> resultado = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0 || k <= 0) return resultado;

        // El heap guarda índices [i, j] y compara por la suma del par
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(p -> nums1[p[0]] + nums2[p[1]]));
        boolean[][] visitado = new boolean[nums1.length][nums2.length];

        minHeap.offer(new int[]{0, 0});   // el par de menor suma posible
        visitado[0][0] = true;

        while (resultado.size() < k && !minHeap.isEmpty()) {
            int[] par = minHeap.poll();
            int i = par[0], j = par[1];
            resultado.add(List.of(nums1[i], nums2[j]));

            // Expandir los dos vecinos del par extraído
            if (i + 1 < nums1.length && !visitado[i + 1][j]) {
                minHeap.offer(new int[]{i + 1, j});
                visitado[i + 1][j] = true;
            }
            if (j + 1 < nums2.length && !visitado[i][j + 1]) {
                minHeap.offer(new int[]{i, j + 1});
                visitado[i][j + 1] = true;
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 7, 11};
        int[] nums2 = {2, 4, 6};
        System.out.println(kSmallestPairs(nums1, nums2, 3));
        // Esperado: [[1, 2], [1, 4], [1, 6]]
    }
}
