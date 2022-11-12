package cinemadispenser;

import java.io.Serializable;
import java.util.HashSet;

public class Session implements Serializable {

    private String hour;  //Pair (sustituido por String)
    private HashSet<Seat> occupiedSeatSet;  //HashSet de asientos ocupados

    public Session(String hora) {
        hour = hora;  //hora de la clase theater
        occupiedSeatSet = new HashSet();
    }

    //Comprobamos si está ocupada la butaca
    public boolean isOccupied(int row, int col) {
        Seat stall = new Seat(row, col);
        boolean result = false;
        for (Seat c : occupiedSeatSet) {
            if (c.equals(stall)) {
                result = true;
            }
        }
        return result;
    }

    public void occupiesSeat(Seat stall) {  //Devuelve el HashSet de los asientos ocupados
        occupiedSeatSet.add(stall);
    }

    public String getHour() {  //Devuelve la hora de la sesion
        return hour;
    }
}
