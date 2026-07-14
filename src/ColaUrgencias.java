import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Cola de urgencias que usa java.util.PriorityQueue (Max-Heap).
 *
 * PriorityQueue por defecto es un Min-Heap, así que usamos
 * Collections.reverseOrder() para invertirlo y que el paciente
 * con MAYOR gravedad se atienda primero.
 */
public class ColaUrgencias {
    private final PriorityQueue<Paciente> cola;
    private int pacientesAtendidos;

    public ColaUrgencias() {
        // Collections.reverseOrder() invierte el Comparable natural
        // → el paciente con mayor gravedad queda al frente
        this.cola = new PriorityQueue<>(Collections.reverseOrder());
        this.pacientesAtendidos = 0;
    }

    /**
     * Registra un nuevo paciente en la cola de urgencias.
     * offer() inserta y reordena en O(log n).
     */
    public void registrarPaciente(Paciente paciente) {
        cola.offer(paciente);
        System.out.printf("  ✚ Registrado: %-15s | %s | Gravedad: %d/10%n",
                paciente.getNombre(),
                paciente.getEnfermedad(),
                paciente.getGravedad());
    }

    /**
     * Atiende al paciente más grave (poll del PriorityQueue).
     * Retorna el paciente atendido.
     */
    public Paciente atenderSiguiente() {
        Paciente paciente = cola.poll();
        if (paciente == null) {
            throw new java.util.NoSuchElementException("No hay pacientes en espera");
        }
        pacientesAtendidos++;
        return paciente;
    }

    /**
     * Muestra quién es el siguiente sin atenderlo (peek).
     */
    public Paciente verSiguiente() {
        return cola.peek();
    }

    public boolean hayPacientes() {
        return !cola.isEmpty();
    }

    public int getPacientesEnEspera() {
        return cola.size();
    }

    public int getPacientesAtendidos() {
        return pacientesAtendidos;
    }
}
