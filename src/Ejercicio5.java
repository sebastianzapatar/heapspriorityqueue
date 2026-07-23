import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Ejercicio 5 — Ordenar un arreglo casi ordenado (K-sorted).
 *
 * Como cada elemento está a lo más k posiciones de su lugar final,
 * el mínimo global de lo que falta siempre está dentro de una ventana
 * de k+1 elementos. Un Min-Heap de ese tamaño basta para ordenar.
 *
 * Complejidad: O(n log k) — mejor que O(n log n) cuando k << n.
 */
public class Ejercicio5 {

    public static void sortKSorted(int[] arr, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int escribir = 0;   // posición donde se escribe el siguiente mínimo

        // 1. Insertar los primeros k+1 elementos (la ventana inicial)
        for (int i = 0; i < Math.min(k + 1, arr.length); i++) {
            minHeap.offer(arr[i]);
        }

        // 2. Por cada elemento restante: sale el mínimo, entra el siguiente
        for (int leer = k + 1; leer < arr.length; leer++) {
            arr[escribir++] = minHeap.poll();
            minHeap.offer(arr[leer]);
        }

        // 3. Vaciar lo que queda en el heap, ya en orden
        while (!minHeap.isEmpty()) {
            arr[escribir++] = minHeap.poll();
        }
    }

    public static void main(String[] args) {
        int[] arr = {6, 5, 3, 2, 8, 10, 9};
        sortKSorted(arr, 3);
        System.out.println(Arrays.toString(arr));
        // Esperado: [2, 3, 5, 6, 8, 9, 10]
    }
}
