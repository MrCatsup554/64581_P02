public class MontecarloNH {
    public static void main(String[] args) {
        long start = System.nanoTime();
        long totalPuntos = 1000000;
        long puntosDentro = 0;

        for (long i = 0; i < totalPuntos; i++) {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1.0) {
                puntosDentro++;
            }
        }

        double piAprox = 4.0 * puntosDentro / totalPuntos;
        System.out.println("Aproximación de PI: " + piAprox);
        long end = System.nanoTime();
        double tiempo = (end - start) / 1_000_000_000.0;
        System.out.println("Tiempo de ejecución: " + tiempo + " s");
    }
}