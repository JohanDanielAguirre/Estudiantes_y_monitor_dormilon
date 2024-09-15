package src;

import java.util.concurrent.Semaphore;

public class Monitor {
    private Semaphore sillasDisponibles;
    private Semaphore monitorDormido;
    private Semaphore mutex;

    public Monitor(int numSillas) {
        this.sillasDisponibles = new Semaphore(numSillas); // Sillas en el corredor
        this.monitorDormido = new Semaphore(0); // El monitor comienza dormido
        this.mutex = new Semaphore(1); // Mutex para controlar el acceso a la silla de atención
    }

    public void atenderEstudiante(int idEstudiante) {
        try {
            mutex.acquire(); // Controlar el acceso a la silla del monitor
            System.out.println("Monitor está atendiendo al estudiante " + idEstudiante);
            Thread.sleep(2000); // Simula el tiempo de atención
            System.out.println("Monitor ha terminado de atender al estudiante " + idEstudiante);
            mutex.release(); // Libera la silla del monitor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void monitor() {
        while (true) {
            try {
                // Espera que haya algún estudiante para despertar al monitor
                monitorDormido.acquire();
                System.out.println("Monitor se ha despertado y está listo para atender.");

                // Atender al estudiante (quien ya ha adquirido el mutex)
                atenderEstudiante(-1);

                if (sillasDisponibles.availablePermits() == 3) {
                    System.out.println("Monitor no tiene más estudiantes, se va a dormir.");
                    monitorDormido.release();
                    System.exit(000);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void llegarEstudiante(int idEstudiante) {
        try {
            // Verificar si hay sillas disponibles
            if (sillasDisponibles.tryAcquire()) {
                System.out.println("Estudiante " + idEstudiante + " se sienta en una silla del corredor.");

                // Si el monitor está dormido, lo despierta
                monitorDormido.release();

                // Espera turno para ser atendido
                atenderEstudiante(idEstudiante);

                // Libera una silla cuando termina
                sillasDisponibles.release();
            } else {
                System.out.println("No hay sillas disponibles. El estudiante " + idEstudiante + " se va y regresa más tarde.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
