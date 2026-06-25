package bw4.DAO;


import bw4.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TrattaDAO {

    private final EntityManager em;
    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta tratta) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(tratta);
        transaction.commit();
        System.out.println("La tratta "+ tratta +" è stata salvata!");
    }

    public Tratta findById(UUID idTratta) {
        return em.find(Tratta.class,idTratta);
    }

    public Tratta findById(String idTratta) {
        return em.find(Tratta.class,UUID.fromString(idTratta));
    }

    public Tratta findByZonaPertenzaECapolinea(String zonaPertenza, String capolinea) {
        return em.createQuery(
                "SELECT t FROM Tratta t WHERE t.zonaPartenza = :zonaPartenza AND t.capolinea = :capolinea", Tratta.class
        )
                .setParameter("zonaPartenza", zonaPertenza)
                .setParameter("capolinea", capolinea)
                .getSingleResult();
    }


    public void deleteById(UUID idTratta) {
        Tratta tratta = findById(idTratta);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(tratta);
        transaction.commit();
        System.out.println("La tratta "+ tratta +" è stata eliminata!");
    }

    public void deleteById(String idTratta) {
        Tratta tratta = findById(idTratta);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(tratta);
        transaction.commit();
        System.out.println("La tratta "+ tratta +" è stata eliminata!");
    }

    public List<Tratta> findAll() {
        try {
            return em.createQuery("FROM Tratta", Tratta.class).getResultList();
        } catch (Exception e) {
            System.out.println("Errore durante il recupero delle tratte: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
