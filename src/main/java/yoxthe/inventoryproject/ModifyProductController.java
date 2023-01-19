package yoxthe.inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class provides the functioning methods for the Modify Product Form of the Inventory Application.
 */
public class ModifyProductController implements Initializable {
    public Button ModifyProductCancelBtn;
    public TextField ModifyProductIDField;
    public TextField ModifyProductNameField;
    public TextField ModifyProductInvField;
    public TextField ModifyProductPriceField;
    public TextField ModifyProductMaxField;
    public TextField ModifyProductMinField;
    public Button ModifyProductSaveBtn;
    public TableView<Part> ModifyProductAllParts;
    public TableView<Part> ModifyProductAssociatedParts;
    public Button ModifyProductAddPartBtn;
    public Button ModifyProductRemovePart;
    public TableColumn<Inventory, Part> allPartsID;
    public TableColumn<Inventory, Part> allPartsName;
    public TableColumn<Inventory, Part> allPartsInv;
    public TableColumn<Inventory, Part> allPartsPrice;
    public TableColumn<Inventory, Part> ProductsPartsID;
    public TableColumn<Inventory, Part> ProductsPartsName;
    public TableColumn<Inventory, Part> ProductsPartsInv;
    public TableColumn<Inventory, Part> ProductsPartsPrice;
    private static Product thePass;
    public TextField allPartsSearch;

    /**
     * This method initializes the Modify Product Page as it's opened.
     * The method sets the Page's tables equal to the ObservableList of All Parts in the Inventory,
     * and the ObservableList of the Parts Associated to the Product being modified.
     * It then fills in the Data Fields in the Form equal to the values of the Product Being Modified.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Initialize table
        ModifyProductAllParts.setItems(Inventory.getAllParts());
        ModifyProductAssociatedParts.setItems(thePass.getAllAssociatedParts());

        //Initialize table columns
        allPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductsPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductsPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductsPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductsPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Fill fields with Product's contents
        ModifyProductIDField.setText(String.valueOf(thePass.getId()));
        ModifyProductIDField.setDisable(true);
        ModifyProductNameField.setText(String.valueOf(thePass.getName()));
        ModifyProductPriceField.setText(String.valueOf(thePass.getPrice()));
        ModifyProductInvField.setText(String.valueOf(thePass.getStock()));
        ModifyProductMaxField.setText(String.valueOf(thePass.getMax()));
        ModifyProductMinField.setText(String.valueOf(thePass.getMin()));
    }

    /**
     * This Method exits the currently running page in the Inventory Application and Return to the Main Form.
     * It uses the FXML Loader to set the Stage with the new scene, thus not generating any additional application windows.
     * @param actionEvent User interface interaction that triggers the method.
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
     * This Method adds a selected Part from the All Parts list to the Modified Product's Associated Parts List.
     * If no Part is selected, it returns and no action occurs.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void ModifyProductAddPart(ActionEvent actionEvent) {
        Part SP = ModifyProductAllParts.getSelectionModel().getSelectedItem();
        if (SP == null) return;
        //Add code to add part to current product parts list
        thePass.addAssociatedPart(SP);
    }

    /**
     * This Method saves the Modified Product, exits the page and returns to the Main Form.
     * The function pulls the data from all the form fields, runs input validation functions to check for
     * blanks or invalid data input, displays error messages if so, or otherwise saves the Product to the Inventory
     * and sets the stage back to the Main Form.
     * RUNTIME ERROR: Initial implementation of the function lacked the input validation necessary to avoid error codes
     * during runtime. I ended up implementing exceptions to catch such errors, and instead of quitting the application it
     * would generate an error message and allow for correction of the inputs.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void saveModifiedProduct(ActionEvent actionEvent) throws IOException {
        String new_name;
        int new_inv;
        int new_max;
        double new_price;
        int new_min;
        String causeOfError = "";

        new_name = ModifyProductNameField.getText();
        if (new_name.isBlank()){
            InventoryApplication.displayError("Name must not be blank.");
            return;
        }
        try {
            causeOfError = "Inv";
            new_inv = Integer.parseInt(ModifyProductInvField.getText());
            causeOfError = "Price";
            new_price = Double.parseDouble(ModifyProductPriceField.getText());
            causeOfError = "Max";
            new_max = Integer.parseInt(ModifyProductMaxField.getText());
            causeOfError = "Min";
            new_min = Integer.parseInt(ModifyProductMinField.getText());

            if (InventoryApplication.checkRange(new_inv, new_max, new_min)) {
                return;
            }
            Product newProduct = new Product(thePass.getId(), new_name, new_price, new_inv, new_min, new_max);
            for (Part P : thePass.getAllAssociatedParts()){
                newProduct.addAssociatedPart(P);
            }
            Inventory.updateProduct(Inventory.allProducts.indexOf(thePass), newProduct);

        }
        catch (NumberFormatException badNumberInput) {
            InventoryApplication.displayError(causeOfError + " value must not be blank.");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("main-form.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that removes a Part from the Modified Product's ObservableList of Associated Parts.
     * This method removes the selected part from the table, or does nothing if not value is selected.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void rmProductPart(ActionEvent actionEvent) {
        Part SP = ModifyProductAssociatedParts.getSelectionModel().getSelectedItem();
        if (SP == null) return;
        if (InventoryApplication.deleteConfirmation()) {
            thePass.deleteAssociatedPart(SP);
        }
        else {
            return;
        }
    }

    /**
     * Method that receives the Product information from the Main Form.
     * This sets the ModifyProductController's internal Product value equal to
     * the product selected to be modified from the Inventory Application's Main Form.
     * @param product Button click that triggers the method
     */
    public static void productToModify(Product product) {
        thePass = product;
    }

    /**
     * Method that searches the list of All Parts for one matching the ID or containing the name passed in from User Input.
     * RUNTIME ERROR I initially experienced an error where typing in a string in the search bar and running the method resulted in
     * NumberFormatException errors, because the function tried to check for the ID first, and would get an error from trying to lookup the part,
     * which would yield the format error that would try to be assigned to the Part value. By wrapping this part in a try method, I was able to
     * overcome this error and achieve the ended functionality.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void searchParts(ActionEvent actionEvent) {
        String searchVal = allPartsSearch.getText();
        ObservableList<Part> searchResult = FXCollections.observableArrayList();

        if (Objects.equals(searchVal, "")) {
            ModifyProductAllParts.setItems(Inventory.allParts);
            return;
        }
        try {
            Part idSearch = Inventory.lookupPart(Integer.parseInt(searchVal));
            searchResult.add(Inventory.lookupPart(Integer.parseInt(searchVal)));
        } catch (Exception noInt){
            searchResult = Inventory.lookupPart(searchVal);
        }
        ModifyProductAllParts.setItems(searchResult);
    }
}
