
package c482project_jpillo2;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 * The C482Project_JPillo2 program implements an application that allows users to
 * track inventory of parts and products.
 * <p><b>FUTURE ENHANCEMENT Add functionality for the program to interface with a database.
 * This would allow data to be retained after the program is closed.  It would also improve 
 * the search functionality allowing users to sort and filter based on all the data fields.</b></p>
 * 
 * JavaDoc folder is included in the src folder.
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class C482Project_JPillo2 extends Application{

    /**
     * This is the main method that launches the inventory program.
     * 
     * @param args arguments to be passed to the launch method
     */
    public static void main(String[] args) 
    {
        
        //Create some Part Test Data
        Part part1 = new InHouse(1, "Wheel", 9.99, 10, 8, 25, 84523);
        Part part2 = new InHouse(2, "Wheel w/sprocket", 13.99, 12, 8, 25, 63521);
        Part part3 = new Outsourced(3, "Unicycle Frame", 23.45, 4, 4, 15, "UniCo");
        Part part4 = new Outsourced(4, "Bicycle Frame", 27.67, 7, 5, 17, "Bikes R Us");
        Part part5 = new Outsourced(5, "Crankshaft with pedals", 15.23, 13, 10, 25, "Universal Cranks");
        
        //Add Parts to Part Table
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        
        //Create some Product Test Data
        Product product1 = new Product(1, "Unicycle", 34.56, 4, 2,6);
        Product product2 = new Product(2, "Ten-Speed", 134.56, 7, 4,10);
        Product product3 = new Product(3, "Mountain Bike", 175.04, 4, 3, 13);
        //Add some Parts to Products
        product2.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
        product2.addAssociatedPart(part5);
        
        //Add Products to Product Table
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        
        
        launch(args);
    }
/**The main entry method for the JavaFX application.
 * 
 * @param stage the primary stage on which to set the application scene
 * @throws Exception failed to load MainForm.fxml
 */
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
