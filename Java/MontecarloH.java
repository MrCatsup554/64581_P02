import java.util.concurrent.ThreadLocalRandom;

public class MontecarloH {
    static class Worker implements Runnable {
        private final long iteraciones;
        private final int idx;
        private final long[] resultados;

        public Worker(long iteraciones, int idx, long[] resultados) {
            this.iteraciones = iteraciones;
            this.idx = idx;
            this.resultados = resultados;
        }

        @Override
        public void run() {
            long dentro = 0;
            ThreadLocalRandom rand = ThreadLocalRandom.current();
            for (long i = 0; i < iteraciones; i++) {
                double x = rand.nextDouble();
                double y = rand.nextDouble();
                if (x * x + y * y <= 1.0) {
                    dentro++;
                }
            }
            resultados[idx] = dentro;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.nanoTime();
        int numHilos = 4;
        long totalPuntos = 1000000;
        long[] puntosPorHilo = new long[numHilos];
        long base = totalPuntos / numHilos;
        long resto = totalPuntos % numHilos;
        for (int i = 0; i < numHilos; i++) {
            puntosPorHilo[i] = base + (i < resto ? 1 : 0);
        }

        Thread[] hilos = new Thread[numHilos];
        long[] resultados = new long[numHilos];

        for (int i = 0; i < numHilos; i++) {
            hilos[i] = new Thread(new Worker(puntosPorHilo[i], i, resultados));
            hilos[i].start();
        }

        for (int i = 0; i < numHilos; i++) {
            hilos[i].join();
        }

        long totalDentro = 0;
        for (int i = 0; i < numHilos; i++) {
            totalDentro += resultados[i];
        }

        double piAprox = 4.0 * totalDentro / totalPuntos;
        System.out.println("Aproximación de PI: " + piAprox);
        long end = System.nanoTime();
        double tiempo = (end - start) / 1_000_000_000.0;
        System.out.println("Tiempo de ejecución: " + tiempo + " s");
    }
}