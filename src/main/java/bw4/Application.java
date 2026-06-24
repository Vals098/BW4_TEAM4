package bw4;

import bw4.DAO.PuntoVenditaDAO;
import bw4.dao.PercorrenzaDAO;
import bw4.dao.TrattaDAO;
import bw4.entities.DistributoreAutomatico;
import bw4.entities.PuntoVendita;
import bw4.entities.RivenditoreAutorizzato;
import bw4.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalTime;
import java.util.UUID;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4_TEAM4");

    public static void main(String[] args) {

//        CREAZIONE ENTITY MANAGER
        EntityManager em = emf.createEntityManager();

//        CREAZIONE DAO
        PuntoVenditaDAO pvd = new PuntoVenditaDAO(em);
        TrattaDAO td = new TrattaDAO(em);
        PercorrenzaDAO pd = new PercorrenzaDAO(em);

//        DATI
        PuntoVendita puntoVendita1 = new RivenditoreAutorizzato("LKI23", "Tabacchi delle fate", "Bosco Fatato");
        PuntoVendita puntoVendita2 = new DistributoreAutomatico("5KS89", "Biglietteria Ali Spiegate","Foresta Incantata", false);

//        pvd.savePuntoVendita(puntoVendita1);
//        pvd.savePuntoVendita(puntoVendita2);





    }
}
