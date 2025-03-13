package data;

import java.time.LocalDate;

public class Trade {
    
    int idTrade;
    int idContract1;
    int idContract2;
    String state;
    LocalDate date;

    public Trade(int idContract1, int idContract2, String state, LocalDate date){
        this.idContract1 = idContract1;
        this.idContract2 = idContract2;
        this.state = state;
        this.date = date;
        this.idTrade = idTrade++;
    }
}
