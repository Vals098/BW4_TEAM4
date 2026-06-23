package bw4.DAO;

import bw4.entities.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.UUID;

public class TesseraDAO {
    private final EntityManager em;
    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tessera tessera) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(tessera);
            transaction.commit();
            System.out.println("Tessera salvata: " + tessera.getNumeroTessera());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Errore: " + e.getMessage());
        }
    }
    public Tessera findById(UUID id) {
        return em.find(Tessera.class, id);
    }
    public void delete(UUID id) {
        Tessera trovata = this.findById(id);
        if (trovata != null) {
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();
                em.remove(trovata);
                transaction.commit();
                System.out.println("Tessera eliminata!");
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                System.err.println("Errore: " + e.getMessage());
            }
        } else {
            System.out.println("Impossibile eliminare " + id + ": non trovata!");
        }
    }
}