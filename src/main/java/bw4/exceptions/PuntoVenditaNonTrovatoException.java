package bw4.exceptions;

import java.util.UUID;

public class PuntoVenditaNonTrovatoException extends RuntimeException {

    public PuntoVenditaNonTrovatoException(String message) {
        super(message);
    }

    public PuntoVenditaNonTrovatoException(UUID idPuntoVendita) {
        super("ACCIPIGNA!! Il punto vendita con id " + idPuntoVendita + " non è parte del Fantabosco!");
    }
}
