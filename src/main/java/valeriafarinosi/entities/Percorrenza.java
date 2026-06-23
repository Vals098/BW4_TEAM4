package valeriafarinosi.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "percorrenza")

public class Percorrenza {

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

    @Column(name = "numero_percorrenza")
    private int numeroPercorrenza;

    //Costruttori

    public Percorrenza() {
    }

    public Percorrenza(Tratta tratta, Mezzo mezzo, LocalTime tempoEffettivo, int numeroPercorrenza) {
        this.idPercorrenza = UUID.randomUUID();
        this.tratta = tratta;
        this.mezzo = mezzo;
        this.tempoEffettivo = tempoEffettivo;
        this.numeroPercorrenza = numeroPercorrenza;
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

    public int getNumeroPercorrenza() {
        return numeroPercorrenza;
    }
}
