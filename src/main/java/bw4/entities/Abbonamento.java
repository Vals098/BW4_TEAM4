package bw4.entities;


import bw4.enums.TipoAbbonamento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("ABBONAMENTO")
public class Abbonamento extends TitoloDiViaggio {

    @Column(name = "tipo_abbonamento", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;

    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private Tessera tessera;

    protected Abbonamento() {
    }

    public Abbonamento(String codiceTitoloDiViaggio, LocalDate dataEmissione,LocalDate dataScadenza,  TipoAbbonamento tipoAbbonamento) {
       super(codiceTitoloDiViaggio, dataEmissione, dataScadenza);
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }


    @Override
    public String toString() {
        return "Abbonamento{" +
                "tipoAbbonamento=" + tipoAbbonamento +
                ", tessera=" + tessera +
                '}';
    }
}
