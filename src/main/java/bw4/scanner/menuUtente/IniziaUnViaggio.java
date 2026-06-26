package bw4.scanner.menuUtente;

import bw4.DAO.BigliettoDAO;
import bw4.DAO.MezzoDAO;
import bw4.DAO.PercorrenzaDAO;
import bw4.DAO.TrattaDAO;
import bw4.DAO.TitoloDiViaggioDAO;
import bw4.entities.Biglietto;
import bw4.entities.Mezzo;
import bw4.entities.TitoloDiViaggio;
import bw4.entities.Tratta;

import java.util.Scanner;

public class IniziaUnViaggio {

    private final Scanner scanner = new Scanner(System.in);
    private final BigliettoDAO bigliettoDAO;
    private final MezzoDAO mezzoDAO;
    private final TitoloDiViaggioDAO titoloDiViaggioDAO;
    private final PercorrenzaDAO percorrenzaDAO;
    private final TrattaDAO trattaDAO;

    public IniziaUnViaggio(BigliettoDAO bigliettoDAO, MezzoDAO mezzoDAO, TitoloDiViaggioDAO titoloDiViaggioDAO, PercorrenzaDAO percorrenzaDAO, TrattaDAO trattaDAO) {
        this.bigliettoDAO = bigliettoDAO;
        this.mezzoDAO = mezzoDAO;
        this.titoloDiViaggioDAO = titoloDiViaggioDAO;
        this.percorrenzaDAO = percorrenzaDAO;
        this.trattaDAO = trattaDAO;
    }

    public void start() {
        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== Inizia un viaggio (Oblitera biglietto) ===\n");

            // chiedi zona partenza
            System.out.println("Da dove parti?");
            System.out.println("- Antro della Strega");
            System.out.println("- Chiosco");
            System.out.println("- Città Laggiù");
            System.out.println("- Reggia di Re Quercia");
            System.out.println("- Tana del Lupo");
            String partenza = scanner.nextLine();

            // chiedi destinazione
            System.out.println("Dove vuoi andare?");
            String destinazione = scanner.nextLine();

            //  trova tratta
            Tratta tratta = trattaDAO.findByZonaPertenzaECapolinea(partenza, destinazione);
            if (tratta == null) {
                System.out.println("Tratta non trovata!");
                inSessione = false;
                continue;
            }

            // 4. trova mezzo sulla tratta
            Mezzo mezzo = percorrenzaDAO.findMezzoByTratta(tratta.getIdTratta());
            if (mezzo == null) {
                System.out.println("Nessun mezzo disponibile su questa tratta!");
                inSessione = false;
                continue;
            }

            System.out.println("Mezzo disponibile: " + mezzo.getNomeMezzo());

            // chiedi codice biglietto
            System.out.println("Inserisci il codice del tuo biglietto:");
            String codice = scanner.nextLine();

            // cerca biglietto
            TitoloDiViaggio titolo = titoloDiViaggioDAO.findByCodice(codice);
            if (titolo == null) {
                System.out.println("Biglietto non trovato!");
                inSessione = false;
                continue;
            }

            // controlla che sia un biglietto e non un abbonamento
            if (!(titolo instanceof Biglietto)) {
                System.out.println("Il codice inserito non è un biglietto!");
                inSessione = false;
                continue;
            }

            Biglietto biglietto = (Biglietto) titolo;

            // controlla se già obliterato
            if (biglietto.isObliterato()) {
                System.out.println("Biglietto già obliterato!");
                inSessione = false;
                continue;
            }

            // obliterazione
            bigliettoDAO.obliteraBiglietto(biglietto.getIdTitoloDiViaggio(), mezzo);

            // chiedi se vuole fare altro
            System.out.println("Vuoi obliterare un altro biglietto? (s/n)");
            String altro = scanner.nextLine();
            if (!altro.equalsIgnoreCase("s")) {
                inSessione = false;
            }
        }
    }
}


