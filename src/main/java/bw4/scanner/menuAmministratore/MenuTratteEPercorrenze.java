package bw4.scanner.menuAmministratore;

import bw4.DAO.*;
import bw4.entities.Mezzo;
import bw4.entities.Percorrenza;
import bw4.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class MenuTratteEPercorrenze {

    private final Scanner scanner = new Scanner(System.in);

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4_TEAM4");



    public void start() {

        EntityManager em = emf.createEntityManager();

        // CREAZIONE DAO
        MezzoDAO md = new MezzoDAO(em);
        TrattaDAO td = new TrattaDAO(em);
        PercorrenzaDAO pd = new PercorrenzaDAO(em);

        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== MENU Tratta e Percorrenze ===\n");
            System.out.println("1. Registra una nuova Tratta nel sistema");
            System.out.println("2. Avvia una nuova Percorrenza (Assegna Mezzo a Tratta)");
            System.out.println("3. Registra Arrivo ");
            System.out.println("4. Calcola Tempo Medio Effettivo di una tratta per un mezzo");
            System.out.println("5. Monitora Percorrenze");
            System.out.println("0. Torna al Menu Amministratore");
            System.out.print("Seleziona un'opzione: ");

            String scelta = scanner.nextLine();
            switch (scelta) {
                case "1":
                    System.out.println("\n--- REGISTRAZIONE NUOVA TRATTA ---");
                    System.out.print("Inserisci la zona di partenza: ");
                    String partenza = scanner.nextLine();
                    System.out.print("Inserisci il capolinea: ");
                    String capolinea = scanner.nextLine();
                    System.out.print("Inserisci il tempo previsto (ore): ");
                    int orePreviste = leggiInteroSicuro();
                    System.out.print("Inserisci il tempo previsto (minuti): ");
                    int minutiPrevisti = leggiInteroSicuro();
                    Tratta nuovaTratta = new Tratta(partenza, capolinea, LocalTime.of(orePreviste, minutiPrevisti));
                    td.save(nuovaTratta);

                    System.out.println("Tratta salvata con successo! ");
                    break;
                case "2":
                    System.out.println("\n--- AVVIO NUOVA PERCORRENZA (SELEZIONE GUIDATA) ---");
                    List<Tratta> listaTratte = td.findAll();
                    if (listaTratte.isEmpty()) {
                        System.out.println("Accipigna! Non ci sono tratte registrate nel sistema. Creane prima una.");
                        break;
                    }

                    System.out.println("Seleziona la Tratta:");
                    for (int i = 0; i < listaTratte.size(); i++) {
                        Tratta trattaCorrente = listaTratte.get(i);
                        System.out.println((i + 1) + ". Da: " + trattaCorrente.getZonaPartenza() + " a: " + trattaCorrente.getCapolinea() + " (Previsto: " + trattaCorrente.getTempoPrevisto() + ")");
                    }
                    System.out.print("Scegli il numero della tratta: ");
                    int indiceTratta = leggiInteroSicuro() - 1;

                    if (indiceTratta < 0 || indiceTratta >= listaTratte.size()) {
                        System.out.println("Per tutte le pigne! Selezione tratta non valida.");
                        break;
                    }
                    Tratta tSelected = listaTratte.get(indiceTratta);


                    List<Mezzo> listaMezzi = md.findAllInServizio();
                    if (listaMezzi.isEmpty()) {
                        System.out.println("Accipigna! Non ci sono mezzi disponibili nel parco mezzi.");
                        break;
                    }

                    System.out.println("\nSeleziona il Mezzo:");
                    for (int i = 0; i < listaMezzi.size(); i++) {
                        Mezzo mezzoCorrente = listaMezzi.get(i);
                        System.out.println((i + 1) + ". Mezzo: " + mezzoCorrente.getNomeMezzo() + " [" + mezzoCorrente.getTipoMezzo() + "]");
                    }
                    System.out.print("Scegli il numero del mezzo: ");
                    int indiceMezzo = leggiInteroSicuro() - 1;

                    if (indiceMezzo < 0 || indiceMezzo >= listaMezzi.size()) {
                        System.out.println("Cervello di folletto! Selezione mezzo non valida.");
                        break;
                    }
                    Mezzo mSelected = listaMezzi.get(indiceMezzo);

                    Percorrenza p = new Percorrenza(tSelected, mSelected);
                    pd.save(p);
                    System.out.println("\nNuova percorrenza avviata con successo! Il mezzo è partito sulla tratta " + tSelected.getZonaPartenza() + " -> " + tSelected.getCapolinea());
                    break;

                case "3":
                    System.out.println("\n--- REGISTRA ARRIVO (SELEZIONE GUIDATA) ---");

                    List<Percorrenza> attive = pd.findPercorrenzeAttive();
                    if (attive.isEmpty()) {
                        System.out.println("Al momento non ci sono mezzi in viaggio nel Fantabosco.");
                        break;
                    }

                    System.out.println("Seleziona la corsa arrivata al capolinea:");
                    for (int i = 0; i < attive.size(); i++) {
                        Percorrenza per = attive.get(i);
                        System.out.println((i + 1) + ". Mezzo: [" + per.getMezzo().getNomeMezzo() +
                                "] sulla tratta: " + per.getTratta().getZonaPartenza() +
                                " -> " + per.getTratta().getCapolinea());
                    }

                    System.out.print("Scegli il numero della corsa: ");
                    int indicePercorrenza = leggiInteroSicuro() - 1;

                    if (indicePercorrenza < 0 || indicePercorrenza >= attive.size()) {
                        System.out.println("Per tutte le pigne! Selezione non valida.");
                        break;
                    }

                    Percorrenza pSelezionata = attive.get(indicePercorrenza);

                    System.out.print("Inserisci le ore effettive impiegate: ");
                    int oreEff = leggiInteroSicuro();
                    System.out.print("Inserisci i minuti effettivi impiegati: ");
                    int minEff = leggiInteroSicuro();

                    LocalTime tempoEffettivo = LocalTime.of(oreEff, minEff);

                    pd.aggiornaTempoEffettivo(pSelezionata.getIdPercorrenza(), tempoEffettivo);
                    System.out.println("\nArrivo registrato! Il viaggio del mezzo " + pSelezionata.getMezzo().getNomeMezzo() + " è concluso.");
                    break;

                case "4":
                    System.out.println("\n--- CALCOLO TEMPO MEDIO EFFETTIVO (SELEZIONE GUIDATA) ---");

                    List<Tratta> tratteMedia = td.findAll();
                    if (tratteMedia.isEmpty()) {
                        System.out.println("Non ci sono tratte nel sistema per calcolare una media.");
                        break;
                    }

                    System.out.println("Seleziona la Tratta da analizzare:");
                    for (int i = 0; i < tratteMedia.size(); i++) {
                        Tratta tr = tratteMedia.get(i);
                        System.out.println((i + 1) + ". " + tr.getZonaPartenza() + " -> " + tr.getCapolinea());
                    }
                    System.out.print("Scegli il numero della tratta: ");
                    int idxTratta = leggiInteroSicuro() - 1;

                    if (idxTratta < 0 || idxTratta >= tratteMedia.size()) {
                        System.out.println("Selezione tratta non valida.");
                        break;
                    }
                    Tratta trattaScelta = tratteMedia.get(idxTratta);


                    List<Mezzo> mezziMedia = md.findAllInServizio();
                    if (mezziMedia.isEmpty()) {
                        System.out.println("Non ci sono mezzi disponibili nel sistema.");
                        break;
                    }

                    System.out.println("\nSeleziona il Mezzo da analizzare:");
                    for (int i = 0; i < mezziMedia.size(); i++) {
                        Mezzo mz = mezziMedia.get(i);
                        System.out.println((i + 1) + ". " + mz.getNomeMezzo() + " [" + mz.getTipoMezzo() + "]");
                    }
                    System.out.print("Scegli il numero del mezzo: ");
                    int idxMezzo = leggiInteroSicuro() - 1;

                    if (idxMezzo < 0 || idxMezzo >= mezziMedia.size()) {
                        System.out.println("Selezione mezzo non valida.");
                        break;
                    }
                    Mezzo mezzoScelto = mezziMedia.get(idxMezzo);

                    System.out.println("\nInterrogazione dell'Archivio per il mezzo "
                            + mezzoScelto.getNomeMezzo() + " sulla tratta "
                            + trattaScelta.getZonaPartenza() + " -> " + trattaScelta.getCapolinea() + "...");

                    LocalTime tempoMedio = pd.calcolaTempoMedioPercorrenza(trattaScelta.getIdTratta(), mezzoScelto.getIdMezzo());

                    if (tempoMedio != null) {
                        System.out.println("\nIl tempo medio di percorrenza effettivo è: " + tempoMedio);
                    } else {
                        System.out.println("\nNon ci sono ancora percorrenze COMPLETATE (con orario di arrivo) per questo binomio mezzo/tratta.");
                    }
                    break;

                case "5":
                    System.out.println("\n--- CONTEGGIO PERCORRENZE COMPLETATE ---");

                    List<Tratta> tratteConta = td.findAll();
                    if (tratteConta.isEmpty()) {
                        System.out.println("Non ci sono tratte registrate nell'archivio.");
                        break;
                    }

                    System.out.println("Seleziona la Tratta da monitorare:");
                    for (int i = 0; i < tratteConta.size(); i++) {
                        Tratta tr = tratteConta.get(i);
                        System.out.println((i + 1) + ". " + tr.getZonaPartenza() + " -> " + tr.getCapolinea());
                    }
                    System.out.print("Scegli il numero della tratta: ");
                    int idxTrattaConta = leggiInteroSicuro() - 1;

                    if (idxTrattaConta < 0 || idxTrattaConta >= tratteConta.size()) {
                        System.out.println("Selezione tratta non valida.");
                        break;
                    }
                    Tratta trattaContaScelta = tratteConta.get(idxTrattaConta);


                    List<Mezzo> mezziConta = md.findAllInServizio();
                    if (mezziConta.isEmpty()) {
                        System.out.println("Non ci sono mezzi registrati nell'archivio.");
                        break;
                    }

                    System.out.println("\nSeleziona il Mezzo da monitorare:");
                    for (int i = 0; i < mezziConta.size(); i++) {
                        Mezzo mz = mezziConta.get(i);
                        System.out.println((i + 1) + ". " + mz.getNomeMezzo() + " [" + mz.getTipoMezzo() + "]");
                    }
                    System.out.print("Scegli il numero del mezzo: ");
                    int idxMezzoConta = leggiInteroSicuro() - 1;

                    if (idxMezzoConta < 0 || idxMezzoConta >= mezziConta.size()) {
                        System.out.println("Selezione mezzo non valida.");
                        break;
                    }
                    Mezzo mezzoContaScelto = mezziConta.get(idxMezzoConta);

                    System.out.println("\nConteggio delle corse completate nell'Archivio...");

                    Long numeroCorse = pd.numeroPercorrenzePerMezzoETratta(trattaContaScelta.getIdTratta(), mezzoContaScelto.getIdMezzo());

                    System.out.println("\n=========================================================");
                    System.out.println("Il mezzo '" + mezzoContaScelto.getNomeMezzo() +
                            "' ha completato la tratta " + trattaContaScelta.getZonaPartenza() +
                            " -> " + trattaContaScelta.getCapolinea() + " per ben " + numeroCorse + " volte.");
                    System.out.println("\n=========================================================");
                    break;

                case "0":
                    System.out.println("\nRitorno alla Consolle Amministratore...");
                    em.close();
                    inSessione = false;
                    break;

                default:
                    System.out.println("\nPer tutte le pigne spignolate! Opzione non valida nel reparto tratte.");
            }
        }
    }

    private int leggiInteroSicuro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Folletto sbadato! Inserisci un numero intero valido: ");
            }
        }
    }
}
