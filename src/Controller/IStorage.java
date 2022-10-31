package Controller;

/***
 * Represents an inteface for storage
 * Required to have both load and store
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public interface IStorage {
	/**
     * All storage have a store function,
     * which will save the list of objects in the storage to data base
     */
    public void storeData();
    
    /**
     * All storage have a load function,
     * which will load the list of objects from data base to storage
     */
    public void loadData();
}
