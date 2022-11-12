package cinemadispenser;

import sienens.CinemaTicketDispenser;

public abstract class Operation {

    Multiplex multiplex;
    CinemaTicketDispenser dispenser;

    public abstract void doOperation();

    public Operation(CinemaTicketDispenser dispenser, Multiplex multiplex) {
        this.dispenser = dispenser;
        this.multiplex = multiplex;
    }

    public CinemaTicketDispenser getDispenser() { //Devuelve el CinemaTicketDispenser creado
        return dispenser;
    }

    public abstract String getTitle();  //Proporciona el titulo

    public Multiplex getMultiplex() {  //Devuelve el Multiplex creado
        return multiplex;
    }

    //Convierte un char a un entero (usado cuando el usuario pulsa un opción de la pantalla del dispensador)
    public int convert(char op) { 
        switch (op) {
            case 'A': {
                return 0;
            }
            case 'B': {
                return 1;
            }
            case 'C': {
                return 2;
            }
            case 'D': {
                return 3;
            }
        }
        return 0;
    }
}
