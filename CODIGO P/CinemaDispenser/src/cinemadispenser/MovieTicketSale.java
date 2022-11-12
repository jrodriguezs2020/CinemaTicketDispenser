package cinemadispenser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import sienens.CinemaTicketDispenser;

public class MovieTicketSale extends Operation {

    MultiplexState multiplexState;

    public MovieTicketSale(CinemaTicketDispenser dispenser, Multiplex multiplex) throws IOException, FileNotFoundException, ClassNotFoundException{
        super(dispenser, multiplex);
        multiplexState = new MultiplexState();
        isNewDay(dispenser);
    }
    
    /*Metodo para que el dueño/encargado del cine pueda restablecer los datos del cine en el caso de que fuese un nuevo día
      en el caso de ser un nuevo día se crearía un nuevo fichero*/
    private void isNewDay (CinemaTicketDispenser dispenser) throws IOException, FileNotFoundException, ClassNotFoundException{
        dispenser.setTitle("¿Es un nuevo día?");
        dispenser.setOption(0, "Si");
        dispenser.setOption(1, "No");
        for (int c = 2; c<=5; c++){
            dispenser.setOption(c, null);
        }
        char op = dispenser.waitEvent(30);
        if (op=='A'){
            serializeMultiplexState();  //Nuevo día
        }
        else{
            deserializeMultiplexState();
        }
    }

    @Override
    public void doOperation() {
        dispenser = this.getDispenser();
        multiplex = this.getMultiplex();
        String language = multiplex.getIdiom();  
        selectTheater(dispenser,language);  
        Theater theatre = null;
        Session session = null;
        ArrayList<Seat> seatList = new ArrayList();
        int mode = 1;
        int tickets = 0;  //Contador de entradas
        boolean back = false;
      
        while (!back) {
            char op = dispenser.waitEvent(30);
            if (op == 0 || op == 'F') {
                back = true;
            } else {
                if (mode == 0) {
                    selectTheater(dispenser,language);  
                } else if (mode == 1) {
                    int film = convert(op);
                    theatre = multiplexState.getTheater(film);
                    selectSession(dispenser, theatre,language);

                } else if (mode == 2) {
                    int nSession = convert(op);
                    session = theatre.getSessionList().get(nSession);
                    selectSeats(dispenser, theatre, session,language);
                    tickets = 0;

                } else if (mode == 3) {
                    if (op == 'A') {
                        back = true;
                    } else if (op == 'B') {
                        try {
                            performPayment(theatre, tickets,session,seatList,language);
                        } catch (IOException ex) {
                            Logger.getLogger(MovieTicketSale.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        back = true;
                    } else {
                        if (tickets < 4) {
                            tickets = occupySeat(op, session, tickets,seatList,language);
                        } else {
                            dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("NO ES POSIBLE AÑADIR MÁS ENTRADAS"));
                        }
                    }
                }
                if (mode != 3) {
                    mode++;
                }
            }
        }
    }

    //Marca como ocupadas las butacas que son elegidas por el cliente y añade 1 a la cuenta de entradas 
    private int occupySeat(char op, Session session, int tickets, ArrayList<Seat> seatList,String language) {
        byte col = (byte) (op & 0xFF);
        byte row = (byte) ((op & 0xFF00) >> 8);
        dispenser.setTitle((java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("FILA: ")+row)+
                            (java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString(" ASIENTO: ")+col));
        if (!session.isOccupied(row, col)) {
            Seat stall = new Seat(row, col);
            seatList.add(stall);
            dispenser.markSeat(row, col, 1);
            tickets++;
        }
        return tickets;
    }
    
    //Muestra las peliculas disponibles para ver en el cine y selecciona una o cancelar (cliente)
    private void selectTheater(CinemaTicketDispenser dispenser, String language) {
        ArrayList<Theater> theaterList = multiplexState.getTheaterList();
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("SELECCIONE UNA PELICULA"));
        int c;
        for (c = 0; c < multiplexState.getNumberOfTheaters(); c++) {  //multiplexState.getNumberOfTheaters() = theaterList.size();
            Film film = theaterList.get(c).getFilm();
            dispenser.setOption(c, film.getName());
        }
        for (c = c; c <= 4; c++) {  //desde el valor c del anterior for hasta 4 no muestra las opciones (null)
            dispenser.setOption(c, null);
        }
        dispenser.setOption(5, java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("CANCELAR"));
    }

    //Muestra las sessiones disponibles para ver en el cine de cada pelicula y selecciona una o cancelar (cliente)
    private void selectSession(CinemaTicketDispenser dispenser, Theater theatre,String language) {
        ArrayList<Session> sessionList = theatre.getSessionList();
        Film film = theatre.getFilm();
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("SELECCIONE UNA SESION"));
        dispenser.setImage("..\\" + film.getPoster());
        dispenser.setDescription(film.getDescription());
        int c;
        for (c = 0; c < sessionList.size(); c++) {  //muestra las sesiones de la pelicula elegida
            dispenser.setOption(c, sessionList.get(c).getHour());
        }
        for (c = c; c <= 4; c++) {  //Pone a null las opciones que no necesitamos
            dispenser.setOption(c, null);
        }
        dispenser.setOption(5, java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("CANCELAR"));
    }

    //Muestra la disposición de la sala de cada pelicula y selecciona las butacas que el cliente elige
    private void selectSeats(CinemaTicketDispenser dispenser, Theater theatre, Session session, String language) {  //posible problema
        int rows = theatre.getMaxRows();
        int cols = theatre.getMaxCols();
        dispenser.setTheaterMode(rows, cols);  //⚠:️ rows != row / cols != col
        dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("SELECCIONE SUS BUTACAS"));
        dispenser.setOption(0, java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("CANCELAR"));
        dispenser.setOption(1, java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("CONTINUAR"));
        for (int row = 1; row <= rows; row++) {  //Mostramos los asientos de la sala elegida
            for (int col = 1; col <= cols; col++) {
                dispenser.markSeat(row, col, 0);
            }
        }
        presentSeats(dispenser, theatre, session);  //Marcamos cada asiento con su estado (libre/ocupado)
    }

    //Se encarga de imprimir las entradas una vez el cliente las ha pagado
    private void print(CinemaTicketDispenser dispenser, String film, int price, int theatre, String hour, ArrayList<Seat> seatList, String language) {
        for (int c = 0; c < seatList.size(); c++) {
            int row = seatList.get(c).getRow();
            int col = seatList.get(c).getCol();
            ArrayList<String> text = new ArrayList<>();
            text.add(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("   ENTRADA PARA ")+film);
            text.add("   =========================");
            text.add(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("   SALA ")+theatre);
            text.add(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("   HORA ")+ hour);
            text.add(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("   FILA ")+row);
            text.add(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("   ASIENTO ")+col);
            text.add((java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("   PRECIO ")+price)+("€"));
            dispenser.print(text);
        }
    }

    /*Se encarga de realizar el pago. Llama a la clase PerformPayment para hacer la comprobación de la tarjeta de crédito 
      y que la comunicación y el pago no se han producido errores*/
    private void performPayment(Theater theatre, int tickets, Session session, ArrayList<Seat> seatList,String language) throws IOException {
        PerformPayment pay = new PerformPayment();
        String film = theatre.getFilm().getName();
        int price = computePrice(theatre, tickets);
        String description = ((java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("COMPRAR ")+tickets)+
                (java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString(" ENTRADAS PARA LA PELÍCULA ")+film)+("\n")+
                (java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString(" PRECIO: ")+price)+("€"));
        boolean completed;
        completed = pay.doOperation(dispenser, price, description,language);  //Se procede al pago
        if (completed) {
            print(dispenser, film, theatre.getPrice(), theatre.getNumber(), session.getHour(), seatList, language); //Imprime las entradas al cliente
            int size = seatList.size();
            for (int c=0; c<seatList.size(); c++){
                session.occupiesSeat(seatList.get(c));  //Pone a ocupadas las entradas que acaban de ser compradas
            }
            serializeMultiplexState(); //Aqui el cliente ya tendría sus entradas por lo que guardamos la información
        } else {
            dispenser.setMessageMode();
            dispenser.setTitle(java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("ERROR EN EL PAGO"));
        }
    }
    
    //Cuando el dispensador de entradas muestra los asientos marca los que están libres u ocupados
    private void presentSeats(CinemaTicketDispenser dispenser, Theater theatre, Session session) {
        HashSet<Seat> seatSet = theatre.getSeatSet();
        for (Seat i : seatSet) {
            if (session.isOccupied(i.getRow(), i.getCol())) {
                dispenser.markSeat(i.getRow(), i.getCol(), 1);  //ocupado
            } else {
                dispenser.markSeat(i.getRow(), i.getCol(), 2);  //libre
            }
        }
    }
    
    //Calcula el precio total a pagar de las entradas
    private int computePrice(Theater theatre, int tickets) {
        int amount = 0;
        int price = theatre.getPrice();
        amount = price * tickets;
        return amount;
    }

    @Override
    public String getTitle() {
        multiplex = this.getMultiplex();
        String language = multiplex.getIdiom();
        String title = (java.util.ResourceBundle.getBundle("cinemadispenser/"+language).getString("COMPRE SUS ENTRADAS"));
        return title;
    }
    
    //Se encarga de guardar toda la información después de cada compra
    public void serializeMultiplexState() throws IOException {
        FileOutputStream fileStream = new FileOutputStream("D:\\DESCARGAS\\POO\\CODIGO P\\info.bin");
        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
        objectStream.writeObject(multiplexState);
        objectStream.close();
    }

    //Se encarga de cargar toda la información al inicio de la ejecución (no es un nuevo día)
    public void deserializeMultiplexState() throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fileStream = new FileInputStream("D:\\DESCARGAS\\POO\\CODIGO P\\info.bin");
        ObjectInputStream objectStream = new ObjectInputStream(fileStream);
        multiplexState  = (MultiplexState) objectStream.readObject();
    }
}
