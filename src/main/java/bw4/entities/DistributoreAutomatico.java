package bw4.entities;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DISTRIBUTORE")
public class DistributoreAutomatico extends PuntoVendita {

    @Column(name = "funzionante")
    private boolean funzionante;

    protected DistributoreAutomatico(){
    }

    public DistributoreAutomatico(String codicePuntoVendita, String nomePuntoVendita, String luogo){
        super(codicePuntoVendita,nomePuntoVendita, luogo);
        this.funzionante = true;
    }

    public boolean isFunzionante() {
        return funzionante;
    }

    public void setFunzionante(boolean funzionante) {
        this.funzionante = funzionante;
    }

    public void mandaInManutenzione(){
        this.funzionante = false;
    }

    public void rimettiInServizio(){
        this.funzionante = true;
    }



    @Override
    public String toString() {
        return "DistributoreAutomatico{" +
                "funzionante=" + funzionante +
                '}';
    }
}
