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
 * This class provides the functioning methods for the Add Product Form of the Inventory Application.
 */
public class AddProductController implements Initializable {
    public Button AddProductCancelBtn;
    public TextField AddProductIDField;
    public TextField AddProductNameField;
    public TextField AddProductInvField;
    public TextField AddProductPriceField;
    public TextField AddProductMaxField;
    public TextField AddProductMinField;
    public Button AddProductSaveBtn;
    public Button AddProductAddPart;
    public TableColumn<Inventory, Part> allPartsID;
    public TableColumn<Inventory, Part> allPartsName;
    public TableColumn<Inventory, Part> allPartsInv;
    public TableColumn<Inventory, Part> allPartsPrice;
    public TableView<Part> ProductsPartstbl;
    public TableColumn<Inventory, Part> ProductsPartID;
    public TableColumn<Inventory, Part> ProductsPartName;
    public TableColumn<Inventory, Part> ProductsPartInv;
    public TableColumn<Inventory, Part> ProductsPartPrice;
    public Button rmAssociatedPart;
    public TableView <Part> allPartstbl;
    private final Product new_Product = new Product(InventoryApplication.productCount, "", 0.00, 0, 0, 0);
    public TextField allPartSearch;

    /**
     * This method initializes the Add Product Page as it's opened.
     * The method sets the Page's tables equal to the ObservableList of All Parts in the Inventory,
     * and the ObservableList of the Parts Associated to the Product being added.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProductsPartstbl.setItems(new_Product.getAllAssociatedParts());
        allPartstbl.setItems(Inventory.getAllParts());

        allPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductsPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductsPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

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
     * This Method adds a selected Part from the All Parts list to the new Product's Associated Parts List.
     * If no Part is selected, it returns and no action occurs.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void addPart(ActionEvent actionEvent) {
        Part SP = allPartstbl.getSelectionModel().getSelectedItem();
        if (SP == null) return;
        //Add code to add part to current product parts list
        new_Product.addAssociatedPart(SP);
    }

    /**
     * Method that removes a Part from the Added Product's ObservableList of Associated Parts.
     * This method removes the selected part from the table, or does nothing if not value is selected.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void rmAssociatedPart(ActionEvent actionEvent) {
        Part SP = ProductsPartstbl.getSelectionModel().getSelectedItem();
        if (SP == null) return;
        if (InventoryApplication.deleteConfirmation()) {
            new_Product.deleteAssociatedPart(SP);
        }
    }

    /**
     * This Method saves the new Product, exits the page and returns to the Main Form.
     * The function pulls the data from all the form fields, runs input validation functions to check for
     * blanks or invalid data input, displays error messages if so, or otherwise saves the Product to the Inventory
     * and sets the stage back to the Main Form.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        String new_name;
        int new_inv;
        int new_max;
        double new_price;
        int new_min;
        String causeOfError = "";

        new_name = AddProductNameField.getText();
        if (new_name.isBlank()){
            InventoryApplication.displayError("Name must not be blank.");
            return;
        }
        try {
            causeOfError = "Inv";
            new_inv = Integer.parseInt(AddProductInvField.getText());
            causeOfError = "Price";
            new_price = Double.parseDouble(AddProductPriceField.getText());
            causeOfError = "Max";
            new_max = Integer.parseInt(AddProductMaxField.getText());
            causeOfError = "Min";
            new_min = Integer.parseInt(AddProductMinField.getText());
            if (InventoryApplication.checkRange(new_inv, new_max, new_min)) {
                return;
            }
        }
        catch (NumberFormatException badNumberInput) {
            InventoryApplication.displayError(causeOfError + " value must be a number.");
            return;
        }

        new_Product.setId(InventoryApplication.productCount);
        new_Product.setName(new_name);
        new_Product.setPrice(new_price);
        new_Product.setMax(new_max);
        new_Product.setMin(new_min);
        new_Product.setStock(new_inv);

        Inventory.addProduct(new_Product);

        InventoryApplication.productCount++;

        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("main-form.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that searches the list of All Parts for one matching the ID or containing the name passed in from User Input.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void searchParts(ActionEvent actionEvent) {
        String searchVal = allPartSearch.getText();
        ObservableList<Part> searchResult = FXCollections.observableArrayList();

        if (Objects.equals(searchVal, "")) {
            allPartstbl.setItems(Inventory.allParts);
            return;
        }
        try {
            searchResult.add(Inventory.lookupPart(Integer.parseInt(searchVal)));
        } catch (Exception noInt){
            searchResult = Inventory.lookupPart(searchVal);
        }
        allPartstbl.setItems(searchResult);

    }
}
