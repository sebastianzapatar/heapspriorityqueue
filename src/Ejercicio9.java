import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Ejercicio 9 — Top K palabras más frecuentes.
 *
 * Min-Heap acotado de tamaño k con comparador compuesto INVERTIDO:
 * en la raíz queda siempre la "peor" palabra (menos frecuente y, en
 * empate, la alfabéticamente mayor), que es la que se expulsa al
 * exceder k. Al final se extrae el heap y se invierte el orden.
 *
 * Complejidad: O(n log k).
 */
public class Ejercicio9 {

    public static List<String> topKFrequent(String[] words, int k) {
        // 1. Contar frecuencias
        Map<String, Integer> frecuencia = new HashMap<>();
        for (String w : words) {
            frecuencia.merge(w, 1, Integer::sum);
        }

        // 2. Min-Heap de tamaño k: raíz = la peor candidata actual.
        //    Menor frecuencia primero; en empate, alfabéticamente MAYOR primero
        //    (porque en el resultado el empate se resuelve alfabéticamente menor).
        PriorityQueue<String> minHeap = new PriorityQueue<>((a, b) -> {
            int cmp = frecuencia.get(a) - frecuencia.get(b);
            return (cmp != 0) ? cmp : b.compareTo(a);
        });

        for (String palabra : frecuencia.keySet()) {
            minHeap.offer(palabra);
            if (minHeap.size() > k) {
                minHeap.poll();   // expulsa la peor: solo sobreviven las top-k
            }
        }

        // 3. El heap entrega de peor a mejor → se invierte al construir la lista
        List<String> resultado = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            resultado.add(0, minHeap.poll());
        }
        return resultado;
    }

    public static void main(String[] args) {
        String[] words = {"the", "day", "is", "sunny", "the", "the",
                          "the", "sunny", "is", "is"};
        System.out.println(topKFrequent(words, 4));
        // Esperado: [the, is, sunny, day]
    }
}
