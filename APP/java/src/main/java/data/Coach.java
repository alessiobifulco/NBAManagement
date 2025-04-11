package data;

public class Coach {

    int idCoach;
    String name;
    String surname;
    int salary;
    int expereince;
    boolean free;

    public Coach(String name, String surname, int salary, int expereince, boolean free) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.expereince = expereince;
        this.free = free;
    }

    public int getIdCoach() {
        return idCoach;
    }

    public void setIdCoach(int idCoach) {
        this.idCoach = idCoach;
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
        return "Coach{" +
                "idCoach=" + idCoach +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", salary=" + salary +
                ", expereince=" + expereince +
                ", free=" + free +
                '}';
    }
}
