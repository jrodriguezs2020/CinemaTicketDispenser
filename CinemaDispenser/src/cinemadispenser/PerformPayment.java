package cinemadispenser;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.CommunicationException;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

public class PerformPayment {

    UrjcBankServer bank;

    public PerformPayment() {
        bank = new UrjcBankServer();
    }

    /*Método que se encarca del pago y de la comunicación con el banco. Este método a pesar de ser doOperation
      no deriva de Operation porque necesita pasarle parámetros*/
    public boolean doOperation(CinemaTicketDispenser dispenser, int amount, String description, String language) {
        title(dispenser, description, language);
        char op = dispenser.waitEvent(30);
        boolean completed = false;
        if (op == '1') {
            if (bank.comunicationAvaiable()) { //Comunicación con el banco
                try {
                    if (bank.doOperation(dispenser.getCardNumber(), amount)) {  //Operación de pago
                        completed = true;
                    }
                } catch (CommunicationException ex) {
                    Logger.getLogger(PerformPayment.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("ERROR EN LA COMUNICACIÓN"));
            }
            if (dispenser.expelCreditCard(30)) {  //Retiene la tarjeta de crédito si no es retirada
                dispenser.retainCreditCard(true);
            }
        }
        return completed;
    }

    //Muestra en pantalla los mensajes y las opciones que hay
    private void title(CinemaTicketDispenser dispenser, String description, String language) {
        dispenser.setMessageMode();
        dispenser.retainCreditCard(false);
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("INSERTE LA TARJETA DE CRÉDITO"));
        dispenser.setDescription(description);
        dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("CANCELAR"));
        dispenser.setOption(1, null);
    }
}
