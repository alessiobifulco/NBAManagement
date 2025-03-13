package data;

public class Excercise {
    
    int idExcercise;
    String name;
    String fundamentals;
    enum Intensity {LOW, MEDIUM, HIGH};
    Intensity intensity;


    public Excercise(String name, String fundamentals, Intensity intensity){
        this.name = name;
        this.fundamentals = fundamentals;
        this.intensity = intensity;
        this.idExcercise = idExcercise++;
    }
}
