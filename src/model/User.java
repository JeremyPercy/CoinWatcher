package model;

import java.sql.SQLException;

public class User extends Model {
    /**
     * Name of table in database
     */
    private final static String TB_NAME = "User";
    /**
     * Properties
     */
    private String userName;
    private String publicKey;
    private String privateKey;

    /**
     * FXML properties
     */

    public User() {
    }

    public User(String publicKey, String privateKey) {
        setPublicKey(publicKey);
        setPrivateKey(privateKey);
    }

    public User(String userName, String publicKey, String privateKey) {
        setUserName(userName);
        setPublicKey(publicKey);
        setPrivateKey(privateKey);
    }

    public boolean createDbTable() {
        return this.query("update",
                "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                        " ('username' string, 'publickey' string, 'privatekey' string)");
    }

    @Override
    public boolean create() {
        return this.query("update", "INSERT INTO " + TB_NAME +
                " ('username', 'publickey', 'privatekey') " +
                "VALUES('" +
                userName + "', '" +
                publicKey + "', '" +
                privateKey + "')");
    }

    @Override
    public void createOrUpdate() throws Exception {

    }

    @Override
    public void read() {
        this.query("result", "select * from user");
        try {
            if (this.result() == null || this.result().isClosed()) {
                return;
            }
            this.privateKey = this.result().getString("privatekey");
            this.publicKey = this.result().getString("publicKey");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update() {
        String query =
                "UPDATE  " + TB_NAME + " SET " +
                        "`publickey` = '" + this.publicKey + "'," +
                        "`privatekey` = '" + this.privateKey + "'";
        return this.query("update", query);
    }

    @Override
    public boolean delete() {
        String query = "DELETE FROM " + TB_NAME;
        return this.query("update", query);
    }

    public static String getTB_NAME() {
        return TB_NAME;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

}
