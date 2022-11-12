
package cinemadispenser;

public class Menu {
    private String nombre;
    private String description;
    private int precio;

    public Menu(String nombre, String description, int precio) {
        this.nombre = nombre;
        this.description = description;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescription() {
        return description;
    }

    public int getPrecio() {
        return precio;
    }
    
    
    
}
