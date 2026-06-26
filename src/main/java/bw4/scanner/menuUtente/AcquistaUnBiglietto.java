package bw4.scanner.menuUtente;
import java.util.List;
import java.util.Scanner;
import bw4.entities.Biglietto;
import bw4.entities.PuntoVendita;
import bw4.DAO.PuntoVenditaDAO;
import bw4.DAO.TitoloDiViaggioDAO;
import java.time.LocalDate;


public class AcquistaUnBiglietto {

    private final Scanner scanner = new Scanner(System.in);
    private final TitoloDiViaggioDAO titoloDiViaggioDAO;
    private final PuntoVenditaDAO puntoVenditaDAO;

    public AcquistaUnBiglietto(TitoloDiViaggioDAO titoloDiViaggioDAO, PuntoVenditaDAO puntoVenditaDAO) {
        this.titoloDiViaggioDAO = titoloDiViaggioDAO;
        this.puntoVenditaDAO = puntoVenditaDAO;
    }

    public void start() {
        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== Acquista un Biglietto ===\n");

            // chiedi luogo
            System.out.println("In quale luogo ti trovi?");
            System.out.println("- Castello dei fiori");
            System.out.println("- Città Laggiù");
            System.out.println("- Chiosco");
            System.out.println("- Antro della Strega");
            System.out.println("- Tana del Lupo");
            System.out.println("- Reggia di Re Quercia");
            String luogo = scanner.nextLine();

            // cerca punti vendita in quel luogo
            List<PuntoVendita> puntiVendita = puntoVenditaDAO.findByLuogo(luogo);
            if (puntiVendita.isEmpty()) {
                System.out.println("Nessun punto vendita in questo luogo!");
                inSessione = false;
                continue;
            }

            // mostra i punti vendita disponibili
            System.out.println("Punti vendita disponibili:");
            for (int i = 0; i < puntiVendita.size(); i++) {
                System.out.println((i + 1) + ". " + puntiVendita.get(i).getNomePuntoVendita());
            }

            // l'utente sceglie
            System.out.println("Scegli il punto vendita (numero):");
            int sceltaPV = Integer.parseInt(scanner.nextLine()) - 1;
            PuntoVendita puntoVendita = puntiVendita.get(sceltaPV);

            // mostra prezzo
            System.out.println("Il prezzo del biglietto è: 2 Monete Lillero");

            //conferma acquisto
            System.out.println("Vuoi confermare l'acquisto? (s/n)");
            String conferma = scanner.nextLine();
            if (!conferma.equalsIgnoreCase("s")) {
                System.out.println("Acquisto annullato.");
                continue;
            }

            // crea codice univoco e biglietto
            String codice = "BIG-" + System.currentTimeMillis();
            Biglietto biglietto = new Biglietto(codice, LocalDate.now(),LocalDate.now().plusDays(1));
            biglietto.setPuntoVendita(puntoVendita);

            // salva
            titoloDiViaggioDAO.save(biglietto);
            System.out.println("Biglietto acquistato con successo! Codice: " + codice);

            // chiedi se vuole fare altro
            System.out.println("Vuoi acquistare un altro biglietto? (s/n)");
            String altro = scanner.nextLine();
            if (!altro.equalsIgnoreCase("s")) {
                break;
            }
        }
    }
}
