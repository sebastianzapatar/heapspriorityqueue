import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Comparator;

/**
 * Ejemplos de uso de java.util.PriorityQueue para Min-Heap y Max-Heap.
 * 
 * PriorityQueue en Java es por defecto un MIN-HEAP.
 * Para crear un MAX-HEAP hay tres formas principales:
 *   1. Collections.reverseOrder()
 *   2. Comparator.reverseOrder()
 *   3. Lambda con comparación invertida
 */
public class PriorityQueueExamples {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║  PriorityQueue: Min-Heap y Max-Heap     ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        demoMinHeap();
        demoMaxHeapCollectionsReverse();
        demoMaxHeapComparatorReverse();
        demoMaxHeapLambda();
        demoWithCustomObject();
    }

    // =====================================================
    // 1. MIN-HEAP (Comportamiento por defecto)
    // =====================================================
    static void demoMinHeap() {
        System.out.println("═══ 1. MIN-HEAP (PriorityQueue por defecto) ═══");

        // PriorityQueue es Min-Heap por defecto
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Insertar elementos
        minHeap.offer(35);  // offer() o add() para insertar
        minHeap.offer(10);
        minHeap.offer(25);
        minHeap.offer(5);
        minHeap.offer(15);

        System.out.println("PriorityQueue: " + minHeap);
        System.out.println("Peek (mínimo): " + minHeap.peek());

        System.out.print("Extracción en orden: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " "); // poll() extrae el mínimo
        }
        System.out.println("\n");
    }

    // =====================================================
    // 2. MAX-HEAP con Collections.reverseOrder()
    // =====================================================
    static void demoMaxHeapCollectionsReverse() {
        System.out.println("═══ 2. MAX-HEAP con Collections.reverseOrder() ═══");

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        maxHeap.offer(35);
        maxHeap.offer(10);
        maxHeap.offer(25);
        maxHeap.offer(5);
        maxHeap.offer(15);

        System.out.println("PriorityQueue: " + maxHeap);
        System.out.println("Peek (máximo): " + maxHeap.peek());

        System.out.print("Extracción en orden: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println("\n");
    }

    // =====================================================
    // 3. MAX-HEAP con Comparator.reverseOrder()
    // =====================================================
    static void demoMaxHeapComparatorReverse() {
        System.out.println("═══ 3. MAX-HEAP con Comparator.reverseOrder() ═══");

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        maxHeap.offer(20);
        maxHeap.offer(40);
        maxHeap.offer(30);
        maxHeap.offer(10);

        System.out.println("Peek (máximo): " + maxHeap.peek());

        System.out.print("Extracción en orden: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println("\n");
    }

    // =====================================================
    // 4. MAX-HEAP con Lambda
    // =====================================================
    static void demoMaxHeapLambda() {
        System.out.println("═══ 4. MAX-HEAP con Lambda ═══");

        // Lambda: invertir la comparación natural
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

        maxHeap.offer(100);
        maxHeap.offer(50);
        maxHeap.offer(75);
        maxHeap.offer(200);
        maxHeap.offer(25);

        System.out.println("Peek (máximo): " + maxHeap.peek());

        System.out.print("Extracción en orden: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println("\n");
    }

    // =====================================================
    // 5. PriorityQueue con Objetos Personalizados
    // =====================================================
    static void demoWithCustomObject() {
        System.out.println("═══ 5. PriorityQueue con Objetos Personalizados ═══");

        // Clase interna para representar una Tarea
        record Tarea(String nombre, int prioridad) {}

        // Min-Heap por prioridad (menor número = mayor prioridad)
        PriorityQueue<Tarea> colaTareas = new PriorityQueue<>(
            Comparator.comparingInt(Tarea::prioridad)
        );

        colaTareas.offer(new Tarea("Enviar correo", 3));
        colaTareas.offer(new Tarea("Bug crítico en producción", 1));
        colaTareas.offer(new Tarea("Actualizar documentación", 5));
        colaTareas.offer(new Tarea("Revisar PR", 2));
        colaTareas.offer(new Tarea("Reunión de equipo", 4));

        System.out.println("Procesando tareas por prioridad (1 = más urgente):");
        while (!colaTareas.isEmpty()) {
            Tarea t = colaTareas.poll();
            System.out.printf("  [Prioridad %d] %s%n", t.prioridad(), t.nombre());
        }

        System.out.println();

        // Max-Heap por prioridad (mayor número = se procesa primero)
        PriorityQueue<Tarea> colaInversa = new PriorityQueue<>(
            Comparator.comparingInt(Tarea::prioridad).reversed()
        );

        colaInversa.offer(new Tarea("Tarea A", 3));
        colaInversa.offer(new Tarea("Tarea B", 1));
        colaInversa.offer(new Tarea("Tarea C", 5));
        colaInversa.offer(new Tarea("Tarea D", 2));

        System.out.println("Max-Heap de tareas (mayor prioridad primero):");
        while (!colaInversa.isEmpty()) {
            Tarea t = colaInversa.poll();
            System.out.printf("  [Prioridad %d] %s%n", t.prioridad(), t.nombre());
        }
    }
}
