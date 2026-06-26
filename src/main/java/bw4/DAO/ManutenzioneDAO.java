package bw4.DAO;

import bw4.entities.Manutenzione;
import bw4.entities.Mezzo;
import bw4.entities.Tratta;
import bw4.enums.StatoMezzo;
import bw4.exceptions.MezzoNonInManutenzioneException;
import bw4.exceptions.NomeMezzoNonTrovatoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class ManutenzioneDAO {
    //ATTRIBUTO

    private final EntityManager entityManager;
    //COSTRUTTORE

    public ManutenzioneDAO(EntityManager em) {
        this.entityManager = em;
    }

    //METODO SAVE
    public void saveInManutenzione(Manutenzione nuovaManutenzione) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(nuovaManutenzione);
        transaction.commit();

    }

    //METODO FIND MANUTENZIONE IN CORSO BY NOME MEZZO

    public Manutenzione findManutenzioneInCorsoByName(String nomeMezzo) {
        TypedQuery<Manutenzione> query = this.entityManager.createQuery("SELECT m FROM Manutenzione m WHERE m.mezzo.nomeMezzo = :nome AND m.dataFine IS NULL", Manutenzione.class);
        query.setParameter("nome", nomeMezzo);

        try {
            Manutenzione manutenzioneTrovata = query.getSingleResult();
            System.out.println("La manutenzione è in corso, ma le pigne tornano sempre sui rami!!");
            return manutenzioneTrovata;
        } catch (NoResultException e) {
            throw new MezzoNonInManutenzioneException(nomeMezzo);
        }
    }

    //METODO SET DATA FINE MANUTENZIONE
    public void setDataFineManutenzione(String nomeMezzo, LocalDate dataFineManutenzione) {
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
        } catch (NomeMezzoNonTrovatoException e) {
            System.out.println(e.getMessage());
        }
    }


    //DATO IL NOME DI UN MEZZO CHE HA CONCLUSO LA MANUTENZIONE, TRACCIA GIORNI MANUTENZIONE

    public long periodoManutenzione(String nomeMezzo) {
        TypedQuery<Manutenzione> query = this.entityManager.createQuery("SELECT m FROM Manutenzione m WHERE m.mezzo.nomeMezzo = :nome AND m.dataFine IS NOT NULL " +
                "ORDER BY m.dataInizio DESC", Manutenzione.class);
        query.setParameter("nome", nomeMezzo);

        List<Manutenzione> manutenzioniConcluse = query.getResultList();

        if (manutenzioniConcluse.isEmpty()) {
            System.out.println("Accipigna! Nessuna manutenzione conclusa trovata per il mezzo: " + nomeMezzo);
            return 0;
        }

        long giorniTotaliFermo = 0;

        for (Manutenzione m : manutenzioniConcluse) {
            LocalDate dataInizio = m.getDataInizio();
            LocalDate dataFine = m.getDataFine();

            long giorniSingoloFermo = ChronoUnit.DAYS.between(dataInizio, dataFine);
            giorniTotaliFermo += giorniSingoloFermo;

        }

        String nomeRealeMezzo = manutenzioniConcluse.get(0).getMezzo().getNomeMezzo();
        System.out.println("Acciderbolina! Il mezzo " + nomeRealeMezzo +
                " in tutta la sua storia è stato fermo per un totale complessivo di " +
                giorniTotaliFermo + " giorni (divisi in " + manutenzioniConcluse.size() + " interventi).");

        return giorniTotaliFermo;

    }
}

