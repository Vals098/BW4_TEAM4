package bw4.scanner.menuUtente;

import java.util.Scanner;

public class IniziaUnViaggio {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== Inizia un viaggio (Oblitera biglietto) ===\n");
        }
    }
}
