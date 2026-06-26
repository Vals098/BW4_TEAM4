package bw4.scanner.menuAmministratore;

import bw4.DAO.*;
import bw4.entities.Mezzo;
import bw4.entities.Percorrenza;
import bw4.entities.Tratta;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class MenuTratteEPercorrenze {

    private final Scanner scanner = new Scanner(System.in);

    PercorrenzaDAO pd;
    TrattaDAO td;
    MezzoDAO md;

    public void start(MezzoDAO md, TrattaDAO td,  PercorrenzaDAO pd) {

        this.md = md;
        this.td = td;
        this.pd = pd;

        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== MENU Tratta e Percorrenze ===\n");
            System.out.println("1. Registra una nuova Tratta nel regno");
            System.out.println("2. Avvia una nuova corsa per un mezzo fatato");
            System.out.println("3. Il magico viaggio è finito! Registra l'arrivo");
            System.out.println("4. Calcola Tempo Medio Effettivo di una tratta per un mezzo");
            System.out.println("5. Monitora le corse dei mezzi del Fantabosco");
            System.out.println("0. Torna al Menu Precedente");
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
                    int orePreviste = leggiOreSicure();
                    System.out.print("Inserisci il tempo previsto (minuti): ");
                    int minutiPrevisti = leggiMinutiSicuri();
                    Tratta nuovaTratta = new Tratta(partenza, capolinea, LocalTime.of(orePreviste, minutiPrevisti));
                    td.save(nuovaTratta);

                    System.out.println("\nEvviva! La Tratta "+partenza+"->"+capolinea+" è stata salvata con successo! ");
                    break;
                case "2":
                    System.out.println("\n--- AVVIO NUOVO VIAGGIO ---");
                    List<Tratta> listaTratte = td.findAll();
                    if (listaTratte.isEmpty()) {
                        System.out.println("\nAccipigna! Non ci sono tratte registrate nel sistema. Creane prima una.");
                        break;
                    }

                    System.out.println("Seleziona la Tratta:");
                    for (int i = 0; i < listaTratte.size(); i++) {
                        Tratta trattaCorrente = listaTratte.get(i);
                        System.out.println((i + 1) + ". Da: " + trattaCorrente.getZonaPartenza() + " a: " + trattaCorrente.getCapolinea() + " (Previsto: " + trattaCorrente.getTempoPrevisto() + ")");
                    }
                    System.out.print("Scegli il numero della tratta: ");
                    int indiceTratta = leggiInteroSicuro() - 1;

                    while (indiceTratta < 0 || indiceTratta >= listaTratte.size()) {
                        System.out.println("\nPer tutte le pigne! Selezione tratta non valida.");
                        System.out.print("\nScegli un numero di tratta valido: ");
                        indiceTratta = leggiInteroSicuro() - 1;
                    }
                    Tratta tSelected = listaTratte.get(indiceTratta);

                    List<Mezzo> listaMezzi = md.findAllInServizioENonAncoraAssegnati();
                    if (listaMezzi.isEmpty()) {
                        System.out.println("\nAccipigna! Non ci sono mezzi disponibili nel parco mezzi.");
                        break;
                    }

                    System.out.println("\nSeleziona il Mezzo:");
                    for (int i = 0; i < listaMezzi.size(); i++) {
                        Mezzo mezzoCorrente = listaMezzi.get(i);
                        System.out.println((i + 1) + ". Mezzo: " + mezzoCorrente.getNomeMezzo() + " [" + mezzoCorrente.getTipoMezzo() + "]");
                    }
                    System.out.print("Scegli il numero del mezzo: ");
                    int indiceMezzo = leggiInteroSicuro() - 1;

                    while (indiceMezzo < 0 || indiceMezzo >= listaMezzi.size()) {
                        System.out.println("\nCervello di folletto! Selezione mezzo non valida.");
                        System.out.print("\nScegli un numero di mezzo valido: ");
                        indiceMezzo = leggiInteroSicuro() - 1;
                    }
                    Mezzo mSelected = listaMezzi.get(indiceMezzo);

                    Percorrenza p = new Percorrenza(tSelected, mSelected);
                    pd.save(p);
                    System.out.println("\nVado e svengo! Il mezzo " + mSelected.getNomeMezzo() + " è partito sulla tratta " + tSelected.getZonaPartenza() + " -> " + tSelected.getCapolinea());
                    break;

                case "3":
                    System.out.println("\n--- REGISTRA ARRIVO ---");

                    System.out.println("\n==================================================");
                    System.out.println("Per mille cuscini! Quanto ci mettono questi mezzi?");
                    System.out.println("==================================================\n");

                    List<Percorrenza> attive = pd.findPercorrenzeAttive();
                    if (attive.isEmpty()) {
                        System.out.println("\nPer tutte le pentole magiche! Al momento non ci sono mezzi in viaggio nel Fantabosco.");
                        break;
                    }

                    System.out.println("Seleziona la corsa:");
                    for (int i = 0; i < attive.size(); i++) {
                        Percorrenza per = attive.get(i);
                        System.out.println((i + 1) + ". Mezzo: [" + per.getMezzo().getNomeMezzo() +
                                "] sulla tratta: " + per.getTratta().getZonaPartenza() +
                                " -> " + per.getTratta().getCapolinea());
                    }

                    System.out.print("Scegli il numero della corsa: ");
                    int indicePercorrenza = leggiInteroSicuro() - 1;

                    while (indicePercorrenza < 0 || indicePercorrenza >= attive.size()) {
                        System.out.println("\nPer tutte le pigne! Selezione non valida.");
                        System.out.println("\nScegli un numero della corsa valido:");
                        indicePercorrenza = leggiInteroSicuro() - 1;
                    }

                    Percorrenza pSelezionata = attive.get(indicePercorrenza);

                    System.out.print("Inserisci le ore effettive impiegate: ");
                    int oreEff = leggiOreSicure();
                    System.out.print("Inserisci i minuti effettivi impiegati: ");
                    int minEff = leggiMinutiSicuri();

                    LocalTime tempoEffettivo = LocalTime.of(oreEff, minEff);

                    pd.aggiornaTempoEffettivo(pSelezionata.getIdPercorrenza(), tempoEffettivo);
                    System.out.println("\nFanta-fantastico! Il viaggio del mezzo " + pSelezionata.getMezzo().getNomeMezzo() + " è concluso.");
                    break;

                case "4":
                    System.out.println("\n--- CALCOLO TEMPO MEDIO EFFETTIVO (SELEZIONE GUIDATA) ---");

                    List<Tratta> tratteMedia = td.findAll();
                    if (tratteMedia.isEmpty()) {
                        System.out.println("\nBisce Secche! Non ci sono tratte nel sistema per calcolare una media.");
                        break;
                    }

                    System.out.println("Seleziona la Tratta da analizzare:");
                    for (int i = 0; i < tratteMedia.size(); i++) {
                        Tratta tr = tratteMedia.get(i);
                        System.out.println((i + 1) + ". " + tr.getZonaPartenza() + " -> " + tr.getCapolinea());
                    }
                    System.out.print("Scegli il numero della tratta: ");
                    int idxTratta = leggiInteroSicuro() - 1;

                    while (idxTratta < 0 || idxTratta >= tratteMedia.size()) {
                        System.out.println("\nSelezione tratta non valida.");
                        System.out.println("\nScegli un numero della tratta valido:");
                        idxTratta = leggiInteroSicuro() - 1;
                    }
                    Tratta trattaScelta = tratteMedia.get(idxTratta);


                    List<Mezzo> mezziMedia = md.findAllInServizio();
                    if (mezziMedia.isEmpty()) {
                        System.out.println("Per tutti i sorci verdi! Non ci sono mezzi disponibili nel sistema.");
                        break;
                    }

                    System.out.println("\nSeleziona il Mezzo da analizzare:");
                    for (int i = 0; i < mezziMedia.size(); i++) {
                        Mezzo mz = mezziMedia.get(i);
                        System.out.println((i + 1) + ". " + mz.getNomeMezzo() + " [" + mz.getTipoMezzo() + "]");
                    }
                    System.out.print("Scegli il numero del mezzo: ");
                    int idxMezzo = leggiInteroSicuro() - 1;

                    while (idxMezzo < 0 || idxMezzo >= mezziMedia.size()) {
                        System.out.println("\nUffa schiuma! Selezione mezzo non valida.");
                        System.out.println("\nScegli un numero mezzo valido:");
                        idxMezzo = leggiInteroSicuro() - 1;
                    }
                    Mezzo mezzoScelto = mezziMedia.get(idxMezzo);

                    LocalTime tempoMedio = pd.calcolaTempoMedioPercorrenza(trattaScelta.getIdTratta(), mezzoScelto.getIdMezzo());

                    if (tempoMedio != null) {
                        System.out.println("\nDritto del ramo, retro del foglio, fammi vedere le cose che voglio!...");
                        System.out.println("\nIl tempo medio di percorrenza effettivo è: " + tempoMedio);
                    } else {
                        System.out.println("\nOh sante acque! Non ci sono ancora percorrenze completate per questo mezzo su questa tratta.");
                    }
                    break;

                case "5":
                    System.out.println("\n--- CONTEGGIO PERCORRENZE COMPLETATE ---");

                    List<Tratta> tratteConta = td.findAll();
                    if (tratteConta.isEmpty()) {
                        System.out.println("Mamma folletta! Non ci sono tratte registrate nell'archivio.");
                        break;
                    }

                    System.out.println("Seleziona la Tratta da monitorare:");
                    for (int i = 0; i < tratteConta.size(); i++) {
                        Tratta tr = tratteConta.get(i);
                        System.out.println((i + 1) + ". " + tr.getZonaPartenza() + " -> " + tr.getCapolinea());
                    }
                    System.out.print("Scegli il numero della tratta: ");
                    int idxTrattaConta = leggiInteroSicuro() - 1;

                    while (idxTrattaConta < 0 || idxTrattaConta >= tratteConta.size()) {
                        System.out.println("\nSelezione tratta non valida.");
                        System.out.println("\nScegli un numero della tratta valido:");
                        idxTrattaConta = leggiInteroSicuro() - 1;
                    }
                    Tratta trattaContaScelta = tratteConta.get(idxTrattaConta);


                    List<Mezzo> mezziConta = md.findAllInServizioENonAncoraAssegnati();
                    if (mezziConta.isEmpty()) {
                        System.out.println("Folletto, folletto, cervello di foglietto! Non ci sono mezzi registrati nell'archivio.");
                        break;
                    }

                    System.out.println("\nSeleziona il Mezzo da monitorare:");
                    for (int i = 0; i < mezziConta.size(); i++) {
                        Mezzo mz = mezziConta.get(i);
                        System.out.println((i + 1) + ". " + mz.getNomeMezzo() + " [" + mz.getTipoMezzo() + "]");
                    }
                    System.out.print("Scegli il numero del mezzo: ");
                    int idxMezzoConta = leggiInteroSicuro() - 1;

                    while (idxMezzoConta < 0 || idxMezzoConta >= mezziConta.size()) {
                        System.out.println("\nSelezione mezzo non valida.");
                        System.out.println("\nScegli un numero mezzo valido:");
                        idxMezzoConta = leggiInteroSicuro() - 1;
                    }
                    Mezzo mezzoContaScelto = mezziConta.get(idxMezzoConta);

                    Long numeroCorse = pd.numeroPercorrenzePerMezzoETratta(trattaContaScelta.getIdTratta(), mezzoContaScelto.getIdMezzo());

                    System.out.println("\n=========================================================");
                    if(numeroCorse==0){System.out.println("Il mezzo '" + mezzoContaScelto.getNomeMezzo() +
                            "' non ha completato la tratta " + trattaContaScelta.getZonaPartenza() +
                            " -> " + trattaContaScelta.getCapolinea() + " neanche una volta.");}
                    else if(numeroCorse==1){ System.out.println("Il mezzo '" + mezzoContaScelto.getNomeMezzo() +
                            "' ha completato la tratta " + trattaContaScelta.getZonaPartenza() +
                            " -> " + trattaContaScelta.getCapolinea() + " una sola volta.");}
                    else if(numeroCorse==2){ System.out.println("Il mezzo '" + mezzoContaScelto.getNomeMezzo() +
                            "' ha completato la tratta " + trattaContaScelta.getZonaPartenza() +
                            " -> " + trattaContaScelta.getCapolinea() + " per ben Bue volte.");}
                    else if(numeroCorse==3){ System.out.println("Il mezzo '" + mezzoContaScelto.getNomeMezzo() +
                            "' ha completato la tratta " + trattaContaScelta.getZonaPartenza() +
                            " -> " + trattaContaScelta.getCapolinea() + " per ben Re volte.");}
                    else if(numeroCorse==4){ System.out.println("Il mezzo '" + mezzoContaScelto.getNomeMezzo() +
                            "' ha completato la tratta " + trattaContaScelta.getZonaPartenza() +
                            " -> " + trattaContaScelta.getCapolinea() + " per ben Gatto volte.");}
                    else if(numeroCorse==5){ System.out.println("Il mezzo '" + mezzoContaScelto.getNomeMezzo() +
                            "' ha completato la tratta " + trattaContaScelta.getZonaPartenza() +
                            " -> " + trattaContaScelta.getCapolinea() + " per ben Pingue volte.");}
                    else {
                    System.out.println("Il mezzo '" + mezzoContaScelto.getNomeMezzo() +
                            "' ha completato la tratta " + trattaContaScelta.getZonaPartenza() +
                            " -> " + trattaContaScelta.getCapolinea() + " per ben " + numeroCorse + " volte.");}
                    System.out.println("=========================================================");
                    break;

                case "0":
                    System.out.println("\nRitorno al Menu Dello Gnomo Archivista...");
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
                System.out.print("\nFolletto sbadato! Inserisci un numero intero valido: ");
            }
        }
    }

    private int leggiOreSicure() {
        while (true) {
            int ore = leggiInteroSicuro();
            if (ore >= 0 && ore <= 23) {
                return ore;
            }
            System.out.print("\nPer mille spadini spuntati! Le ore su un orologio vanno da 0 a 23. Riprova: ");
        }
    }

    private int leggiMinutiSicuri() {
        while (true) {
            int minuti = leggiInteroSicuro();
            if (minuti >= 0 && minuti <= 59) {
                return minuti;
            }
            System.out.print("\nPer la corona di Re Quercia! I minuti devono essere compresi tra 0 e 59. Riprova: ");
        }
    }


}
