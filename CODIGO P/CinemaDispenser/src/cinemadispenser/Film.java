package cinemadispenser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class Film implements Serializable {

    private String name;
    private String poster;
    private int duration;
    private String description;

    public String getName() { //Devuelve el nombre de la pelicula
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {  ////Devuelve el cartel de la pelicula
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getDuration() { //Devuelve la duración de la pelicula
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {  //Devuelve la descripción de la pelicula 
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Cargamos del fichero el titulo, la descripción y el cartel de las películas
    public Film(String FileName) throws FileNotFoundException, IOException {
        FileInputStream FileStream = new FileInputStream(FileName);
        Scanner sc = new Scanner(FileStream);
        while (sc.hasNextLine()) {
            String next = sc.nextLine();
            String title = sc.findInLine("Title: "); //Buscamos
            if (title != null) {
                name = sc.nextLine(); //Titulo de la película
                if (sc.hasNextLine()) {
                    next = sc.nextLine();
                    if (sc.hasNext()) {
                        description = sc.nextLine(); //Descripcion (no se hace como los demas porque no se especifica que es la descripcion)
                    }
                }
            }
            String foto = sc.findInLine("Poster: ");  //Buscamos
            if (foto != null) {
                poster = sc.nextLine(); //Cartel de la pelicula
            }
        }
    }
}
