package bw4.DAO;

import bw4.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


public class UtenteDAO {
    private final EntityManager em;
    public UtenteDAO(EntityManager em) {
        this.em = em;
    }
    public void save(Utente utente) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(utente);
            transaction.commit();
            System.out.println("Utente salvato: " + utente.getNome() + " " + utente.getCognome());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public Utente findById(long id) {
        return em.find(Utente.class, id);
    }
//    public void delete(long id) {
//        Utente trovato = this.findById(id);
//        if (trovato != null) {
//            EntityTransaction transaction = em.getTransaction();
//            try {
//                transaction.begin();
//                em.remove(trovato);
//                transaction.commit();
//                System.out.println("Utente eliminato!");
//            } catch (Exception e) {
//                if (transaction.isActive()) {
//                    transaction.rollback();
//                }
//                System.err.println("Errore: " + e.getMessage());
//            }
//        } else {
//            System.out.println("Impossibile eliminare " + id + ": non trovato!");
//        }
//    }
}
