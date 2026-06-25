package bw4.entities;

import bw4.enums.StatoMezzo;
import bw4.enums.TipoMezzo;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "mezzo" )
public class Mezzo {

    //ATTRIBUTI
    @Id
    @GeneratedValue
    @Column(name = "id_mezzo")
    private UUID idMezzo;

    @Column(nullable = false)
    private int capienza;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_mezzo", nullable = false)
    private StatoMezzo statoMezzo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mezzo", nullable = false)
    private TipoMezzo tipoMezzo;

    @Column(name = "nome_mezzo", nullable = false, unique = true)
    private String nomeMezzo;

    //COSTRUTTORE VUOTO
    protected Mezzo() {
    }


    //COSTRUTTORE

    public Mezzo(TipoMezzo tipoMezzo, String nomeMezzo){

        this.tipoMezzo = tipoMezzo;
        this.nomeMezzo = nomeMezzo;

        this.statoMezzo = StatoMezzo.IN_SERVIZIO; //di default nasce per essere utilizzato

        if(tipoMezzo == TipoMezzo.TRAM){
            this.capienza = 120;
        } else if (tipoMezzo == TipoMezzo.AUTOBUS) {
            this.capienza = 50;
        }

    }

    //GETTER E SETTER
    public UUID getIdMezzo() { return idMezzo; }

    public int getCapienza() { return capienza; }

    public StatoMezzo getStatoMezzo() { return statoMezzo; }

    public TipoMezzo getTipoMezzo() { return tipoMezzo; }

    public String getNomeMezzo() { return nomeMezzo; }

    public void setStatoMezzo(StatoMezzo statoMezzo) {
        this.statoMezzo = statoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    //TOSTRING


    @Override
    public String toString() {
        return "Mezzo{" +
                "idMezzo=" + getIdMezzo() +
                ", capienza=" + getCapienza() +
                ", statoMezzo=" + getStatoMezzo() +
                ", tipoMezzo=" + getTipoMezzo() +
                ", nomeMezzo='" + getNomeMezzo() + '\'' +
                '}';
    }
}


