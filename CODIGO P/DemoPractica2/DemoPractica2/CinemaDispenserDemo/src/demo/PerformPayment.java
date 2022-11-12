
package demo;

import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

public class PerformPayment {
    private UrjcBankServer bank;
    
    public void doOperation(){
    }

    public PerformPayment(UrjcBankServer bank) { //ponia tambien cinematicketdispenser y multiplex
        this.bank = bank;
    }

}
