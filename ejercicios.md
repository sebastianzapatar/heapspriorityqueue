# 📝 Ejercicios Prácticos — Heaps en Java

## 🟢 Nivel Básico

### Ejercicio 1: Construir un Min-Heap manualmente
Dado el siguiente arreglo: `[45, 20, 14, 12, 31, 7, 11, 13, 7]`

1. Dibuja el árbol binario completo correspondiente.
2. Aplica el algoritmo de **heapify** (bottom-up) para convertirlo en un Min-Heap.
3. Muestra el arreglo resultante después de heapify.

---

### Ejercicio 2: Operaciones básicas con PriorityQueue
Escribe un programa Java que:
1. Cree un `PriorityQueue<Integer>` (Min-Heap).
2. Inserte los valores: `50, 30, 40, 10, 20, 35, 25`.
3. Imprima el `peek()`.
4. Extraiga elementos con `poll()` hasta vaciar la cola, imprimiendo cada uno.
5. Repita todo usando un **Max-Heap** con `Collections.reverseOrder()`.

```java
// Esqueleto:
import java.util.PriorityQueue;
import java.util.Collections;

public class Ejercicio2 {
    public static void main(String[] args) {
        // TODO: Implementar Min-Heap y Max-Heap con PriorityQueue
    }
}
```

---

### Ejercicio 3: Heap con Strings
Usa un `PriorityQueue<String>` para:
1. Insertar los nombres: `"Zara", "Miguel", "Ana", "Carlos", "Diana"`.
2. Extraer todos en orden alfabético (Min-Heap).
3. Luego repetir extrayendo en orden inverso (Max-Heap).

---

## 🟡 Nivel Intermedio

### Ejercicio 4: K-ésimo elemento más grande
Dado un arreglo de enteros y un valor `k`, encuentra el **k-ésimo elemento más grande**.

**Ejemplo:**
- Input: `arr = [3, 2, 1, 5, 6, 4]`, `k = 2`
- Output: `5` (el segundo más grande)

**Pista:** Usa un Min-Heap de tamaño `k`. Al final, el `peek()` es la respuesta.

```java
public class Ejercicio4 {
    public static int kthLargest(int[] arr, int k) {
        // TODO: Implementar usando PriorityQueue
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 5, 6, 4};
        System.out.println("2do más grande: " + kthLargest(arr, 2)); // 5
        System.out.println("4to más grande: " + kthLargest(arr, 4)); // 3
    }
}
```

---

### Ejercicio 5: Ordenar un arreglo casi ordenado (K-sorted)
Un arreglo está **casi ordenado** si cada elemento está a lo más `k` posiciones de su posición final.

Dado `arr = [6, 5, 3, 2, 8, 10, 9]` con `k = 3`, ordénalo eficientemente usando un Min-Heap de tamaño `k+1`.

**Complejidad esperada:** O(n log k)

```java
import java.util.PriorityQueue;

public class Ejercicio5 {
    public static void sortKSorted(int[] arr, int k) {
        // TODO: Usar PriorityQueue de tamaño k+1
        // 1. Insertar los primeros k+1 elementos
        // 2. Para cada elemento restante:
        //    - poll() el mínimo al arreglo
        //    - offer() el siguiente elemento
        // 3. Vaciar el heap restante
    }

    public static void main(String[] args) {
        int[] arr = {6, 5, 3, 2, 8, 10, 9};
        sortKSorted(arr, 3);
        System.out.println(java.util.Arrays.toString(arr));
        // Esperado: [2, 3, 5, 6, 8, 9, 10]
    }
}
```

---

### Ejercicio 6: Merge K listas ordenadas
Dadas `k` listas ordenadas, combínalas en una sola lista ordenada.

**Ejemplo:**
```
Lista 1: [1, 4, 7]
Lista 2: [2, 5, 8]
Lista 3: [3, 6, 9]
Resultado: [1, 2, 3, 4, 5, 6, 7, 8, 9]
```

**Pista:** Usa un Min-Heap que almacene el valor actual y el índice de la lista.

```java
import java.util.*;

public class Ejercicio6 {
    public static List<Integer> mergeKLists(List<List<Integer>> lists) {
        // TODO: Usar PriorityQueue con comparador personalizado
        // Cada elemento del heap: [valor, indiceLista, indiceEnLista]
        return new ArrayList<>();
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
```

---

## 🔴 Nivel Avanzado

### Ejercicio 7: Mediana en flujo de datos
Implementa una clase `MedianFinder` que soporte:
- `addNum(int num)`: Agrega un número al flujo.
- `findMedian()`: Retorna la mediana actual.

**Usa la técnica de dos heaps** (maxHeap izquierdo + minHeap derecho).

```java
import java.util.*;

public class Ejercicio7 {

    static class MedianFinder {
        private PriorityQueue<Integer> maxHeap; // mitad inferior
        private PriorityQueue<Integer> minHeap; // mitad superior

        public MedianFinder() {
            // TODO: Inicializar heaps
        }

        public void addNum(int num) {
            // TODO: Insertar y balancear
        }

        public double findMedian() {
            // TODO: Calcular mediana
            return 0.0;
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
```

---

### Ejercicio 8: Reorganizar String sin caracteres adyacentes iguales
Dado un string, reorganiza los caracteres de modo que **ningún carácter adyacente sea igual**. Si no es posible, retorna `""`.

**Ejemplo:**
- Input: `"aab"` → Output: `"aba"`
- Input: `"aaab"` → Output: `""`

**Pista:** Usa un Max-Heap ordenado por frecuencia. En cada paso, toma el carácter con mayor frecuencia que no sea igual al último colocado.

```java
import java.util.*;

public class Ejercicio8 {
    public static String reorganize(String s) {
        // TODO:
        // 1. Contar frecuencia de cada carácter
        // 2. Crear Max-Heap ordenado por frecuencia
        // 3. En cada iteración, extraer el más frecuente
        //    (si es igual al último, tomar el segundo más frecuente)
        // 4. Decrementar frecuencia y reinsertar si > 0
        return "";
    }

    public static void main(String[] args) {
        System.out.println(reorganize("aab"));    // "aba" o "baa" no válido
        System.out.println(reorganize("aaab"));   // ""
        System.out.println(reorganize("aabb"));   // "abab" o "baba"
    }
}
```

---

### Ejercicio 9: Top K palabras más frecuentes
Dado un arreglo de strings y un entero `k`, retorna las `k` palabras más frecuentes, ordenadas de mayor a menor frecuencia. Si dos palabras tienen la misma frecuencia, ordénalas alfabéticamente.

```java
import java.util.*;

public class Ejercicio9 {
    public static List<String> topKFrequent(String[] words, int k) {
        // TODO:
        // 1. Contar frecuencias con Map
        // 2. Usar PriorityQueue con comparador compuesto
        //    (por frecuencia descendente, luego alfabético)
        // 3. Extraer los K primeros
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        String[] words = {"the", "day", "is", "sunny", "the", "the",
                          "the", "sunny", "is", "is"};
        System.out.println(topKFrequent(words, 4));
        // Esperado: [the, is, sunny, day]
    }
}
```

---

### Ejercicio 10: Implementar HeapSort in-place
Implementa el algoritmo **HeapSort** sin usar `PriorityQueue`, directamente sobre el arreglo.

**Pasos:**
1. Construir un Max-Heap in-place con heapify.
2. Intercambiar la raíz con el último elemento.
3. Reducir el tamaño del heap y hacer siftDown en la raíz.
4. Repetir hasta ordenar.

```java
public class Ejercicio10 {

    public static void heapSort(int[] arr) {
        int n = arr.length;

        // TODO: Paso 1 — Construir Max-Heap (heapify bottom-up)

        // TODO: Paso 2 — Extraer elementos uno a uno
        // Intercambiar arr[0] con arr[i], luego siftDown(arr, 0, i)
    }

    private static void siftDown(int[] arr, int index, int heapSize) {
        // TODO: Implementar siftDown para Max-Heap
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        System.out.println("Original: " + java.util.Arrays.toString(arr));
        heapSort(arr);
        System.out.println("Ordenado: " + java.util.Arrays.toString(arr));
        // Esperado: [5, 6, 7, 11, 12, 13]
    }
}
```

---

## 💡 Tabla de Referencia Rápida

| Ejercicio | Tema | Dificultad | Concepto Clave |
|-----------|------|------------|----------------|
| 1 | Heapify manual | 🟢 | Visualización de heap |
| 2 | PriorityQueue básico | 🟢 | Min/Max Heap con API |
| 3 | Heap con Strings | 🟢 | Comparable genérico |
| 4 | K-ésimo más grande | 🟡 | Min-Heap acotado |
| 5 | K-sorted array | 🟡 | Heap ventana deslizante |
| 6 | Merge K listas | 🟡 | Heap con metadata |
| 7 | Mediana streaming | 🔴 | Dos heaps balanceados |
| 8 | Reorganizar string | 🔴 | Max-Heap + greedy |
| 9 | Top K frecuentes | 🔴 | Heap + HashMap |
| 10 | HeapSort in-place | 🔴 | Heapify + extracción |
