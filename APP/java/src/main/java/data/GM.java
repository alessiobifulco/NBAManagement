package data;

public class GM {
    
    String name_surname;
    String mail;
    String password;
    int idGM = 0;

    public GM(String name_surname, String mail, String password){
        this.mail = mail;
        this.name_surname = name_surname;
        this.password = password;
        this.idGM = idGM++;
    }
}
