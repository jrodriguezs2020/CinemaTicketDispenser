package cinemadispenser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Theater implements Serializable {

    private int number;
    private int price;
    private HashSet<Seat> seatSet;
    private Film film;
    private int rows; //número máximo de filas
    private int cols; //número máximo de columnas
    private ArrayList<Session> sessionList = new ArrayList();

    //En este metodo cargamos el contenido de los ficheros
    public Theater(String FileName, String FileName2) throws IOException {
        film = new Film(FileName);
        loadSeats(FileName2);
        FileInputStream FileStream = new FileInputStream(FileName);
        Scanner sc = new Scanner(FileStream);

        while (sc.hasNextLine()) {
            String theatre = sc.findInLine("Theatre: "); //⚠:️ theatre != theater  
            if (theatre != null) {
                number = sc.nextInt(); //Numero de sala
            }
            String aux = sc.nextLine();
            String session = sc.findInLine("Sessions: "); //Buscamos las horas de las sesiones
            if (session != null) {
                for (int c = 0; c < 4; c++) {
                    String hora = sc.findInLine("[^\\s]....");
                    if (hora != null) {
                        addSession(hora);  //Añadimos una sesión a la lista por cada hora
                    }
                }
            }
            String cost = sc.findInLine("Price: ");  //Buscamos
            if (cost != null) {
                price = sc.nextInt();  //Precio de las entradas de la pelicula
            }
        }
    }

    public int getNumber() {  //Devuelve el numero de sala
        return number;
    }

    public int getPrice() {  //Devuelve el precio de una pelicula
        return price;
    }

    public void addSession(String hora) {  //Añade una sesión de la pelicula por cada hora leida en el fichero
        Session session = new Session(hora);
        sessionList.add(session);
    }

    public Film getFilm() {  //Devuelve una pelicula
        return film;
    }

    public ArrayList<Session> getSessionList() {  //Devuelve la lista de sesiones de cada pelicula
        return sessionList;
    }

    public HashSet<Seat> getSeatSet() {
        return seatSet;
    }

    public int getMaxRows() { //devuelve el numero máximo de filas
        return rows;
    }

    public int getMaxCols() {  //devuelve el numero máximo de columnas
        return cols;
    }

    //Leemos del fichero la disposición de cada sala y la guardamos
    private void loadSeats(String FileName) throws FileNotFoundException {
        seatSet = new HashSet();
        int cont = 0;
        rows = 0;
        cols = 0;
        FileInputStream FileStream = new FileInputStream(FileName);
        Scanner sc = new Scanner(FileStream);
        while (sc.hasNextLine()) {
            String next = sc.nextLine();
            cont++;
            int nCols = next.length();  //la longitud de la fila es el numero de columnas
            for (int c = 0; c < nCols; c++) {
                char asterisk = next.charAt(c);
                if (asterisk == ('*')) {  //Si se lee * es una nueva butaca
                    Seat stall = new Seat(cont, c + 1);  //stall = puesto (hace referencia a asiento/butaca para no repetir seat)
                    seatSet.add(stall);
                }
                if (cols < c + 1) {
                    cols = c + 1;  //getMaxCols
                }
            }
        }
        rows = cont;  //getMaxRows
    }
}
