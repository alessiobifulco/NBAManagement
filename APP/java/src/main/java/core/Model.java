package core;

import data.*;
import model.DBModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Model {

    public static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }

 
}
