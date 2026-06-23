package bw4.entities;


import bw4.enums.TipoAbbonamento;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ABBONAMENTO")
public class Abbonamento extends TitoloDiViaggio {

    @Column(name = "tipo_abbonamento")
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;

//    @ManyToOne
//    @JoinColumn(name = "id_tessera")
//    private Tessera tessera;

    protected Abbonamento() {
    }

    public Abbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "tipoAbbonamento=" + tipoAbbonamento +
                '}';
    }
}
