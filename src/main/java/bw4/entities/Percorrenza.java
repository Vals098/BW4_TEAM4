package bw4.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "percorrenza")

public class
Percorrenza {

    //Attributi

    @Id
    @Column(name = "id_percorrenza")
    private UUID idPercorrenza;

    @ManyToOne
    @JoinColumn(name = "id_tratta", nullable = false)
    private Tratta tratta;

    @ManyToOne
    @JoinColumn(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    @Column(name = "tempo_effettivo")
    private LocalTime tempoEffettivo;

    //Costruttori

    public Percorrenza() {
    }

    public Percorrenza(Tratta tratta, Mezzo mezzo) {
        this.idPercorrenza = UUID.randomUUID();
        this.tratta = tratta;
        this.mezzo = mezzo;
        this.tempoEffettivo = null;
    }

    //Getter e Setter

    public UUID getIdPercorrenza() {
        return idPercorrenza;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public LocalTime getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(LocalTime tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    public void setMezzo(Mezzo mezzo) { this.mezzo = mezzo; }

    public void setTratta(Tratta tratta) { this.tratta = tratta;}

    @Override
    public String toString() {
        return "Percorrenza{" +
                "idPercorrenza=" + idPercorrenza +
                ", idTratta=" + (tratta != null ? tratta.getIdTratta() : null) +
                ", idMezzo=" + (mezzo != null ? mezzo.getIdMezzo() : null) +
                ", tempoEffettivo=" + tempoEffettivo +
                '}';
    }
}
