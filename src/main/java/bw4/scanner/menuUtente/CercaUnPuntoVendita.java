package bw4.scanner.menuUtente;

import bw4.DAO.PuntoVenditaDAO;
import bw4.entities.DistributoreAutomatico;
import bw4.entities.PuntoVendita;

import java.util.List;
import java.util.Scanner;

public class CercaUnPuntoVendita {
    private final Scanner scanner = new Scanner(System.in);

    private final PuntoVenditaDAO pvd;

    public CercaUnPuntoVendita(PuntoVenditaDAO pvd) {
        this.pvd = pvd;
    }

    public void start() {
        boolean inSessione = true;

        while (inSessione) {

            List<String> luoghi = pvd.findAllLuoghi();

            System.out.println("\n=== Cerca Un Punto Vendita ===\n");
            System.out.println("Tranquillo caro amico, come per magia appaiono solo i Punti Vendita funzionanti!");
            System.out.println("Fai la tua scelta:");

            for (int i = 0; i < luoghi.size(); i++) {
                System.out.println((i + 1) + ". " + luoghi.get(i));
            }

            System.out.println("Premi 0 per tornare al MENU del Cittadino di Città Laggiù ");

            int scelta = leggiInteroSicuro();

            if (scelta == 0) {
                inSessione = false;
                continue;
            }

            if (scelta < 1 || scelta > luoghi.size()) {
                System.out.println("Per la barba di Tomelilla! Inserisci un numero valido!");
                continue;
            }

            String luogoScelto = luoghi.get(scelta - 1);

            List<PuntoVendita> puntiVendita = pvd.findByLuogo(luogoScelto);

            for (PuntoVendita pv : puntiVendita) {

                String tipo;

                if (pv instanceof DistributoreAutomatico) {
                    tipo = "Distributore Automatico";
                } else {
                    tipo = "Rivenditore Autorizzato";
                }

                System.out.println(
                        " | Codice: " + pv.getCodicePuntoVendita() +
                                " | Nome: " + pv.getNomePuntoVendita() + " Tipo: " + tipo);
                System.out.println();
            }
        }
    }
    private int leggiInteroSicuro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Per il Gran Libro della Fantasia! inserisci un numero!");
            }
        }
    }


}
