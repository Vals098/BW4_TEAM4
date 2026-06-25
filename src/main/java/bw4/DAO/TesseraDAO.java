package bw4.DAO;

import bw4.entities.Tessera;
import bw4.entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
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
//    public void delete(UUID id) {
//        Tessera trovata = this.findById(id);
//        if (trovata != null) {
//            EntityTransaction transaction = em.getTransaction();
//            try {
//                transaction.begin();
//                em.remove(trovata);
//                transaction.commit();
//                System.out.println("Tessera eliminata!");
//            } catch (Exception e) {
//                if (transaction.isActive()) {
//                    transaction.rollback();
//                }
//                System.err.println("Errore: " + e.getMessage());
//            }
//        } else {
//            System.out.println("Impossibile eliminare " + id + ": non trovata!");
//        }
//    }




//    METODO CONTROLLO SCADENZA TESSERA DATO NUMERO TESSERA (Vale)
    public boolean isValid(int numeroTessera){

        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera",
                Tessera.class);
        query.setParameter("numeroTessera", numeroTessera);
        Tessera tessera = query.getSingleResult();
        if(tessera.getDataDiScadenza().isBefore(LocalDate.now())){
             System.out.println("Per le verruche della mia bisnonna! Tessera scaduta!");
             return false;
        }
        System.out.println("Che strabiliante meraviglia! Tessera valida!");
        return true;

    }

//    METODO RINNOVO TESSERA
//    set data di emissione a LocalDate.now()
    public void rinnovaTessera(int numeroTessera){

        TypedQuery<Tessera> query = em.createQuery(
                "SELECT t FROM Tessera t WHERE t.numeroTessera = :numeroTessera",
                Tessera.class);

        query.setParameter("numeroTessera", numeroTessera);

        Tessera tessera = query.getSingleResult();

        if(tessera.getDataDiScadenza().isBefore(LocalDate.now())){

            tessera.setDataDiEmissione(LocalDate.now());
            tessera.setDataDiScadenza(LocalDate.now().plusYears(1));

            em.getTransaction().begin();
            em.merge(tessera);
            em.getTransaction().commit();

            System.out.println("Che strabiliante meraviglia! La tessera di numero " + numeroTessera + " è sata rinnovata!");

        } else {
            System.out.println("Per mille pentole magiche! La tessera è ancora valida!");
        }

    }




}