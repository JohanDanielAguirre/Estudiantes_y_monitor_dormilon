package src;

import java.util.concurrent.Semaphore;

public class Estudiante implements Runnable {
    private int idEstudiante;
    private Monitor monitor;

    public Estudiante(int idEstudiante, Monitor monitor) {
        this.idEstudiante = idEstudiante;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        monitor.llegarEstudiante(idEstudiante);
    }
}

