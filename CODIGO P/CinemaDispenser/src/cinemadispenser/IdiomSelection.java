package cinemadispenser;

import static java.util.Collections.list;
import sienens.CinemaTicketDispenser;

public class IdiomSelection extends Operation {

    private String[] list;

    //Añadimos a una lista los idiomas de los que dispone la máquina dispensadora de entradas
    public IdiomSelection(CinemaTicketDispenser dispenser, Multiplex multiplex) {
        super(dispenser, multiplex);
        list = new String[4];
        list[0] = "Castellano";
        list[1] = "Inglés";
        list[2] = "Catalán";
        list[3] = "Euskera";
    }

    @Override
    public void doOperation() {
        multiplex = this.getMultiplex();
        String language = multiplex.getIdiom();
        dispenser = this.getDispenser();
        chooseIdiom(language); //Muestra los idiomas
        char op = dispenser.waitEvent(30);
        if (op != 'F') {
            int n = convert(op);  //Convierte a entero la opcion elegida
            multiplex.setIdiom(list[n]);  //seleccion del idioma
        }
    }

    //Muestra los idiomas disponibles en pantalla en las diferentes opciones 
    private void chooseIdiom(String language) {
        dispenser.setMenuMode();
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("SELECCIONE UN IDIOMA"));
        dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("CASTELLANO"));
        dispenser.setOption(1, java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("INGLÉS"));
        dispenser.setOption(2, java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("CATALÁN"));
        dispenser.setOption(3, java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("EUSKERA"));
        dispenser.setOption(4, null); //Las opciones no necesarias puestas a null
        dispenser.setOption(5, null);
    }

    @Override
    public String getTitle() {
        multiplex = this.getMultiplex();
        String language = multiplex.getIdiom();
        String title = (java.util.ResourceBundle.getBundle("cinemadispenser/" + language).getString("IDIOMA"));// java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("IDIOMA");
        return title;
    }

}
