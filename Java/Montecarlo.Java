public class Montecarlo {
    static class Worker extends Thread {
        private long puntosDentro = 0;
        private long iteraciones;

        public Worker(long iteraciones) {
            this.iteraciones = iteraciones;
        }

        public void run() {
            for (long i = 0; i < iteraciones; i++) {
                double x = Math.random();
                double y = Math.random();
                if (x * x + y * y <= 1.0) {
                    puntosDentro++;
                }
            }
        }

        public long getPuntosDentro() {
            return puntosDentro;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numHilos = 4;
        long totalPuntos = 1000000;
        long puntosPorHilo = totalPuntos / numHilos;
        long resto = totalPuntos % numHilos;

        Worker[] hilos = new Worker[numHilos];

        for (int i = 0; i < numHilos; i++) {
            long iteraciones = puntosPorHilo + (i < resto ? 1 : 0);
            hilos[i] = new Worker(iteraciones);
            hilos[i].start();
        }

        long totalDentro = 0;
        for (int i = 0; i < numHilos; i++) {
            hilos[i].join();
            totalDentro += hilos[i].getPuntosDentro();
        }

        double piAprox = 4.0 * totalDentro / totalPuntos;
        System.out.println("Aproximación de PI: " + piAprox);
    }
}