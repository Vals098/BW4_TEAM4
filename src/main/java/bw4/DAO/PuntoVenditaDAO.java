package bw4.DAO;

import bw4.entities.PuntoVendita;
import bw4.exceptions.PuntoVenditaNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class PuntoVenditaDAO {

    private final EntityManager em;

    public PuntoVenditaDAO(EntityManager em){
        this.em = em;
    }

//    SAVE
    public void savePuntoVendita(PuntoVendita newPuntoVendita){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(newPuntoVendita);

        transaction.commit();

        System.out.println("Il punto vendita " + newPuntoVendita + " è stato salvato correttamente!");
    }

    //    FINDBYID
    public PuntoVendita findPuntoVenditaById(UUID idPuntoVendita){
        PuntoVendita found = em.find(PuntoVendita.class, idPuntoVendita);

        if(found == null){ throw new PuntoVenditaNonTrovatoException(idPuntoVendita);}

        return found;

    }

//    DELETEBYID
//    public void deletePuntoVenditaById(UUID idPuntoVendita){
//
//        PuntoVendita found = em.find(PuntoVendita.class, idPuntoVendita);
//
//        if(found == null){ throw new PuntoVenditaNonTrovatoException(idPuntoVendita);}
//
//        em.getTransaction().begin();
//        em.remove(found);
//        em.getTransaction().commit();
//
//    }

//    GET PUNTO VENDITA DATO LUOGO
    public List<PuntoVendita> findPuntoVenditaByLuogo(String luogo){

        TypedQuery<PuntoVendita> query = em.createQuery(
                "SELECT p FROM PuntoVendita p WHERE p.luogo = :luogo",
                PuntoVendita.class);

        query.setParameter("luogo", luogo);

        return query.getResultList();

    }





}
