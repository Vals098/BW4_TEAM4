package bw4.scanner.menuAmministratore;

import bw4.DAO.ManutenzioneDAO;
import bw4.DAO.MezzoDAO;
import bw4.entities.Manutenzione;
import bw4.entities.Mezzo;
import bw4.enums.StatoMezzo;
import bw4.enums.TipoMezzo;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.Metamodel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuParcoMezzi {
    private final Scanner scanner = new Scanner(System.in);


    //ATTRIBUTO
    private final MezzoDAO md;
    private final ManutenzioneDAO manutenzioneDAO;

    //COSTRUTTORE

    public MenuParcoMezzi(MezzoDAO mezzoDAO, ManutenzioneDAO manutenzioneDAO) {
        this.md = mezzoDAO;
        this.manutenzioneDAO = manutenzioneDAO;
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
            System.out.println("4. per tenere traccia dei giorni totali di manutenzione di un mezzo");
            System.out.println("5. per sapere il numero di biglietti vidimati su un mezzo");

            int scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1:
                   //salva nuovo mezzo
                    break;
                case 2:
                    System.out.println("Per tutti i legnetti storti del bosco! Qual è il nome del mezzo che ha bisogno di manutenzione?");
                    String nomeMezzo = scanner.nextLine();
                    System.out.println("E perché ha bisogno di manutenzione?");
                    String causaManutenzione = scanner.nextLine();
                    Mezzo mezzoInManutenzione = md.findMezzoByNameAndChangeStatus(nomeMezzo, StatoMezzo.IN_MANUTENZIONE );
                    Manutenzione nuovaManutenzione = new Manutenzione(LocalDate.now(), mezzoInManutenzione, causaManutenzione);
                    manutenzioneDAO.saveInManutenzione(nuovaManutenzione);
                    break;

                case 3:
                    System.out.println("Di quale mezzo vuoi tenere traccia dei giorni di manutenzione?");
                    String nomeMezzoTracciaGiorniManutenzione = scanner.nextLine();
                    manutenzioneDAO.periodoManutenzione(nomeMezzoTracciaGiorniManutenzione);
                    break;

                case 4:
                    break;

                default:
                    System.out.println("Per tutte le pigne spignolate!");

            }
        }
    }
}
