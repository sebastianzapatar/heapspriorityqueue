import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Ejercicio 6 — Merge de K listas ordenadas.
 *
 * El Min-Heap guarda una "tripleta" [valor, índiceLista, índiceEnLista]
 * y siempre entrega el menor valor pendiente entre todas las listas.
 * Al extraer un elemento se inserta el siguiente de su misma lista.
 *
 * Complejidad: O(N log k), donde N es el total de elementos.
 */
public class Ejercicio6 {

    public static List<Integer> mergeKLists(List<List<Integer>> lists) {
        // El heap compara las tripletas por su valor (posición 0)
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(t -> t[0]));

        // Sembrar el heap con el primer elemento de cada lista no vacía
        for (int i = 0; i < lists.size(); i++) {
            if (!lists.get(i).isEmpty()) {
                minHeap.offer(new int[]{lists.get(i).get(0), i, 0});
            }
        }

        List<Integer> resultado = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            int[] menor = minHeap.poll();
            int valor = menor[0], lista = menor[1], pos = menor[2];
            resultado.add(valor);

            // Reponer con el siguiente elemento de la misma lista
            if (pos + 1 < lists.get(lista).size()) {
                minHeap.offer(new int[]{lists.get(lista).get(pos + 1), lista, pos + 1});
            }
        }
        return resultado;
    }

    public static void main(String[] args) {
        List<List<Integer>> lists = List.of(
                List.of(1, 4, 7),
                List.of(2, 5, 8),
                List.of(3, 6, 9)
        );
        System.out.println(mergeKLists(lists));
        // Esperado: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}
