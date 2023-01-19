package yoxthe.inventoryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class initializes the Modify Part Page and its component parts and Methods.
 */
public class ModifyPartController implements Initializable {
    public TextField ModifyPartDepenField;
    private int partSource;
    public Button ModifyPartCancelBtn;
    public RadioButton ModifyPartInHouseRadio;
    public RadioButton ModifyPartOutsourcedRadio;
    public TextField ModifyPartIDField;
    public TextField ModifyPartNameField;
    public TextField ModifyPartInvField;
    public TextField ModifyPartPriceField;
    public TextField ModifyPartMaxField;
    public TextField ModifyPartMinField;
    public Button ModifyPartSaveBtn;
    private static Part thePass;
    public Label ModifyPartConditionalLbl;

    /**
     * This method initializes the Form, setting its value fields equal to the Part information passed in from the Main Form.
     * It disables the ID Field, sets the Radio equal to the type matching the part, and fills in all fields with editable text.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModifyPartIDField.setText(String.valueOf(thePass.getId()));
        ModifyPartIDField.setDisable(true);
        ModifyPartNameField.setText(String.valueOf(thePass.getName()));
        ModifyPartInvField.setText(String.valueOf(thePass.getStock()));
        ModifyPartPriceField.setText(String.valueOf(thePass.getPrice()));
        ModifyPartMaxField.setText(String.valueOf(thePass.getMax()));
        ModifyPartMinField.setText(String.valueOf(thePass.getMin()));
        if (thePass instanceof inHouse) {
            ModifyPartOutsourcedRadio.setSelected(false);
            ModifyPartInHouseRadio.setSelected(true);
            ModifyPartConditionalLbl.setText("Machine ID");
            ModifyPartDepenField.setText(String.valueOf(((inHouse) thePass).getMachineId()));
            partSource = 0;
        } else if (thePass instanceof outSourced){
            ModifyPartInHouseRadio.setSelected(false);
            ModifyPartOutsourcedRadio.setSelected(true);
            ModifyPartConditionalLbl.setText("Company Name");
            ModifyPartDepenField.setText(String.valueOf(((outSourced) thePass).getCompanyName()));
            partSource = 1;
        } else {
            throw new RuntimeException("No Part Selected");
        }
    }

    /**
     * Method that returns to the Main Form.
     * @param actionEvent User interface interaction that triggers the Method.
     */
    public void returnToMainForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("main-form.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that initializes thePass value with the Part passed in from the Main Form at this method call.
     * @param part The Part selected in the Main Form that is chosen to modified.
     */
    public static void partToModify (Part part) {
        thePass = part;
    }

    /**
     * Method that saves the Modified Part to the All Parts list.
     * Checks for input validation, accepting and saving the Part to the Inventory if free of errors.
     * If there are errors, displays the appropriate message and allows opportunity to adjust input data.
     * @param actionEvent User interface interaction that triggers the Method.
     */
    public void saveModifiedPart(ActionEvent actionEvent) throws IOException {
        int new_id;
        String new_name;
        int new_inv;
        int new_max;
        double new_price;
        int new_min;
        int new_machineID;
        String new_companyName;
        String causeOfError = "";

        if (partSource == 0) {
            new_name = ModifyPartNameField.getText();
            if (new_name.isBlank()) {
                InventoryApplication.displayError("Name must not be blank.");
            }
            try {
                causeOfError = "ID";
                new_id = thePass.getId();
                causeOfError = "Price";
                new_price = Double.parseDouble(ModifyPartPriceField.getText());
                causeOfError = "Inv";
                new_inv = Integer.parseInt(ModifyPartInvField.getText());
                causeOfError = "Max";
                new_max = Integer.parseInt(ModifyPartMaxField.getText());
                causeOfError = "Min";
                new_min = Integer.parseInt(ModifyPartMinField.getText());
                causeOfError = "Machine ID";
                new_machineID = Integer.parseInt(ModifyPartDepenField.getText());

                if (InventoryApplication.checkRange(new_inv, new_max, new_min)) {
                    return;
                }

                Inventory.updatePart(Inventory.allParts.indexOf(thePass), new inHouse(new_id, new_name, new_price, new_inv, new_min, new_max, new_machineID));
            }
            catch (NumberFormatException badNumberInput) {
                InventoryApplication.displayError(causeOfError + " value must be a number.");
                return;
            }
        }
        else if (partSource == 1){
            new_name = ModifyPartNameField.getText();
            if (new_name.isBlank()) {
                InventoryApplication.displayError("Name must not be blank.");
                return;
            }
            new_companyName = ModifyPartDepenField.getText();
            if (new_companyName.isBlank()) {
                InventoryApplication.displayError("Company Name must not be blank.");
                return;
            }
            try {
                causeOfError = "ID";
                new_id = thePass.getId();
                causeOfError =  "Price";
                new_price = Double.parseDouble(ModifyPartPriceField.getText());
                causeOfError = "Inv";
                new_inv = Integer.parseInt(ModifyPartInvField.getText());
                causeOfError = "Max";
                new_max = Integer.parseInt(ModifyPartMaxField.getText());
                causeOfError = "Min";
                new_min = Integer.parseInt(ModifyPartMinField.getText());

                if (InventoryApplication.checkRange(new_inv, new_max, new_min)) {
                    return;
                }

                Inventory.updatePart(Inventory.allParts.indexOf(thePass), new outSourced(new_id, new_name, new_price, new_inv, new_min, new_max, new_companyName));
            }
            catch (NumberFormatException badNumberInput) {
                InventoryApplication.displayError(causeOfError + " value must be a number.");
                return;
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("main-form.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that alters the Form Fields if InHouse Radio is selected.
     * If the Radio is selected, it sets the Outsourced Radio to false and adjusts the
     * Dependent Field to the Machine ID to accept the proper input value.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void inHouseSelected(ActionEvent actionEvent) {
        ModifyPartOutsourcedRadio.setSelected(false);
        ModifyPartConditionalLbl.setText("Machine ID");
        partSource = 0;
    }

    /**
     * Method that alters the Form Fields if OutSourced Radio is selected.
     * If the Radio is selected, it sets the InHouse Radio to false and adjusts the
     * Dependent Field to the Company Name to accept the proper input value.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void OutSourcedSelected(ActionEvent actionEvent) {
        ModifyPartInHouseRadio.setSelected(false);
        ModifyPartConditionalLbl.setText("Company Name");
        partSource = 1;
    }
}
