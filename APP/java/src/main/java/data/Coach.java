package data;

public class Coach {
    
    int idCoach;
    String name;
    String surname;
    int salary;
    int expereince;
    boolean free;

    public Coach(String name, String surname, int salary, int expereince, boolean free){
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.expereince = expereince;
        this.free = free;
    }
}
