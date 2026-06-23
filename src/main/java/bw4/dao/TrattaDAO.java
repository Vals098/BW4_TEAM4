package bw4.dao;

import bw4.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

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
        return em.find(Tratta.class, idTratta);
    }

    public void deleteById(UUID idTratta) {
        Tratta tratta = em.find(Tratta.class, idTratta);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(tratta);
        transaction.commit();
        System.out.println("La tratta "+ tratta +" è stata eliminata!");
    }
}
