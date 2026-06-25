package bw4.DAO;

import bw4.entities.DistributoreAutomatico;
import bw4.entities.PuntoVendita;
import bw4.exceptions.PuntoVenditaNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.SimpleTimeZone;
import java.util.UUID;

public class PuntoVenditaDAO {

    private final EntityManager em;

    public PuntoVenditaDAO(EntityManager em){
        this.em = em;
    }

//    SAVE
    public void save(PuntoVendita newPuntoVendita){
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        em.persist(newPuntoVendita);

        transaction.commit();

        System.out.println("Ultim'ora dal Fantabosco! Il nuovo punto vendita " + newPuntoVendita.getNomePuntoVendita() + " è apparso!");
    }

    //    FINDBYID
    public PuntoVendita findById(UUID idPuntoVendita){
        PuntoVendita found = em.find(PuntoVendita.class, idPuntoVendita);

        if(found == null){ throw new PuntoVenditaNonTrovatoException(idPuntoVendita);}

        return found;

    }

//    DELETEBYID
    public void deleteById(UUID idPuntoVendita){

        PuntoVendita found = em.find(PuntoVendita.class, idPuntoVendita);

        if(found == null){ throw new PuntoVenditaNonTrovatoException(idPuntoVendita);}

        em.getTransaction().begin();
        em.remove(found);
        em.getTransaction().commit();

    }

//    GET PUNTO VENDITA DATO LUOGO
    public List<PuntoVendita> findByLuogo(String luogo){

        TypedQuery<PuntoVendita> query = em.createQuery(
                "SELECT p FROM PuntoVendita p WHERE LOWER(p.luogo) = LOWER(:luogo)",
                PuntoVendita.class);

        query.setParameter("luogo", luogo);

        List<PuntoVendita> risultati = query.getResultList();

        if(risultati.isEmpty()){
            throw new PuntoVenditaNonTrovatoException("Per la mia corona di ghiande! Nessun fantastico punto vendita nel luogo " + luogo + "!");
        }

        return risultati;

    }

//    nel main
//String luogo = "Castello dei fiori";
//
//    List<PuntoVendita> risultati = pvd.findByLuogo(luogo);
//        System.out.println("Per mille Pentole Magiche!");
//        System.out.println("Punti Vendita in zona " + luogo + ":");
//
//        risultati.forEach(puntoVendita -> System.out.println(puntoVendita.getNomePuntoVendita()));


    //    GET DISTRIBUTORE AUTOMATICO FUORI SERVIZIO DATO LUOGO
    public List<DistributoreAutomatico> findByFuoriServizio(String luogo){

        TypedQuery<DistributoreAutomatico> query = em.createQuery(
                "SELECT d FROM DistributoreAutomatico d WHERE LOWER(d.luogo) = LOWER(:luogo) AND d.funzionante = false",
                DistributoreAutomatico.class);

                query.setParameter("luogo", luogo);

                List<DistributoreAutomatico> risultati = query.getResultList();

                if(risultati.isEmpty()){
                    throw new PuntoVenditaNonTrovatoException("Che strabiliante meraviglia! Nessun Distributore Automatico guasto nel luogo " + luogo + "!" );
                }
        return risultati;

//                nel main
//        List<DistributoreAutomatico> risultati = pvd.findByFuoriServizio("Castello dei fiori");
//        System.out.println("Per le verruche della mia bisnonna!");
//        System.out.println("Attualmente fuori servizio:");
//
//        risultati.forEach(distributoreAutomatico -> System.out.println(distributoreAutomatico.getNomePuntoVendita()));

    }





}
