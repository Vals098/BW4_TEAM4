package bw4.scanner.menuAmministratore;

import bw4.DAO.TesseraDAO;
import bw4.DAO.UtenteDAO;
import java.util.Scanner;

public class MenuUtentiETessere {
    private final Scanner scanner = new Scanner(System.in);
    private final UtenteDAO ud;
    private final TesseraDAO tesseraDAO;

    public MenuUtentiETessere(UtenteDAO ud, TesseraDAO tesseraDAO) {
        this.ud = ud;
        this.tesseraDAO = tesseraDAO;
    }

    public void start() {
            System.out.print("Inserisci il numero della tessera da controllare: ");
            String input = scanner.nextLine();
            try {
                int numeroTessera = Integer.parseInt(input);
                tesseraDAO.controllaERinnova(numeroTessera, scanner);
            } catch (NumberFormatException e) {
                System.out.println("Accipigna! Devi inserire un numero valido!");
            }
    }

}