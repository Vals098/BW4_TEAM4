package bw4.DAO;

import bw4.entities.Utente;
import bw4.exceptions.UtenteNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;


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

    public Utente findById(UUID id) {
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

//    FIND BY CODICE UTENTE (Vale)
public Utente findByCodiceUtente(String codiceUtente){
        TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE codiceUtente = :codiceUtente",
                Utente.class);
        query.setParameter("codiceUtente", codiceUtente);
        Utente found = query.getSingleResult();
        if(found == null){
            throw new UtenteNonTrovatoException("L'utente " + codiceUtente + " non fa parte del Fantabosco!");
        }

        return found;
}

//in main
// System.out.println("Fantavoloso utente trovato!");
// System.out.println(utente1FromDB.getNome() + " " + utente1FromDB.getCognome());



}
