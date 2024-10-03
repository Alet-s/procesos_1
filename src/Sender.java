import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * @author Alejandro Serrano
 */
public class Sender {
    Process child;

    /**
     * Constructor: inicializa el subproceso que lanzará el programa deseado y después lo lanza formalmente.
     *
     * @param command1 Argumento 1 del array de Strings que tomará el constructor de ProcessBuilder.
     * @param command2 Argumento 2 del array de Strings que tomará el constructor de ProcessBuilder.
     * @param command3 Argumento 3 del array de Strings que tomará el constructor de ProcessBuilder.
     */
    public Sender(String command1, String command2, String command3) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command1, command2, command3);
        child = processBuilder.start();
    }

    /**
     * Getter de Process
     *
     * @return El subproceso sobre el que se están realizando operaciones.
     */
    public Process getChild() {
        return child;
    }

    /**
     * Abre un nuevo PrintStream mediante el que enviar caracteres al subproceso asociado.
     *
     * @return eL PrintStream asociado a la entrada estándar del subproceso.
     */
    public PrintStream sendToChild() {
        return new PrintStream(child.getOutputStream(), true);//Autoflush a true.
    }

    /**
     * Abre un nuevo BufferedReader utilizado para recibir el Stream emitido por la salida estándar del subproceso asociado.
     *
     * @return el BufferedReader asociado a la salida estándar del subproceso.
     */
    public BufferedReader readFromChild() {
        InputStreamReader isr = new InputStreamReader(child.getInputStream());
        return new BufferedReader(isr);
    }

    public static void main(String[] args) {
        Instant start = Instant.now();// Empezamos a contar el tiempo de ejecución transcurrido.
        Scanner scanner = new Scanner(System.in);// Instancia de Scanner
        Sender parent; // Declaración de Sender.

        // Instanciación de Sender y lanzamiento del subproceso.
        try {
            parent = new Sender("java", "-jar", ".\\out\\artifacts\\tarea_procesos_jar\\tarea_procesos.jar");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String lettersToSend;//String a ser enviado al subproceso.
        String numbersReturned;//Números a ser devueltos por el subproceso.
        while (!(lettersToSend = scanner.nextLine().strip()).equalsIgnoreCase("fin")) {

            //1- Envíamos las letras.
            parent.sendToChild().println(lettersToSend);

            //2- Capturamos el Stream del subproceso.
            try {
                System.out.println(numbersReturned = parent.readFromChild().readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        parent.getChild().destroy();//Acabamos con el subproceso.
        scanner.close();//Cerramos Scanner.
        Instant finish = Instant.now();//Marca de tiempo final
        long timeElapsed;//Tiempo final de ejecución.
        System.out.println("Duración de la ejecución: " + (timeElapsed = Duration.between(start, finish).toSeconds()) + " segundos.");
    }
}
