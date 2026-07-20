/**
 * Representa un paciente en la sala de urgencias.
 * Implementa Comparable para que el MaxHeap pueda ordenar
 * automáticamente por gravedad (mayor gravedad = mayor prioridad).
 *
 * Escala de gravedad (1-10):
 *   1-3  → Leve      (dolor de cabeza, gripe leve...)
 *   4-6  → Moderado  (fracturas simples, fiebre alta...)
 *   7-8  → Grave     (hemorragias, convulsiones...)
 *   9-10 → Crítico   (paro cardíaco, trauma severo...)
 */
public class Paciente implements Comparable<Paciente> {
    private final String nombre;
    private final int edad;
    private final String enfermedad;
    private final int gravedad; // 1 (leve) a 10 (crítico)
    private final String horaLlegada;

    public Paciente(String nombre, int edad, String enfermedad, int gravedad, String horaLlegada) {
        if (gravedad < 1 || gravedad > 10) {
            throw new IllegalArgumentException("La gravedad debe estar entre 1 y 10");
        }
        this.nombre = nombre;
        this.edad = edad;
        this.enfermedad = enfermedad;
        this.gravedad = gravedad;
        this.horaLlegada = horaLlegada;
    }

    // --- Getters ---
    public String getNombre()     { return nombre; }
    public int getEdad()          { return edad; }
    public String getEnfermedad() { return enfermedad; }
    public int getGravedad()      { return gravedad; }
    public String getHoraLlegada(){ return horaLlegada; }

    /**
     * Orden natural: comparación por gravedad únicamente.
     * El MaxHeap usa compareTo > 0 para decidir quién sube,
     * así que el paciente con MAYOR gravedad queda en la raíz.
     *
     * Nota: el desempate por hora de llegada (FIFO entre pacientes
     * con la misma gravedad) no va aquí — lo define el Comparator
     * ORDEN_ATENCION de ColaUrgencias.
     */
    @Override
    public int compareTo(Paciente otro) {
        return Integer.compare(this.gravedad, otro.gravedad);
    }

    /**
     * Retorna la categoría de triaje según la gravedad.
     */
    public String getCategoriaTriage() {
        if (gravedad >= 9) return "🔴 CRÍTICO";
        if (gravedad >= 7) return "🟠 GRAVE";
        if (gravedad >= 4) return "🟡 MODERADO";
        return "🟢 LEVE";
    }

    @Override
    public String toString() {
        return String.format("%-20s | Edad: %2d | %-30s | Gravedad: %2d/10 %-12s | Llegó: %s",
                nombre, edad, enfermedad, gravedad, getCategoriaTriage(), horaLlegada);
    }
}
