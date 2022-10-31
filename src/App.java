import UI.MainUI;

/***
 * Starts the application
 * 
 * @version 1.0
 * @since 2022-04-17
 */

public class App {
	/**
     * Main method of the application
     * Runs mainUI
     */
    public static void main(String[] args) throws Exception {
        MainUI mainUI = new MainUI();

        mainUI.run();
    }
}