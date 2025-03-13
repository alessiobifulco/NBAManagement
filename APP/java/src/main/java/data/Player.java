package data;

public class Player {
    
    int idPlayer;
    String name;
    String surname;
    enum Position {PG, SG, SF, PF, C};
    int age;
    Position position;
    enum Category {Superstar, AllStar, Bench, RolePlayer};
    Category category;
    int rank;
    int expereince;
    boolean freeAgent;


    public Player(String name, String surname, int age, Position position, Category category, int rank, int expereince, boolean freeAgent){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.position = position;
        this.category = category;
        this.rank = rank;
        this.expereince = expereince;
        this.freeAgent = freeAgent;
        this.idPlayer = idPlayer++;
    }

}
