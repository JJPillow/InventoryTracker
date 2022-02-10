
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
 * FXML Controller class for the Modify Product Form FXML page
 *
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class ModifyProductFormController implements Initializable 
{

    Stage stage;
    Parent scene;
    private static Product selectedProduct = null;
    private ObservableList<Part> prodPartsList = FXCollections.observableArrayList();
    
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
    private Button AddPartBtn;

    @FXML
    private TableView<Part> ProductPartsTableView;
    
    @FXML
    private TableColumn<Part, Integer> prodPartIdCol;

    @FXML
    private TableColumn<Part, String> ProdPartNameCol;

    @FXML
    private TableColumn<Part, Integer> prodPartInvCol;

    @FXML
    private TableColumn<Part, Double> prodPartPriceCol;

    @FXML
    private Button RemovePartBtn;

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
    void onActionAddPartToProduct(ActionEvent event) 
    {
        try
        {
            Part part = PartsTableView.getSelectionModel().getSelectedItem();
            part.getClass();    //causes exception if no part chosen
            prodPartsList.add(part);
        }
        catch(Exception e)
        {
            displayMessage("You must select a part first!");
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
     * Makes changes to a product in inventory. 
     * @param event click interaction on FXML Button Control
     * @throws IOException failure to load Main Form 
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException 
    {
        try
        {
            String name = ProdNameTxt.getText();
            int inventory = Integer.parseInt(ProdInvTxt.getText());
            double price = Double.parseDouble(ProdPriceTxt.getText());
            int max = Integer.parseInt(ProdMaxTxt.getText());
            int min = Integer.parseInt(ProdMinTxt.getText());
            int id = Integer.parseInt(ProdIDTxt.getText());
            
            if (!name.isEmpty())
            {
                if (max > min)
                { 
                    if (price > 0)
                    {
                        if((inventory >= min) && (inventory <= max))
                        {
                            Product product = new Product(id, name, price, inventory, min, max);
                            for (Part part : prodPartsList)
                                product.addAssociatedPart(part);

                            int index = Inventory.getAllProducts().indexOf(Inventory.lookupProduct(id));
                            Inventory.updateProduct(index, product);

                            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show(); 
                        }
                        else
                        {
                            displayMessage("Inventory must be between max and min!");
                        }
                    }
                    else
                    {
                        displayMessage("Price must be greater than zero!");
                    }
                }
                else
                {
                    displayMessage("Max must be greater than min!");
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
     * Removes a part from the list of associated parts for the product. 
     * @param event mouse click on FXML Button
     */
    @FXML
    void onActionRemovePartFromProduct(ActionEvent event) 
    {      
        try
        {
            Part part = ProductPartsTableView.getSelectionModel().getSelectedItem();
            part.getClass(); //to cause exception if no part selected
            prodPartsList.remove(part);
        }
        catch(Exception e)
        {
            displayMessage("You must select a part first!");
        }
    }

    /**
     * Sets text in text fields on form with data from product. 
     * @param product use product field data to fill text fields
     */    
    public void sendProduct(Product product)
    {
        selectedProduct = product;
             
        ProdIDTxt.setText(String.valueOf(selectedProduct.getId()));
        ProdNameTxt.setText(selectedProduct.getName());
        ProdInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        ProdPriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        ProdMaxTxt.setText(String.valueOf(selectedProduct.getMax()));
        ProdMinTxt.setText(String.valueOf(selectedProduct.getMin()));
        
        for (Part part : selectedProduct.getAssociatedParts())
            prodPartsList.add(part);
        
        ProductPartsTableView.setItems(prodPartsList);
                
        prodPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
              

    }

     /**
     * Displays a popup warning box to display a message to the user
     * @param message message to display in the warning popup
     */
    void displayMessage(String message)
    {   
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Initializes the controller class and sets the data to be shown in the table views. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        PartsTableView.setItems(Inventory.getAllParts());
                
        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }  
}
