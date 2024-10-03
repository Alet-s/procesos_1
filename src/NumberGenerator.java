import java.util.Scanner;

/**
 * @author Alejandro Serrano
 */

/**
 * Genera números aleatorios y los imprime por consola.
 */
public class NumberGenerator {
    /**
     * Genera números aleatorios del 0 al 10 (ambos inclusive).
     */
    private int generateNumber() {
        return (int) (11 * Math.random());//Casteo a int
    }

//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);//Instancia de Scanner.
//        NumberGenerator ng = new NumberGenerator();//Instancia de clase generadora de números.
//        //Bucle para capturar el stream.
//        while (sc.hasNextLine()) {
//            sc.nextLine();//Saltamos la línea para reiniciar el bucle
//            System.out.println(ng.generateNumber());
//        }
//        sc.close();
//    }
}
