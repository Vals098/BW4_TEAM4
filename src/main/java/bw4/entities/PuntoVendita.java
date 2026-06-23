package bw4.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "punto_vendita")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_punto_vendita")
public abstract class PuntoVendita {

    @Id
    @GeneratedValue
    @Column(name = "id_punto_vendita")
    private UUID idPuntoVendita;

    @Column(name = "codice_punto_vendita", unique = true, nullable = false)
    private String codicePuntoVendita;

    @Column(name = "luogo", nullable = false)
    private String luogo;

    @OneToMany(mappedBy = "puntoVendita")
    private List<TitoloDiViaggio> titoliDiViaggio;

    protected PuntoVendita() {
    }

    public PuntoVendita(String codicePuntoVendita, String luogo) {
        this.codicePuntoVendita = codicePuntoVendita;
        this.luogo = luogo;
    }


    public UUID getIdPuntoVendita() {
        return idPuntoVendita;
    }

    public String getCodicePuntoVendita() {
        return codicePuntoVendita;
    }

    public String getLuogo() {
        return luogo;
    }



    @Override
    public String toString() {
        return "PuntoVendita{" +
                "idPuntoVendita=" + idPuntoVendita +
                ", codicePuntoVendita='" + codicePuntoVendita + '\'' +
                ", luogo='" + luogo + '\'' +
                ", titoliDiViaggio=" + titoliDiViaggio +
                '}';
    }
}
