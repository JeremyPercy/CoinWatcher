package model;

import interfaces.ModelInterface;
import util.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Model implements ModelInterface {

    private DatabaseConnection db;

    /**
     * For every model instantiated it can use db as connection.
     */
    Model() {
        try {
            this.db = DatabaseConnection.getInstance().openDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves and closes connection to DB
     */
    public void save() {
        this.db.closeDatabase();
    }

    /**
     * Set to result
     */
    public ResultSet result() {
        return this.db.getResultSet();
    }

    /**
     * set query
     */

    boolean query(String option, String query) {
        return this.db.query(option, query);
    }

    String getCurrentTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
