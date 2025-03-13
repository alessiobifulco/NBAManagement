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

    public Contract(int idPlayer, int idTeam, int salary, LocalDate dateStart, int years, boolean state){
        this.idPlayer = idPlayer;
        this.idTeam = idTeam;
        this.salary = salary;
        this.dateStart = dateStart;
        this.years = years;
        this.state = state;
        this.idContract = idContract++;
    }



   
}
