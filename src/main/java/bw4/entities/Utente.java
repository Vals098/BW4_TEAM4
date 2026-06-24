package bw4.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Utente {

    @Id
    @GeneratedValue
    @Column(name = "id_utente")
    private UUID idUtente;

    @Column(name = "codice_utente", nullable = false, unique = true)
    private String codiceUtente;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(name = ("data_di_nascita"), nullable = false)
    private LocalDate dataDiNascita;

    @Column(name = "luogo_di_nascita")
    private String luogoDiNascita;

    @Column(name = "mestiere")
    private String mestiere;


    // Relazione
    @OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
    private Tessera tessera;

    public Utente() {
    }

    public Utente(String codiceUtente, String nome, String cognome, LocalDate dataDiNascita, String luogoDiNascita, String mestiere) {
        this.codiceUtente = codiceUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.luogoDiNascita = luogoDiNascita;
        this.mestiere = mestiere;
    }

//    getter


    public UUID getIdUtente() {
        return idUtente;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public String getLuogoDiNascita() {
        return luogoDiNascita;
    }

    public String getMestiere() {
        return mestiere;
    }

    public Tessera getTessera() {
        return tessera;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + idUtente +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita='" + dataDiNascita + '\'' +
                ", cards=" + tessera +
                '}';
    }
}


