package bw4.scanner.menuAmministratore;

import bw4.DAO.BigliettoDAO;
import bw4.DAO.ManutenzioneDAO;
import bw4.DAO.MezzoDAO;
import bw4.entities.Manutenzione;
import bw4.entities.Mezzo;
import bw4.enums.StatoMezzo;
import bw4.enums.TipoMezzo;
import bw4.exceptions.NomeMezzoNonTrovatoException;
import bw4.exceptions.TipoMezzoNonAutorizzatoException;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.Metamodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class MenuParcoMezzi {
    private final Scanner scanner = new Scanner(System.in);


    //ATTRIBUTO
    private final MezzoDAO md;
    private final ManutenzioneDAO manutenzioneDAO;
    private final BigliettoDAO bd;

    //COSTRUTTORE

    public MenuParcoMezzi(MezzoDAO mezzoDAO, ManutenzioneDAO manutenzioneDAO, BigliettoDAO bd) {
        this.md = mezzoDAO;
        this.manutenzioneDAO = manutenzioneDAO;
        this.bd = bd;
    }

    //METODI

    public void start() {
        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== MENU Parco Mezzi ===\n");
            System.out.println("Eccoti Gipo! Dimmi come vuoi proseguire, digita:");
            System.out.println("1. Per creare un nuovo mezzo");
            System.out.println("2. Per segnalare un nuovo guasto");
            System.out.println("3. Per impostare la data di fine manutenzione di un mezzo");
            System.out.println("4. Per tenere traccia dei giorni totali di manutenzione di un mezzo");
            System.out.println("5. Per sapere il numero di biglietti vidimati su un mezzo");
            System.out.println("6. Per avere una lista di tutti i mezzi in servizio");
            System.out.println("7. Per avere lo storico di tutte le manutenzioni");
            System.out.println("0. Torna al menu dello Gnomo Archivista");

            int scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1:
                    System.out.println("Sei pronto alla creazione del nuovo mezzo? Bene! Mettiamoci a lavoro!");

                    boolean mezzoCreatoConSuccesso = false;

                    while (!mezzoCreatoConSuccesso) {

                        System.out.println("Che tipo di mezzo vuoi creare? Digita AUTOBUS o TRAM (oppure ESCI per annullare)");
                        String input = scanner.nextLine().toUpperCase();

                        if (Objects.equals(input, "ESCI")) {
                            System.out.println("Operazione annullata. Torno al menu principale.");
                            break;
                        }


                        try{
                            TipoMezzo tipoMezzo = TipoMezzo.valueOf(input);

                            if (tipoMezzo != TipoMezzo.AUTOBUS && tipoMezzo != TipoMezzo.TRAM) {
                                throw new TipoMezzoNonAutorizzatoException(tipoMezzo);
                            }

                            System.out.println("Hai scelto " + tipoMezzo + ".Sei sicuro della tua scelta? Digita SI oppure NO");
                            String conferma = scanner.nextLine().toUpperCase();

                            if (Objects.equals(conferma, "SI")) {
                                System.out.println("Tutto va a gonfie vele, come ti tratta il vento? Scegli un nome per il tuo mezzo!");
                                String nomeMezzo = scanner.nextLine();

                                Mezzo mezzoCreato = new Mezzo(tipoMezzo, nomeMezzo);
                                md.saveMezzo(mezzoCreato);
                                System.out.println("Per tutti i peli della mia coda, questo sì che è un colpo ben riuscito! Hai creato il mezzo: " + mezzoCreato);

                                mezzoCreatoConSuccesso = true;

                            } else if (Objects.equals(conferma, "NO")) {
                                System.out.println("Nessun problema, ricominciamo dall'inizio.");
                            } else {
                                System.out.println("La risposta può essere solo SI o NO. Per sicurezza ricominciamo.");
                            }

                        } catch (IllegalArgumentException e) {
                            System.out.println("Accipigna! Il mezzo scritto '" + input + "' è proibito dalle leggi del Fantabosco. Non esiste questo tipo di mezzo!");
                            System.out.println("Riprova un secondo colpo!");
                        } catch (TipoMezzoNonAutorizzatoException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Riprova a inserire un tipo valido.");
                        }
                            }
                    break;


                case 2:
                    System.out.println("Per tutti i legnetti storti del bosco! Qual è il nome del mezzo che ha bisogno di manutenzione?");
                    String nomeMezzoPerLaManutenzione = scanner.nextLine();
                    System.out.println("E perché ha bisogno di manutenzione?");
                    String causaManutenzione = scanner.nextLine();
                    System.out.println("In quale data si è rotto il mezzo? (YYYY-MM-DD)");
                    String dataInizioManutenzione = scanner.nextLine();

                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
                        LocalDate dataIM = LocalDate.parse(dataInizioManutenzione, formatter);
                        Mezzo mezzoInManutenzione = md.findMezzoByNameAndChangeStatus(nomeMezzoPerLaManutenzione, StatoMezzo.IN_MANUTENZIONE);

                        if (mezzoInManutenzione == null) {
                            System.out.println("Accipigna! Nessun mezzo trovato con il nome: " + nomeMezzoPerLaManutenzione);
                            break;
                        }

                        Manutenzione nuovaManutenzione = new Manutenzione(dataIM, mezzoInManutenzione, causaManutenzione);

                        manutenzioneDAO.saveInManutenzione(nuovaManutenzione);

                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Per tutti i legnetti! Il formato della data non è valido. Usa YYYY-MM-DD.");
                    } catch (Exception e) {
                        System.out.println("Errore durante l'inserimento in manutenzione: " + e.getMessage());
                    }
                    break;


                case 3:

                    boolean mezzoTrovatoConSuccesso = false;
                    String nomeMezzoDataFineManutenzione = "";
                    Manutenzione manutenzioneInCorso = null;


                    while (!mezzoTrovatoConSuccesso) {
                        System.out.println("Di quale mezzo vuoi impostare la data di fine manutenzione?");
                        nomeMezzoDataFineManutenzione = scanner.nextLine();


                        if (Objects.equals(nomeMezzoDataFineManutenzione.toUpperCase(), "ESCI")) {
                            System.out.println("Operazione annullata. Torno al menu.");
                            break;
                        }

                        try {
                            manutenzioneInCorso = manutenzioneDAO.findManutenzioneInCorsoByName(nomeMezzoDataFineManutenzione);

                            mezzoTrovatoConSuccesso = true;

                        } catch (NomeMezzoNonTrovatoException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Per favore, controlla il nome e riprova.");
                        } catch (bw4.exceptions.MezzoNonInManutenzioneException e) {
                            System.out.println("Attenzione: " + e.getMessage());
                            System.out.println("Scegli un altro mezzo.");
                        }

                    }

                    if (mezzoTrovatoConSuccesso) {
                        boolean dataValida = false;

                        while (!dataValida) {
                            System.out.println("inserisci la data di fine manutenzione (YYYY-MM-DD) oppure (YYYY-M-D)");
                            String dataInput = scanner.nextLine();
                            DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");

                            try {
                                LocalDate dataFineConvertita = LocalDate.parse(dataInput, dataFormatter);
                                manutenzioneDAO.setDataFineManutenzione(nomeMezzoDataFineManutenzione, dataFineConvertita);
                                dataValida = true;
                            } catch (java.time.format.DateTimeParseException e) {
                                System.out.println("Per tutti i legnetti! Il formato della data non è valido. Usa (YYYY-MM-DD) oppure (YYYY-M-D)");
                            }
                        }
                    }
                    break;

                case 4:
                    System.out.println("Di quale mezzo vuoi tenere traccia dei giorni totali di manutenzione?");
                    String nomeMezzoTracciaGiorniManutenzione = scanner.nextLine();
                    manutenzioneDAO.periodoManutenzione(nomeMezzoTracciaGiorniManutenzione);
                    break;

                case 5:
                    System.out.println("Di quale mezzo vuoi sapere il numero dei biglietti vidimati?");
                    String nomeMezzoCountObliteration = scanner.nextLine();
                    bd.countObliterazioniPerNomeMezzo(nomeMezzoCountObliteration);
                    break;

                case 6:
                    System.out.println("Ecco a te la lista di tutti i mezzi in servizio");
                  List<Mezzo> mezziInServizio = md.findAllInServizio();
                    for (Mezzo m : mezziInServizio) {
                        System.out.println("- " + m);
                    }
                    System.out.println();
                    break;

                case 7:
                    System.out.println("Ecco a te la lista di tutti i mezzi in manutenzione");
                    List<Manutenzione> mezziInManutenzione = manutenzioneDAO.findAllManutenzioni();
                    System.out.println(mezziInManutenzione + "\n");
                    break;

                case 0:
                    inSessione = false;
                    break;

                default:
                    System.out.println("Per tutte le pigne spignolate!");

            }
        }
    }
}
