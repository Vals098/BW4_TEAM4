package bw4.scanner;

import bw4.DAO.*;
import bw4.scanner.menuUtente.*;

import java.util.Scanner;

public class MenuUtente {

    private final Scanner scanner = new Scanner(System.in);

    private final PuntoVenditaDAO pvd;
    private final MezzoDAO md;
    private final TrattaDAO td;
    private final PercorrenzaDAO pd;
    private final UtenteDAO ud;
    private final TesseraDAO tesseraDAO;
    private final TitoloDiViaggioDAO tvd;
    private final BigliettoDAO bd;
    private final ManutenzioneDAO manutenzioneDAO;

    public MenuUtente(PuntoVenditaDAO pvd,
                              MezzoDAO md,
                              TrattaDAO td,
                              PercorrenzaDAO pd,
                              UtenteDAO ud,
                              TesseraDAO tesseraDAO,
                              TitoloDiViaggioDAO tvd,
                              BigliettoDAO bd,
                              ManutenzioneDAO manutenzioneDAO) {

        this.pvd = pvd;
        this.md = md;
        this.td = td;
        this.pd = pd;
        this.ud = ud;
        this.tesseraDAO = tesseraDAO;
        this.tvd = tvd;
        this.bd = bd;
        this.manutenzioneDAO = manutenzioneDAO;

    }

    public void start() {

        boolean inSessione = true;

        while (inSessione) {

            System.out.println("\n=== MENU del Cittadino di Città Laggiù ===");
            System.out.println("1. Cerca un Punto Vendita");
            System.out.println("2. Acquista un Biglietto");
            System.out.println("3. Acquista un Abbonamento");
            System.out.println("4. Controlla validità Tessera");
            System.out.println("5. Rinnova Tessera");
            System.out.println("6. Inizia un viaggio (Oblitera Biglietto)");
            System.out.println("0. Torna alla schermata iniziale - Log-out");
            System.out.print("Seleziona un'opzione: ");

            String scelta = scanner.nextLine();

            switch (scelta) {

                case "1":
                    CercaUnPuntoVendita cercaUnPuntoVendita = new CercaUnPuntoVendita();
                    cercaUnPuntoVendita.start();
                    break;

                case "2":
                    AcquistaUnBiglietto acquistaUnBiglietto = new AcquistaUnBiglietto();
                    acquistaUnBiglietto.start();
                    break;

                case "3":
                    AcquistaUnAbbonameto acquistaUnAbbonameto = new AcquistaUnAbbonameto();
                    acquistaUnAbbonameto.start();
                    break;

                case "4":
                    ControllaValiditaTessera controllaValiditaTessera = new ControllaValiditaTessera();
                    controllaValiditaTessera.start();
                    break;

                case "5":
                    RinnovaTessera  rinnovaTessera = new RinnovaTessera();
                    rinnovaTessera.start();
                    break;

                case "6":
                    IniziaUnViaggio iniziaUnViaggio = new IniziaUnViaggio();
                    iniziaUnViaggio.start();
                    break;

                case "0":
                    System.out.println("\nCiao amico di Città Laggiù... alla prossima!\n");
                    inSessione = false;
                    break;

                default:
                    System.out.println("\nPer tutte le pigne spignolate! Questa opzione non è valida.\n");
            }
        }
    }

    private void cercaPuntoVendita() {
        System.out.println("\nPer mille Pentole Magiche! Ricerca Punto Vendita...");
    }

    private void acquistaBiglietto() {
        System.out.println("\nChe strabiliante meraviglia! Acquisto Biglietto...");
    }

    private void acquistaAbbonamento() {
        System.out.println("\nChe strabiliante meraviglia! Acquisto Abbonamento...");
    }

    private void controllaTessera() {
        System.out.println("\nControllo validità Tessera...");
    }

    private void rinnovaTessera() {
        System.out.println("\nRinnovo Tessera...");
    }

    private void obliteraBiglietto() {
        System.out.println("\nBuon viaggio nel Fantabosco! Obliterazione del Biglietto...");
    }
}