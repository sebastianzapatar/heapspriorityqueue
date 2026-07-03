import java.util.*;

/**
 * Ejemplos adicionales y casos de uso reales de Heaps.
 * 
 * Este archivo demuestra aplicaciones prácticas:
 * 1. HeapSort (ordenamiento con heap)
 * 2. K elementos más grandes/pequeños
 * 3. Mediana en flujo de datos (Median Finder)
 */
public class HeapExamples {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║    Ejemplos y Aplicaciones de Heaps     ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        demoHeapSort();
        demoKLargest();
        demoKSmallest();
        demoMedianFinder();
    }

    // =====================================================
    // 1. HEAP SORT — Ordenamiento usando un Heap
    // =====================================================
    static void demoHeapSort() {
        System.out.println("═══ 1. HEAP SORT ═══");
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original:  " + Arrays.toString(arr));

        // HeapSort ascendente usando PriorityQueue (Min-Heap)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int val : arr) {
            minHeap.offer(val);
        }

        int[] sorted = new int[arr.length];
        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = minHeap.poll();
        }
        System.out.println("Ordenado:  " + Arrays.toString(sorted));

        // HeapSort descendente usando Max-Heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int val : arr) {
            maxHeap.offer(val);
        }

        int[] sortedDesc = new int[arr.length];
        for (int i = 0; i < sortedDesc.length; i++) {
            sortedDesc[i] = maxHeap.poll();
        }
        System.out.println("Desc:      " + Arrays.toString(sortedDesc));
        System.out.println();
    }

    // =====================================================
    // 2. K ELEMENTOS MÁS GRANDES
    // =====================================================
    static void demoKLargest() {
        System.out.println("═══ 2. K ELEMENTOS MÁS GRANDES ═══");
        int[] arr = {3, 1, 5, 12, 2, 11, 7, 19, 4};
        int k = 3;
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("K = " + k);

        // Truco: usar un Min-Heap de tamaño K
        // Al final, el heap contiene los K más grandes
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int val : arr) {
            minHeap.offer(val);
            if (minHeap.size() > k) {
                minHeap.poll(); // Eliminar el más pequeño
            }
        }

        System.out.print("Los " + k + " más grandes: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");
        }
        System.out.println("\n");
    }

    // =====================================================
    // 3. K ELEMENTOS MÁS PEQUEÑOS
    // =====================================================
    static void demoKSmallest() {
        System.out.println("═══ 3. K ELEMENTOS MÁS PEQUEÑOS ═══");
        int[] arr = {7, 10, 4, 3, 20, 15, 1};
        int k = 3;
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("K = " + k);

        // Truco inverso: usar un Max-Heap de tamaño K
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int val : arr) {
            maxHeap.offer(val);
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Eliminar el más grande
            }
        }

        System.out.print("Los " + k + " más pequeños: ");
        List<Integer> result = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            result.add(maxHeap.poll());
        }
        Collections.reverse(result);
        System.out.println(result);
        System.out.println();
    }

    // =====================================================
    // 4. MEDIANA EN FLUJO DE DATOS (Median Finder)
    // =====================================================
    static void demoMedianFinder() {
        System.out.println("═══ 4. MEDIANA EN FLUJO DE DATOS ═══");
        System.out.println("Técnica: Dos heaps (max-heap izquierdo + min-heap derecho)\n");

        // maxHeap: mitad inferior de los datos
        PriorityQueue<Integer> maxHeapLeft = new PriorityQueue<>(Collections.reverseOrder());
        // minHeap: mitad superior de los datos
        PriorityQueue<Integer> minHeapRight = new PriorityQueue<>();

        int[] stream = {5, 15, 1, 3, 8, 7, 9, 10, 6, 2};

        for (int num : stream) {
            // Insertar en el heap correspondiente
            if (maxHeapLeft.isEmpty() || num <= maxHeapLeft.peek()) {
                maxHeapLeft.offer(num);
            } else {
                minHeapRight.offer(num);
            }

            // Balancear: la diferencia de tamaño no debe ser > 1
            if (maxHeapLeft.size() > minHeapRight.size() + 1) {
                minHeapRight.offer(maxHeapLeft.poll());
            } else if (minHeapRight.size() > maxHeapLeft.size()) {
                maxHeapLeft.offer(minHeapRight.poll());
            }

            // Calcular mediana
            double median;
            if (maxHeapLeft.size() > minHeapRight.size()) {
                median = maxHeapLeft.peek();
            } else {
                median = (maxHeapLeft.peek() + minHeapRight.peek()) / 2.0;
            }

            System.out.printf("Insertar %2d → Mediana = %.1f  " +
                    "(izq=%s, der=%s)%n",
                    num, median, maxHeapLeft, minHeapRight);
        }
    }
}
