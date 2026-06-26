package bw4.scanner;

import bw4.DAO.*;
import bw4.scanner.menuAmministratore.*;

import java.util.Scanner;

public class MenuAmministratore {

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
    private final AbbonamentoDAO ad;

    public MenuAmministratore(
            AbbonamentoDAO ad,
            PuntoVenditaDAO pvd,
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
        this.ad = ad;

    }

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

//                case "1":
//                    MenuUtentiETessere menuUtentiETessere = new MenuUtentiETessere( );
//                    menuUtentiETessere.start();
//                    break;

                case "2":
                    MenuBigliettiEAbbonamenti menuBigliettiEAbbonamenti = new MenuBigliettiEAbbonamenti(ad, bd, tvd, tesseraDAO);
                    menuBigliettiEAbbonamenti.start();
                    break;

                case "3":
                    MenuPuntiVendita menuPuntiVendita = new MenuPuntiVendita(pvd);
                    menuPuntiVendita.start();
                    break;

                case "4":
                    MenuParcoMezzi menuParcoMezzi = new MenuParcoMezzi(md, manutenzioneDAO, bd);
                    menuParcoMezzi.start();
                    break;

                case "5":
                    MenuTratteEPercorrenze menuTratteEPercorrenze = new MenuTratteEPercorrenze();
                    menuTratteEPercorrenze.start(
                            md,
                            td,
                            pd);
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