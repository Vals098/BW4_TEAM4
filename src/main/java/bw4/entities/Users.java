package bw4.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "utenti")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(name = ("data_di_nascita"), nullable = false, length = 10)
    private String dataDiNascita;


    // Relazione bidirezionale
    @OneToOne(mappedBy = "id_utente", cascade = CascadeType.ALL)
    private Cards cards;

    public Users() {
    }

    public Users(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public long getId() {
        return id;
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

    public Cards getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataDiNascita='" + dataDiNascita + '\'' +
                ", cards=" + cards +
                '}';
    }
}


