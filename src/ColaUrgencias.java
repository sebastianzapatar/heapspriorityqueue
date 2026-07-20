import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Cola de urgencias que usa java.util.PriorityQueue (Max-Heap).
 *
 * PriorityQueue por defecto es un Min-Heap, así que invertimos la
 * comparación por gravedad para que el paciente MÁS GRAVE quede al frente.
 *
 * Desempate: PriorityQueue no es estable (con prioridades iguales no
 * garantiza orden de llegada), por eso el comparador desempata por
 * horaLlegada → entre dos pacientes con la misma gravedad se atiende
 * primero al que llegó antes (FIFO).
 */
public class ColaUrgencias {

    /**
     * Criterio de atención:
     *   1. Mayor gravedad primero (comparingInt(...).reversed()).
     *   2. A igual gravedad, menor hora de llegada primero.
     * La hora en formato "HH:mm" se compara bien como String
     * porque el orden lexicográfico coincide con el cronológico.
     */
    private static final Comparator<Paciente> ORDEN_ATENCION =
            Comparator.comparingInt(Paciente::getGravedad).reversed()
                      .thenComparing(Paciente::getHoraLlegada);

    private final PriorityQueue<Paciente> cola;
    private int pacientesAtendidos;

    public ColaUrgencias() {
        this.cola = new PriorityQueue<>(ORDEN_ATENCION);
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
