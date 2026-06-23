package bw4.exceptions;

import java.util.UUID;

public class PuntoVenditaNonTrovatoException extends RuntimeException {
    public PuntoVenditaNonTrovatoException( UUID idPuntoVendita) {
        super("Il punto vendita con id " + idPuntoVendita + " non è stato trovato.");
    }
}
