package bw4;

import bw4.DAO.*;
import bw4.entities.DistributoreAutomatico;
import bw4.entities.Mezzo;
import bw4.entities.PuntoVendita;
import bw4.entities.RivenditoreAutorizzato;
import bw4.enums.StatoMezzo;
//import bw4.DAO.AbbonamentoDAO;
import bw4.entities.*;
import bw4.enums.TipoMezzo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
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
                TitoloDiViaggioDAO tvd = new TitoloDiViaggioDAO(em);
                BigliettoDAO bd = new BigliettoDAO(em);
                ManutenzioneDAO manutenzioneDAO = new ManutenzioneDAO(em);
                // AbbonamentoDAO ad = new AbbonamentoDAO(em);

                // DATI

                // MEZZI

                Mezzo mezzoAntroChiosco = new Mezzo(TipoMezzo.AUTOBUS, "Antrochiosco");
                Mezzo mezzoAntroCittaLaggiu = new Mezzo(TipoMezzo.TRAM, "Antrocitta");
                Mezzo mezzoAntroReggia = new Mezzo(TipoMezzo.AUTOBUS, "Antroreggia");
                Mezzo mezzoAntroTana = new Mezzo(TipoMezzo.AUTOBUS, "Antrotana");
                Mezzo mezzoChioscoAntro = new Mezzo(TipoMezzo.AUTOBUS, "Chioscantro");
                Mezzo mezzoChioscoCittaLaggiu = new Mezzo(TipoMezzo.TRAM, "Chioscocitta");
                Mezzo mezzoChioscoReggia = new Mezzo(TipoMezzo.AUTOBUS, "Chioscoreggia");
                Mezzo mezzoChioscoTana = new Mezzo(TipoMezzo.AUTOBUS, "Chioscotana");
                Mezzo mezzoCittaLaggiuAntro = new Mezzo(TipoMezzo.TRAM, "Cittantro");
                Mezzo mezzoCittaLaggiuChiosco = new Mezzo(TipoMezzo.TRAM, "Cittachiosco");
                Mezzo mezzoCittaLaggiuReggia = new Mezzo(TipoMezzo.TRAM, "Cittareggia");
                Mezzo mezzocittaLaggiuTana = new Mezzo(TipoMezzo.TRAM, "Cittatana");
                Mezzo mezzoReggiaAntro = new Mezzo(TipoMezzo.AUTOBUS, "Reggiantro");
                Mezzo mezzoReggiaChiosco = new Mezzo(TipoMezzo.AUTOBUS, "Reggiachiosco");
                Mezzo mezzoReggiaCittaLaggiu = new Mezzo(TipoMezzo.TRAM, "Reggiacitta");
                Mezzo mezzoReggiaTana = new Mezzo(TipoMezzo.AUTOBUS, "Reggiatana");
                Mezzo mezzoTanaAntro = new Mezzo(TipoMezzo.AUTOBUS, "Tanantro");
                Mezzo mezzoTanaChiosco = new Mezzo(TipoMezzo.AUTOBUS, "Tanachiosco");
                Mezzo mezzoTanaCittalaggiu = new Mezzo(TipoMezzo.TRAM, "Tanacitta");
                Mezzo mezzoTanaReggia = new Mezzo(TipoMezzo.AUTOBUS, "Tanareggia");

                // PUNTI VENDITA

                PuntoVendita puntoVendita1 = new RivenditoreAutorizzato("LKI23", "Tabacchi delle fate",
                                "Castello dei fiori");
                PuntoVendita puntoVendita2 = new RivenditoreAutorizzato("KUDFG", "Arriverai cantando", "Città Laggiù");
                PuntoVendita puntoVendita3 = new RivenditoreAutorizzato("HF98S", "Strabiliante magia", "Chiosco");
                PuntoVendita puntoVendita4 = new RivenditoreAutorizzato("36ITI", "Ghiande in giro",
                                "Reggia di Re Quercia");
                PuntoVendita puntoVendita5 = new DistributoreAutomatico("249OI", "Viaggi Stregoneschi",
                                "Antro della Strega", false);
                PuntoVendita puntoVendita6 = new DistributoreAutomatico("9DF6K", "Qui e La",
                                "Città Laggiù", true);
                PuntoVendita puntoVendita7 = new DistributoreAutomatico("09S89", "Fantavigliosa avventura",
                                "Chiosco", true);
                PuntoVendita puntoVendita8 = new DistributoreAutomatico("9LDW0", "La tana di Lucio",
                                "Tana del Lupo", false);
                PuntoVendita puntoVendita9 = new DistributoreAutomatico("SPN45", "Tabacchi Sua Maesta",
                                "Reggia di Re Quercia", false);
                PuntoVendita puntoVendita10 = new DistributoreAutomatico("09WDD", "Viaggi Fatati",
                                "Castello dei fiori", false);

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

                //IN MANUTENZIONE
                Mezzo reggiatanaDalDB = md.findMezzoByName("Reggiatana");
                Mezzo antrochioscoDalDB = md.findMezzoByName("Antrochiosco");
                Manutenzione manutenzioneReggiatana = new Manutenzione(LocalDate.of(2026,6,24), reggiatanaDalDB, "Problemi al motore");
                Manutenzione manutenzioneAntrochiosco = new Manutenzione(LocalDate.of(2026,5,10), antrochioscoDalDB, "Perdita olio");


                // METODO SAVE

                // PUNTO VENDITA
                // pvd.save(puntoVendita1);
                // pvd.save(puntoVendita2);
                // pvd.save(puntoVendita3);
                // pvd.save(puntoVendita4);
                // pvd.save(puntoVendita5);
                // pvd.save(puntoVendita6);
                // pvd.save(puntoVendita7);
                // pvd.save(puntoVendita8);
                // pvd.save(puntoVendita9);
                // pvd.save(puntoVendita10);

                // MEZZO
                // md.saveMezzo(mezzoAntroChiosco);
                // md.saveMezzo(mezzoAntroCittaLaggiu);
                // md.saveMezzo(mezzoAntroReggia);
                // md.saveMezzo(mezzoAntroTana);
                // md.saveMezzo(mezzoChioscoAntro);
                // md.saveMezzo(mezzoChioscoCittaLaggiu);
                // md.saveMezzo(mezzoChioscoReggia);
                // md.saveMezzo(mezzoChioscoTana );
                // md.saveMezzo(mezzoCittaLaggiuAntro);
                // md.saveMezzo(mezzoCittaLaggiuChiosco);
                // md.saveMezzo(mezzoCittaLaggiuReggia);
                // md.saveMezzo(mezzocittaLaggiuTana);
                // md.saveMezzo(mezzoReggiaAntro);
                // md.saveMezzo(mezzoReggiaChiosco );
                // md.saveMezzo(mezzoReggiaCittaLaggiu);
                // md.saveMezzo(mezzoReggiaTana);
                // md.saveMezzo(mezzoTanaAntro);
                // md.saveMezzo(mezzoTanaChiosco );
                // md.saveMezzo(mezzoTanaCittalaggiu);
                // md.saveMezzo(mezzoTanaReggia);

                // TRATTA
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

                //IN MANUTENZIONE
//                manutenzioneDAO.save(manutenzioneReggiatana);
//                manutenzioneDAO.save(manutenzioneAntrochiosco);



                //METODI

                // METODO MODIFICA IL TIPO DI MEZZO DA BUS A TRAM E VICEVERSA
                // md.modificaTipoMezzo(UUID.fromString("95c82485-8605-43b4-9e97-efed36a05399"),
                // TipoMezzo.TRAM);

                // METODO TROVA MEZZO BY NAME
                // md.findMezzoByName("Reggiatana");

                // METODO RICERCA MEZZO PER NOME E CAMBIA STATO DEL MEZZO
                // md.findMezzoByNameAndChangeStatus("Reggiatana", StatoMezzo.IN_MANUTENZIONE);
                // md.findMezzoByNameAndChangeStatus("Antrochiosco",
                // StatoMezzo.IN_MANUTENZIONE);

                //METODO TROVA MANUTENZIONE IN CORSO
                Manutenzione manutenzioneInCorso1 = manutenzioneDAO.findManutenzioneInCorsoByName("Antrochiosco");

                //METODO SET DATA FINE ALLA MANUTENZIONE IN CORSO
                manutenzioneDAO.setDataFineManutenzione(manutenzioneInCorso1, LocalDate.of(2026,6,10));


                // METODO ELIMINA TRATTA
                Tratta eliminabile = new Tratta("Eliminabile", "Eliminabile", LocalTime.of(0, 30));
                // td.save(eliminabile);
                // System.out.println(td.findById("48e48cee-d0bc-4c93-973a-d9a82a20e585"));
                // td.deleteById("48e48cee-d0bc-4c93-973a-d9a82a20e585");


                // Test
                List<TitoloDiViaggio> risultati = tvd.findAll();
                for (TitoloDiViaggio t : risultati) {
                        System.out.println(t);
                }

        }
}
