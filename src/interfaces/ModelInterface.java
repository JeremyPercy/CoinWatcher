package interfaces;

public interface ModelInterface {

    /**
     * Creates table for model.
     * To be used in first time starting application
     *
     * @return true on success
     */
    boolean createDbTable();

    /**
     * Inserts new row in database
     *
     * @return fail when something went wrong
     */
    boolean create();

    /**
     * Checks if model already exists in database. If not, it will create one. Else it will update the record
     * @throws Exception
     */
    void createOrUpdate() throws Exception;

    /**
     * Reads line from database
     */
    void read();

    /**
     * Updates row in database
     *
     * @return
     */
    boolean update() throws Exception;

    /**
     * Deletes row from database
     *
     * @return
     */
    boolean delete() throws Exception;

    /**
     * Saves model. Use this after all queries are done.
     * For DB, it will close connection.
     */
    void save();

}
