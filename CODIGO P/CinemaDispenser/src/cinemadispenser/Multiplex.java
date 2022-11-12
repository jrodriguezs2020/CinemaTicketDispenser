package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import sienens.CinemaTicketDispenser;

public class Multiplex {

    String idiom;

    public Multiplex() {
        idiom = "Castellano"; //Idioma por defecto el castellano
    }

    //Método con el que comienza la ejecución del dispensador de entradas de cine
    public void start() throws IOException, FileNotFoundException, ClassNotFoundException {
        CinemaTicketDispenser dispenser = new CinemaTicketDispenser();
        MainMenu menu = new MainMenu(dispenser, this);
        menu.doOperation();
    }

    public String getIdiom() {
        return idiom;
    }

    public void setIdiom(String idiom) {
        this.idiom = idiom;
    }
}
