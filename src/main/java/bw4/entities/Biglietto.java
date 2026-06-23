package bw4.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("BIGLIETTO")
public class Biglietto extends TitoloDiViaggio{

    @Column(name = "obliterato")
    private boolean obliterato;

    @Column(name = "data_e_ora")
    private LocalDateTime dataEOra;

//    @ManyToOne
//    @JoinColumn(name = "id_mezzo")
//    private Mezzo mezzo;

    protected Biglietto(){}

    public Biglietto(String codiceTitoloDiViaggio, LocalDate dataEmissione, LocalDate dataScadenza){
        super(codiceTitoloDiViaggio, dataEmissione, dataScadenza);
        this.obliterato = false;
    }

    public boolean isObliterato() {
        return obliterato;
    }

    public LocalDateTime getDataEOra() {
        return dataEOra;
    }

//    public Mezzo getMezzo() {
//        return mezzo;
//    }

    public void setObliterato(boolean obliterato) {
        this.obliterato = obliterato;
    }

    public void setDataEOra(LocalDateTime dataEOra) {
        this.dataEOra = dataEOra;
    }

//    public void setMezzo(Mezzo mezzo) {
//        this.mezzo = mezzo;
//    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "obliterato=" + obliterato +
                ", dataEOra=" + dataEOra +
                ", mezzo=" + mezzo +
                '}';
    }
}
