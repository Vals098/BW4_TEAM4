package bw4;

import bw4.DAO.MezzoDAO;
import bw4.DAO.PuntoVenditaDAO;
import bw4.dao.PercorrenzaDAO;
import bw4.dao.TrattaDAO;
import bw4.entities.DistributoreAutomatico;
import bw4.entities.Mezzo;
import bw4.entities.PuntoVendita;
import bw4.entities.RivenditoreAutorizzato;
import bw4.enums.TipoMezzo;
import bw4.entities.Tratta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalTime;
import java.util.UUID;

public class Application {

        private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("BW4_TEAM4");

        public static void main(String[] args) {

                // CREAZIONE ENTITY MANAGER
                EntityManager em = emf.createEntityManager();

                // CREAZIONE DAO
                PuntoVenditaDAO pvd = new PuntoVenditaDAO(em);
                MezzoDAO md = new MezzoDAO(em);

                TrattaDAO td = new TrattaDAO(em);
                PercorrenzaDAO pd = new PercorrenzaDAO(em);

                // DATI
                PuntoVendita puntoVendita1 = new RivenditoreAutorizzato("LKI23", "Tabacchi delle fate", "Città Laggiù");
                PuntoVendita puntoVendita2 = new DistributoreAutomatico("5KS89", "Biglietteria Ali Spiegate",
                                "Castello dei fiori", false);

                Mezzo mezzo1 = new Mezzo(TipoMezzo.AUTOBUS, "AntroChiosco");

                // METODO SAVE
                // pvd.savePuntoVendita(puntoVendita1);
                // pvd.savePuntoVendita(puntoVendita2);
                // md.saveMezzo(mezzo1);

                // METODO MODIFICA IL TIPO DI MEZZO DA BUS A TRAM E VICEVERSA
                // md.modificaTipoMezzo(UUID.fromString("95c82485-8605-43b4-9e97-efed36a05399"),
                // TipoMezzo.TRAM);

                // TRATTE
                Tratta antroDellaStregaToChiosco = new Tratta("Antro della Strega", "Chiosco", LocalTime.of(0, 20));
                Tratta antroDellaStregaToCittaLaggiu = new Tratta("Antro della Strega", "Città Laggiù",
                                LocalTime.of(1, 30));
                Tratta antroDellaStregaToReggiaDiReQuercia = new Tratta("Antro della Strega", "Reggia di Re Quercia",
                                LocalTime.of(0, 40));
                Tratta antroDellaStregaToTanaDelLupo = new Tratta("Antro della Strega", "Tana del Lupo",
                                LocalTime.of(0, 30));
                Tratta chioscoToAntroDellaStrega = new Tratta("Chiosco", "Antro della Strega", LocalTime.of(0, 20));
                Tratta chioscoToCittaLaggiu = new Tratta("Chiosco", "Città Laggiù", LocalTime.of(1, 50));
                Tratta chioscoToReggiaDiReQuercia = new Tratta("Chiosco", "Reggia di Re Quercia", LocalTime.of(1, 0));
                Tratta chioscoToTanaDelLupo = new Tratta("Chiosco", "Tana del Lupo", LocalTime.of(0, 50));
                Tratta cittaLaggiuToAntroDellaStrega = new Tratta("Città Laggiù", "Antro della Strega",
                                LocalTime.of(1, 30));
                Tratta cittaLaggiuToChiosco = new Tratta("Città Laggiù", "Chiosco", LocalTime.of(1, 50));
                Tratta cittaLaggiuToReggiaDiReQuercia = new Tratta("Città Laggiù", "Reggia di Re Quercia",
                                LocalTime.of(2, 10));
                Tratta cittaLaggiuToTanaDelLupo = new Tratta("Città Laggiù", "Tana del Lupo", LocalTime.of(2, 0));
                Tratta reggiaDiReQuerciaToAntroDellaStrega = new Tratta("Reggia di Re Quercia", "Antro della Strega",
                                LocalTime.of(0, 40));
                Tratta reggiaDiReQuerciaToChiosco = new Tratta("Reggia di Re Quercia", "Chiosco", LocalTime.of(1, 0));
                Tratta reggiaDiReQuerciaToCittaLaggiu = new Tratta("Reggia di Re Quercia", "Città Laggiù",
                                LocalTime.of(2, 10));
                Tratta reggiaDiReQuerciaToTanaDelLupo = new Tratta("Reggia di Re Quercia", "Tana del Lupo",
                                LocalTime.of(0, 10));
                Tratta tanaDelLupoToAntroDellaStrega = new Tratta("Tana del Lupo", "Antro della Strega",
                                LocalTime.of(0, 30));
                Tratta tanaDelLupoToChiosco = new Tratta("Tana del Lupo", "Chiosco", LocalTime.of(0, 50));
                Tratta tanaDelLupoToCittaLaggiu = new Tratta("Tana del Lupo", "Città Laggiù", LocalTime.of(2, 0));
                Tratta tanaDelLupoToReggiaDiReQuercia = new Tratta("Tana del Lupo", "Reggia di Re Quercia",
                                LocalTime.of(0, 10));
                Tratta eliminabile = new Tratta("Eliminabile", "Eliminabile", LocalTime.of(0, 30));
                // td.save(antroDellaStregaToChiosco);
                // td.save(antroDellaStregaToCittaLaggiu);
                // td.save(antroDellaStregaToReggiaDiReQuercia);
                // td.save(antroDellaStregaToTanaDelLupo);
                // td.save(chioscoToAntroDellaStrega);
                // td.save(chioscoToCittaLaggiu);
                // td.save(chioscoToReggiaDiReQuercia);
                // td.save(chioscoToTanaDelLupo);
                // td.save(cittaLaggiuToAntroDellaStrega);
                // td.save(cittaLaggiuToChiosco);
                // td.save(cittaLaggiuToReggiaDiReQuercia);
                // td.save(cittaLaggiuToTanaDelLupo);
                // td.save(reggiaDiReQuerciaToAntroDellaStrega);
                // td.save(reggiaDiReQuerciaToChiosco);
                // td.save(reggiaDiReQuerciaToCittaLaggiu);
                // td.save(reggiaDiReQuerciaToTanaDelLupo);
                // td.save(tanaDelLupoToAntroDellaStrega);
                // td.save(tanaDelLupoToChiosco);
                // td.save(tanaDelLupoToCittaLaggiu);
                // td.save(tanaDelLupoToReggiaDiReQuercia);
                // td.save(eliminabile);
                // System.out.println(td.findById("48e48cee-d0bc-4c93-973a-d9a82a20e585"));
                // td.deleteById("48e48cee-d0bc-4c93-973a-d9a82a20e585");

        }
}
