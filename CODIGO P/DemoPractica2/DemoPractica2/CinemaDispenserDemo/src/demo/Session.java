
package demo;

import jdk.internal.net.http.common.Pair;

public class Session extends Seat {
    private Pair hour;
    private Seat occupiedSeatSet;

    public Session(Pair hour, Seat occupiedSeatSet, int row, int col) {
        super(row, col);
        this.hour = hour;
        this.occupiedSeatSet = occupiedSeatSet;
    }
    
    public boolean isOccupied (int row, int col){
        //si estan ocupados devuelve true sino false
        return true;
        
    }
    
    public void occupiesSeat (int row, int col){
        //crear array para guardar los ocupados
        
            //añadir el asiento a ocupados
        
    }
    
    public Pair getHour (){
    
        return null;//para que de momento no de error
    
    }
    
    public void unoccupiesSeat (int row, int col){
    //crear array para guardar los libres
    }
    
    
}
