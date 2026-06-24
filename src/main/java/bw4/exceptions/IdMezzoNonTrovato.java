package bw4.exceptions;

import java.util.UUID;

public class IdMezzoNonTrovato extends RuntimeException {
    public IdMezzoNonTrovato(UUID idMezzo) {
        super("il mezzo con id " + idMezzo + " non è stato trovato. ");
    }
}
