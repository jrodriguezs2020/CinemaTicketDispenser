
package demo;

import java.util.ArrayList;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

public class MultiplexState{
    private ArrayList <Theater> theaterList;
    
    public void FilmSelector(ArrayList <Theater> theaterList){ //falta cinematicketdispenser y urjcbankserver
        this.theaterList = theaterList;
    } 

    public void loadMoviesAndSessions(){
    
    }
    
    public Theater getTheater(){
        return null;
    }
    
    public int getNumberOfTheaters(){
        return theaterList.size();
    }
    
    public void getTheaterList(){
    } 

}
