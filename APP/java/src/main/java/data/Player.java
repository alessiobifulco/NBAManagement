package data;

public class Player {

    int idPlayer;
    String name;
    String surname;
    public enum Position {PG, SG, SF, PF, C};
    int age;
    Position position;
    public enum Category {Superstar, AllStar, Bench, RolePlayer};
    Category category;
    int rank;
    int experience;
    boolean freeAgent;

    // Costruttore senza idPlayer, che viene assegnato automaticamente dal database
    public Player(String name, String surname, int age, Position position, Category category, int rank, int experience, boolean freeAgent){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.position = position;
        this.category = category;
        this.rank = rank;
        this.experience = experience;
        this.freeAgent = freeAgent;
    }

    // Getter e Setter
    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean isFreeAgent() {
        return freeAgent;
    }

    public void setFreeAgent(boolean freeAgent) {
        this.freeAgent = freeAgent;
    }
}
