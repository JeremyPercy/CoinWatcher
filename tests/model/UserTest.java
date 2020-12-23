package model;

import org.junit.jupiter.api.Test;
import util.DatabaseConnection;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    /**
     * Testing if you can create table in the database with a function from DatabaseConnection util
      */
    @Test
    void createTableInDB() {
        DatabaseConnection.initializeDBConnection();
        User user = new User();
        assertTrue(user.createDbTable(), "Connection and table create succeeded");
    }

    /**
     * Testing if you can read database after create a database and insert data.
     */
    @Test
    void readUserFromDb() {
        DatabaseConnection.initializeDBConnection();
        User user = new User("Jeremy", "12324214", "34455");
        assertTrue(user.create());
        user.read();
        assertNotNull(user.result(), "Read user data successfully");
    }

    /**
     * Testing if you can delete users table
     */
    @Test
    void checkDelete() {
        DatabaseConnection.initializeDBConnection();
        User user = new User();
        assertTrue(user.delete(), "delete table succeeded");
    }
}