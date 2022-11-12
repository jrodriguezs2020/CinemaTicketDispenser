
package demo;

import sienens.CinemaTicketDispenser;

public class Multiplex {
    
    MainMenu menu;
    CinemaTicketDispenser dispenser;
    private String idiom;

    public Multiplex(String idiom, MainMenu menu, CinemaTicketDispenser dispenser) { //NO SE SI HACE FALTA UN CONSTRUCTOR
        this.idiom = idiom;
        this.menu = menu;
        this.dispenser = dispenser;
    }
    
    public void start(){
    }
    
    public String getIdiom(){
        return null;// no devuelve eso
    }
    
    public void setIdiom(String idiom){
    }
    
}
