package bw4.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "manutenzioni")
public class Manutenzione {
    //ATTRIBUTI
    @Id
    @GeneratedValue
    @Column(name = "id_manutenzione", unique = true, nullable = false)
    private UUID idManutenzione;

    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;

    @Column(name = "data_fine")
    private LocalDate dataFine;

    @ManyToOne
    @JoinColumn(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    @Column(nullable = false)
    private String causa;

    //COSTRUTTORE VUOTO

    protected Manutenzione(){}

    //COSTRUTTORE
    public Manutenzione(LocalDate dataInizio, Mezzo mezzo, String causa){
        this.dataInizio = dataInizio;
        this.mezzo = mezzo;
        this.causa = causa;
        //data fine impostata automaticamente a null.
    }

    //GETTER E SETTER


    public UUID getIdManutenzione() { return idManutenzione; }

    public LocalDate getDataInizio() { return dataInizio; }

    public Mezzo getMezzo() { return mezzo; }

    public String getCausa() { return causa; }

    public LocalDate getDataFine() { return dataFine; }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    } //set per impostare la data di fine manutenzione.

    //TOSTRING

    @Override
    public String toString() {
        return "Manutenzione{" +
                "idManutenzione=" + getIdManutenzione() +
                ", dataInizio=" + getDataInizio() +
                ", dataFine=" + getDataFine() +
                ", mezzo=" + (getMezzo() != null ? getMezzo().getNomeMezzo() : "null") +
                ", causa='" + getCausa() + '\'' +
                '}';
    }
}
