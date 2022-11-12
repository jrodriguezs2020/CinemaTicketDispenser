package cinemadispenser;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MultiplexState implements Serializable {

    private ArrayList<Theater> theaterList;

    //Creamos un array para cada pelicula con su sala correspondiente
    public MultiplexState() throws IOException {
        theaterList = new ArrayList();
        Theater theatre1 = new Theater("..\\Movie1.txt", "..\\Theater2.txt");
        theaterList.add(theatre1);
        Theater theatre2 = new Theater("..\\Movie2.txt", "..\\Theater3.txt");
        theaterList.add(theatre2);
        Theater theatre3 = new Theater("..\\Movie3.txt", "..\\Theater4.txt");
        theaterList.add(theatre3);
        Theater theatre4 = new Theater("..\\Movie4.txt", "..\\Theater1.txt");
        theaterList.add(theatre4);
    }

    public void FilmSelector() {
    }

    public void loadMoviesAndSessions() {
    }

    public Theater getTheater(int n) { //Devuelve una pelicula con su sala
        return theaterList.get(n);
    }

    public int getNumberOfTheaters() {  //devuelve el numero de peliculas que hay
        return theaterList.size();
    }

    public ArrayList getTheaterList() {  //devuelve el array anteriormente creado
        return theaterList;
    }

}
