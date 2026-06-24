package bw4;

import bw4.DAO.*;
import bw4.entities.*;
import bw4.enums.TipoMezzo;
import bw4.exceptions.UtenteNonTrovatoException;
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
                UtenteDAO ud = new UtenteDAO(em);
                TesseraDAO tesseraDAO = new TesseraDAO(em);



               // DATI
                PuntoVendita puntoVendita1 = new RivenditoreAutorizzato("LKI23", "Tabacchi delle fate", "Castello dei fiori");
                PuntoVendita puntoVendita2 = new RivenditoreAutorizzato("KUDFG", "Arriverai cantando", "Città Laggiù");
                PuntoVendita puntoVendita3 = new RivenditoreAutorizzato("HF98S", "Strabiliante magia", "Chiosco");
                PuntoVendita puntoVendita4 = new RivenditoreAutorizzato("36ITI", "Ghiande in giro", "Reggia di Re Quercia");
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


                Mezzo mezzo1 = new Mezzo(TipoMezzo.AUTOBUS, "AntroChiosco");

//                utenti e tessere
            Utente utente1 = new Utente("MICOT","Milo", "Cotogno", LocalDate.of(1981, 1, 14), "Regno di Fiabiselva", "Bibitiere e Aggiusta guai");
            Utente utente2 = new Utente("LULU","Lupo", "Lucio", LocalDate.of(1956, 1, 16), "Fittaforesta", "Lupo di Fiaba");
            Utente utente3 = new Utente("STRVAR","Strega", "Varana", LocalDate.of(1976, 10, 9), "Stregovia", "Strega Viola");
            Utente utente4 = new Utente("FALU", "Fata", "Lina", LocalDate.of(2010, 5, 30), "Regno d'Oltracque", "Fata Assistente");
            Utente utente5 = new Utente("GNORO","Gnomo", "Ronfo", LocalDate.of(2018, 12, 24), "Villaggio degli Gnomi", "Produrre tappi di sughero");
            Utente utente6 = new Utente("ORORC","Orchessa", "Orchidea", LocalDate.of(1945, 3, 31), "Orchiburghia", "Contadina di cocomeronzoli");
            Utente utente7 = new Utente("REGGAR","Reginotta", "Gardenia", LocalDate.of(2000, 3, 31), "Isola Giardinia", "Regina");
            Utente utente8 = new Utente("CUZIB","Cuoco", "Zibibbo", LocalDate.of(1998, 4, 27), "Stregovia", "Cuoco");
            Tessera tessera1 = new Tessera(1234,LocalDate.of(2025,4,19));
            Tessera tessera2 = new Tessera(5678,LocalDate.of(2026,5,10));
            Tessera tessera3 = new Tessera(9542,LocalDate.of(2025,11,3));
            Tessera tessera4 = new Tessera(9346,LocalDate.of(2023,1,11));
            Tessera tessera5 = new Tessera(0653,LocalDate.of(2026,6,24));
            Tessera tessera6 = new Tessera(1398,LocalDate.of(2025,12,6));
            Tessera tessera7 = new Tessera(4577,LocalDate.of(2024,7,30));
            Tessera tessera8 = new Tessera(3573,LocalDate.of(2026,2,14));


                // METODO SAVE
                 pvd.save(puntoVendita1);
                 pvd.save(puntoVendita2);
                 pvd.save(puntoVendita3);
                 pvd.save(puntoVendita4);
                 pvd.save(puntoVendita5);
                 pvd.save(puntoVendita6);
                 pvd.save(puntoVendita7);
                 pvd.save(puntoVendita8);
                 pvd.save(puntoVendita9);
                 pvd.save(puntoVendita10);
                 md.saveMezzo(mezzo1);

            ud.save(utente1);
            ud.save(utente2);
            ud.save(utente3);
            ud.save(utente4);
            ud.save(utente5);
            ud.save(utente6);
            ud.save(utente7);
            ud.save(utente8);


//            FINDBYCODICEUTENTE
//            try{
//                Utente utente1FromDB = ud.findByCodiceUtente("MICOT");
//                Utente utente2FromDB = ud.findByCodiceUtente("LULU");
//                Utente utente3FromDB = ud.findByCodiceUtente("STRVAR");
//                Utente utente4FromDB = ud.findByCodiceUtente("FALU");
//                Utente utente5FromDB = ud.findByCodiceUtente("GNORO");
//                Utente utente6FromDB = ud.findByCodiceUtente("ORORC");
//                Utente utente7FromDB = ud.findByCodiceUtente("REGGAR");
//                Utente utente8FromDB = ud.findByCodiceUtente("CUZIB");
//
//
//                System.out.println("Fantavoloso utente trovato!");
//                System.out.println(utente1FromDB.getNome() + " " + utente1FromDB.getCognome());
//
//                tessera1.setUtente(utente1FromDB);
//                tessera2.setUtente(utente2FromDB);
//                tessera3.setUtente(utente3FromDB);
//                tessera4.setUtente(utente4FromDB);
//                tessera5.setUtente(utente5FromDB);
//                tessera6.setUtente(utente6FromDB);
//                tessera7.setUtente(utente7FromDB);
//                tessera8.setUtente(utente8FromDB);
//
//
//                tesseraDAO.save(tessera1);
//                tesseraDAO.save(tessera2);
//                tesseraDAO.save(tessera3);
//                tesseraDAO.save(tessera4);
//                tesseraDAO.save(tessera5);
//                tesseraDAO.save(tessera6);
//                tesseraDAO.save(tessera7);
//                tesseraDAO.save(tessera8);
//
//            } catch (UtenteNonTrovatoException e){
//                System.out.println(e.getMessage());
//            }



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
//                 td.save(antroDellaStregaToChiosco);
//                 td.save(antroDellaStregaToCittaLaggiu);
//                 td.save(antroDellaStregaToReggiaDiReQuercia);
//                 td.save(antroDellaStregaToTanaDelLupo);
//                 td.save(chioscoToAntroDellaStrega);
//                 td.save(chioscoToCittaLaggiu);
//                 td.save(chioscoToReggiaDiReQuercia);
//                 td.save(chioscoToTanaDelLupo);
//                 td.save(cittaLaggiuToAntroDellaStrega);
//                 td.save(cittaLaggiuToChiosco);
//                 td.save(cittaLaggiuToReggiaDiReQuercia);
//                 td.save(cittaLaggiuToTanaDelLupo);
//                 td.save(reggiaDiReQuerciaToAntroDellaStrega);
//                 td.save(reggiaDiReQuerciaToChiosco);
//                 td.save(reggiaDiReQuerciaToCittaLaggiu);
//                 td.save(reggiaDiReQuerciaToTanaDelLupo);
//                 td.save(tanaDelLupoToAntroDellaStrega);
//                 td.save(tanaDelLupoToChiosco);
//                 td.save(tanaDelLupoToCittaLaggiu);
//                 td.save(tanaDelLupoToReggiaDiReQuercia);
//                 td.save(eliminabile);
                // System.out.println(td.findById("48e48cee-d0bc-4c93-973a-d9a82a20e585"));
                // td.deleteById("48e48cee-d0bc-4c93-973a-d9a82a20e585");

        }
}
