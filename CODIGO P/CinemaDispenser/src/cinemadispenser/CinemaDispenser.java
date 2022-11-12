package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CinemaDispenser {

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        Multiplex multiplex = new Multiplex();
        multiplex.start();
    }
}
