
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class for the Add Product Form FXML page
 *
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class AddProductFormController implements Initializable 
{
    
    Stage stage;
    Parent scene;
    private ObservableList<Part> prodPartsList = FXCollections.observableArrayList();
    private static int idCounter = 0;

    @FXML
    private TextField ProdIDTxt;

    @FXML
    private TextField ProdNameTxt;

    @FXML
    private TextField ProdInvTxt;

    @FXML
    private TextField ProdPriceTxt;

    @FXML
    private TextField ProdMaxTxt;

    @FXML
    private TextField ProdMinTxt;

    @FXML
    private TextField PartSearchTxt;
    
    @FXML
    private Button PartSearchBtn;

    @FXML
    private TableView<Part> PartsTableView;
    
    @FXML
    private TableColumn<Part, Integer> PartIdCol;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, Integer> PartInvCol;

    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    @FXML
    private Button AddProdBtn;

    @FXML
    private TableView<Part> ProdPartsTableView;
    
     @FXML
    private TableColumn<Part, Integer> ProdPartIdCol;

    @FXML
    private TableColumn<Part, String> ProdPartNameCol;

    @FXML
    private TableColumn<Part, Integer> ProdPartInvCol;

    @FXML
    private TableColumn<Part, Double> ProdPartPriceCol;

    @FXML
    private Button RmvPartBtn;

    @FXML
    private Button SaveBtn;

    @FXML
    private Button CancelBtn;
    
    /**
     * Searches the part inventory by ID Number or Name. 
     * @param event interaction with an FXML Button
     */
    @FXML
    void onActionSearchParts(ActionEvent event) 
    {
        try
        {
            int searchVal = Integer.parseInt(PartSearchTxt.getText());
            Part searchResults = Inventory.lookupPart(searchVal);
            if (searchResults != null)
                PartsTableView.getSelectionModel().select(searchResults);
            else
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("No results found!");
                errorAlert.showAndWait();
            }
        }
        catch(Exception e)
        {
            String searchVal = PartSearchTxt.getText();
            ObservableList searchResults = Inventory.lookupPart(searchVal);
            if (!(searchResults.isEmpty()))
                PartsTableView.setItems(searchResults);
            else
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("No results found!");
                errorAlert.showAndWait();
            }
        }
    }

    /**
     * Adds a selected part from the all parts list to the list of associated
     * parts for the product. 
     * @param event interaction with an FXML Button
     * @throws IOException 
     */
    @FXML
    void onActionAddPartToProduct(ActionEvent event) throws IOException 
    {
        try
        {
            Part part = PartsTableView.getSelectionModel().getSelectedItem();
            part.getClass();
            prodPartsList.add(part);
        }
        catch(Exception e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("You must select a part first!");
            errorAlert.showAndWait();
        }


    }

    /**
     * Creates a product and adds it to the inventory then returns to the Main Form.   
     * @param event interaction with an FXML Button
     * @throws IOException 
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException 
    {
        try
        {   
            String name = ProdNameTxt.getText();
            int inventory = Integer.parseInt(ProdInvTxt.getText());
            double price = Double.parseDouble(ProdPriceTxt.getText());
            int max = Integer.parseInt(ProdMaxTxt.getText());
            int min = Integer.parseInt(ProdMinTxt.getText());
            
            idCounter++;
            int id = idCounter;
            if (!name.isEmpty())
            {
                if (max > min)
                { 
                    if((inventory >= min) && (inventory <= max))
                    {
                        if (price > 0)
                        {
                            Product product = new Product(id, name, price, inventory, min, max);
                            for (Part part : prodPartsList)
                                product.addAssociatedPart(part);
                            Inventory.addProduct(product);

                            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show(); 
                        }
                        else
                        {
                            displayMessage("Price must be greater than zero!");
                        }
                    }
                    else
                    {
                        displayMessage("Inventory must be between max and min");
                    }
                }
                else
                {
                    displayMessage("Max must be greater than min");
                }
            }
            else
            {
                displayMessage("Name should not be empty");
            }
        }
        catch(NumberFormatException e)
        {
            displayMessage("Please enter valid values " + e.getMessage());
        }
    }

    /**
     * Returns user to the Main Form. 
     * @param event interaction with FXML Button
     * @throws IOException 
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException 
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Removes a part from the list of associated parts for the product. 
     * @param event mouse click on FXML Button
     */
    @FXML
    void onActionRemovePartFromProduct(ActionEvent event) 
    {
        Part part = null;
        part = ProdPartsTableView.getSelectionModel().getSelectedItem();
        if (part == null)
        {  
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("You must select a part first!");
            errorAlert.showAndWait();    
        }
        else
            prodPartsList.remove(part);    
    }
    
    /**
     * Displays a popup warning box to display a message to the user
     * @param message message to display in the warning popup
     */
    void displayMessage(String message)
    {
        idCounter--;    
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Initializes the controller class and sets the data to be shown in the tables. 
     * @param url unused
     * @param rb unused
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //Show all parts in part table
        PartsTableView.setItems(Inventory.getAllParts());    
        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Show associated parts
        ProdPartsTableView.setItems(prodPartsList);    
        ProdPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProdPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProdPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        for (Product product : Inventory.getAllProducts())
            if (product.getId() > idCounter)
                idCounter = product.getId();           
    }    
}
