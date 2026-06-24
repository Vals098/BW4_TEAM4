package bw4.DAO;

import bw4.entities.Percorrenza;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalTime;
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
}
