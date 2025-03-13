package data;

import java.time.LocalDate;

public class Training {
    
    int idTraining;
    int idCoach;
    enum Category {GROUP, INDIVIDUAL};
    Category category;
    int idPlayer;
    double duration;
    LocalDate date;
    String description;

    public Training(int idCoach, Category category, int idPlayer, double duration, LocalDate date, String description){
        this.idCoach = idCoach;
        this.category = category;
        this.idPlayer = idPlayer;
        this.duration = duration;
        this.date = date;
        this.description = description;
        this.idTraining = idTraining++;
    }

    public Training(int idCoach, Category category, double duration, LocalDate date, String description){
        this.idTraining = idTraining++;
        this.idCoach = idCoach;
        this.category = category;
        this.idPlayer = 0;
        this.duration = duration;
        this.date = date;
        this.description = description;
    }

}