package bw4.DAO;

import bw4.entities.Abbonamento;
import bw4.entities.Tessera;
import bw4.enums.TipoAbbonamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AbbonamentoDAO {
    private final EntityManager em;

    public AbbonamentoDAO(EntityManager em) {
        this.em = em;
    }

    // Crea abbonamento solo se tessera valida
    public void creaAbbonamento(Tessera tessera, TipoAbbonamento tipo, String codice, LocalDate dataInizio) {
        if (!tessera.isTesseraValida()) {
            System.out.println("Tessera scaduta! Impossibile creare abbonamento.");
            return;
        }
        LocalDate dataFine = tipo == TipoAbbonamento.SETTIMANALE
                ? dataInizio.plusWeeks(1)
                : dataInizio.plusMonths(1);

        Abbonamento abbonamento = new Abbonamento(codice, dataInizio, dataFine, tipo, tessera);

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(abbonamento);
            transaction.commit();
            System.out.println("Abbonamento creato: " + codice);
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("Errore: " + e.getMessage());
        }
    }

    // Trova per id
    public Abbonamento findById(UUID id) {
        return em.find(Abbonamento.class, id);
    }

    // Trova tutti gli abbonamenti di una tessera
    public List<Abbonamento> findByTessera(UUID tesseraId) {
        return em.createQuery(
                        "SELECT a FROM Abbonamento a WHERE a.tessera.idTessera = :tesseraId",
                        Abbonamento.class)
                .setParameter("tesseraId", tesseraId)
                .getResultList();
    }

    // Verifica se esiste un abbonamento valido per una tessera
    public boolean hasAbbonamentoValido(UUID tesseraId) {
        long count = em.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a " +
                                "WHERE a.tessera.idTessera = :tesseraId " +
                                "AND a.dataScadenza >= :oggi", Long.class)
                .setParameter("tesseraId", tesseraId)
                .setParameter("oggi", LocalDate.now())
                .getSingleResult();
        return count > 0;
    }
    // Conta abbonamenti venduti in un periodo
    public long countAbbonamentiVenduti(LocalDate da, LocalDate a) {
        return em.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a " +
                                "WHERE a.dataEmissione BETWEEN :da AND :a", Long.class)
                .setParameter("da", da)
                .setParameter("a", a)
                .getSingleResult();
    }

    // Conta abbonamenti per punto vendita in un periodo
    public long countAbbonamentiPerPuntoVendita(UUID puntoVenditaId, LocalDate da, LocalDate a) {
        return em.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a " +
                                "WHERE a.puntoVendita.idPuntoVendita = :puntoId " +
                                "AND a.dataEmissione BETWEEN :da AND :a", Long.class)
                .setParameter("puntoId", puntoVenditaId)
                .setParameter("da", da)
                .setParameter("a", a)
                .getSingleResult();
    }
}
