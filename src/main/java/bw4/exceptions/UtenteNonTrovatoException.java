package bw4.exceptions;


import java.util.UUID;

public class UtenteNonTrovatoException extends RuntimeException {

    public UtenteNonTrovatoException(String message){
      super(message);
    }


    public UtenteNonTrovatoException(UUID idUtente){
        super("ACCIPIGNA!! L'utente con id " + idUtente + " non fa parte del Fantabosco!");
    }
}
