/**
 * Cola de urgencias que encapsula un MaxHeap de Pacientes.
 * Proporciona métodos específicos del dominio hospitalario.
 *
 * Los pacientes con mayor gravedad se atienden primero gracias
 * al MaxHeap, que mantiene al más grave siempre en la raíz.
 */
public class ColaUrgencias {
    private final MaxHeap<Paciente> heap;
    private int pacientesAtendidos;

    public ColaUrgencias() {
        this.heap = new MaxHeap<>();
        this.pacientesAtendidos = 0;
    }

    /**
     * Registra un nuevo paciente en la cola de urgencias.
     * El MaxHeap lo ubicará según su gravedad.
     */
    public void registrarPaciente(Paciente paciente) {
        heap.insert(paciente);
        System.out.printf("  ✚ Registrado: %-15s | %s | Gravedad: %d/10%n",
                paciente.getNombre(),
                paciente.getEnfermedad(),
                paciente.getGravedad());
    }

    /**
     * Atiende al paciente más grave (extractMax del heap).
     * Retorna el paciente atendido.
     */
    public Paciente atenderSiguiente() {
        Paciente paciente = heap.extractMax();
        pacientesAtendidos++;
        return paciente;
    }

    /**
     * Muestra quién es el siguiente sin atenderlo (peek).
     */
    public Paciente verSiguiente() {
        return heap.peek();
    }

    public boolean hayPacientes() {
        return !heap.isEmpty();
    }

    public int getPacientesEnEspera() {
        return heap.size();
    }

    public int getPacientesAtendidos() {
        return pacientesAtendidos;
    }
}
