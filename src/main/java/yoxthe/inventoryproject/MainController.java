package yoxthe.inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class that supports the functions for the Main Form page of the Inventory Application
 */
public class MainController implements Initializable {

    public Label TheLabel;
    public TableView<Part> tblPartMain;
    public TableColumn<Inventory,Part> allPartsID;
    public TableColumn<Inventory, Part> allPartsName;
    public TableColumn<Inventory, Part> allPartsLevel;
    public TableColumn<Inventory, Part> allPartsPrice;
    public TableView<Product> tblProductMain;
    public TableColumn<Inventory, Product> allProductsID;
    public TableColumn<Inventory, Product> allProductsName;
    public TableColumn<Inventory, Product> allProductsLevel;
    public TableColumn<Inventory, Product> allProductsPrice;
    public Label lblInventoryMain;
    public Label lblProducts;
    public Button btnAppExit;
    public Button btnModifyPartMain;
    public Button btnDeletePartMain;
    public Button btnAddPartMain;
    public Button btnModifyProductMain;
    public Button btnDeleteProductMain;
    public Button btnAddProductMain;
    public Label lblParts;
    public TextField mainPartSearch;
    public TextField mainProductSearch;

    /**
     * Method that initializes the Main Form page upon it's opening.
     * It populates the Table Views with the All Parts and All Products Observable Lists
     * of the Inventory class, and sets the cells to handle the ID, NAME, PRICE, AND STOCK values.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tblPartMain.setItems(Inventory.getAllParts());
        tblProductMain.setItems(Inventory.getAllProducts());
        allPartsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        allProductsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        allProductsName.setCellValueFactory(new PropertyValueFactory<>("name"));
        allProductsLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allProductsPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Method that Initializes the Add Part Controller and sets it on the Application Stage.
     * Using the FXML loader, the Method closes the Main Form Scene and sets the stage with Add Part Scene,
     * thereby opening no new windows of the application.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void AddPartMain(ActionEvent actionEvent) throws IOException {
        //Initialize new Controller
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("add-part.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This Method closes the Inventory Application.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void ExitAppMain(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Method that deletes the selected Part in the All Parts table.
     * If a part is selected, the part is deleted. If a Part is not selected, the
     * Method returns without action.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void DeletePartMain(ActionEvent actionEvent) {
        Part SP = tblPartMain.getSelectionModel().getSelectedItem();
        if (SP == null) return;
        if (InventoryApplication.deleteConfirmation()) {
            Inventory.deletePart(SP);
        }
    }

    /**
     * Method that opens the Modify Part page.
     * If a Part is selected, the Method sets the stage to the Modify Part scene,
     * and if no part is selected, the Method returns without action.
     * @param actionEvent User interface interaction that triggers the Method.
     */
    public void ModifyPartMain(ActionEvent actionEvent) throws IOException {
        Part SP = tblPartMain.getSelectionModel().getSelectedItem();
        if (SP == null) return;
        ModifyPartController.partToModify(SP);
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("modify-part.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Modify Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that opens the Modify Product Page.
     * If a Product is selected, the Method sets the stage to the Modify Product scene,
     * and if no Product is selected, the Method returns without action.
     * @param actionEvent User interface interaction that triggers the Method.
     */
    public void ModifyProductMain(ActionEvent actionEvent) throws IOException {
        Product SP = tblProductMain.getSelectionModel().getSelectedItem();
        if (SP == null) return;
        ModifyProductController.productToModify(SP);
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("modify-product.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Modify Product Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that deletes a selected Product.
     * If a product is selected, it generates a confirmation alert, and deletes if the confirmation is given.
     * If the confirmation is not given, or no Product is selected, the Method returns without action.
     * @param actionEvent User interface interaction that triggers the Method.
     */
    public void DeleteProductMain(ActionEvent actionEvent) {
        Product SP = tblProductMain.getSelectionModel().getSelectedItem();
        if (SP == null) return;
        if (SP.getAllAssociatedParts().size() > 0) {
            InventoryApplication.displayError("You must not delete Products with Associated Parts. Associated Parts: " + SP.getAllAssociatedParts().size());
            return;
        }
        if (InventoryApplication.deleteConfirmation()) {
            Inventory.deleteProduct(SP);
        }

    }

    /**
     * Method that Initializes the Add Product Page.
     * Sets the running stage to the Add Product scene, closing the Main Form scene
     * in doing so.
     * @param actionEvent User interface interaction that triggers the method.
     */
    public void AddProductMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("add-product.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Add Product Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that searches the All Parts list.
     * Searches the list for the Part with matching ID value or name to the value
     * inputted in the search field.
     * @param actionEvent User interface interaction that triggers the Method.
     */
    public void searchParts(ActionEvent actionEvent) {
        String searchVal = mainPartSearch.getText();
        ObservableList<Part> searchResult = FXCollections.observableArrayList();

        boolean check = false;
        if (Objects.equals(searchVal, "")) {
            tblPartMain.setItems(Inventory.allParts);
            return;
        }
        try {
            searchResult.add(Inventory.lookupPart(Integer.parseInt(searchVal)));

        } catch (Exception noInt){
            searchResult = Inventory.lookupPart(searchVal);
        }
        tblPartMain.setItems(searchResult);

    }

    /**
     * Method that searches the all Products list.
     * Searches the list for the Product with matching ID value or name to the value
     * inputted in the search field.
     * @param actionEvent User interface interaction that triggers the Method.
     */
    public void searchProducts(ActionEvent actionEvent) {
        String searchVal = mainProductSearch.getText();
        ObservableList<Product> searchResult = FXCollections.observableArrayList();

        if (Objects.equals(searchVal, "")) {
            tblProductMain.setItems(Inventory.allProducts);
            return;
        }
        try {
            searchResult.add(Inventory.lookupProduct(Integer.parseInt(searchVal)));
        } catch (Exception noInt){
            searchResult = Inventory.lookupProduct(searchVal);
        }
        tblProductMain.setItems(searchResult);

    }
}