package controller;

import org.junit.jupiter.api.Test;
import util.DatabaseConnection;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    /**
     * Check if there is a user in the database return false when not. Test when you have insert a user in database.
     */
    @Test
    void checkIfUserExist() {
        DatabaseConnection.initializeDBConnection();
        UserController userController = new UserController();
        assertTrue(userController.getCheckUser(), "There is a user in database");
    }

}