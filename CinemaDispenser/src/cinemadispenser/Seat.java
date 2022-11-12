package cinemadispenser;

import java.io.Serializable;

public class Seat implements Serializable {

    private int row;
    private int col;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { //Devuelve una fila
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() { //Devuelve una columna
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    //Programamos el equals que usaremos en la clase session en el metodo isOccupied
    public boolean equals(Seat stall) {
        if ((row == stall.getRow()) & (col == stall.getCol())) {
            return true;
        } else {
            return false;
        }
    }

}
