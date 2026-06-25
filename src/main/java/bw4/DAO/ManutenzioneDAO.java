package bw4.DAO;

import bw4.entities.Manutenzione;
import bw4.entities.Mezzo;
import bw4.enums.StatoMezzo;
import bw4.exceptions.MezzoNonInManutenzioneException;
import bw4.exceptions.NomeMezzoNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
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
    public void saveInManutenzione(Manutenzione nuovaManutenzione){
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(nuovaManutenzione);
        transaction.commit();
        System.out.println("Per tutti i Fanti e i Re del mazzo! Il mezzo " + nuovaManutenzione.getMezzo().getNomeMezzo() + " è stato aggiunto alla lista dei mezzi in manutenzione!");
    }

    //METODO FIND MANUTENZIONE ATTIVA BY NOME MEZZO

    public Manutenzione findManutenzioneInCorsoByName (String nomeMezzo){
        TypedQuery<Manutenzione> query = this.entityManager.createQuery("SELECT m FROM Manutenzione m WHERE m.mezzo.nomeMezzo = :nome AND m.dataFine IS NULL", Manutenzione.class);
        query.setParameter("nome", nomeMezzo);

        try {
            Manutenzione manutenzioneTrovata = query.getSingleResult();
            System.out.println("Acciderbolina! La manutenzione è in corso, ma non preoccuparti presto il mezzo tornerà funzionante!");
            return manutenzioneTrovata;
        } catch (NoResultException e) {
            throw new MezzoNonInManutenzioneException(nomeMezzo);
        }
        }

    //METODO SET DATA FINE MANUTENZIONE
public void setDataFineManutenzione (String nomeMezzo, LocalDate dataFineManutenzione){
    try {
        Manutenzione manutenzioneInCorso = findManutenzioneInCorsoByName(nomeMezzo);
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();

            manutenzioneInCorso.setDataFine(dataFineManutenzione);

            if (manutenzioneInCorso.getMezzo() != null) {
                manutenzioneInCorso.getMezzo().setStatoMezzo(StatoMezzo.IN_SERVIZIO);
            }

            transaction.commit();

            nomeMezzo = (manutenzioneInCorso.getMezzo() != null)
                    ? manutenzioneInCorso.getMezzo().getNomeMezzo()
                    : "Uffa, superuffa e arciuffa! Questo mezzo non appare.";

            System.out.println("MESSAGGIO IMPORTANTE PER TUTTO IL FANTABOSCO: il mezzo " + nomeMezzo + " È DI NUOVO IN FUNZIONE!");

        } catch (Exception e) {

            if (transaction.isActive()) transaction.rollback();

            System.out.println("Errore durante la chiusura della manutenzione: ");

        }
    }    catch (NomeMezzoNonTrovatoException e) {
            System.out.println(e.getMessage());
        }
    }

    }

