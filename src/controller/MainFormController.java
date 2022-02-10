
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class for the Main Form FXML page
 *
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class MainFormController implements Initializable 
{ 
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField PartSearchTxt;
 
    @FXML
    private Button partSearchBtn;

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
    private Button ModifyPartBtn;

    @FXML
    private Button DeletePartBtn;

    @FXML
    private TextField ProdSearchTxt;
    
    @FXML
    private Button prodSearchBtn;

    @FXML
    private TableView<Product> ProductsTableView;
    
    @FXML
    private TableColumn<Product, Integer> ProdIdCol;

    @FXML
    private TableColumn<Product, String> ProdNameCol;

    @FXML
    private TableColumn<Product, Integer> ProdInvCol;

    @FXML
    private TableColumn<Product, Double> ProdPriceCol;

    @FXML
    private Button AddProdBtn;

    @FXML
    private Button ModifyProdBtn;

    @FXML
    private Button DeleteProdBtn;

    @FXML
    private Button ExitBtn;

    /**
     * Searches the part inventory by ID Number or Name
     * @param event click interaction on FXML button control
     */
    @FXML
    void onActionSearchParts(ActionEvent event) 
    {  
        try
        {
            int searchVal = Integer.parseInt(PartSearchTxt.getText());
            Part foundPart = Inventory.lookupPart(searchVal);
            if (foundPart != null)
                PartsTableView.getSelectionModel().select(foundPart);
            else
                displayMessage("No results found!");

        }
        catch(Exception e)
        {
            String searchVal = PartSearchTxt.getText();
            ObservableList searchResults = Inventory.lookupPart(searchVal);
            if (!(searchResults.isEmpty()))
                PartsTableView.setItems(searchResults);
            else displayMessage("No results found!");
        }   
    }
    
    /**
     * Searches the product inventory by ID Number or Name
     * @param event click interaction on FXML button control
     */
    @FXML
    void onActionSearchProducts(ActionEvent event) 
    {
        try
        {
            int searchVal = Integer.parseInt(ProdSearchTxt.getText());
            Product foundProduct = Inventory.lookupProduct(searchVal);
            if (foundProduct != null)
                ProductsTableView.getSelectionModel().select(foundProduct);
            else
                displayMessage("No results found!");

        }
        catch(Exception e)
        {
            String searchVal = ProdSearchTxt.getText();
            ObservableList searchResults = Inventory.lookupProduct(searchVal);
            if (!(searchResults.isEmpty()))
                ProductsTableView.setItems(searchResults);
            else
                displayMessage("No results found!");
        }
    }

    /**
     * Removes a part from the inventory list. 
     * @param event click interaction on FXML button control
     */
    @FXML
    void onActionDeletePart(ActionEvent event) 
    {
        Part part = PartsTableView.getSelectionModel().getSelectedItem();
        try
        {
            part.getClass();  //causes exception if no part selected
            Alert warnAlert = new Alert(Alert.AlertType.CONFIRMATION, "Part will be deleted, are you sure?");
            Optional<ButtonType> result = warnAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
                Inventory.deletePart(part);
        }
        catch(Exception e)
        {
            displayMessage("You must select a part first!");
        }
    }

    /**
     * Removes a product from the product inventory.  Product will not be removed
     * if it still has associated parts.
     * @param event click interaction on FXML Button
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) 
    {
        Product product = ProductsTableView.getSelectionModel().getSelectedItem();
            try
            { 
                if (product.getAssociatedParts().isEmpty())
                {
                    Alert warnAlert = new Alert(Alert.AlertType.CONFIRMATION, "Product will be deleted, are you sure?");
                    Optional<ButtonType> result = warnAlert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK)
                        Inventory.deleteProduct(product);
                }
                else
                    displayMessage("Product cannot be deleted while it has associated parts!");
            }  
            catch(Exception e)
            {
                displayMessage("You must select a product first!");
            }
    }

    /**
     * Displays the Add Parts Form
     * @param event click interaction on FXML Button Control
     * @throws IOException failure to load the Add Part Form
     */
    @FXML
    void onActionDisplayAddPartForm(ActionEvent event) throws IOException 
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays the Add Product Form
     * @param event click interaction on FXML Button Control
     * @throws IOException failure to load the Add Product Form
     */
    @FXML
    void onActionDisplayAddProductForm(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddProductForm.fxml"));
        scene = loader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Displays Modify Part Form and sends data from selected part to populate 
     * fields on the form. 
     * @param event click interaction with FXML Button Control
     * @throws IOException failure to load the Modify Part Form
     */
    @FXML
    void onActionDisplayModifyPartForm(ActionEvent event) throws IOException 
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
            scene = loader.load();

            ModifyPartFormController MPtFController = loader.getController();
            MPtFController.sendPart(PartsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (Exception e)
        {
            displayMessage("You must select a part first!");
        }
    }

     /**
     * Displays Modify Product Form and sends data from selected product to populate 
     * fields on the form. 
     * @param event click interaction with FXML Button Control
     */
    @FXML
    void onActionDisplayModifyProductForm(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            scene = loader.load();

            ModifyProductFormController MPrFController = loader.getController();
            MPrFController.sendProduct(ProductsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (Exception e)
        {
            displayMessage("You must select a product first!");
        }
    }

    /**
     * Exits the program. 
     * @param event click interaction with FXML Button Control 
     */
    @FXML
    void onActionExit(ActionEvent event) 
    {
        System.exit(0);
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
     * Initializes the controller class and sets the data to be shown in the tables.
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
        
        //Show all products in product table
        ProductsTableView.setItems(Inventory.getAllProducts());
        ProdIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }     
}
