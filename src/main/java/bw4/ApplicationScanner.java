package bw4;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class ApplicationScanner {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4_TEAM4");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        Scanner scanner = new Scanner(System.in);
        boolean eseguiApp = true;

        System.out.println("============================================================");
        System.out.println("   Buongiorno! Buoncielo! Buonsole! ... e buonapigna a te!  ");
        System.out.println("============================================================");

        while (eseguiApp) {
            //MENU DI ACCESSO
            System.out.println("1. Accedi al Sistema");
            System.out.println("0. Chiudi Applicazione");
            System.out.print("Seleziona un'opzione: ");

            String sceltaMenuAccesso = scanner.nextLine();

            if (sceltaMenuAccesso.equals("0")) {
                System.out.println("\nChiusura dell'applicazione in corso... Alla prossima pigna!\n");
                eseguiApp = false;
                break;
            } else if (!sceltaMenuAccesso.equals("1")) {
                System.out.println("\nFolletto, folletto, cervello di foglietto!... portato via dal vento, vuoi stare un po' più attento?! Inserisci 1 o 0.\n");
                continue;
            }

            //LOGIN
            String ruolo = "";
            boolean autenticato = false;

            System.out.println("\n--- ACCESSO AL SISTEMA ---");
            System.out.print("Inserisci Username: ");
            String username = scanner.nextLine();
            System.out.print("Inserisci Password: ");
            String password = scanner.nextLine();

            if (username.equals("Gipo") && password.equals("Scribantino")) {
                ruolo = "ADMIN";
                autenticato = true;
                System.out.println("\nAccesso eseguito come Gnomo Archivista!.");
            } else if (username.equals("Amico") && password.equals("Di Città Laggiù")) {
                ruolo = "USER";
                autenticato = true;
                System.out.println("\nAccesso eseguito come cittadino di Città Laggiù");
            } else {
                System.out.println("\nAccipigna! Credenziali errate!");
            }

            //MENU PRINCIPALE
            boolean inSessione = autenticato;

            while (inSessione) {
                if (ruolo.equals("ADMIN")) {
                    System.out.println("\n=== MENU dello Gnomo Archivista ===");
                    System.out.println("1. Gestione UTENTI E TESSERE");
                    System.out.println("2. Gestione BIGLIETTI E ABBONAMENTI");
                    System.out.println("3. Gestione PUNTI VENDITA");
                    System.out.println("4. Gestione PARCO MEZZI");
                    System.out.println("5. Gestione TRATTE E PERCORRENZE");
                    System.out.println("0. Torna alla schermata iniziale - Log-out");
                    System.out.print("Seleziona un'opzione: ");

                    String scelta = scanner.nextLine();

                    switch (scelta) {
                        case "1":
                            break;
                        case "2":
                            break;
                        case "3":
                            break;
                        case "4":
                            break;
                        case "5":
                            break;
                        case "0":
                            System.out.println("\nGira per gioco Giostra dei Giorni, giro per giro quel giorno ritorni!\n");
                            inSessione = false;
                            break;
                        default:
                            System.out.println("\nPer tutte le pigne spignolate! Questa opzione non è valida.");
                    }

                } else if (ruolo.equals("USER")) {
                    System.out.println("\n=== MENU UTENTE ===");
                    System.out.println("1. Cerca un punto vendita");
                    System.out.println("2. Acquista o Rinnova Tessera");
                    System.out.println("3. Inizia un viaggio");
                    System.out.println("0. Torna alla schermata iniziale - Log-out");
                    System.out.print("Seleziona un'opzione: ");

                    String scelta = scanner.nextLine();

                    switch (scelta) {
                        case "1":
                            break;
                        case "2":
                            break;
                        case "3":
                            break;
                        case "0":
                            System.out.println("\nCiao amico di Città Laggiù alla prossima...\n");
                            inSessione = false;
                            break;
                        default:
                            System.out.println("\nPer tutte le pigne spignolate! Questa opzione non è valida.\n");
                    }
                }
            }
        }

        scanner.close();
        em.close();
        emf.close();
    }
}