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
        boolean inSessione = true;

        while (inSessione) {
            System.out.println("\n=== MENU Utenti e Tessere ===\n");
        }
    }


}
