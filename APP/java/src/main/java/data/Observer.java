package data;

public class Observer {

    int idObserver;
    String name;
    String surname;
    int salary;
    int expereince;
    boolean free;

    public Observer(String name, String surname, int salary, int expereince, boolean free) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.expereince = expereince;
        this.free = free;
    }

    public int getIdObserver() {
        return idObserver;
    }

    public void setIdObserver(int idObserver) {
        this.idObserver = idObserver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getExpereince() {
        return expereince;
    }

    public void setExpereince(int expereince) {
        this.expereince = expereince;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    @Override
    public String toString() {
        return "Observer{" +
                "idObserver=" + idObserver +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", salary=" + salary +
                ", expereince=" + expereince +
                ", free=" + free +
                '}';
    }
}
