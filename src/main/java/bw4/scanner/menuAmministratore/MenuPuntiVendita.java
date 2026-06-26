package bw4.scanner.menuAmministratore;

import bw4.DAO.PuntoVenditaDAO;
import bw4.entities.DistributoreAutomatico;
import bw4.entities.RivenditoreAutorizzato;
import bw4.exceptions.PuntoVenditaNonTrovatoException;

import java.util.List;
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
//            implementare lista luoghi
            System.out.println("2. Numero biglietti/abbonamenti dato Punto Vendita");
//            implementare lista punti vendita
            System.out.println("3. Controllo distributori guasti nella zona");
//            implementare lista luoghi
            System.out.println("4. Segnalazione distributore guasto");
//            implementare errore se è già segnato come guasto
            System.out.println("5. Segnalazione distributore nuovamente in servizio");
            System.out.println("0. Torna al menu dello Gnomo Archivista");

            String scelta = scanner.nextLine();


            switch (scelta) {
                case "1":
                    System.out.println("CREAZIONE NUOVO PUNTO VENDITA");
                    System.out.println("Inserisci il codice del nuovo magico Punto Vendita:");
                    System.out.println("Consiglio folletto: TB6Y8");
                    String newCodice = scanner.nextLine();

                    System.out.println("Inserisci il magico luogo in cui si trova il punto vendita:");
                    String newLuogo = scanner.nextLine();

                    System.out.println("Inserisci il nome del fantastico punto vendita:");
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
                    try {
                        pvd.findByLuogo(luogo);
                        List<DistributoreAutomatico> guasti = pvd.findGuastiByLuogo(luogo);

                        if (guasti.isEmpty()) {
                            System.out.println("Per la mia corona di ghiande! Nessun distributore guasto nel luogo " + luogo + "!");
                        } else {
                            guasti.forEach(d ->
                                    System.out.println("Distributori Guasti Trovati: " + d.getNomePuntoVendita()));
                        }

                    } catch (PuntoVenditaNonTrovatoException e) {
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

                case "5":
                    System.out.println("SEGNALAZIONE DISTRIBUTORE NUOVAMENTE IN SERVIZIO");
                    System.out.println("Inserisci il codice del distributore:");
                    String codice2 = scanner.nextLine();
                    try {
                        pvd.rimettiInServizio(codice2);
                    } catch (PuntoVenditaNonTrovatoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "0":
                    inSessione = false;
                    break;




            }



        }
    }
}
