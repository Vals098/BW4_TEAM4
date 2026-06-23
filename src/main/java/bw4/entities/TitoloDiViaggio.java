package bw4.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "titolo_di_viaggio")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_titolo_di_viaggio")
public abstract class TitoloDiViaggio {

    @Id
    @GeneratedValue
    @Column(name = "id_titolo_di_viaggio")
    private UUID idTitoloDiViaggio;

    @Column(name = "codice_titolo_di_viaggio", unique = true, nullable = false)
    private String codiceTitoloDiViaggio;

    @Column(name = "data_emissione", nullable = false)
    private LocalDate dataEmissione;

    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @ManyToOne
    @JoinColumn(name = "id_punto_vendita")
    private PuntoVendita puntoVendita;


    protected TitoloDiViaggio() {
    }

    public TitoloDiViaggio(String codiceTitoloDiViaggio, LocalDate dataEmissione, LocalDate dataScadenza) {
        this.codiceTitoloDiViaggio = codiceTitoloDiViaggio;
        this.dataEmissione = dataEmissione;
        this.dataScadenza = dataScadenza;
    }

    public UUID getIdTitoloDiViaggio() {
        return idTitoloDiViaggio;
    }

    public String getCodiceTitoloDiViaggio() {
        return codiceTitoloDiViaggio;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public PuntoVendita getPuntoVendita() {
        return puntoVendita;
    }

    @Override
    public String toString() {
        return "TitoloDiViaggio{" +
                "idTitoloDiViaggio=" + idTitoloDiViaggio +
                ", codiceTitoloDiViaggio='" + codiceTitoloDiViaggio + '\'' +
                ", dataEmissione=" + dataEmissione +
                ", dataScadenza=" + dataScadenza +
                ", puntoVendita=" + puntoVendita +
                '}';
    }
}
