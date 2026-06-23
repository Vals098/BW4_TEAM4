package bw4.dao;

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

public void deleteById(UUID idPercorrenza) {
    Percorrenza percorrenza = findById(idPercorrenza);
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    em.remove(percorrenza);
    transaction.commit();
    System.out.println("La percorrenza " + percorrenza + " è stata eliminata!");
}

    public Double calcolaTempoMedioPercorrenza(UUID idTratta, UUID idMezzo){
        List<LocalTime> tempi = em.createQuery(
                        "SELECT p.tempoEffettivo FROM Percorrenza p WHERE p.tratta.idTratta = :idTratta AND p.mezzo.idMezzo = :idMezzo AND p.tempoEffettivo IS NOT NULL",
                        LocalTime.class)
                .setParameter("idTratta", idTratta)
                .setParameter("idMezzo", idMezzo)
                .getResultList();

        if(tempi.isEmpty()){
            System.out.println("Nessuna percorrenza per questo mezzo su questa tratta");
            return 0.0;
        }

        double sommaSecondi = tempi.stream().mapToDouble(LocalTime::toSecondOfDay).sum();
        double mediaSecondi = sommaSecondi / tempi.size();

        return mediaSecondi / 60;
    }
}
