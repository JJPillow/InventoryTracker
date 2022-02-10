
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class for the Add Part Form FXML page.
 *
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class AddPartFormController implements Initializable 
{
    
    /**
     * Stage on which to set the scene when changing to another form.
     */
    private Stage stage;
    
    /**
     * Scene which displays another form. 
     */
    private Parent scene;
    
    /**
     * Holds a counter to create part IDs, increments when a new part is created.
     */
    private static int idCounter = 0;
    
    /**
     * Radio Button for the In-house part.  If selected, triggers the 
     * onActionInHouseRbtnListener() method.
     */
    @FXML
    private RadioButton AddPrtInRBtn;

    /**
     * Group of radio buttons that includes the AddPrtInRbtn and AddPrtOutRbtn.
     * Only one radio button can be selected at a time.
     */
    @FXML
    private ToggleGroup PartSource;

    /**
     *  Radio Button for the Outsourced part.  If selected, triggers the 
     * onActionOutsourceRbtnListener() method.
     */
    @FXML
    private RadioButton AddPrtOutRBtn;

    /**
     * Holds the part ID number.
     */
    @FXML
    private TextField PartIDTxt;

    /**
     * Holds the name of the part. 
     */
    @FXML
    private TextField PartNameTxt;

    /**
     * Holds the current inventory number for the part. 
     */
    @FXML
    private TextField PartInvTxt;

    /**
     * Holds the part price. 
     */
    @FXML
    private TextField PartPriceTxt;

    /**
     * Holds the maximum inventory number for the part. 
     */
    @FXML
    private TextField PartMaxTxt;

    /**
     * Holds the minimum inventory number for the part. 
     */
    @FXML
    private TextField PartMinTxt;

    /**
     * Variable label with text reading Machine ID for an In-house part or 
     * Company Name for an Outsourced part.  Label text changed by radio buttons 
     * in the PartSource toggle group. 
     */
    @FXML
    private Label PartSourceLbl;

    /**
     * Holds either the MachineID data for an In-house part, or the name of the 
     * sourcing company for an Outsourced part. 
     */
    @FXML
    private TextField PartMachIDTxt;

    /**
     * Launches the onActionAddPart() method to save a new part. 
     */
    @FXML
    private Button PartSaveBtn;

    /**
     * Launches the onActionDisplayMainForm() method to cancel adding a new part
     * and return to the main form. 
     */
    @FXML
    private Button CancelBtn;

/**
 * Adds a part to the ObservableList in Inventory then returns to the Main Form.
 * <p><b>RUNTIME ERROR NumberFormatException was thrown by the parseInt and 
 * parseDouble methods called to handle user text input.  It was caught and a 
 * warning popup window was implemented to prompt the user to change the input.
 * </b></p>
 * 
 * @param event OnButtonClick event
 * @throws IOException FXMLLoader.load throws exception if the file isn't found
 */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException 
    {
        idCounter++;
        int id = idCounter;
        
        try
        {  
            String name = PartNameTxt.getText();
            int inventory = Integer.parseInt(PartInvTxt.getText());
            double price = Double.parseDouble(PartPriceTxt.getText());
            int max = Integer.parseInt(PartMaxTxt.getText());
            int min = Integer.parseInt(PartMinTxt.getText());
            
            
            if (!name.isEmpty())
            {
                if (max > min)
                { 
                    if((inventory >= min) && (inventory <= max))
                    { 
                        if (price > 0)
                        {   
                            if(AddPrtInRBtn.isSelected())
                            {
                                int machId = Integer.parseInt(PartMachIDTxt.getText());
                                Inventory.addPart(new InHouse(id, name, price, inventory, min, max, machId));
                            }
                            else
                            {
                                String compName = PartMachIDTxt.getText();
                                Inventory.addPart(new Outsourced(id, name, price, inventory, min, max, compName));
                            }
                            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show(); 
                        }
                        else
                            displayMessage("Price must be greater than zero!");
                    }   
                    else
                        displayMessage("Inventory must be between max and min");
                }
                else
                    displayMessage("Max must be greater than min");
            }
            else
                displayMessage("Name should not be empty");
        }
        catch(NumberFormatException e)
        {
            displayMessage("Please enter valid values " + e.getMessage()); 
        }
    }
    
    /**
     * Returns to the main form
     * @param event FXML Button interaction
     * @throws IOException Fails to load if FXML page is not found
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Unsaved data will be cleared, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    
    /**
     * Shows label for machine ID when In-House part type is selected
     * @param event FXML Button interaction
     */
    @FXML
    void onActionInHouseRbtnListener(ActionEvent event) 
    {
        if(AddPrtInRBtn.isSelected())
            PartSourceLbl.setText("Machine ID");
    }
    
    /**
     * Shows label for Company Name when Outsource part type is selected
     * @param event FXML Button interaction
     */
    @FXML
    void onActionOutsourceRbtnListener(ActionEvent event) 
    {
        if(AddPrtOutRBtn.isSelected())
            PartSourceLbl.setText("Company Name");
    }
    
    /**
     * Displays a popup warning box to display a message to the user
     * @param message a string warning message to display 
     */
    void displayMessage(String message)
    {
        idCounter--;    
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Initializes the controller class.
     * <p><b>LOGICAL ERROR The best way to create unique id numbers for the parts
     * would have been to add it to add a counter to the part constructor that 
     * increments every time the constructor is called.  But since the part class
     * wasn't allowed to be modified, I got around it by using the highest part 
     * ID number already in the parts list to initialize a counter that increments
     * before adding a new part.</b></p>
     * 
     * @param url unused
     * @param rb unused
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //Find the highest part number used in test data to initialize the part creation counter
        for (Part part : Inventory.getAllParts())
            if (part.getId() > idCounter)
                idCounter = part.getId();
    }     
}
