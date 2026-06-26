package bw4.DAO;

import bw4.entities.Mezzo;
import bw4.entities.Percorrenza;
import bw4.entities.Tratta;
import bw4.enums.StatoMezzo;
import bw4.enums.TipoMezzo;
import bw4.exceptions.IdMezzoNonTrovatoException;
import bw4.exceptions.NomeMezzoNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MezzoDAO {
    //ATTRIBUTO
    private final EntityManager entityManager;

    //COSTRUTTORE

    public MezzoDAO(EntityManager em) {
        this.entityManager = em;
    }

    //METODO SAVE
    public void saveMezzo(Mezzo nuovoMezzo) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(nuovoMezzo);
        transaction.commit();
    }

    //METODO FIND MEZZO BY ID
    public Mezzo findMezzoById(UUID idMezzo) {
        Mezzo mezzoDalDB = this.entityManager.find(Mezzo.class, idMezzo);
        if (mezzoDalDB == null) {
            throw new IdMezzoNonTrovatoException(idMezzo);
        }
        return mezzoDalDB;
    }

    //METODO UPDATE TIPO MEZZO TROVATO CON ID
    public void modificaTipoMezzo(UUID idMezzo, TipoMezzo nuovoTipo) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Mezzo mezzoTrovatoDalDB = findMezzoById(idMezzo);

            if (mezzoTrovatoDalDB != null) {
                mezzoTrovatoDalDB.setTipoMezzo(nuovoTipo);
                transaction.commit();
                System.out.println("Il tipo del mezzo è aggiornato con successo!");
            } else {
                System.out.println("Acciderbolina! Mezzo con id " + idMezzo + " non trovato!");
                transaction.rollback();
            }
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante la modifica: " + e.getMessage());
        }
    }


    //METODO RICERCA PER NOME MEZZO
    public Mezzo findMezzoByName(String nomeMezzo) {
        TypedQuery<Mezzo> query = this.entityManager.createQuery("SELECT m FROM Mezzo m WHERE m.nomeMezzo = :nomeMezzo", Mezzo.class);
        query.setParameter("nomeMezzo", nomeMezzo);
        try {
            Mezzo mezzoTrovato = query.getSingleResult();
            return mezzoTrovato;
        } catch (NoResultException e) {
            throw new NomeMezzoNonTrovatoException(nomeMezzo);
        }
    }

    //METODO RICERCA MEZZO PER NOME E CAMBIA STATO DEL MEZZO
    public Mezzo findMezzoByNameAndChangeStatus(String nomeMezzo, StatoMezzo statoMezzo) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Mezzo mezzoTrovato = findMezzoByName(nomeMezzo);

            if (mezzoTrovato != null) {
                mezzoTrovato.setStatoMezzo(statoMezzo);
                transaction.commit();
                if(statoMezzo == StatoMezzo.IN_MANUTENZIONE){
                    System.out.println("Per tutti i Fanti e i Re del mazzo! Il mezzo " + nomeMezzo + " è ora in manutenzione!");
                } else if (statoMezzo == StatoMezzo.IN_SERVIZIO) {
                    System.out.println("MESSAGGIO IMPORTANTE PER TUTTO IL FANTABOSCO: il mezzo " +nomeMezzo + " è di nuovo in funzione!");
                }

                return mezzoTrovato;
            } else {
                transaction.rollback();
                throw new NomeMezzoNonTrovatoException(nomeMezzo);
            }
        } catch (NomeMezzoNonTrovatoException e) {
            if (transaction.isActive())
                transaction.rollback();
            System.out.println(e.getMessage());
            return null;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("Errore imprevisto del sistema Fantabosco");
            return null;
        }
    }

    //RICERCA MEZZO PER NOME E VERIFICA SE E' IN SERVIZIO

    public Mezzo mezzoInServizio (String nomeMezzo){
        try {
            Mezzo mezzoTrovato = findMezzoByName(nomeMezzo);

            if (mezzoTrovato != null && mezzoTrovato.getStatoMezzo()==StatoMezzo.IN_SERVIZIO) {
                return mezzoTrovato;
            }else { throw new NomeMezzoNonTrovatoException(nomeMezzo);}
        } catch (NomeMezzoNonTrovatoException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



    //RICERCA TUTTI I MEZZI IN SERVIZIO

    public List<Mezzo> findAllInServizioENonAncoraAssegnati() {
        try {
            return this.entityManager.createQuery(
                    "SELECT m FROM Mezzo m WHERE m.statoMezzo = bw4.enums.StatoMezzo.IN_SERVIZIO AND m.idMezzo NOT IN (SELECT p.mezzo.idMezzo FROM Percorrenza p WHERE p.tempoEffettivo IS NULL)",
                    Mezzo.class
            ).getResultList();
        } catch (Exception e) {
            System.out.println("Errore durante il recupero dei mezzi: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
