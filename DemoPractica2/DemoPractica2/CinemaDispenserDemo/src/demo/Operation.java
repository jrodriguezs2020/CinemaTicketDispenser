
package demo;

import sienens.CinemaTicketDispenser;

public class Operation {
    public Multiplex multipex;
    
    public void doOperation(){
    }
    
    public Operation(CinemaTicketDispenser multiplex, Multiplex multipex) { //+Operation(CInemaTicketDispenser, Multiplex)
        this.multipex = multipex;
    }    

    public CinemaTicketDispenser getDispenser(){
        return null;//esto no devuelve
    }
    
    public String getTitle(){
        return null;//no devuelve eso
    }

    public Multiplex getMultiplex(){
        return null;//no devuelve esto
    }
    


}
