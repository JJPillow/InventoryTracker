
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class for the Modify Part Form FXML page
 *
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class ModifyPartFormController implements Initializable 
{
    Stage stage;
    Parent scene;
    private static Part selectedPart = null;

    
    @FXML
    private RadioButton PartSourceInRBtn;

    @FXML
    private RadioButton PartSourceOutRbtn;

    @FXML
    private TextField PartIDTxt;

    @FXML
    private TextField PartNameTxt;

    @FXML
    private TextField PartInvTxt;

    @FXML
    private TextField PartPriceTxt;

    @FXML
    private TextField PartMaxTxt;

    @FXML
    private TextField PartMinTxt;

    @FXML
    private Label partSourceLbl;

    @FXML
    private TextField MachIDTxt;

    @FXML
    private Button PartSaveBtn;

    @FXML
    private Button CancelBtn;

     /**
     * Returns user to the Main Form. 
     * @param event click interaction with FXML Button Control
     * @throws IOException 
     */
    @FXML
    void onActionDisplayMainForm(ActionEvent event) throws IOException 
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Changes will be lost, do you want to continue?");
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
     * @param event click interaction with FXML Button Control
     */   
    @FXML
    void onActionInHouseRBtnListener(ActionEvent event) 
    {
        if(PartSourceInRBtn.isSelected())
            partSourceLbl.setText("Machine ID");
    }

    /**
     * Makes changes to a part in inventory. 
     * @param event click interaction on FXML Button Control
     * @throws IOException failure to load Main Form 
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException 
    {
        int id = Integer.parseInt(PartIDTxt.getText());
        Part modifiedPart = null;
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
                        if(price > 0)
                        {
                            if(PartSourceInRBtn.isSelected())
                            {
                                int machId = Integer.parseInt(MachIDTxt.getText());
                                modifiedPart = new InHouse(id, name, price, inventory, min, max, machId);
                            }
                            else
                            {
                                String compName = MachIDTxt.getText();
                                modifiedPart = new Outsourced(id, name, price, inventory, min, max, compName);
                            }                
                            int index = Inventory.getAllParts().indexOf(Inventory.lookupPart(id));
                            Inventory.updatePart(index, modifiedPart);
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
     * Shows label for Company Name when Outsource part type is selected
     * @param event click interaction on FXML Button Control
     */
    @FXML
    void onActionOutsourceRBtnListener(ActionEvent event) 
    {
        if(PartSourceOutRbtn.isSelected())
            partSourceLbl.setText("Company Name");
    }
    
    /**
     * Sets text in text fields on form with data from part. 
     * @param part use part field data to fill text fields
     */
    public void sendPart(Part part)
    {
       selectedPart = part;
       if(selectedPart instanceof InHouse)
        {
            PartSourceInRBtn.setSelected(true);
            MachIDTxt.setText(String.valueOf((((InHouse)selectedPart).getMachineId())));
        }
        else if (selectedPart instanceof Outsourced)
        {
            PartSourceOutRbtn.setSelected(true);
            MachIDTxt.setText(((Outsourced)selectedPart).getCompanyName());
            partSourceLbl.setText("Company Name"); 
        }
        else
            MachIDTxt.setText("");
       
        PartIDTxt.setText(String.valueOf(selectedPart.getId()));
        PartNameTxt.setText(selectedPart.getName());
        PartInvTxt.setText(String.valueOf(selectedPart.getStock()));
        PartPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        PartMaxTxt.setText(String.valueOf(selectedPart.getMax()));
        PartMinTxt.setText(String.valueOf(selectedPart.getMin()));
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    }    
    
}
