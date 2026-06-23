package bw4.entities;

import jakarta.persistence.*;

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    @Column(name = "id_utente")
    private long idUtente;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(name = ("data_di_nascita"), nullable = false, length = 10)
    private String dataDiNascita;


    // Relazione
    @OneToOne(mappedBy = "idUtente", cascade = CascadeType.ALL)
    private Tessera tessera;

    public Utente() {
    }

    public Utente(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

//    getter
    public long getIdUtente() {
        return idUtente;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getDataDiNascita() {
        return dataDiNascita;
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


