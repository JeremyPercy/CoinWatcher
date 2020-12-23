package util;

import java.sql.*;

public class DatabaseConnection {

    /**
     * Set path to the SQLlite database file
     */
    private static final String PATH = "";
    private static final String DB_NAME = "coinwatcher.db";
    private static Connection conn = null;
    private static DatabaseConnection instance;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private int openCounter;

    /**
     * Set up database connection
     */
    public static void initializeDBConnection() {
        if (instance == null ) {
            instance = new DatabaseConnection();
        }
    }

    /**
     * Returns instance of DatabaseConnection
     * @return
     * @throws SQLException
     */
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            throw new SQLException(DatabaseConnection.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }

    /**
     * Opens database only once, to make sure database is not locked
     * @return DatabaseConnection
     */
    public synchronized DatabaseConnection openDatabase() {
        openCounter++;
        if(openCounter == 1) {
            // Opening new database
            try {
                conn = DriverManager.getConnection("jdbc:sqlite:" + PATH + DB_NAME);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    /**
     * Closes database with checks so it won't close too much.
     */
    public synchronized void closeDatabase() {
        openCounter--;
        if(openCounter == 0) {
            // Closing database
            this.close();
        }
    }

    /**
     * This method can be used to execute queries
     * To read or retrieve data use option "result"
     * To create update delete, use option "update"
     */
    public boolean query(String option, String query) {

        try {
            if (conn != null && query != null) {
                preparedStatement = conn.prepareStatement(query);
                switch (option) {
                    case "result":
                        resultSet = preparedStatement.executeQuery();
                        break;
                    case "update":
                        preparedStatement.executeUpdate();
                        break;
                    default:
                        return false;
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Could not execute SQL " + e.getMessage());
            return false;
        }

    }

    /**
     * Returns the result set
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * close connection when done query
     */
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close the connection" + e.getMessage());
        }
    }
}
