package bw4;

import bw4.DAO.MezzoDAO;
import bw4.DAO.PuntoVenditaDAO;
import bw4.entities.DistributoreAutomatico;
import bw4.entities.Mezzo;
import bw4.entities.PuntoVendita;
import bw4.entities.RivenditoreAutorizzato;
import bw4.enums.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.UUID;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4_TEAM4");

    public static void main(String[] args) {

//        CREAZIONE ENTITY MANAGER
        EntityManager em = emf.createEntityManager();

//        CREAZIONE DAO
        PuntoVenditaDAO pvd = new PuntoVenditaDAO(em);
        MezzoDAO md = new MezzoDAO(em);


//        DATI
        PuntoVendita puntoVendita1 = new RivenditoreAutorizzato("LKI23", "Tabacchi delle fate", "Bosco Fatato");
        PuntoVendita puntoVendita2 = new DistributoreAutomatico("5KS89", "Biglietteria Ali Spiegate","Foresta Incantata", false);

        Mezzo mezzo1 = new Mezzo(TipoMezzo.AUTOBUS,"AntroChiosco");



//       METODO  SAVE
        //pvd.savePuntoVendita(puntoVendita1);
        //pvd.savePuntoVendita(puntoVendita2);
        //md.saveMezzo(mezzo1);

        //METODO MODIFICA IL TIPO DI MEZZO DA BUS A TRAM E VICEVERSA
        //md.modificaTipoMezzo(UUID.fromString("95c82485-8605-43b4-9e97-efed36a05399"), TipoMezzo.TRAM);


    }
}
