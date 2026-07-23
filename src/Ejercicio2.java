import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Ejercicio 2 — Operaciones básicas con PriorityQueue.
 *
 * Inserta 50, 30, 40, 10, 20, 35, 25 en un Min-Heap y luego en un
 * Max-Heap (Collections.reverseOrder()), mostrando peek() y los poll()
 * hasta vaciar cada cola.
 */
public class Ejercicio2 {

    private static final int[] VALORES = {50, 30, 40, 10, 20, 35, 25};

    public static void main(String[] args) {
        // ---- Min-Heap: PriorityQueue por defecto usa el orden natural ----
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int v : VALORES) minHeap.offer(v);

        System.out.println("=== Min-Heap ===");
        System.out.println("peek(): " + minHeap.peek());     // 10, el mínimo
        System.out.print("poll() hasta vaciar: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");          // sale en orden ascendente
        }
        System.out.println();

        // ---- Max-Heap: se invierte el comparador con reverseOrder() ----
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int v : VALORES) maxHeap.offer(v);

        System.out.println("\n=== Max-Heap ===");
        System.out.println("peek(): " + maxHeap.peek());     // 50, el máximo
        System.out.print("poll() hasta vaciar: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");          // sale en orden descendente
        }
        System.out.println();
    }
}
