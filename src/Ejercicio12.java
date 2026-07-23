import java.util.PriorityQueue;

/**
 * Ejercicio 12 — Costo mínimo para conectar cuerdas.
 *
 * Estrategia greedy: unir siempre las dos cuerdas más cortas, porque
 * cada unión se vuelve a "pagar" en las uniones siguientes — conviene
 * que las cuerdas largas participen la menor cantidad de veces.
 * El Min-Heap entrega las dos mínimas en O(log n).
 *
 * Ejemplo {4, 3, 2, 6}: 2+3=5 (costo 5) → 5+4=9 (costo 14) → 9+6=15 (costo 29).
 */
public class Ejercicio12 {

    public static int minCost(int[] ropes) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int r : ropes) minHeap.offer(r);

        int costoTotal = 0;
        // Mientras haya más de una cuerda: unir las dos más cortas
        while (minHeap.size() > 1) {
            int union = minHeap.poll() + minHeap.poll();
            costoTotal += union;
            minHeap.offer(union);   // la cuerda resultante vuelve al heap
        }
        return costoTotal;
    }

    public static void main(String[] args) {
        int[] ropes = {4, 3, 2, 6};
        System.out.println("Costo mínimo: " + minCost(ropes)); // Esperado: 29
    }
}
