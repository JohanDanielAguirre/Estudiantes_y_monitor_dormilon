package src;

public class Main {
    public static void main(String[] args) {
        final int numSillas = 3;
        Monitor monitor = new Monitor(numSillas);

        // Crear hilo del monitor
        Thread hiloMonitor = new Thread(() -> monitor.monitor());
        hiloMonitor.start();

        // Crear hilos para los estudiantes
        for (int i = 1; i <= 6; i++) {
            Estudiante estudiante = new Estudiante(i, monitor);
            Thread hiloEstudiante = new Thread(estudiante);
            hiloEstudiante.start();
            try {
                Thread.sleep(1000); // Simula la llegada de estudiantes en intervalos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
