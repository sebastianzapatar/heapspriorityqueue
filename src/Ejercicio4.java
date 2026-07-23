import java.util.PriorityQueue;

/**
 * Ejercicio 4 — K-ésimo elemento más grande.
 *
 * Técnica del Min-Heap acotado de tamaño k: se recorren los n elementos
 * y el heap conserva solo los k más grandes vistos hasta el momento.
 * La raíz (peek) es entonces el k-ésimo más grande.
 *
 * Complejidad: O(n log k) en tiempo, O(k) en memoria.
 */
public class Ejercicio4 {

    public static int kthLargest(int[] arr, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int v : arr) {
            minHeap.offer(v);
            // Si supera el tamaño k, sale el menor: solo sobreviven los k mayores
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        // La raíz es el menor de los k mayores = k-ésimo más grande
        return minHeap.peek();
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 5, 6, 4};
        System.out.println("2do más grande: " + kthLargest(arr, 2)); // 5
        System.out.println("4to más grande: " + kthLargest(arr, 4)); // 3
    }
}
