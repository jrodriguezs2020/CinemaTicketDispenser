package demo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sienens.CinemaTicketDispenser;
import urjc.UrjcBankServer;

/**
 *
 * @author jvelez
 */
class CinemaManager {
    CinemaTicketDispenser dispenser = new CinemaTicketDispenser();    
    
    
    UrjcBankServer bank = new UrjcBankServer();
    int mode = 0;
    
    void run() {        
        dispenser.setTitle("URJC Cinema - Bienvenido");
        dispenser.setOption(0, "Modo cine");
        dispenser.setOption(1, "Comprar palomitas");
        dispenser.setOption(2, "Pulsar este botón solo si hay dentro una tarjeta de crédito, en cuyo caso se la traga definitvamente.");
        dispenser.setOption(3, "Devolver la tarjeta");
        dispenser.setOption(4, "Imprimir entradas\nPelícula Dune");
        dispenser.setOption(5, "Pintar cartel de Dune");
        
        int rows = (int)(Math.random() * 10)+4;
        int cols = (int)(Math.random() * 16)+4;
        
        while(true) {
            char c = dispenser.waitEvent(30);
            if (c == '1') {
                dispenser.retainCreditCard(false);
            } else if (c == 'A') {
                
                if (mode == 0) {
                    dispenser.setTitle("Selecciones sus butacas");
                    dispenser.setOption(0, "Modo menú");                    
                    dispenser.setOption(1, "Configurar sala al azar");
                    rows = (int)(Math.random() * 10)+4;
                    cols = (int)(Math.random() * 16)+4;
                    dispenser.setTheaterMode(rows, cols);
                    mode = 1;
                }
                else  {
                    dispenser.setTitle("URJC Cinema - Bienvenido");
                    dispenser.setOption(0, "Modo cine");
                    dispenser.setOption(1, "Comprar palomitas");
                    dispenser.setMenuMode();
                    mode = 0;
                }
                
                
            } else if (c == 'B') {
                if (mode == 1) {
                    if (Math.random() > 0.5) {                
                        for (int row = 1; row < rows; row++)
                        for (int col = 1; col < cols; col++)
                            dispenser.markSeat(row, col, 2);
                        for (int cont = 1; cont < rows; cont++)
                            dispenser.markSeat(cont, cols/2, 0);
                    } else {
                        for (int row = 1; row < rows; row++)
                        for (int col = 1; col < cols; col++)
                            if (Math.random() > 0.7)
                                dispenser.markSeat(row, col, 0);
                            else if (Math.random() > 0.5)
                                dispenser.markSeat(row, col, 1);
                            else
                                dispenser.markSeat(row, col, 2);
                    }
                } else {
                    dispenser.setMessageMode();
                    dispenser.setTitle("Comprando palomitas");
                    dispenser.setDescription("Inserte la tarjeta\n Para que podamos cobrarle las palomitas\n aunque no funciona y tendrá que pulsar cancelar\n");
                    dispenser.setOption(1, null);
                    dispenser.setOption(0, "Cancelar");
                    mode = 1;
                }
                
            } else if (c == 'C') {
                dispenser.retainCreditCard(true);
            } else if (c == 'D') {
                boolean expelled = dispenser.expelCreditCard(5);
                if (!expelled)
                    dispenser.retainCreditCard(true);                    
            } else if (c == 'E') {
                List <String> text = new ArrayList<>();
                text.add("   Entrada para DUNE");
                text.add("   ===================");
                text.add("   Sala 7");
                text.add("   18:30");
                text.add("   Fila 3");
                text.add("   Asiento 14");
                text.add("   Precio 9€");
                                
                dispenser.print(text);
                
                text = new ArrayList<>();
                text.add("   Entrada para DUNE");
                text.add("   ===================");
                text.add("   Sala 7");
                text.add("   18:30");
                text.add("   Fila 3");
                text.add("   Asiento 15");
                text.add("   Precio 9€");
                dispenser.print(text);
            } else if (c == 'F') {
                dispenser.setImage("../Dune.jpg");
                dispenser.setDescription("Arrakis, el planeta del desierto, de la familia Harkonnen, queda en manos de la casa Atreides cuando el emperador le cede la explotación de la especia, la materia prima más valiosa de la galaxia y también una droga que amplifica la conciencia y extiende la vida. El duque Leto (Oscar Isaac), la dama Jessica (Rebecca Ferguson) y el hijo de ambos, Paul Atreides (Timothée Chalamet), llegan al planeta para recuperar el nombre de su casa, pero pronto se ven envueltos en una trama de traiciones que les llevará a valorar a los Fremen, habitantes del desierto relacionados con la especia.\n\n" +
                                         "No recomendada a menores de 14 años.");
            } else if (c != 0) {
                byte col = (byte)(c & 0xFF);
                byte row = (byte)((c & 0xFF00) >> 8);
                dispenser.setTitle("Fila: " + row + " Asiento: " + col);
                dispenser.markSeat(row, col, 1);
            }
        }
    }    
}
