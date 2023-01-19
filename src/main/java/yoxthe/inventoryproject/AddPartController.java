package yoxthe.inventoryproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class initiates the Add Part form.
 */
public class AddPartController implements Initializable {

    public TextField AddPartDepenField;
    private int partSource;
    public Button AddPartCancelBtn;
    public RadioButton AddPartInHouseRadio;
    public RadioButton AddPartOutsourcedRadio;
    public TextField AddPartIDField;
    public TextField AddPartNameField;
    public TextField AddPartInvField;
    public TextField AddPartPriceField;
    public TextField AddPartMaxField;
    public TextField AddPartMinField;
    public Button AddPartSaveBtn;
    public Label AddPartConditionalLbl;

    /**
     * This Method initializes the Add Part page.
     * It disables the ID field, which is generated in Application and not by the user,
     * and by default sets up the page to support input of an InHouse Part.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddPartIDField.setDisable(true);
        AddPartIDField.setText("Auto Gen-Disabled");
        AddPartInHouseRadio.setSelected(true);
        AddPartOutsourcedRadio.setSelected(false);
        AddPartConditionalLbl.setText("Machine ID");
        partSource = 0;
    }

    /**
     * Method that Initializes the Main Form.
     * This sets the stage to the Main Form scene without saving the part to be added.
     * @param actionEvent User interface interaction that triggers the Method.
     */
    public void returnToMain(ActionEvent actionEvent) throws IOException {
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
        AddPartOutsourcedRadio.setSelected(false);
        AddPartConditionalLbl.setText("Machine ID");
        partSource = 0;
    }

    /**
     * Method that alters the Form Fields if OutSourced Radio is selected.
     * If the Radio is selected, it sets the InHouse Radio to false and adjusts the
     * Dependent Field to the Company Name to accept the proper input value.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void outSourcedSelected(ActionEvent actionEvent) {
        AddPartInHouseRadio.setSelected(false);
        AddPartConditionalLbl.setText("Company Name");
        partSource = 1;
    }

    /**
     * Method that validates the user input and saves the New Part to the Inventory.
     * This validates based on the type of Part selected to be created, InHouse or OutSourced,
     * and displays alerts, providing opportunity for correction of the input data if any inputs
     * are invalid.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void saveNewPart(ActionEvent actionEvent) throws IOException {
        int new_id;
        String new_name;
        int new_inv;
        int new_max;
        double new_price;
        int new_min;
        int new_machineID;
        String new_companyName;
        String causeOfError = "";

        //FIX ME
        if (partSource == 0) {
            new_name = AddPartNameField.getText();
            if (new_name.isBlank()) {
                InventoryApplication.displayError("Name must not be blank.");
                return;
            }
            try {
                causeOfError = "ID";
                new_id = InventoryApplication.partCount;
                causeOfError = "Price";
                new_price = Double.parseDouble(AddPartPriceField.getText());
                causeOfError = "Inventory";
                new_inv = Integer.parseInt(AddPartInvField.getText());
                causeOfError = "Max";
                new_max = Integer.parseInt(AddPartMaxField.getText());
                causeOfError = "Min";
                new_min = Integer.parseInt(AddPartMinField.getText());
                if (InventoryApplication.checkRange(new_inv, new_max, new_min)) {
                    return;
                }
                causeOfError = "Machine ID";
                new_machineID = Integer.parseInt(AddPartDepenField.getText());

                Inventory.addPart(new inHouse(new_id, new_name, new_price, new_inv, new_min, new_max, new_machineID));
            }
            catch (NumberFormatException badNumberInput) {
                InventoryApplication.displayError(causeOfError + " value must be a number.");
                return;
            }
        }
        else if (partSource == 1) {
            new_name = AddPartNameField.getText();
            if (new_name.isBlank()) {
                InventoryApplication.displayError("Name must not be blank.");
                return;
            }

            new_companyName = AddPartDepenField.getText();
            if (new_companyName.isBlank()) {
                InventoryApplication.displayError("Company Name must not be blank.");
                return;
            }
            try {
                causeOfError = "ID";
                new_id = InventoryApplication.partCount;
                causeOfError = "Price";
                new_price = Double.parseDouble(AddPartPriceField.getText());
                causeOfError = "Inventory";
                new_inv = Integer.parseInt(AddPartInvField.getText());
                causeOfError = "Max";
                new_max = Integer.parseInt(AddPartMaxField.getText());
                causeOfError = "Min";
                new_min = Integer.parseInt(AddPartMinField.getText());
                if (InventoryApplication.checkRange(new_inv, new_max, new_min)) {
                    return;
                }

                Inventory.addPart(new outSourced(new_id, new_name, new_price, new_inv, new_min, new_max, new_companyName));
            }
            catch (NumberFormatException badNumberInput){
                InventoryApplication.displayError(causeOfError + " value must be a number.");
                return;
            }

        }

        InventoryApplication.partCount++;

        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("main-form.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();


    }
}
