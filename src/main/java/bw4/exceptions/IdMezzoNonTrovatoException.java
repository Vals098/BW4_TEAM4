package bw4.exceptions;

import java.util.UUID;

public class IdMezzoNonTrovatoException extends RuntimeException {
    public IdMezzoNonTrovatoException(UUID idMezzo) {
        super("Acciderbolina! il mezzo con id " + idMezzo + " non è stato trovato. ");
    }
}
