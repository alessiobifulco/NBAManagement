package data;

import java.time.LocalDate;

public class Login {
    
    LocalDate date;
    int idGM;
    int idLogin;

    public Login(int idGM, int idLogin, LocalDate date){
        this.date = date;
        this.idGM = 0;
        this.idLogin = 0;
    }
}
