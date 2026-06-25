package bw4.exceptions;

public class MezzoNonInManutenzioneException extends RuntimeException {
    public MezzoNonInManutenzioneException(String nomeMezzo) {
        super("Che ventata di buona sorte! Il mezzo" +nomeMezzo + " NON è in manutenzione");
    }
}
