package bw4.DAO;

import bw4.entities.Manutenzione;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ManutenzioneDAO {
    //ATTRIBUTO

    private final EntityManager entityManager;
    //COSTRUTTORE

    public ManutenzioneDAO(EntityManager em){
        this.entityManager = em;
    }

    //METODO SAVE
    public void save(Manutenzione nuovaManutenzione){
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(nuovaManutenzione);
        transaction.commit();
        System.out.println("manutenzione" +nuovaManutenzione + "aggiunta con successo!");
    }

    }
