import java.util.Arrays;

/**
 * Ejercicio 10 — HeapSort in-place (sin PriorityQueue).
 *
 * 1. Se construye un Max-Heap sobre el propio arreglo (heapify bottom-up, O(n)).
 * 2. Se intercambia la raíz (máximo) con el último elemento del heap.
 * 3. Se reduce el heap en 1 y se hace siftDown de la nueva raíz.
 * 4. Se repite: la zona ordenada crece de derecha a izquierda.
 *
 * Complejidad: O(n log n) en tiempo, O(1) en memoria extra.
 */
public class Ejercicio10 {

    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Paso 1 — Construir Max-Heap: siftDown desde el último padre a la raíz
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, i, n);
        }

        // Paso 2 — Extraer el máximo uno a uno hacia el final del arreglo
        for (int i = n - 1; i > 0; i--) {
            int tmp = arr[0];        // raíz (máximo actual) → zona ordenada
            arr[0] = arr[i];
            arr[i] = tmp;
            siftDown(arr, 0, i);     // restaurar el heap en la parte no ordenada
        }
    }

    /** Hunde el nodo index intercambiándolo con su hijo mayor hasta cumplir la propiedad Max-Heap. */
    private static void siftDown(int[] arr, int index, int heapSize) {
        while (true) {
            int mayor = index;
            int izq = 2 * index + 1, der = 2 * index + 2;   // fórmulas de hijos

            if (izq < heapSize && arr[izq] > arr[mayor]) mayor = izq;
            if (der < heapSize && arr[der] > arr[mayor]) mayor = der;
            if (mayor == index) return;                      // ya cumple la propiedad

            int tmp = arr[index];
            arr[index] = arr[mayor];
            arr[mayor] = tmp;
            index = mayor;                                   // seguir bajando
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        System.out.println("Original: " + Arrays.toString(arr));
        heapSort(arr);
        System.out.println("Ordenado: " + Arrays.toString(arr));
        // Esperado: [5, 6, 7, 11, 12, 13]
    }
}
