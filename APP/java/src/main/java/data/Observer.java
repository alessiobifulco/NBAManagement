package data;

public class Observer {
    
    int idObserver;
    String name;
    String surname;
    int salary;
    int expereince;
    boolean free;

    public Observer(String name, String surname, int salary, int expereince, boolean free){
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.expereince = expereince;
        this.free = free;
    }
}
