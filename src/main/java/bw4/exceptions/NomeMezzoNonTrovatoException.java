package bw4.exceptions;

public class NomeMezzoNonTrovatoException extends RuntimeException {
    public NomeMezzoNonTrovatoException(String nomeMezzo) {
        super("Per tutti i funghi velenosi ! Il nome " + nomeMezzo + " non esiste proprio nel registro di Fiabiselva!");
    }
}
