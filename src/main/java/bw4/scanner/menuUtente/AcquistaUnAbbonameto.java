package bw4.scanner.menuUtente;

import bw4.DAO.AbbonamentoDAO;
import bw4.DAO.TesseraDAO;
import bw4.entities.Tessera;
import bw4.enums.TipoAbbonamento;
import java.time.LocalDate;
import java.util.Scanner;

public class AcquistaUnAbbonameto {

    private final Scanner scanner = new Scanner(System.in);
    private final AbbonamentoDAO abbonamentoDAO;
    private final TesseraDAO tesseraDAO;

    public AcquistaUnAbbonameto(AbbonamentoDAO abbonamentoDao, TesseraDAO tesseraDao) {
        this.abbonamentoDAO = abbonamentoDao;
        this.tesseraDAO = tesseraDao;
    }

    public void start() {
        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== Acquista un Abbonamento ===\n");

            //chiedi il numero della tessera
            System.out.println("Inserisci il numero della tessera:");
            int numeroTessera = scanner.nextInt();
            scanner.nextLine();

            //cerca la tessera
            Tessera tessera = tesseraDAO.findByNumeroTessera(numeroTessera);
            if (tessera == null) {
                System.out.println("Tessera non trovata");
                inSessione = false;
                continue;
            }

            //chiedi il tipo di abbonamento
            System.out.println("Inserisci il tipo di abbonamento:");
            System.out.println("1 - Settimanale (10 Monete Lillero)");
            System.out.println("2 - Mensile (35 Monete Lillero)");
            int scelta = scanner.nextInt();
            scanner.nextLine();

            TipoAbbonamento tipo = scelta == 1
                    ? TipoAbbonamento.SETTIMANALE
                    : TipoAbbonamento.MENSILE;

            //Mostra prezzo
            if (tipo == TipoAbbonamento.SETTIMANALE) {
                System.out.println("Prezzo: 10 Monete Lillero");
            } else {
                System.out.println("Prezzo: 35 Monete Lillero");
            }

            // corferma acquisto
            System.out.println(" Confermi l'acquisto? (s/n)");
            String conferma = scanner.nextLine();
            if (!conferma.equalsIgnoreCase("s")) {
                System.out.println("Acquisto annullato.");
                inSessione = false;
                continue;
            }

            //crea codice e chiama DAO
            String codice = "ABB-" + numeroTessera + "-" + tipo + "-" + LocalDate.now();

            //controlla se tessera valida prima di creare abbonamento
            if (!tessera.isValid(tessera.getNumeroTessera())) {
                System.out.println("Tessera scaduta! Non puoi acquistare un abbonamento.");
                System.out.println("Vuoi rinnovare la tessera per 50 monete Lillero? (s/n)");
                String rinnovo = scanner.nextLine();
                if (rinnovo.equalsIgnoreCase("s")) {
                    tesseraDAO.controllaERinnova(numeroTessera);
                    System.out.println("Tessera rinnovata! Ora puoi acquistare un abbonamento.");
                } else {
                    System.out.println("Acquisto annullato.");
                    inSessione = false;
                }
            }
            else
            {
                abbonamentoDAO.creaAbbonamento(tessera, tipo, codice, LocalDate.now());
                System.out.println("Abbonamento acquistato con successo!");

            }

            // 7. chiedi se vuole fare altro
            System.out.println("Vuoi acquistare un altro abbonamento? (s/n)");
            String altro = scanner.nextLine();
            if (!altro.equalsIgnoreCase("s")) {
                inSessione = false;
            }


        }
    }
}
