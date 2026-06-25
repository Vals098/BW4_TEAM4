package bw4.DAO;

import bw4.entities.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.UUID;

public class TesseraDAO {
    private static EntityManager em;

    public TesseraDAO(EntityManager em) {
        TesseraDAO.em = em;
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

    // METODO CONTROLLO SCADENZA TESSERA DATO NUMERO TESSERA
    public static boolean isValid(int numeroTessera) {
        try {
            TypedQuery<Tessera> query = em.createQuery(
                    "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera",
                    Tessera.class);
            query.setParameter("numeroTessera", numeroTessera);
            Tessera tessera = query.getSingleResult();

            if (tessera.getDataDiScadenza().isBefore(LocalDate.now())) {
                System.out.println("Per le verruche della mia bisnonna! Tessera scaduta!");
                return false;
            }
            System.out.println("Che strabiliante meraviglia! Tessera valida!");
            return true;
        } catch (NoResultException e) {
            System.out.println("Accipigna! Nessuna tessera trovata con numero: " + numeroTessera);
            return false;
        }
    }

    // METODO RINNOVO TESSERA
    public void rinnovaTessera(int numeroTessera) {
        try {
            TypedQuery<Tessera> query = em.createQuery(
                    "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera",
                    Tessera.class);
            query.setParameter("numeroTessera", numeroTessera);
            Tessera tessera = query.getSingleResult();

            if (tessera.getDataDiScadenza().isBefore(LocalDate.now())) {
                EntityTransaction transaction = em.getTransaction();
                try {
                    transaction.begin();

                    tessera.setDataDiEmissione(LocalDate.now());
                    tessera.setDataDiScadenza(LocalDate.now().plusYears(1));
                    em.merge(tessera);

                    transaction.commit();
                    System.out.println("Che strabiliante meraviglia! La tessera numero " + numeroTessera + " è stata rinnovata!");
                } catch (Exception ex) {
                    if (transaction.isActive()) transaction.rollback();
                    System.err.println("Errore durante il salvataggio del rinnovo: " + ex.getMessage());
                }
            } else {
                System.out.println("Per mille pentole magiche! La tessera è ancora valida!");
            }
        } catch (NoResultException e) {
            System.out.println("Impossibile rinnovare: nessuna tessera trovata con numero: " + numeroTessera);
        }
    }
}