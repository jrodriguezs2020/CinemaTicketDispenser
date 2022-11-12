
package demo;

import java.util.ArrayList;
import sienens.CinemaTicketDispenser;

public class MainMenu {
    public ArrayList <Operation> operationList;

    
    public void doOperation(){
    }

    public MainMenu(ArrayList<Operation> operationList) { //el constructor en el diagrama viene de diferente manera con
        this.operationList = operationList;                 //cinematicketdispenser y multiplex
    }
    

    public void presentMenu(){
    }
    
    public String getTitle(){
        return null; //no devuelve eso
    }
    
}
