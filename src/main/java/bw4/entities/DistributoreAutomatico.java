package bw4.entities;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("DISTRIBUTORE")
public class DistributoreAutomatico extends PuntoVendita {

    @Column(name = "funzionante")
    private boolean funzionante;

}
