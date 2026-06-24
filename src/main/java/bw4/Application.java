package bw4;

import bw4.DAO.PuntoVenditaDAO;
import bw4.entities.DistributoreAutomatico;
import bw4.entities.PuntoVendita;
import bw4.entities.RivenditoreAutorizzato;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4_TEAM4");

    public static void main(String[] args) {

//        CREAZIONE ENTITY MANAGER
        EntityManager em = emf.createEntityManager();

//        CREAZIONE DAO
        PuntoVenditaDAO pvd = new PuntoVenditaDAO(em);

//        DATI
        PuntoVendita puntoVendita1 = new RivenditoreAutorizzato("LKI23", "Tabacchi delle fate", "Città Laggiù");
        PuntoVendita puntoVendita2 = new DistributoreAutomatico("5KS89", "Biglietteria Ali Spiegate","Castello dei fiori", false);

//       METODO  SAVE
  //pvd.savePuntoVendita(puntoVendita1);
  //pvd.savePuntoVendita(puntoVendita2);


    }
}
