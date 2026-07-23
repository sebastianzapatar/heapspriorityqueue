import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Ejercicio 3 — Heap con Strings.
 *
 * String implementa Comparable, así que PriorityQueue lo ordena
 * alfabéticamente sin comparador extra. Con reverseOrder() se obtiene
 * el orden alfabético inverso.
 */
public class Ejercicio3 {

    private static final List<String> NOMBRES =
            List.of("Zara", "Miguel", "Ana", "Carlos", "Diana");

    public static void main(String[] args) {
        // ---- Min-Heap: extrae en orden alfabético ----
        PriorityQueue<String> minHeap = new PriorityQueue<>(NOMBRES);
        System.out.print("Orden alfabético:  ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }
        System.out.println();

        // ---- Max-Heap: extrae en orden alfabético inverso ----
        PriorityQueue<String> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.addAll(NOMBRES);
        System.out.print("Orden inverso:     ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println();
    }
}
