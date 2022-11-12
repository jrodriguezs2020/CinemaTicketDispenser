
package demo;

import sienens.CinemaTicketDispenser;

public class MovieTicketSale {
    private MultiplexState state;

    public void doOeration(){
    }
    
    public MovieTicketSale(MultiplexState state) { //ponia tambien cinematicketdispenser
        this.state = state;
    }
        
    private Theater selectTheater(){
        return null;//no devuelve eso
    }
    
    /*private selectSeats (Theater, Session){ 
    // hay que pasarle el theater y la sesion de los asientos seleccionados
    }
    
    private Session selectSession(Theater){ 
    //hay que pasarle el theater
        return null;
    }*/
    
    private boolean performPayment(){
        return false;//o true
    }
    
    private void presentSeats(){
    }
    
    private void computePrice(){
    }
    
    public String getTitle(){
    //mirar en el correo
        return null;//no devuelve eso
    }
    
    public void serializeMultiplexState(){
    }
    
    public void deserializeMultiplexState(){
    }
    
}
