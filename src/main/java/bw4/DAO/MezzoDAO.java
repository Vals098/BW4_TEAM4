package bw4.DAO;

import bw4.entities.Mezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MezzoDAO {
    //ATTRIBUTO
private final EntityManager entityManager;

    //COSTRUTTORE

    public MezzoDAO(EntityManager em){
        this.entityManager = em;
    }

    //METODO SAVE
    public void save(Mezzo nuovoMezzo){
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(nuovoMezzo);
        transaction.commit();
        System.out.println("il mezzo" + nuovoMezzo + "è stato salvato");
    }

    //METODO RICERCA PER NOME MEZZO
    public List<Mezzo> getMezzoByName(String nomeMezzo){
        TypedQuery<Mezzo>query = this.entityManager.createQuery("SELECT m FROM Mezzo m WHERE m.nomeMezzo = :nomeMezzo", Mezzo.class);
query.setParameter("nomeMezzo", nomeMezzo);
List<Mezzo> listaMezzi = query.getResultList();
return listaMezzi;
    }

}
