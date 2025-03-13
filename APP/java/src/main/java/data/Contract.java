package data;

import java.time.LocalDate;

public class Contract {

    int idContract;
    int idPlayer;
    int idTeam;
    int salary;
    LocalDate dateStart;
    int years;
    boolean state;

    // Costruttore senza idContract, che verrà assegnato automaticamente dal database
    public Contract(int idPlayer, int idTeam, int salary, LocalDate dateStart, int years, boolean state){
        this.idPlayer = idPlayer;
        this.idTeam = idTeam;
        this.salary = salary;
        this.dateStart = dateStart;
        this.years = years;
        this.state = state;
    }

    // Getter e Setter
    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
