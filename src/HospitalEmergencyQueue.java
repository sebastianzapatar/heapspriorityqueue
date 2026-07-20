/**
 * ╔═══════════════════════════════════════════════════════════════╗
 * ║       COLA DE URGENCIAS DE HOSPITAL CON MAX-HEAP            ║
 * ╠═══════════════════════════════════════════════════════════════╣
 * ║  Los pacientes se atienden según la GRAVEDAD de su          ║
 * ║  enfermedad. Mayor gravedad = se atiende primero.           ║
 * ║  A igual gravedad, se atiende al que llegó primero (FIFO).  ║
 * ║                                                             ║
 * ║  Clases utilizadas:                                         ║
 * ║    - Paciente.java      → Modelo del paciente               ║
 * ║    - ColaUrgencias.java → PriorityQueue<Paciente> (MaxHeap  ║
 * ║      por gravedad + desempate por hora de llegada)          ║
 * ╚═══════════════════════════════════════════════════════════════╝
 */
public class HospitalEmergencyQueue {

    public static void main(String[] args) {

        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🏥  SISTEMA DE COLA DE URGENCIAS - HOSPITAL CENTRAL  🏥                          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════════════════════════════════════════╝\n");

        ColaUrgencias urgencias = new ColaUrgencias();

        // ─── Fase 1: Llegan pacientes durante la mañana ───
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📋 FASE 1: Registro de pacientes (7:00 - 9:00 AM)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        urgencias.registrarPaciente(new Paciente(
            "María García",    45, "Dolor de cabeza persistente",  2, "07:15"));

        urgencias.registrarPaciente(new Paciente(
            "Carlos López",    67, "Dolor torácico agudo",         9, "07:30"));

        urgencias.registrarPaciente(new Paciente(
            "Ana Martínez",    32, "Fractura de muñeca",           5, "07:45"));

        urgencias.registrarPaciente(new Paciente(
            "Pedro Sánchez",    8, "Fiebre alta (40°C)",           6, "08:00"));

        urgencias.registrarPaciente(new Paciente(
            "Laura Rodríguez", 55, "Accidente cerebrovascular",   10, "08:15"));

        urgencias.registrarPaciente(new Paciente(
            "José Hernández",  28, "Esguince de tobillo",          3, "08:30"));

        urgencias.registrarPaciente(new Paciente(
            "Rosa Díaz",       72, "Hemorragia digestiva",         8, "08:45"));

        // Misma gravedad (5) que Ana Martínez, pero llegó después:
        // el desempate por hora de llegada atenderá primero a Ana
        urgencias.registrarPaciente(new Paciente(
            "Elena Vargas",    51, "Fractura de cadera",           5, "08:50"));

        urgencias.registrarPaciente(new Paciente(
            "Miguel Torres",   19, "Gripe común",                  1, "09:00"));

        System.out.printf("%n  📊 Pacientes en espera: %d%n", urgencias.getPacientesEnEspera());

        // ─── Fase 2: Mostrar el siguiente paciente ───
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("👁️  SIGUIENTE EN SER ATENDIDO (peek):");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        Paciente siguiente = urgencias.verSiguiente();
        System.out.println("  → " + siguiente);

        // ─── Fase 3: Atender pacientes en orden de gravedad ───
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🩺  FASE 2: Atención de pacientes (orden por GRAVEDAD - MaxHeap)");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        int turno = 1;
        int pacientesAAtender = 4;
        while (urgencias.hayPacientes() && turno <= pacientesAAtender) {
            Paciente atendido = urgencias.atenderSiguiente();
            System.out.printf("  Turno %d: %s%n", turno, atendido);
            turno++;
        }

        System.out.printf("%n  📊 Pacientes atendidos: %d | En espera: %d%n",
                urgencias.getPacientesAtendidos(),
                urgencias.getPacientesEnEspera());

        // ─── Fase 4: Llega una emergencia nueva ───
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🚨 FASE 3: ¡EMERGENCIA! Nuevo paciente crítico");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        urgencias.registrarPaciente(new Paciente(
            "Diego Ramírez",   40, "Paro cardiorrespiratorio",    10, "10:30"));

        System.out.println("\n  ⚠️  El nuevo paciente crítico pasa al FRENTE de la cola automáticamente.");
        System.out.printf("  → Siguiente: %s%n", urgencias.verSiguiente().getNombre());

        // ─── Fase 5: Atender todos los restantes ───
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🩺  FASE 4: Atención de pacientes restantes");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        while (urgencias.hayPacientes()) {
            Paciente atendido = urgencias.atenderSiguiente();
            System.out.printf("  Turno %d: %s%n", turno, atendido);
            turno++;
        }

        // ─── Resumen final ───
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("📊 RESUMEN DEL TURNO");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("  Total pacientes atendidos: %d%n", urgencias.getPacientesAtendidos());
        System.out.printf("  Pacientes en espera:       %d%n", urgencias.getPacientesEnEspera());
        System.out.println("\n  ✅ Todos los pacientes fueron atendidos.");

        // ─── Explicación del MaxHeap ───
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║  📚 ¿CÓMO FUNCIONA EL MAX-HEAP EN ESTE EJEMPLO?             ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                               ║");
        System.out.println("║  1. ColaUrgencias usa un Comparator explícito:                ║");
        System.out.println("║     → gravedad DESC + hora de llegada ASC (desempate FIFO)    ║");
        System.out.println("║                                                               ║");
        System.out.println("║  2. PriorityQueue usa ese Comparator internamente             ║");
        System.out.println("║     → El paciente con MAYOR gravedad sube a la raíz           ║");
        System.out.println("║     → A igual gravedad, se atiende al que llegó PRIMERO       ║");
        System.out.println("║       (ej: Ana 07:45 antes que Elena 08:50, ambas gravedad 5) ║");
        System.out.println("║                                                               ║");
        System.out.println("║  3. poll() siempre retorna el más grave                       ║");
        System.out.println("║     → O(log n) para reorganizar el heap                      ║");
        System.out.println("║                                                               ║");
        System.out.println("║  4. offer() agrega y reordena automáticamente                 ║");
        System.out.println("║     → Un paciente crítico nuevo sube al tope al instante      ║");
        System.out.println("║                                                               ║");
        System.out.println("║  Complejidades:                                               ║");
        System.out.println("║     offer() → O(log n)                                        ║");
        System.out.println("║     poll()  → O(log n)                                        ║");
        System.out.println("║     peek()  → O(1)                                            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
    }
}
