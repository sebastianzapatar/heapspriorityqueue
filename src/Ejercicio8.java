import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Ejercicio 8 — Reorganizar String sin caracteres adyacentes iguales.
 *
 * Estrategia greedy con Max-Heap por frecuencia: en cada paso se coloca
 * el carácter más frecuente disponible. El carácter recién usado se
 * "retiene" un turno (no se reinserta de inmediato) para que nunca
 * quede junto a sí mismo.
 *
 * Si en algún momento el heap se vacía pero aún queda un carácter
 * retenido con frecuencia pendiente, no hay solución → "".
 */
public class Ejercicio8 {

    public static String reorganize(String s) {
        // 1. Contar la frecuencia de cada carácter
        Map<Character, Integer> frecuencia = new HashMap<>();
        for (char c : s.toCharArray()) {
            frecuencia.merge(c, 1, Integer::sum);
        }

        // 2. Max-Heap de pares [carácter, frecuencia], ordenado por frecuencia
        PriorityQueue<int[]> maxHeap =
                new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (var e : frecuencia.entrySet()) {
            maxHeap.offer(new int[]{e.getKey(), e.getValue()});
        }

        StringBuilder resultado = new StringBuilder();
        int[] retenido = null;   // el último usado, en espera de un turno

        // 3. Greedy: usar siempre el más frecuente que no sea el último colocado
        while (!maxHeap.isEmpty()) {
            int[] actual = maxHeap.poll();
            resultado.append((char) actual[0]);
            actual[1]--;

            // El retenido del turno anterior ya puede volver al heap
            if (retenido != null) {
                maxHeap.offer(retenido);
                retenido = null;
            }
            // Si al actual le quedan usos, queda retenido este turno
            if (actual[1] > 0) {
                retenido = actual;
            }
        }

        // Si quedó un carácter con usos pendientes, era imposible reorganizar
        return (retenido == null) ? resultado.toString() : "";
    }

    public static void main(String[] args) {
        System.out.println("aab  → \"" + reorganize("aab") + "\"");   // "aba"
        System.out.println("aaab → \"" + reorganize("aaab") + "\"");  // ""
        System.out.println("aabb → \"" + reorganize("aabb") + "\"");  // "abab" o "baba"
    }
}
