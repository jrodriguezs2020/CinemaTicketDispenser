
package demo;

public class Film {
    private String name;
    //private FileName poster;
    private int duration;
    private String description;

    public Film(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }
    
    public String getName(){
        return name;
    }
    
    //public FileName getPoster(){
        
    //}
    
    public int getDuration(){
        return duration;
    }
    
    public String getDescription(){
        return description;
    }
}
