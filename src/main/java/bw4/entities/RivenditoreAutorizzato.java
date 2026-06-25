package bw4.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RIVENDITORE")
public class RivenditoreAutorizzato extends PuntoVendita {

    protected RivenditoreAutorizzato(){
    }

    public RivenditoreAutorizzato(String codicePuntoVendita, String nomePuntoVendita, String luogo){
        super(codicePuntoVendita,nomePuntoVendita, luogo);
    }

}
