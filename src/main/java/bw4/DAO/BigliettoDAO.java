package bw4.DAO;
import bw4.entities.Biglietto;
import bw4.entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;

public class BigliettoDAO {
    private final EntityManager em;

    public BigliettoDAO(EntityManager em) { this.em = em; }

    public void obliteraBiglietto(UUID bigliettoId, Mezzo mezzo) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Biglietto biglietto = em.find(Biglietto.class, bigliettoId);
            if (biglietto == null) {
                System.out.println("Biglietto non trovato!");
                return;
            }
            if (biglietto.isObliterato()) {
                System.out.println("Biglietto già obliterato!");
                return;
            }
            biglietto.setObliterato(true);
            biglietto.setMezzo(mezzo);
            biglietto.setDataEOra(LocalDateTime.now());
            em.merge(biglietto);
            transaction.commit();
            System.out.println("Biglietto obliterato!");
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public long countObliterazioniPerMezzo(UUID mezzoId, LocalDateTime da, LocalDateTime a) {
        return em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b " +
                                "WHERE b.obliterato = true " +
                                "AND b.mezzo.idMezzo = :mezzoId " +
                                "AND b.dataEOra BETWEEN :da AND :a", Long.class)
                .setParameter("mezzoId", mezzoId)
                .setParameter("da", da)
                .setParameter("a", a)
                .getSingleResult();
    }



    public long countObliterazioniPerNomeMezzo( String nomeMezzo) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.obliterato = true AND b.mezzo.nomeMezzo = :nome", Long.class);
query.setParameter("nome", nomeMezzo);
Long numeroObliterazioni = query.getSingleResult();

        System.out.println("il numero di obliterazioni sul mezzo " +nomeMezzo+ " è " + numeroObliterazioni);

return numeroObliterazioni;



    }







    public long countBigliettiVenduti(LocalDate da, LocalDate a) {
        return em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b " +
                                "WHERE b.dataEmissione BETWEEN :da AND :a", Long.class)
                .setParameter("da", da)
                .setParameter("a", a)
                .getSingleResult();
    }
}
