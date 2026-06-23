package bw4.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tratta")
public class Tratta {

    //Attributi

    @Id
    @Column(name = "id_tratta")
    private UUID idTratta;

    @Column(name = "zona_partenza", nullable = false)
    private String zonaPartenza;

    @Column(name = "capolinea", nullable = false)
    private String capolinea;

    @Column(name = "tempo_previsto", nullable = false)
    private LocalTime tempoPrevisto;

    @OneToMany(mappedBy = "tratta")
    private List<Percorrenza> percorrenze;

    //Costruttori

    public Tratta() {
    }

    public Tratta(String zonaPartenza, String capolinea, LocalTime tempoPrevisto) {
        this.idTratta = UUID.randomUUID();
        this.zonaPartenza = zonaPartenza;
        this.capolinea = capolinea;
        this.tempoPrevisto = tempoPrevisto;
    }

    // Getter e Setter

    public UUID getIdTratta() {
        return idTratta;
    }

    public String getZonaPartenza() {
        return zonaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public LocalTime getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(LocalTime tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }

    public List<Percorrenza> getPercorrenze() {
        return percorrenze;
    }
}
