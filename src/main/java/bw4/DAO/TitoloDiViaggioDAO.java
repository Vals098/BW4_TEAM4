package bw4.DAO;

import bw4.entities.TitoloDiViaggio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TitoloDiViaggioDAO {
    private final EntityManager em;

    public TitoloDiViaggioDAO(EntityManager em) {
        this.em = em;
    }

    public void save(TitoloDiViaggio titolo) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(titolo);
            transaction.commit();
            System.out.println("Titolo di viaggio salvato: " + titolo.getCodiceTitoloDiViaggio());
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public TitoloDiViaggio findById(UUID id) {

        return em.find(TitoloDiViaggio.class, id);
    }

    public TitoloDiViaggio findByCodice(String codice) {
        List<TitoloDiViaggio> risultati = em.createQuery(
                        "SELECT t FROM TitoloDiViaggio t " +
                                "WHERE t.codiceTitoloDiViaggio = :codice",
                        TitoloDiViaggio.class)
                .setParameter("codice", codice)
                .getResultList();
        return risultati.isEmpty() ? null : risultati.get(0);
    }

    public List<TitoloDiViaggio> findAll() {
        return em.createQuery(
                        "SELECT t FROM TitoloDiViaggio t", TitoloDiViaggio.class)
                .getResultList();
    }

    // Tutti i titoli in un periodo
    public List<TitoloDiViaggio> findByPeriodo(LocalDate da, LocalDate a) {
        return em.createQuery(
                        "SELECT t FROM TitoloDiViaggio t " +
                                "WHERE t.dataEmissione BETWEEN :da AND :a", TitoloDiViaggio.class)
                .setParameter("da", da)
                .setParameter("a", a)
                .getResultList();
    }

    // Count totale titoli in un periodo
    public long countByPeriodo(LocalDate da, LocalDate a) {
        return em.createQuery(
                        "SELECT COUNT(t) FROM TitoloDiViaggio t " +
                                "WHERE t.dataEmissione BETWEEN :da AND :a", Long.class)
                .setParameter("da", da)
                .setParameter("a", a)
                .getSingleResult();
    }

    // Count per punto vendita in un periodo
    public long countByPuntoVendita(UUID puntoVenditaId, LocalDate da, LocalDate a) {
        return em.createQuery(
                        "SELECT COUNT(t) FROM TitoloDiViaggio t " +
                                "WHERE t.puntoVendita.idPuntoVendita = :puntoId " +
                                "AND t.dataEmissione BETWEEN :da AND :a", Long.class)
                .setParameter("puntoId", puntoVenditaId)
                .setParameter("da", da)
                .setParameter("a", a)
                .getSingleResult();
    }
}

