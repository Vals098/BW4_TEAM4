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

    public DistributoreAutomatico(String codicePuntoVendita, String luogo,int numeroTitoliDiViaggio, boolean funzionante){
        super(codicePuntoVendita,luogo, numeroTitoliDiViaggio);
        this.funzionante = funzionante;
    }

    public boolean isFunzionante() {
        return funzionante;
    }

    public void setFunzionante(boolean funzionante) {
        this.funzionante = funzionante;
    }

    @Override
    public String toString() {
        return "DistributoreAutomatico{" +
                "funzionante=" + funzionante +
                '}';
    }
}
