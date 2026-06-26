package bw4.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue
    @Column(name = "id_utente")
    private UUID idUtente;

    @Column(name = "codice_utente", nullable = true)
    private String codiceUtente;

    @Column(name = "nome", nullable = true)
    private String nome;

    @Column(name = "cognome", nullable = true)
    private String cognome;

    @Column(name = "data_nascita", nullable = true)
    private LocalDate dataNascita;

    @Column(name = "residenza", nullable = true)
    private String residenza;

    @Column(name = "professione", nullable = true)
    private String professione;

    @Column(name = "data_cancellazione", nullable = true)
    private LocalDate dataCancellazione;

    // Costruttore vuoto richiesto da JPA
    public Utente() {}


    public Utente(String codiceUtente, String nome, String cognome, LocalDate dataNascita, String residenza, String professione) {
        this.codiceUtente = codiceUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.residenza = residenza;
        this.professione = professione;
        this.dataCancellazione = null;
    }

    // GETTER E SETTER
    public UUID getIdUtente() {
        return idUtente;
    }

    public String getCodiceUtente() {
        return codiceUtente;
    }

    public void setCodiceUtente(String codiceUtente) {
        this.codiceUtente = codiceUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getResidenza() {
        return residenza;
    }

    public void setResidenza(String residenza) {
        this.residenza = residenza;
    }

    public String getProfessione() {
        return professione;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
    }

    public LocalDate getDataCancellazione() {
        return dataCancellazione;
    }

    public void setDataCancellazione(LocalDate dataCancellazione) {
        this.dataCancellazione = dataCancellazione;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "idUtente=" + idUtente +
                ", codiceUtente='" + codiceUtente + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataCancellazione=" + dataCancellazione +
                '}';
    }
}