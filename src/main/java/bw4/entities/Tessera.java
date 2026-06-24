package bw4.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Tessera {
    @Id
    @GeneratedValue
    @Column(name = "id_tessera")
    private UUID idTessera;

    @Column(name = "numero_tessera", nullable = false, unique = true )
    private int numeroTessera;

    @Column(name = "data_di_emissione", nullable = false)
    private LocalDate dataDiEmissione;

    @Column(name = "data_di_scadenza", nullable = false)
    private LocalDate dataDiScadenza;

//    Relazione
    @OneToOne
    @JoinColumn(name = "id_utente",nullable = false)
    private Utente utente;

    public Tessera() {}

    public Tessera(int numeroTessera, LocalDate dataDiEmissione) {
        this.numeroTessera = numeroTessera;
        this.dataDiEmissione = dataDiEmissione;
        this.dataDiScadenza = dataDiEmissione.plusYears(1);
    }

//    Getter

    public UUID getIdTessera() {
        return idTessera;
    }

    public int getNumeroTessera() {
        return numeroTessera;
    }

    public LocalDate getDataDiEmissione() {
        return dataDiEmissione;
    }

    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

    public Utente getUtente() {
        return utente;
    }

    //    Setter


    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setDataDiEmissione(LocalDate dataDiEmissione) {
        this.dataDiEmissione = dataDiEmissione;
    }

    public void setDataDiScadenza(LocalDate dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                ", numeroTessera=" + numeroTessera +
                ", dataDiEmissione=" + dataDiEmissione +
                ", dataDiScadenza=" + dataDiScadenza +
                '}';
    }
//    // METODO CONTROLLO SCADENZA
//    public boolean isTesseraValida() {
//        LocalDate oggi = LocalDate.now();
//        if (this.dataDiScadenza.isBefore(oggi)) {
//            return false;
//        } else {
//            return true;
//        }
//    }
}
