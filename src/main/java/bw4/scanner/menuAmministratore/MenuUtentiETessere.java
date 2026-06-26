package bw4.scanner.menuAmministratore;

import bw4.DAO.TesseraDAO;
import bw4.DAO.UtenteDAO;
import bw4.entities.Utente;
import java.util.Scanner;

public class MenuUtentiETessere {
    private final Scanner scanner = new Scanner(System.in);
    private final UtenteDAO ud;
    private final TesseraDAO tesseraDAO;

    public MenuUtentiETessere(UtenteDAO ud, TesseraDAO tesseraDAO) {
        this.ud = ud;
        this.tesseraDAO = tesseraDAO;
    }
    public void start() {
        while (true) {
            System.out.println("--- Sottomenu Gestione Utenti e Tessere ---");
            System.out.println("1. Verifica e Rinnova Tessera");
            System.out.println("2. Elimina Utente");
            System.out.println("0. Torna al menu principale");
            System.out.print("Seleziona un'opzione: ");

            String scelta = scanner.nextLine().trim();

            switch (scelta) {
                case "1":
                    System.out.print("Inserisci il numero della tessera da controllare: ");
                    String input = scanner.nextLine();
                    try {
                        int numeroTessera = Integer.parseInt(input);
                        tesseraDAO.controllaERinnova(numeroTessera, scanner);
                    } catch (NumberFormatException e) {
                        System.out.println("Accipigna! Devi inserire un numero valido!");
                    }
                    break;
                case "2":
                    System.out.println("Inserisci il CODICE UTENTE da eliminare ES.(MICOT,LULU,GNORO,ecc..) : ");
                    String codiceInput = scanner.nextLine().toUpperCase().trim();

                    Utente utente = ud.findByCodiceUtente(codiceInput);
                    if (utente != null) {
                        ud.avviaCancellazioneUtente(utente.getIdUtente());
                    } else {
                        System.out.println("Accipigna! Nessun utente trovato con il codice: " + codiceInput);
                    }
                    break;
                case "0":
                    System.out.println("Ritorno al menu precedente...");
                    return;
                default:
                    System.out.println("Opzione non valida! Riprova.");
                    break;
            }
        }
    }
}