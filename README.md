# 🏔️ Heaps en Java — Estructuras de Datos

## 📋 Descripción

Este módulo cubre la estructura de datos **Heap** (Montículo) en Java. Incluye teoría, comparación con árboles binarios de búsqueda (BST), implementación genérica desde cero, y uso de `PriorityQueue` para Min-Heap y Max-Heap.

## 📁 Estructura del Proyecto

```
hepas/
├── README.md                    # Este archivo
├── index.html                   # Presentación interactiva HTML
├── ejercicios.md                # Ejercicios prácticos con heaps
└── src/
    ├── MinHeap.java             # Implementación genérica de Min-Heap
    ├── MaxHeap.java             # Implementación genérica de Max-Heap
    ├── PriorityQueueExamples.java  # Uso de PriorityQueue (Min y Max)
    └── HeapExamples.java        # Ejemplos adicionales y casos de uso
```

## 🧠 ¿Qué es un Heap?

Un **Heap** es un árbol binario **completo** que satisface la **propiedad del heap**:

- **Min-Heap**: El valor del padre es **menor o igual** que el de sus hijos → la raíz es el **mínimo**.
- **Max-Heap**: El valor del padre es **mayor o igual** que el de sus hijos → la raíz es el **máximo**.

## ⚡ Complejidad de Operaciones

| Operación       | Complejidad |
|-----------------|-------------|
| Insertar        | O(log n)    |
| Eliminar raíz   | O(log n)    |
| Consultar raíz  | O(1)        |
| Construir heap  | O(n)        |
| Buscar elemento | O(n)        |

## 🔄 Heap vs Árbol Binario de Búsqueda (BST)

| Característica        | Heap                          | BST                            |
|-----------------------|-------------------------------|--------------------------------|
| **Propiedad**         | Padre ≤/≥ hijos               | Izquierdo < Padre < Derecho    |
| **Estructura**        | Árbol binario **completo**    | No necesariamente completo     |
| **Acceso rápido a**   | Mínimo o Máximo (O(1))        | Cualquier elemento (O(log n))  |
| **Representación**    | Array (eficiente)             | Nodos enlazados                |
| **Uso principal**     | Colas de prioridad            | Búsquedas ordenadas            |
| **Ordenamiento**      | No mantiene orden total       | Mantiene orden total           |

## 🚀 Cómo ejecutar

```bash
# Compilar todos los archivos
javac src/*.java

# Ejecutar los ejemplos de PriorityQueue
java -cp src PriorityQueueExamples

# Ejecutar los ejemplos generales
java -cp src HeapExamples
```

## 📚 Referencias

- [Heap Implementation in Java — GeeksForGeeks](https://www.geeksforgeeks.org/java/heap-implementation-in-java/)
- [A Comprehensive Guide to Heaps in Java — Last9](https://last9.io/blog/heaps-in-java/)
- [Java PriorityQueue — Oracle Docs](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/PriorityQueue.html)
