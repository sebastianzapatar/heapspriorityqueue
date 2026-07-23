import java.util.Arrays;

/**
 * Ejercicio 1 — Construir un Min-Heap manualmente (heapify bottom-up).
 *
 * Arreglo inicial: [45, 20, 14, 12, 31, 7, 11, 13, 7]
 *
 * Árbol binario completo inicial (lectura por niveles del arreglo):
 *
 *                 45(0)
 *              /        \
 *          20(1)         14(2)
 *          /   \         /   \
 *      12(3)  31(4)   7(5)  11(6)
 *      /   \
 *   13(7)  7(8)
 *
 * Heapify bottom-up: se aplica siftDown desde el último nodo NO hoja
 * (índice n/2 − 1 = 3) hasta la raíz (índice 0):
 *
 *   i=3: 12 > min(13, 7)=7  → swap(3,8) → [45, 20, 14, 7, 31, 7, 11, 13, 12]
 *   i=2: 14 > min(7, 11)=7  → swap(2,5) → [45, 20, 7, 7, 31, 14, 11, 13, 12]
 *   i=1: 20 > min(7, 31)=7  → swap(1,3) → [45, 7, 7, 20, 31, 14, 11, 13, 12]
 *        20 > min(13, 12)=12 → swap(3,8) → [45, 7, 7, 12, 31, 14, 11, 13, 20]
 *   i=0: 45 > min(7, 7)=7   → swap(0,1) → [7, 45, 7, 12, 31, 14, 11, 13, 20]
 *        45 > min(12, 31)=12 → swap(1,3) → [7, 12, 7, 45, 31, 14, 11, 13, 20]
 *        45 > min(13, 20)=13 → swap(3,7) → [7, 12, 7, 13, 31, 14, 11, 45, 20]
 *
 * Arreglo final (Min-Heap): [7, 12, 7, 13, 31, 14, 11, 45, 20]
 *
 *                  7(0)
 *              /        \
 *          12(1)         7(2)
 *          /   \         /   \
 *      13(3)  31(4)  14(5)  11(6)
 *      /   \
 *   45(7)  20(8)
 *
 * Este programa reproduce el proceso e imprime cada paso para comprobarlo.
 */
public class Ejercicio1 {

    public static void main(String[] args) {
        int[] arr = {45, 20, 14, 12, 31, 7, 11, 13, 7};

        System.out.println("Arreglo inicial: " + Arrays.toString(arr));
        System.out.println("\nÁrbol inicial:");
        imprimirArbol(arr);

        // Heapify bottom-up: siftDown desde el último padre hasta la raíz — O(n)
        int n = arr.length;
        System.out.println("\nAplicando heapify desde i = n/2 - 1 = " + (n / 2 - 1) + " hasta i = 0:");
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, i, n);
            System.out.println("  después de siftDown(" + i + "): " + Arrays.toString(arr));
        }

        System.out.println("\nArreglo final (Min-Heap): " + Arrays.toString(arr));
        System.out.println("\nÁrbol final:");
        imprimirArbol(arr);
    }

    /** Hunde el nodo i intercambiándolo con su hijo menor hasta cumplir la propiedad Min-Heap. */
    private static void siftDown(int[] arr, int i, int n) {
        while (true) {
            int menor = i;
            int izq = 2 * i + 1, der = 2 * i + 2;   // fórmulas de hijos

            if (izq < n && arr[izq] < arr[menor]) menor = izq;
            if (der < n && arr[der] < arr[menor]) menor = der;
            if (menor == i) return;                  // ya cumple la propiedad

            int tmp = arr[i];
            arr[i] = arr[menor];
            arr[menor] = tmp;
            i = menor;                               // seguir bajando
        }
    }

    /** Imprime el heap como árbol por niveles (raíz arriba). */
    private static void imprimirArbol(int[] arr) {
        int niveles = (int) (Math.log(arr.length) / Math.log(2)) + 1;
        int i = 0;
        for (int nivel = 0; nivel < niveles && i < arr.length; nivel++) {
            int nodosEnNivel = 1 << nivel;                     // 2^nivel
            int margen = (1 << (niveles - nivel)) - 1;         // sangría decreciente
            StringBuilder linea = new StringBuilder(" ".repeat(margen * 2));
            for (int j = 0; j < nodosEnNivel && i < arr.length; j++, i++) {
                linea.append(String.format("%-4d", arr[i])).append(" ".repeat(margen * 2));
            }
            System.out.println(linea);
        }
    }
}
