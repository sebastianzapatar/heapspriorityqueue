import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementación genérica de un Min-Heap.
 * 
 * En un Min-Heap, el elemento más pequeño siempre está en la raíz.
 * Cada padre es menor o igual que sus hijos.
 * 
 * Se almacena internamente en un ArrayList (representación por arreglo).
 * 
 * Fórmulas de navegación (índice base 0):
 *   - Padre de i:       (i - 1) / 2
 *   - Hijo izquierdo:   2 * i + 1
 *   - Hijo derecho:     2 * i + 2
 * 
 * @param <T> Tipo de dato que debe ser Comparable
 */
public class MinHeap<T extends Comparable<T>> {

    private final List<T> heap;

    public MinHeap() {
        this.heap = new ArrayList<>();
    }

    /**
     * Construye un Min-Heap a partir de una lista existente.
     * Usa el algoritmo de heapify bottom-up con complejidad O(n).
     */
    public MinHeap(List<T> items) {
        this.heap = new ArrayList<>(items);
        buildHeap();
    }

    // ==================== OPERACIONES PRINCIPALES ====================

    /**
     * Inserta un elemento en el heap.
     * Lo agrega al final y lo sube (sift up) hasta restaurar la propiedad.
     * Complejidad: O(log n)
     */
    public void insert(T value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    /**
     * Extrae y retorna el elemento mínimo (raíz).
     * Reemplaza la raíz con el último elemento y lo baja (sift down).
     * Complejidad: O(log n)
     */
    public T extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("El heap está vacío");
        }

        T min = heap.get(0);
        T lastElement = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastElement);
            siftDown(0);
        }

        return min;
    }

    /**
     * Retorna el elemento mínimo sin eliminarlo.
     * Complejidad: O(1)
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("El heap está vacío");
        }
        return heap.get(0);
    }

    /**
     * Verifica si el heap está vacío.
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Retorna el número de elementos en el heap.
     */
    public int size() {
        return heap.size();
    }

    // ==================== OPERACIONES INTERNAS ====================

    /**
     * Sube un elemento hasta que se cumpla la propiedad del Min-Heap.
     * Se usa después de insertar un elemento al final.
     */
    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = getParentIndex(index);
            // Si el hijo es menor que el padre, intercambiar
            if (heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break; // Ya está en la posición correcta
            }
        }
    }

    /**
     * Baja un elemento hasta que se cumpla la propiedad del Min-Heap.
     * Se usa después de extraer la raíz.
     */
    private void siftDown(int index) {
        int size = heap.size();

        while (true) {
            int smallest = index;
            int left = getLeftChildIndex(index);
            int right = getRightChildIndex(index);

            // Comparar con hijo izquierdo
            if (left < size && heap.get(left).compareTo(heap.get(smallest)) < 0) {
                smallest = left;
            }

            // Comparar con hijo derecho
            if (right < size && heap.get(right).compareTo(heap.get(smallest)) < 0) {
                smallest = right;
            }

            // Si el menor no es el actual, intercambiar y continuar
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break; // Propiedad del heap restaurada
            }
        }
    }

    /**
     * Construye el heap desde un arreglo desordenado.
     * Aplica siftDown desde el último nodo no-hoja hasta la raíz.
     * Complejidad: O(n) — más eficiente que insertar uno a uno O(n log n)
     */
    private void buildHeap() {
        // El último nodo no-hoja está en (n/2 - 1)
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    // ==================== UTILIDADES ====================

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Representación visual del heap como árbol.
     */
    @Override
    public String toString() {
        return "MinHeap" + heap.toString();
    }

    /**
     * Imprime el heap como un árbol por niveles.
     */
    public void printTree() {
        if (isEmpty()) {
            System.out.println("(heap vacío)");
            return;
        }

        int level = 0;
        int count = 0;
        int levelSize = 1;

        System.out.println("=== Min-Heap (Árbol por niveles) ===");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < heap.size(); i++) {
            if (count == 0) {
                if (level > 0) {
                    System.out.println(sb.toString());
                    sb = new StringBuilder();
                }
                sb.append("Nivel ").append(level).append(": ");
            }
            sb.append(heap.get(i));
            if (i < heap.size() - 1 && count < levelSize - 1) {
                sb.append(", ");
            }

            count++;
            if (count == levelSize) {
                count = 0;
                levelSize *= 2;
                level++;
            }
        }
        System.out.println(sb.toString());
    }

    // ==================== DEMO ====================

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     DEMO: MinHeap<T> Genérico       ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // --- Min-Heap con Integer ---
        System.out.println("--- Min-Heap con Integer ---");
        MinHeap<Integer> intHeap = new MinHeap<>();
        int[] values = {35, 10, 25, 5, 15, 30, 20};

        // --- Insertar uno a uno (siftUp en cada insert) ---
        System.out.println("Insertando: ");
        for (int v : values) {
            System.out.print(v + " ");
            intHeap.insert(v);
        }
        System.out.println("\n");
        intHeap.printTree();

        // --- Consultar y extraer la raíz (el mínimo) ---
        System.out.println("\nPeek (mínimo): " + intHeap.peek());
        System.out.println("ExtractMin: " + intHeap.extractMin());
        System.out.println("Nuevo mínimo: " + intHeap.peek());
        intHeap.printTree();

        // --- Min-Heap con String: compareTo() usa orden alfabético ---
        System.out.println("\n--- Min-Heap con String ---");
        MinHeap<String> stringHeap = new MinHeap<>();
        String[] names = {"Carlos", "Ana", "Diana", "Bernardo", "Eva"};

        for (String name : names) {
            stringHeap.insert(name);
        }

        // Extraer todo produce orden ascendente (heap sort)
        System.out.println("Orden por extracción (alfabético):");
        while (!stringHeap.isEmpty()) {
            System.out.print(stringHeap.extractMin() + " ");
        }
        System.out.println();

        // --- Construir desde lista ---
        System.out.println("\n--- Construir Min-Heap desde lista (heapify O(n)) ---");
        List<Integer> data = List.of(50, 30, 40, 10, 20, 35, 25);
        System.out.println("Datos originales: " + data);
        MinHeap<Integer> builtHeap = new MinHeap<>(new ArrayList<>(data));
        builtHeap.printTree();
    }
}
