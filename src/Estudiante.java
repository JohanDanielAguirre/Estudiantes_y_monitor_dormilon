package src;

import java.util.concurrent.Semaphore;

public class Estudiante implements Runnable {
    private int idEstudiante;
    private Monitor monitor;
    private boolean atendido;

    public Estudiante(int idEstudiante, Monitor monitor) {
        this.idEstudiante = idEstudiante;
        this.monitor = monitor;
        atendido = false;
    }

    public void atencionEstudiante() {
        try {
            if (monitor.monitorDormido.availablePermits() == 0) {
                System.out.println("Monitor se esta despertando");
                Thread.sleep(5000);
                System.out.println("Monitor se ha despertado y está listo para atender.");
                monitor.monitorDormido.release();
            }
            System.out.println("Estudiante " + idEstudiante + ": Esta siendo atendido");
            Thread.sleep(3000);
            System.out.println("Estudiante " + idEstudiante + ": Ha terminado");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            while (!atendido) {
                int espera;
                //Toca tener en cuenta un mutex cuando se vaya a usar
                //monitor.mutex.acquire();

                System.out.println("Estudiante " + idEstudiante + ": Llega a la monitoria");
                if(monitor.sillasDisponibles.availablePermits() > 0 ) {
                    monitor.sillasDisponibles.acquire();
                    System.out.println("Estudiante " + idEstudiante + ": Se sienta a esperar");
                    monitor.atencionMonitor.acquire();
                    monitor.sillasDisponibles.release();
                    atencionEstudiante();
                    monitor.atencionMonitor.release();
                    atendido = true;

                } else {
                    //Cuando no hay sillas disponibles el estudiante se va a dar una vuelta
                    espera = (int) (Math.random()*5)+ 2;
                    System.out.println("Estudiante " + idEstudiante + ": No encontro lugar, Regresara en: " + (espera) + "s");
                    Thread.sleep(espera*1000);
                }
            }
        }
         catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

