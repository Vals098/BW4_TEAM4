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

    @Column(name = "codice_punto_vendita", unique = true)
    private String codicePuntoVendita;

    @Column(name = "luogo")
    private String luogo;

    @Column(name = "numero_titoli_di_viaggio")
    private int numeroTitoliDiViaggio;

    @OneToMany(mappedBy = "puntoVendita")
    private List<TitoloDiViaggio> titoliDiViaggio;

    protected PuntoVendita() {
    }

    public PuntoVendita(String codicePuntoVendita, String luogo, int numeroTitoliDiViaggio) {
        this.codicePuntoVendita = codicePuntoVendita;
        this.luogo = luogo;
        this.numeroTitoliDiViaggio = numeroTitoliDiViaggio;
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

    public int getNumeroTitoliDiViaggio() {
        return numeroTitoliDiViaggio;
    }


    @Override
    public String toString() {
        return "PuntoVendita{" +
                "idPuntoVendita=" + idPuntoVendita +
                ", codicePuntoVendita='" + codicePuntoVendita + '\'' +
                ", luogo='" + luogo + '\'' +
                ", numeroTitoliDiViaggio=" + numeroTitoliDiViaggio +
                ", titoliDiViaggio=" + titoliDiViaggio +
                '}';
    }
}
