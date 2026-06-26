package bw4.DAO;

import bw4.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.UUID;

public class UtenteDAO {
    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    // Salva un nuovo utente nel database
    public void save(Utente utente) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(utente);
            transaction.commit();
            System.out.println("Utente salvato con successo: " + utente.getIdUtente());
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    // CERCO UTENTE TRAMITE ID
    public Utente findById(UUID id) {
        return em.find(Utente.class, id);
    }

    public Utente findByCodiceUtente(String codice) {
        try {
            return em.createQuery("SELECT u FROM Utente u WHERE u.codiceUtente = :codice", Utente.class)
                    .setParameter("codice", codice)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } catch (Exception e) {
            System.err.println("Errore durante la ricerca per codice utente: " + e.getMessage());
            return null;
        }
    }

    // Trova un utente tramite il suo nome personale
    public Utente findByNome(String nome) {
        try {
            return em.createQuery("SELECT u FROM Utente u WHERE u.nome = :nome", Utente.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } catch (Exception e) {
            System.err.println("Errore durante la ricerca per nome: " + e.getMessage());
            return null;
        }
    }

    public void avviaCancellazioneUtente(UUID idUtente) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Utente utente = em.find(Utente.class, idUtente);
            if (utente != null) {
                // Facciamo partire il timer
                utente.setDataCancellazione(LocalDate.now());
                // Svuotiamo i reali dati personali presenti nella nuova entità Utente
                utente.setNome(null);
                utente.setCognome(null);
                utente.setResidenza(null);
                utente.setProfessione(null);
                utente.setDataNascita(null);
                em.merge(utente);
                System.out.println("La riga vuota verrà rimossa tra 1 mese.");
            } else {
                System.out.println("Accipigna! Nessun utente trovato con ID: " + idUtente);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public void pulisciUtentiEliminatiDefinitivamente() {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Calcoliamo la data (esattamente un mese fa da oggi)
            LocalDate limiteUnMeseFa = LocalDate.now().minusMonths(1);
            // Eseguiamo il DELETE per fare pulizia sul DB
            int righeCancellate = em.createQuery(
                            "DELETE FROM Utente u WHERE u.dataCancellazione IS NOT NULL AND u.dataCancellazione <= :dataLimite")
                    .setParameter("dataLimite", limiteUnMeseFa)
                    .executeUpdate();
            transaction.commit();

            if (righeCancellate > 0) {
                System.out.println("PULIZIA DATABASE: " + righeCancellate + " utenti eliminati da più di un mese.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Errore: " + e.getMessage());
        }
    }
}