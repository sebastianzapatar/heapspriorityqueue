import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementación genérica de un Max-Heap.
 *
 * En un Max-Heap, el elemento más grande siempre está en la raíz.
 * Cada padre es mayor o igual que sus hijos.
 *
 * Se almacena internamente en un ArrayList (representación por arreglo).
 *
 * Fórmulas de navegación (índice base 0):
 *   - Padre de i:       (i - 1) / 2
 *   - Hijo izquierdo:   2 * i + 1
 *   - Hijo derecho:     2 * i + 2
 *
 * La única diferencia con MinHeap es la dirección de las comparaciones:
 * aquí un elemento "gana" y sube/queda arriba cuando es MAYOR, no menor.
 *
 * @param <T> Tipo de dato que debe ser Comparable
 */
public class MaxHeap<T extends Comparable<T>> {

    private final List<T> heap;

    public MaxHeap() {
        this.heap = new ArrayList<>();
    }

    /**
     * Construye un Max-Heap a partir de una lista existente.
     * Copia los elementos y aplica heapify bottom-up (buildHeap).
     * Complejidad: O(n) — más eficiente que insertar uno a uno O(n log n)
     */
    public MaxHeap(List<T> items) {
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
     * Extrae y retorna el elemento máximo (raíz).
     * Reemplaza la raíz con el último elemento y lo baja (sift down).
     * Complejidad: O(log n)
     */
    public T extractMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("El heap está vacío");
        }

        T max = heap.get(0);
        T lastElement = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastElement);
            siftDown(0);
        }

        return max;
    }

    /**
     * Retorna el elemento máximo sin eliminarlo.
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
     * Sube un elemento hasta que se cumpla la propiedad del Max-Heap.
     * Se usa después de insertar un elemento al final.
     * Diferencia clave con MinHeap: aquí subimos si el hijo es MAYOR
     * que el padre (compareTo > 0 en lugar de < 0).
     */
    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            // Si el hijo es mayor que el padre, intercambiar
            if (heap.get(index).compareTo(heap.get(parentIndex)) > 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break; // Ya está en la posición correcta
            }
        }
    }

    /**
     * Baja un elemento hasta que se cumpla la propiedad del Max-Heap.
     * Se usa después de extraer la raíz.
     * Diferencia clave con MinHeap: buscamos el MAYOR de los hijos
     * (en vez del menor) para decidir con quién intercambiar.
     */
    private void siftDown(int index) {
        int size = heap.size();

        while (true) {
            int largest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

            // Comparar con hijo izquierdo
            if (left < size && heap.get(left).compareTo(heap.get(largest)) > 0) {
                largest = left;
            }
            // Comparar con hijo derecho
            if (right < size && heap.get(right).compareTo(heap.get(largest)) > 0) {
                largest = right;
            }

            // Si el mayor no es el actual, intercambiar y continuar
            if (largest != index) {
                swap(index, largest);
                index = largest;
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

    /**
     * Intercambia los elementos en las posiciones i y j del arreglo.
     */
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Representación textual simple del heap (orden de arreglo, no de árbol).
     */
    @Override
    public String toString() {
        return "MaxHeap" + heap.toString();
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

        System.out.println("=== Max-Heap (Árbol por niveles) ===");
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
        System.out.println("║     DEMO: MaxHeap<T> Genérico       ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        // --- Insertar uno a uno (siftUp en cada insert) ---
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        int[] values = {10, 40, 20, 50, 30, 15, 35};

        System.out.println("Insertando: ");
        for (int v : values) {
            System.out.print(v + " ");
            maxHeap.insert(v);
        }
        System.out.println("\n");
        maxHeap.printTree();

        // --- Consultar y extraer la raíz (el máximo) ---
        System.out.println("\nPeek (máximo): " + maxHeap.peek());
        System.out.println("ExtractMax: " + maxHeap.extractMax());
        System.out.println("Nuevo máximo: " + maxHeap.peek());

        // --- Extraer todo produce orden descendente (heap sort) ---
        System.out.println("\nExtraer todos en orden descendente:");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.extractMax() + " ");
        }
        System.out.println();

        // --- Construir desde lista (heapify O(n)) ---
        System.out.println("\n--- Construir Max-Heap desde lista ---");
        List<Integer> data = List.of(5, 15, 10, 30, 20, 25);
        System.out.println("Datos originales: " + data);
        MaxHeap<Integer> builtHeap = new MaxHeap<>(new ArrayList<>(data));
        builtHeap.printTree();
    }
}
