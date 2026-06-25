package bw4.DAO;

import bw4.entities.Mezzo;
import bw4.entities.Percorrenza;
import bw4.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PercorrenzaDAO {

    private final EntityManager em;

    public PercorrenzaDAO(EntityManager em) {
            this.em = em;
    }

    public void save(Percorrenza percorrenza){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(percorrenza);
        transaction.commit();
        System.out.println("La percorrenza " + percorrenza + " è stata salvata!");
    }

    public Percorrenza findById(UUID idPercorrenza){
        return  em.find(Percorrenza.class, idPercorrenza);
    }

    public Percorrenza findById(String idPercorrenza){
        return  em.find(Percorrenza.class, UUID.fromString(idPercorrenza));
    }

    public void deleteById(UUID idPercorrenza) {
        Percorrenza percorrenza = findById(idPercorrenza);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(percorrenza);
        transaction.commit();
        System.out.println("La percorrenza " + percorrenza + " è stata eliminata!");
    }

    public void deleteById(String idPercorrenza) {
        Percorrenza percorrenza = findById(idPercorrenza);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(percorrenza);
        transaction.commit();
        System.out.println("La percorrenza " + percorrenza + " è stata eliminata!");
    }

    public LocalTime calcolaTempoMedioPercorrenza(UUID idTratta, UUID idMezzo){
        List<LocalTime> tempi = em.createQuery(
                        "SELECT p.tempoEffettivo FROM Percorrenza p WHERE p.tratta.idTratta = :idTratta AND p.mezzo.idMezzo = :idMezzo AND p.tempoEffettivo IS NOT NULL",
                        LocalTime.class)
                .setParameter("idTratta", idTratta)
                .setParameter("idMezzo", idMezzo)
                .getResultList();

        if(tempi.isEmpty()){
            System.out.println("Nessuna percorrenza per questo mezzo su questa tratta");
            return LocalTime.of(0,0);
        }

        double sommaSecondi = tempi.stream().mapToDouble(LocalTime::toSecondOfDay).sum();
        long mediaSecondi = (long) (sommaSecondi / tempi.size());

        return LocalTime.ofSecondOfDay(mediaSecondi);
    }

    public LocalTime calcolaTempoMedioPercorrenza(String idTratta, String idMezzo){
        List<LocalTime> tempi = em.createQuery(
                        "SELECT p.tempoEffettivo FROM Percorrenza p WHERE p.tratta.idTratta = :idTratta AND p.mezzo.idMezzo = :idMezzo AND p.tempoEffettivo IS NOT NULL",
                        LocalTime.class)
                .setParameter("idTratta", UUID.fromString(idTratta))
                .setParameter("idMezzo", UUID.fromString(idMezzo))
                .getResultList();

        if(tempi.isEmpty()){
            System.out.println("Nessuna percorrenza per questo mezzo su questa tratta");
            return LocalTime.of(0,0);
        }

        double sommaSecondi = tempi.stream().mapToDouble(LocalTime::toSecondOfDay).sum();
        long mediaSecondi = (long) (sommaSecondi / tempi.size());

        return LocalTime.ofSecondOfDay(mediaSecondi);
    }


    public Long numeroPercorrenzePerMezzoETratta(UUID idTratta, UUID idMezzo) {
        return em.createQuery(
                        "SELECT COUNT(p) FROM Percorrenza p WHERE p.tratta.idTratta = :idTratta AND p.mezzo.idMezzo = :idMezzo AND p.tempoEffettivo IS NOT NULL",
                        Long.class)
                .setParameter("idTratta", idTratta)
                .setParameter("idMezzo", idMezzo)
                .getSingleResult();
    }

    public Long numeroPercorrenzePerMezzoETratta(String idTratta, String idMezzo) {
        return em.createQuery(
                        "SELECT COUNT(p) FROM Percorrenza p WHERE p.tratta.idTratta = :idTratta AND p.mezzo.idMezzo = :idMezzo AND p.tempoEffettivo IS NOT NULL",
                        Long.class)
                .setParameter("idTratta", UUID.fromString(idTratta))
                .setParameter("idMezzo", UUID.fromString(idMezzo))
                .getSingleResult();
    }

    public void aggiornaTempoEffettivo(UUID idPercorrenza, LocalTime tempoEffettivo) {
        Percorrenza percorrenza = findById(idPercorrenza);

        if (percorrenza.getTempoEffettivo() == null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            percorrenza.setTempoEffettivo(tempoEffettivo);
            transaction.commit();
            System.out.println("Tempo effettivo aggiornato con successo per la percorrenza: " + idPercorrenza);
        } else {
            System.out.println("Alla percorrenza con ID: " + idPercorrenza + " è già stato assegnato un tempo effettivo!");
        }
    }

    public void aggiornaTempoEffettivo(String idPercorrenza, LocalTime tempoEffettivo) {
        Percorrenza percorrenza = findById(idPercorrenza);

        if (percorrenza.getTempoEffettivo() == null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            percorrenza.setTempoEffettivo(tempoEffettivo);
            transaction.commit();
            System.out.println("Tempo effettivo aggiornato con successo per la percorrenza: " + idPercorrenza);
        } else {
            System.out.println("Alla percorrenza con ID: " + idPercorrenza + " è già stato assegnato un tempo effettivo!");
        }
    }

    public List<Percorrenza> findPercorrenzeAttive() {
        try {
            return this.em.createQuery(
                    "SELECT p FROM Percorrenza p WHERE p.tempoEffettivo IS NULL",
                    Percorrenza.class
            ).getResultList();
        } catch (Exception e) {
            System.out.println("Errore durante il recupero delle percorrenze attive: " + e.getMessage());
            return new ArrayList<>();
        }
    }
//METODO ASSEGNA TRATTA A MEZZO IN SERVIZIO

    public void assegnaTrattaMezzo(Mezzo mezzo, Tratta tratta){

        if (mezzo == null) {
            System.out.println("Uffa, superuffa! Non posso assegnare la tratta perché il mezzo non è in servizio o non esiste.");
            return;
        }

        EntityTransaction transaction = this.em.getTransaction();
        try {
            transaction.begin();

            Percorrenza nuovaPercorrenza = new Percorrenza();
            nuovaPercorrenza.setMezzo(mezzo);
            nuovaPercorrenza.setTratta(tratta);

            this.em.persist(nuovaPercorrenza);

            transaction.commit();

            System.out.println("MESSAGGIO IMPORTANTE PER TUTTO IL FANTABOSCO: Il mezzo "
                    + mezzo.getNomeMezzo() + " è partito sulla tratta "
                    + tratta.getZonaPartenza() + " -> " + tratta.getCapolinea() + "!");

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Accipigna! Il database ha fatto i capricci. Impossibile salvare la percorrenza.");
        }
        }
}


