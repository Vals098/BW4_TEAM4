package bw4.exceptions;

import bw4.enums.TipoMezzo;

public class TipoMezzoNonAutorizzatoException extends RuntimeException {
    public TipoMezzoNonAutorizzatoException(TipoMezzo tipoMezzo) {
        super("Accipigna, qui le pigne si mettono di traverso! Il mezzo indicato è PROIBITO nel Fantabosco");
    }
}
