package Controller;

/***
 * Represents an interface of controller
 * Required to implement CRUD
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public interface IController {
    /**
     * All controller have a create function,
     * which will take in the object and
     * ultimately add it to a database
     * 
     * @param entities Declared as object to allow different entities to be pass in
     */
    public void create(Object entities);

    /**
     * All controller have a read function
     */
    public void read();

    /**
     * All controller have a delete function,
     * which will remove object entity from the datebase
     * 
     * @param entities Declared as object to allow different entities to be pass in
     */
    public void delete(Object entities);

    /**
     * All controller have a update function,
     * Take and object entity in
     * based on user choice
     * update with user input
     * 
     * @param entities Declared as object to allow different entities to be pass in
     * @param choice   User choice
     * @param value    User input value
     */
    public void update(Object entities, int choice, String value);

}
