package cinemadispenser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import sienens.CinemaTicketDispenser;

public class MainMenu extends Operation {

    public ArrayList<Operation> operationList;

    public MainMenu(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException, FileNotFoundException, ClassNotFoundException {
        super(dispenser, multiplex);
        operationList = new ArrayList();
        IdiomSelection operation1 = new IdiomSelection(dispenser, multiplex);
        operationList.add(operation1);
        MovieTicketSale operation2 = new MovieTicketSale(dispenser, multiplex);
        operationList.add(operation2);
    }

    @Override
    public void doOperation() {
        dispenser = this.getDispenser();
        while (true) {
            presentMenu(dispenser);
            char op = dispenser.waitEvent(30);
            int nOperation = convert(op);

            if (op != 0 & op != '1') {
                operationList.get(nOperation).doOperation();
            }
        }
    }

    //Metodo al que llama doOperation y muestra el titulo y las opciones en el dispensador
    private void presentMenu(CinemaTicketDispenser dispenser) {
        String title = this.getTitle();
        dispenser.setMenuMode();
        dispenser.setTitle(title);
        dispenser.setImage("");        //Se podría quitar, pero cuando cancelas en la pantalla para elegir sesion y vuelve al principio
        dispenser.setDescription("");  //se queda el cartel de la película que estaba y la descripción   
        int c;
        for (c = 0; c < operationList.size(); c++) {
            dispenser.setOption(c, operationList.get(c).getTitle());
        }
        for (c = c; c <= 5; c++) {  //pone a nulo las opciones que no necesitamos (para que no sagan si no tienen ninguna utilidad)
            dispenser.setOption(c, null);
        }
    }

    @Override
    public String getTitle() {
        multiplex = this.getMultiplex();
        String language = multiplex.getIdiom();
        String title = (java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("BIENVENIDO"));
        return title;
    }
}
