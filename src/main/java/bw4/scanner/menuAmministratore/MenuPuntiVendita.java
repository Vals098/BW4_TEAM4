package bw4.scanner.menuAmministratore;

import bw4.DAO.PuntoVenditaDAO;
import bw4.entities.DistributoreAutomatico;
import bw4.entities.RivenditoreAutorizzato;
import bw4.exceptions.PuntoVenditaNonTrovatoException;

import java.util.Scanner;

public class MenuPuntiVendita {

    private final Scanner scanner = new Scanner(System.in);

    private final PuntoVenditaDAO pvd;

    public MenuPuntiVendita(PuntoVenditaDAO pvd) {
        this.pvd = pvd;
    }

    public void start() {
        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== MENU Punti Vendita ===\n");
            System.out.println("1. Crea nuovo Punto Vendita");
            System.out.println("2. Numero biglietti/abbonamenti dato Punto Vendita");
            System.out.println("3. Controllo distributori guasti nella zona");
            System.out.println("4. Segnalazione distributore guasto");
            System.out.println("0. Torna al menu amministratore");

            String scelta = scanner.nextLine();


            switch (scelta) {
                case "1":
                    System.out.println("CREAZIONE NUOVO PUNTO VENDITA");
                    System.out.println("Inserisci il codice del nuovo Punto Vendita:");
                    String newCodice = scanner.nextLine();

                    System.out.println("Inserisci il magico luogo in cui si trova il punto vendita:");
                    String newLuogo = scanner.nextLine();

                    System.out.println("Inserisci il nome del punto vendita:");
                    String newNome = scanner.nextLine();

                    System.out.println("Scegli tipologia punto vendita:");
                    System.out.println("1. DISTRIBUTORE AUTOMATICO");
                    System.out.println("2. RIVENDITORE AUTORIZZATO");

                    String sceltaTipoMezzo = scanner.nextLine();

                    if (sceltaTipoMezzo.equals("1")) {
                        System.out.println("Creazione nuovo DISTRIBUTORE AUTOMATICO");
                        DistributoreAutomatico newDistributore = new DistributoreAutomatico(newCodice, newNome, newLuogo);
                        pvd.save(newDistributore);
                        System.out.println("Fantavoloso! Nuovo Distributore Automatico apparso per magia!");
                        break;

                    } else if (sceltaTipoMezzo.equals("2")) {
                        System.out.println("Nuovo fantavoloso Rivenditore Automatico apparso nel Fantamondo!");
                        RivenditoreAutorizzato newRivenditore = new RivenditoreAutorizzato(newCodice, newNome, newLuogo);
                        pvd.save(newRivenditore);
                        break;
                    } else {
                        System.out.println("\nFolletto, folletto, cervello di foglietto!... portato via dal vento, vuoi stare un po' più attento?! Inserisci 1 o 2.\n");
                    }
                    break;

                case "2":
                        System.out.println("Inserisci il codice del Punto Vendita:");
                        String codice = scanner.nextLine();
                    try {
                        pvd.findByCodice(codice);

                        long numeroBiglietti = pvd.countBigliettiPerPuntoVendita(codice);
                        long numeroAbbonamenti = pvd.countAbbonamentiPerPuntoVendita(codice);

                        System.out.println("Biglietti venduti: " + numeroBiglietti);
                        System.out.println("Abbonamenti venduti: " + numeroAbbonamenti);
                        System.out.println("Totale titoli venduti: " + (numeroBiglietti + numeroAbbonamenti));
                    }catch(PuntoVenditaNonTrovatoException e){
                    System.out.println(e.getMessage());}

                    break;
                case "3":
                    System.out.println("Inserisci il magico luogo dove si trova il Punto Vendita:");
                    String luogo = scanner.nextLine();
                    try{
                        pvd.findGuastiByLuogo(luogo);
                    }catch(PuntoVenditaNonTrovatoException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case "4":
                    System.out.println("SEGNALAZIONE DISTRIBUTORE GUASTO");
                    System.out.println("Inserisci il codice del distributore:");
                    String codice1 = scanner.nextLine();
                    try {
                        pvd.mandaInManutenzione(codice1);
                    } catch (PuntoVenditaNonTrovatoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;




            }



        }
    }
}
