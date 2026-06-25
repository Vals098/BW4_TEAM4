package bw4.scanner;

import bw4.DAO.*;

import java.util.Scanner;

public class MenuIniziale {

    private final Scanner scanner = new Scanner(System.in);

//    DAO
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

    public MenuIniziale( AbbonamentoDAO ad,
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

    public void start(){

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

                MenuAmministratore menuAmministratore =
                        new MenuAmministratore(
                                ad,
                                pvd,
                                md,
                                td,
                                pd,
                                ud,
                                tesseraDAO,
                                tvd,
                                bd,
                                manutenzioneDAO
                        );

                menuAmministratore.start();

            } else if (username.equals("Amico") && password.equals("Di Città Laggiù")) {
                ruolo = "USER";
                autenticato = true;
                System.out.println("\nAccesso eseguito come cittadino di Città Laggiù");

                MenuUtente menuUtente = new MenuUtente(
                        ad,
                        pvd,
                        md,
                        td,
                        pd,
                        ud,
                        tesseraDAO,
                        tvd,
                        bd,
                        manutenzioneDAO);

                menuUtente.start();

            } else {
                System.out.println("\nAccipigna! Credenziali errate!");
            }



        }


    }



}
