public class HeapSort {

    /**
     * Ordena un arreglo utilizando el algoritmo HeapSort.
     * 
     * @param arr el arreglo a ordenar
     */
    public void sort(int arr[]) {
        int n = arr.length;

        // Construir el Max Heap (reorganizar el arreglo)
        // Empezamos desde el último nodo no hoja y vamos hacia la raíz
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extraer elementos del heap uno por uno
        for (int i = n - 1; i > 0; i--) {
            // Mover la raíz actual (el elemento más grande) al final del arreglo
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Llamar a max heapify en el heap reducido
            heapify(arr, i, 0);
        }
    }

    /**
     * Convierte un subárbol con raíz en el nodo i en un Max Heap, asumiendo
     * que los subárboles ya son Max Heaps.
     * 
     * @param arr el arreglo que representa el heap
     * @param n   el tamaño del heap
     * @param i   el índice de la raíz del subárbol
     */
    void heapify(int arr[], int n, int i) {
        int largest = i; // Inicializar el más grande como la raíz
        int left = 2 * i + 1; // Índice del hijo izquierdo
        int right = 2 * i + 2; // Índice del hijo derecho

        // Si el hijo izquierdo es más grande que la raíz
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // Si el hijo derecho es más grande que el más grande hasta ahora
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // Si el más grande no es la raíz
        if (largest != i) {
            // Intercambiar la raíz con el más grande
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Llamar recursivamente a heapify en el subárbol afectado
            heapify(arr, n, largest);
        }
    }

    // Método principal para probar la implementación
    public static void main(String args[]) {
        int arr[] = { 12, 11, 13, 5, 6, 7 };
        int n = arr.length;

        HeapSort ob = new HeapSort();
        ob.sort(arr);

        System.out.println("Arreglo ordenado es");
        for (int i = 0; i < n; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
