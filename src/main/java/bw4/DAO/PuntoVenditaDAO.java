package bw4.DAO;

import bw4.entities.DistributoreAutomatico;
import bw4.entities.PuntoVendita;
import bw4.exceptions.PuntoVenditaNonTrovatoException;
import bw4.exceptions.TitoloDiViaggioNonTrovatoException;
import jakarta.persistence.*;

import java.time.LocalDate;
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

//    FIND PUNTO VENDITA BY CODICE PV
    public PuntoVendita findByCodice(String codicePuntoVendita){
        TypedQuery<PuntoVendita> query = em.createQuery(
                "SELECT p FROM PuntoVendita p WHERE p.codicePuntoVendita = :codicePuntoVendita",
                PuntoVendita.class);
        query.setParameter("codicePuntoVendita", codicePuntoVendita);

        try{
            return query.getSingleResult();
        } catch (NoResultException e){
            throw new PuntoVenditaNonTrovatoException("Per tutte le verruche della mia bisnonna!! Nessun punto con codice " + codicePuntoVendita + " trovato!");
        }

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


//    GET PUNTI VENDITA GUASTI DATO LUOGO
    public List<DistributoreAutomatico> findGuastiByLuogo(String luogo){

        TypedQuery<DistributoreAutomatico> query = em.createQuery(
                "SELECT d FROM DistributoreAutomatico d WHERE LOWER(d.luogo) = LOWER(:luogo) AND d.funzionante = false",
                DistributoreAutomatico.class);

        query.setParameter("luogo", luogo);

        List<DistributoreAutomatico> risultati = query.getResultList();

        if(risultati.isEmpty()){
            throw new PuntoVenditaNonTrovatoException("Per la mia corona di ghiande! Nessun distributore guasto nel luogo " + luogo + "!!");
        }

        return risultati;

    }



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


//    MANDA IL MEZZO IN MANUTENZIONE
    public void mandaInManutenzione(String codicePuntoVendita){

        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            TypedQuery<DistributoreAutomatico> query = em.createQuery(
                    "SELECT d FROM DistributoreAutomatico d WHERE d.codicePuntoVendita = :codicePuntoVendita",
            DistributoreAutomatico.class);

            query.setParameter("codicePuntoVendita", codicePuntoVendita);

            DistributoreAutomatico d = query.getSingleResult();

            d.mandaInManutenzione();

            transaction.commit();

            System.out.println("Fantaviglioso! Il distributore automatico è in manutenzione!");

        } catch (NoResultException e) {
            throw new PuntoVenditaNonTrovatoException(
                    "Nessun distributore associato al codice " + codicePuntoVendita
            );
        }


        }


    // Conta abbonamenti per punto vendita
    public long countAbbonamentiPerPuntoVendita(String codicePuntoVendita) {

        try {
            return em.createQuery(
                            "SELECT COUNT(a) FROM Abbonamento a " +
                                    "WHERE a.puntoVendita.codicePuntoVendita = :codice",
                            Long.class)
                    .setParameter("codice", codicePuntoVendita)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new PuntoVenditaNonTrovatoException(
                    "Nessun distributore associato al codice " + codicePuntoVendita
            );
        }

    }

    // Conta biglietti per punto vendita
    public long countBigliettiPerPuntoVendita(String codicePuntoVendita) {

        try {
            return em.createQuery(
                            "SELECT COUNT(a) FROM Abbonamento a " +
                                    "WHERE a.puntoVendita.codicePuntoVendita = :codice",
                            Long.class)
                    .setParameter("codice", codicePuntoVendita)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new PuntoVenditaNonTrovatoException(
                    "Nessun distributore associato al codice " + codicePuntoVendita
            );
        }

    }

    // Conta titoli di viaggio per punto vendita
    public long countTitoliPerPuntoVendita(String codicePuntoVendita) {

        Long numeroBiglietti = em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b " +
                                "WHERE b.puntoVendita.codicePuntoVendita = :codice",
                        Long.class)
                .setParameter("codice", codicePuntoVendita)
                .getSingleResult();

        Long numeroAbbonamenti = em.createQuery(
                        "SELECT COUNT(a) FROM Abbonamento a " +
                                "WHERE a.puntoVendita.codicePuntoVendita = :codice",
                        Long.class)
                .setParameter("codice", codicePuntoVendita)
                .getSingleResult();

        return numeroBiglietti + numeroAbbonamenti;
    }

    }





