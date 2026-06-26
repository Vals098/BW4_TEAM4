package bw4.scanner.menuAmministratore;

import bw4.DAO.PuntoVenditaDAO;
import bw4.entities.DistributoreAutomatico;
import bw4.entities.PuntoVendita;
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
        boolean running = true;

        while (running) {
            System.out.println("\n=== MENU Punti Vendita ===\n");
            System.out.println("1. Crea nuovo Punto Vendita");
//            implementare lista luoghi
            System.out.println("2. Numero biglietti/abbonamenti dato Punto Vendita");
            System.out.println("3. Controllo distributori guasti nella zona");
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
                        System.out.println("Inserisci il magico Punto Vendita:");

                        List<PuntoVendita> puntiVendita = pvd.findAllPuntiVendita();

                    for (int i = 0; i < puntiVendita.size(); i++) {
                        System.out.println((i + 1) + ". " + puntiVendita.get(i).getNomePuntoVendita());
                    }

                    System.out.println("Premi 0 per tornare al MENU dello Gnomo Archivista.");

                    int sceltaPV = leggiInteroSicuro();

                    if (sceltaPV == 0) {
                        running = false;
                        continue;
                    }

                    if (sceltaPV < 1 || sceltaPV > puntiVendita.size()) {
                        System.out.println("Per la barba di Tomelilla! Inserisci un numero valido!");
                        continue;
                    }

                    PuntoVendita luogoScelto = puntiVendita.get(sceltaPV - 1);

                    List<PuntoVendita> listaPuntiVendita = pvd.findByLuogo(luogoScelto.getLuogo());

                    for (PuntoVendita pv : listaPuntiVendita) {

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

                        String codice = luogoScelto.getCodicePuntoVendita();

                    try {
                        pvd.findByCodice(codice);

                        long numeroBiglietti = pvd.countBigliettiPerPuntoVendita(codice);
                        long numeroAbbonamenti = pvd.countAbbonamentiPerPuntoVendita(codice);

//                        System.out.println("Biglietti venduti: " + numeroBiglietti);
//                        System.out.println("Abbonamenti venduti: " + numeroAbbonamenti);
//                        System.out.println("Totale titoli venduti: " + (numeroBiglietti + numeroAbbonamenti));
                    }catch(PuntoVenditaNonTrovatoException e){
                    System.out.println(e.getMessage());}

                    break;
                case "3":

                    List<String> luoghi2 = pvd.findAllLuoghi();

                    if(luoghi2.isEmpty()){
                        System.out.println("Per il Gran Libro della Fantasia! Non esistono ancora luoghi con punti vendita!");
                        break;
                    }

                    System.out.println("Scegli il magico luogo dove si trova il Punto Vendita:");

                    for (int i = 0; i < luoghi2.size(); i++) {
                        System.out.println((i + 1) + ". " + luoghi2.get(i));
                    }

                    System.out.println("Premi 0 per tornare al MENU dello Gnomo Archivista.");

                    int sceltaLuogo1 = leggiInteroSicuro();

                    if (sceltaLuogo1 == 0) {
                        running = false;
                        continue;
                    }

                    if (sceltaLuogo1 < 1 || sceltaLuogo1 > luoghi2.size()) {
                        System.out.println("Per la barba di Tomelilla! Inserisci un numero valido!");
                        continue;
                    }

                    String luogoScelto1 = luoghi2.get(sceltaLuogo1 - 1);


                    try {
                        pvd.findByLuogo(luogoScelto1);
                        List<DistributoreAutomatico> guasti = pvd.findGuastiByLuogo(luogoScelto1);

                        if (guasti.isEmpty()) {
                            System.out.println("Per la mia corona di ghiande! Nessun distributore guasto nel luogo " + luogoScelto1 + "!");
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

                    List<DistributoreAutomatico> distributori = pvd.findAllFunzionanti();

                    if(distributori.isEmpty()){
                        System.out.println("Accipigna!! Non ci sono distributori funzionanti nel Fantamondo!");
                        break;
                    }

                    System.out.println("Strabiliante magia! Appaiono solo i distributori segnati come funzionanti!");

                    for (int i = 0; i < distributori.size(); i++) {
                        System.out.println((i + 1) + ". " + distributori.get(i).getNomePuntoVendita());
                    }

                    System.out.println("Scegli il distributore da mandare in manutenzione:");
                    System.out.println("Premi 0 per tornare al MENU dello Gnomo Archivista.");

                    int sceltaDistributore = leggiInteroSicuro();

                    if (sceltaDistributore == 0) {
                        running = false;
                        continue;
                    }

                    if (sceltaDistributore < 1 || sceltaDistributore > distributori.size()) {
                        System.out.println("Per la barba di Tomelilla! Inserisci un numero valido!");
                        continue;
                    }

                    String disScelto = distributori.get(sceltaDistributore - 1).getCodicePuntoVendita();


                    try {
                        pvd.mandaInManutenzione(disScelto);
                    } catch (PuntoVenditaNonTrovatoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "5":
                    System.out.println("SEGNALAZIONE DISTRIBUTORE NUOVAMENTE IN SERVIZIO");
                    List<DistributoreAutomatico> distributoriGuasti = pvd.findAllGuasti();

                    if(distributoriGuasti.isEmpty()){
                        System.out.println("Meraviglia delle meraviglie!! Non ci sono distributori guasti nel Fantamondo!");
                        break;
                    }

                    System.out.println("Strabiliante magia! Appaiono solo i distributori segnati come guasti!");

                    for (int i = 0; i < distributoriGuasti.size(); i++) {
                        System.out.println((i + 1) + ". " + distributoriGuasti.get(i).getNomePuntoVendita());
                    }

                    System.out.println("Scegli il distributore da rimettere in servizio:");
                    System.out.println("Premi 0 per tornare al MENU dello Gnomo Archivista.");

                    int sceltaDistributoreGuasto = leggiInteroSicuro();

                    if (sceltaDistributoreGuasto == 0) {
                        running = false;
                        continue;
                    }

                    if (sceltaDistributoreGuasto < 1 || sceltaDistributoreGuasto > distributoriGuasti.size()) {
                        System.out.println("Per la barba di Tomelilla! Inserisci un numero valido!");
                        continue;
                    }

                    String disGuastoScelto = distributoriGuasti.get(sceltaDistributoreGuasto - 1).getCodicePuntoVendita();

                    try {
                        pvd.rimettiInServizio(disGuastoScelto);
                    } catch (PuntoVenditaNonTrovatoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "0":
                    running = false;
                    break;




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
