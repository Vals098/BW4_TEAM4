package bw4.DAO;

import bw4.entities.Utente;
import bw4.exceptions.UtenteNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

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

    // FIND BY CODICE UTENTE
    public Utente findByCodiceUtente(String codiceUtente) {
        try {
            TypedQuery<Utente> query = em.createQuery(
                    "SELECT u FROM Utente u WHERE u.codiceUtente = :codiceUtente",
                    Utente.class);
            query.setParameter("codiceUtente", codiceUtente);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new UtenteNonTrovatoException("Accipigna!! L'utente con codice: " + codiceUtente + " non fa parte del Fantabosco!");
        }
    }

    // Dato il numero tessera trova l'utente
    public Utente findByNumeroTessera(int numeroTessera) {
        try {
            TypedQuery<Utente> query = em.createQuery(
                    "SELECT u FROM Utente u WHERE u.tessera.numeroTessera = :numTessera",
                    Utente.class);
            query.setParameter("numTessera", numeroTessera);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nessun utente trovato con il numero tessera: " + numeroTessera);
            return null; // O puoi lanciare un'eccezione personalizzata anche qui
        }
    }
}