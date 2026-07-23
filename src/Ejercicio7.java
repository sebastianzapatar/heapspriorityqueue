import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Ejercicio 7 — Mediana en flujo de datos (técnica de dos heaps).
 *
 * maxHeap: mitad INFERIOR de los números (su raíz es el mayor de los chicos).
 * minHeap: mitad SUPERIOR de los números (su raíz es el menor de los grandes).
 *
 * Invariantes: todo maxHeap ≤ todo minHeap, y sus tamaños difieren a lo más en 1
 * (maxHeap puede tener uno extra). Así la mediana siempre está en las raíces.
 *
 * addNum: O(log n) — findMedian: O(1).
 */
public class Ejercicio7 {

    static class MedianFinder {
        private final PriorityQueue<Integer> maxHeap; // mitad inferior
        private final PriorityQueue<Integer> minHeap; // mitad superior

        public MedianFinder() {
            maxHeap = new PriorityQueue<>(Collections.reverseOrder());
            minHeap = new PriorityQueue<>();
        }

        public void addNum(int num) {
            // 1. Entra por la mitad inferior…
            maxHeap.offer(num);
            // 2. …pero el mayor de los chicos pasa a los grandes,
            //    garantizando maxHeap ≤ minHeap
            minHeap.offer(maxHeap.poll());
            // 3. Rebalancear: maxHeap puede tener a lo más 1 elemento extra
            if (minHeap.size() > maxHeap.size()) {
                maxHeap.offer(minHeap.poll());
            }
        }

        public double findMedian() {
            if (maxHeap.size() > minHeap.size()) {
                return maxHeap.peek();                            // total impar
            }
            return (maxHeap.peek() + minHeap.peek()) / 2.0;       // total par
        }
    }

    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        mf.addNum(1);
        System.out.println(mf.findMedian()); // 1.0
        mf.addNum(2);
        System.out.println(mf.findMedian()); // 1.5
        mf.addNum(3);
        System.out.println(mf.findMedian()); // 2.0
        mf.addNum(4);
        System.out.println(mf.findMedian()); // 2.5
        mf.addNum(5);
        System.out.println(mf.findMedian()); // 3.0
    }
}
