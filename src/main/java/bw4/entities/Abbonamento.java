package bw4.entities;

import bw4.enums.TipoAbbonamento;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "abbonamento")
@PrimaryKeyJoinColumn(name = "id_titolo_di_viaggio")
@DiscriminatorValue("ABBONAMENTO")
public class Abbonamento extends TitoloDiViaggio {

    @Column(name = "tipo_abbonamento")
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;

    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private Tessera tessera;

    protected Abbonamento() {}

    public Abbonamento(String codice, LocalDate dataInizio, LocalDate dataFine,
                       TipoAbbonamento tipoAbbonamento, Tessera tessera) {
        super(codice, dataInizio, dataFine);
        this.tipoAbbonamento = tipoAbbonamento;
        this.tessera = tessera;
    }

    // GETTER
    public TipoAbbonamento getTipoAbbonamento() { return tipoAbbonamento; }
    public Tessera getTessera() { return tessera; }

    // SETTER
    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }
    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "tipoAbbonamento=" + tipoAbbonamento +
                ", tessera=" + tessera +
                '}';
    }
}