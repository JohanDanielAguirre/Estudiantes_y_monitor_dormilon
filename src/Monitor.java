package src;

import java.util.concurrent.Semaphore;

public class Monitor {
    Semaphore sillasDisponibles;
    Semaphore monitorDormido;
    Semaphore atencionMonitor;
    Semaphore mutex;

    public boolean dormido;

    public Monitor(int numSillas) {
        this.sillasDisponibles = new Semaphore(numSillas); // Sillas en el corredor
        this.monitorDormido = new Semaphore(1); // El monitor comienza dormido
        this.atencionMonitor = new Semaphore(1); // Las personas que atiende el monitor a la vez
        this.mutex = new Semaphore(1); // Mutex para controlar cuando se vaya a acceder a memoria

    }

    public void monitor() {
        dormido = false;
        while (true ) {
            try {
                if (monitorDormido.availablePermits() == 1 && sillasDisponibles.availablePermits() == 3 && atencionMonitor.availablePermits() == 1) {
                    System.out.println("Monitor no tiene más estudiantes, se va a dormir.");
                    monitorDormido.acquire();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
