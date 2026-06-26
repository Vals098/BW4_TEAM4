package bw4.scanner.menuUtente;

import bw4.DAO.TesseraDAO;
import bw4.DAO.UtenteDAO;
import java.util.Scanner;

public class ControllaValiditaTessera {
    private final Scanner scanner = new Scanner(System.in);
    private final UtenteDAO ud;
    private final TesseraDAO tesseraDAO;

    public ControllaValiditaTessera (UtenteDAO ud, TesseraDAO tesseraDAO) {
        this.ud = ud;
        this.tesseraDAO = tesseraDAO;
    }

    public void start() {
        boolean inSessione = true;
        while (inSessione) {
            System.out.println("------- MENU UTENTI E TESSERE --------");
            System.out.println("1. Verifica e Gestisci Tessera");
            System.out.println("0. Torna a Città Laggiù");
            System.out.print("Scegli un'opzione: ");
            String scelta = scanner.nextLine();
            switch (scelta) {
                case "1":
                    gestisciVerificaTessera();
                    break;
                case "0":
                    inSessione = false;
                    System.out.println("Ritorno al menu precedente...");
                    break;
                default:
                    System.out.println("Per tutte le pigne spignolate! Opzione non valida.");
            }
        }
    }
    private void gestisciVerificaTessera() {
        System.out.print("Inserisci il numero della tua tessera da controllare: ");
        String input = scanner.nextLine();
        try {
            int numeroTessera = Integer.parseInt(input);
            tesseraDAO.controllaERinnova(numeroTessera, scanner);
        } catch (NumberFormatException e) {
            System.out.println("Accipigna! Devi inserire un numero valido!");
        }
    }
}