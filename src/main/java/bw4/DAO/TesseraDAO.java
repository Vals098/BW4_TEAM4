package bw4.DAO;

import bw4.entities.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.Scanner; // <-- IMPORTANTE: Aggiunto l'import per far funzionare la versione del menu!
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

    public Tessera findByNumeroTessera(int numeroTessera) {
        try {
            return em.createQuery(
                            "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera",
                            Tessera.class)
                    .setParameter("numeroTessera", numeroTessera)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Tessera non trovata con numero: " + numeroTessera);
            return null;
        }
    }

    /**
     * 1. VERSIONE AUTOMATICA (Per i test rapidi nel Main / Application.java)
     */
    public void controllaERinnova(int numeroTessera) {
        try {
            TypedQuery<Tessera> query = em.createQuery(
                    "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera",
                    Tessera.class);
            query.setParameter("numeroTessera", numeroTessera);
            Tessera tessera = query.getSingleResult();

            if (tessera.getDataDiScadenza().isBefore(LocalDate.now())) {
                System.out.println("Per le verruche della mia bisnonna! Tessera scaduta!");
                System.out.println("la tessera " + numeroTessera + " è scaduta.");
                System.out.println("Procedo automaticamente con la richiesta di rinnovo");

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
                System.out.println("Che strabiliante meraviglia! Tessera valida!");
                System.out.println("La tessera " + numeroTessera + " adesso è attiva.");
            }
        } catch (NoResultException e) {
            System.out.println("Accipigna! Nessuna tessera trovata con numero: " + numeroTessera);
        }
    }

    /**
     * 2. VERSIONE INTERATTIVA CON SCANNER (Per il MenuUtentiETessere dell'Amministratore)
     */
    public void controllaERinnova(int numeroTessera, Scanner scanner) {
        try {
            TypedQuery<Tessera> query = em.createQuery(
                    "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera",
                    Tessera.class);
            query.setParameter("numeroTessera", numeroTessera);
            Tessera tessera = query.getSingleResult();

            if (tessera.getDataDiScadenza().isBefore(LocalDate.now())) {
                System.out.println("Per le verruche della mia bisnonna! Tessera scaduta!");
                System.out.println("La tessera " + numeroTessera + " è scaduta.");
                System.out.println("1. Procedi con la richiesta di rinnovo");
                System.out.println("2. Esci dal sistema e termina l'operazione");
                System.out.print("Scegli: ");
                String risposta = scanner.nextLine();

                if ("1".equals(risposta)) {
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
                        System.err.println("Errore durante il rinnovo: " + ex.getMessage());
                    }
                } else {
                    System.out.println("Operazione terminata. Nessun rinnovo effettuato.");
                }
            } else {
                System.out.println("Che strabiliante meraviglia! Tessera valida!");
                System.out.println("La tessera " + numeroTessera + " adesso è attiva.");
            }
        } catch (NoResultException e) {
            System.out.println("Accipigna! Nessuna tessera trovata con numero: " + numeroTessera);
        }
    }
}