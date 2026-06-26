package bw4.scanner.menuAmministratore;

import bw4.DAO.AbbonamentoDAO;
import bw4.DAO.BigliettoDAO;
import bw4.DAO.TesseraDAO;
import bw4.DAO.TitoloDiViaggioDAO;
import bw4.entities.Tessera;
import bw4.entities.TitoloDiViaggio;

import java.time.LocalDate;
import java.util.Scanner;

public class MenuBigliettiEAbbonamenti {

    private final Scanner scanner = new Scanner(System.in);
    private final AbbonamentoDAO abbonamentoDAO;
    private final BigliettoDAO bigliettoDAO;
    private final TitoloDiViaggioDAO titoloDiViaggioDAO;
    private final TesseraDAO tesseraDAO;

    public MenuBigliettiEAbbonamenti(AbbonamentoDAO abbonamentoDAO,
                                     BigliettoDAO bigliettoDAO,
                                     TitoloDiViaggioDAO titoloDiViaggioDAO,
                                     TesseraDAO tesseraDAO) {
        this.abbonamentoDAO = abbonamentoDAO;
        this.bigliettoDAO = bigliettoDAO;
        this.titoloDiViaggioDAO = titoloDiViaggioDAO;
        this.tesseraDAO = tesseraDAO;
    }

    public void start() {
        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== MENU Biglietti e Abbonamenti ===\n");
            System.out.println("1. Cerca titolo di viaggio per codice");
            System.out.println("2. Trova abbonamenti per numero tessera");
            System.out.println("3. Verifica validità abbonamento per numero tessera");
            System.out.println("4. Conta biglietti venduti in un periodo");
            System.out.println("5. Conta abbonamenti venduti in un periodo");
            System.out.println("0. Torna al menu precedente");
            System.out.print("Seleziona un'opzione: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> cercaPerCodice();
                case "2" -> trovaTesseraAbbonamenti();
                case "3" -> verificaValiditaAbbonamento();
                case "4" -> contaBiglietti();
                case "5" -> contaAbbonamenti();
                case "0" -> inSessione = false;
                default -> System.out.println("Opzione non valida!");
            }
        }
    }

    private void cercaPerCodice() {
        System.out.println("Inserisci il codice del titolo di viaggio:");
        String codice = scanner.nextLine();
        TitoloDiViaggio titolo = titoloDiViaggioDAO.findByCodice(codice);
        if (titolo == null) {
            System.out.println("Nessun titolo trovato con codice: " + codice);
        } else {
            System.out.println("Trovato: " + titolo);
        }
    }

    private void trovaTesseraAbbonamenti() {
        System.out.println("Inserisci il numero della tessera:");
        int numeroTessera = Integer.parseInt(scanner.nextLine());
        Tessera tessera = tesseraDAO.findByNumeroTessera(numeroTessera);
        if (tessera == null) {
            System.out.println("Tessera non trovata!");
            return;
        }
        System.out.println(abbonamentoDAO.findByTessera(tessera.getIdTessera()));
    }

    private void verificaValiditaAbbonamento() {
        System.out.println("Inserisci il numero della tessera:");
        int numeroTessera = Integer.parseInt(scanner.nextLine());
        Tessera tessera = tesseraDAO.findByNumeroTessera(numeroTessera);
        if (tessera == null) {
            System.out.println("Tessera non trovata!");
            return;
        }
        boolean valido = abbonamentoDAO.hasAbbonamentoValido(tessera.getIdTessera());
        System.out.println(valido ? "Abbonamento valido!" : "Nessun abbonamento valido per questa tessera!");
    }

    private void contaBiglietti() {
        System.out.println("Inserisci data inizio (YYYY-MM-DD):");
        LocalDate da = LocalDate.parse(scanner.nextLine());
        System.out.println("Inserisci data fine (YYYY-MM-DD):");
        LocalDate a = LocalDate.parse(scanner.nextLine());
        long totale = bigliettoDAO.countBigliettiVenduti(da, a);
        System.out.println("Biglietti venduti nel periodo: " + totale);
    }

    private void contaAbbonamenti() {
        System.out.println("Inserisci data inizio (YYYY-MM-DD):");
        LocalDate da = LocalDate.parse(scanner.nextLine());
        System.out.println("Inserisci data fine (YYYY-MM-DD):");
        LocalDate a = LocalDate.parse(scanner.nextLine());
        long totale = abbonamentoDAO.countAbbonamentiVenduti(da, a);
        System.out.println("Abbonamenti venduti nel periodo: " + totale);
    }
}
