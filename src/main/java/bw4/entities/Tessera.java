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
    private Utente idUtente;

    public Tessera() {}

    public Tessera(UUID idTessera, int numeroTessera, LocalDate dataDiEmissione, LocalDate dataDiScadenza, Utente utente) {
        this.idTessera = idTessera;
        this.numeroTessera = numeroTessera;
        this.dataDiEmissione = dataDiEmissione;
        this.dataDiScadenza = dataDiScadenza;
        this.idUtente = utente;
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
        return idUtente;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "idTessera=" + idTessera +
                ", numeroTessera=" + numeroTessera +
                ", dataDiEmissione=" + dataDiEmissione +
                ", dataDiScadenza=" + dataDiScadenza +
                ", idUtente=" + idUtente +
                '}';
    }
}
