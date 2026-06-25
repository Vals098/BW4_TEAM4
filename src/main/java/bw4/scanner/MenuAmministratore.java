package bw4.scanner;

import java.util.Scanner;

public class MenuAmministratore {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {

        boolean inSessione = true;

        while (inSessione) {

            System.out.println("\n=== MENU dello Gnomo Archivista ===");
            System.out.println("1. Gestione UTENTI E TESSERE");
            System.out.println("2. Gestione BIGLIETTI E ABBONAMENTI");
            System.out.println("3. Gestione PUNTI VENDITA");
            System.out.println("4. Gestione PARCO MEZZI");
            System.out.println("5. Gestione TRATTE E PERCORRENZE");
            System.out.println("0. Torna alla schermata iniziale - Log-out");

            String scelta = scanner.nextLine();

            switch (scelta) {

                case "1":
                    menuUtentiETessere();
                    break;

                case "2":
                    menuBiglietti();
                    break;

                case "3":
                    menuPuntiVendita();
                    break;

                case "4":
                    menuMezzi();
                    break;

                case "5":
                    menuTratte();
                    break;

                case "0":
                    inSessione = false;
                    break;

                default:
                    System.out.println("Per tutte le pigne spignolate!");
            }
        }
    }

    private void menuUtentiETessere() {
        System.out.println("\nPer mille Pentole Magiche! Ricerca Punto Vendita...");
    }

    private void menuBiglietti() {
        System.out.println("\nChe strabiliante meraviglia! Acquisto Biglietto...");
    }

    private void menuPuntiVendita() {
        System.out.println("\nChe strabiliante meraviglia! Acquisto Abbonamento...");
    }

    private void menuMezzi() {
        System.out.println("\nControllo validità Tessera...");
    }

    private void menuTratte() {
        System.out.println("\nRinnovo Tessera...");
    }





}