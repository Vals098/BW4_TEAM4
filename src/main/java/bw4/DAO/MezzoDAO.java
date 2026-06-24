package bw4.DAO;

import bw4.entities.Mezzo;
import bw4.enums.TipoMezzo;
import bw4.exceptions.IdMezzoNonTrovato;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.UUID;

public class MezzoDAO {
    //ATTRIBUTO
private final EntityManager entityManager;

    //COSTRUTTORE

    public MezzoDAO(EntityManager em){
        this.entityManager = em;
    }

    //METODO SAVE
    public void saveMezzo(Mezzo nuovoMezzo){
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(nuovoMezzo);
        transaction.commit();
        System.out.println(nuovoMezzo + "salvato con successo!");
    }

    //METODO FIND MEZZO BY ID
    public Mezzo findMezzoById (UUID idMezzo){
        Mezzo mezzoDalDB = this.entityManager.find(Mezzo.class, idMezzo);
        if(mezzoDalDB == null){
            throw new IdMezzoNonTrovato(idMezzo);
        }
        return mezzoDalDB;
    }

    //METODO UPDATE TIPO MEZZO TROVATO CON ID
    public void modificaTipoMezzo (UUID idMezzo, TipoMezzo nuovoTipo){
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            Mezzo mezzoTrovatoDalDB=  findMezzoById(idMezzo);

            if(mezzoTrovatoDalDB != null){mezzoTrovatoDalDB.setTipoMezzo(nuovoTipo);
            transaction.commit();
            System.out.println("Mezzo aggiornato con successo!");
            } else{
                System.out.println("mezzo con id " +idMezzo + " non trovato!");
                transaction.rollback();
            }
        } catch (RuntimeException e){
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante la modifica: " + e.getMessage());
        }
    }


    //METODO RICERCA PER NOME MEZZO
    public List<Mezzo> getMezzoByName(String nomeMezzo){
        TypedQuery<Mezzo>query = this.entityManager.createQuery("SELECT m FROM Mezzo m WHERE m.nomeMezzo = :nomeMezzo", Mezzo.class);
query.setParameter("nomeMezzo", nomeMezzo);
List<Mezzo> listaMezzi = query.getResultList();
return listaMezzi;
    }

}
