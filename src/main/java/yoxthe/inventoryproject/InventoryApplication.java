package yoxthe.inventoryproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class initiates the Inventory Application providing the initial initialization, and defining several
 * application-wide methods.
 * FUTURE ENHANCEMENT: Output functionality could be implemented that would allow for the saving of the Inventory State after
 * application closing and reopening, allowing it to be used to store an actual inventory. This would look like outputting data
 * to a txt or csv document, or even a SQL database then having code to read that input and populate the Inventory structures
 * on application startup.
 */
public class InventoryApplication extends Application {
    public static int partCount;
    public static int productCount;

    /**
     * This method starts the application.
     * It initializes the partCount and productCount values used throughout the program, adds some default values
     * to be displayed, and initializes the Main Form to be displayed as the first screen.
     * @param stage The default stage that gets set and passed on Application launch.
     */
    @Override
    public void start(Stage stage) throws IOException {
        partCount = Inventory.allParts.size();
        productCount = Inventory.allProducts.size();
        Inventory.addPart(new outSourced(partCount, "Brake", 15.99, 10, 1, 15, "Tractor Co"));
        Inventory.addProduct(new Product(productCount, "Bike", 299.99, 5, 1, 30));


        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("main-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();

        partCount = Inventory.allParts.size();
        productCount = Inventory.allProducts.size();

    }

    /**
     * This method checks the quantity values for the given Object and checks for valid inputs.
     * It checks if the max is less than the min value, the value is less than 0, and that the stock value is between
     * the max and min values, returning true if any check yields an error.
     * @param stock Stock int value.
     * @param max Max int value.
     * @param min Min int value
     * @return True or false if there are errors with the values passed into the method.
     */
    public static boolean checkRange(int stock, int max, int min){
        boolean check = (stock < min) || (stock > max);

        if (min > max) {
            displayError("Min value must be less than the max value.");
            return true;
        } else if( min < 0 ) {
            displayError("Min value must be greater than 0.");
            return true;
        }
        else if (check) {
            displayError("Stock value must be between Min and Max values.");
            return true;
        }
        return false;
    }

    /**
     * Method that displays an Alert Confirmation for Item Deletion.
     * Used throughout the program wherever Delete functionality was being implemented.
     * @return True/False based on the user response to the Confirmation Alert.
     */
    public static boolean deleteConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the item?", ButtonType.NO, ButtonType.YES);
        alert.showAndWait();
        return alert.getResult().equals(ButtonType.YES);
    }

    /**
     * Method that displays an error message to the user.
     * The Error Message displayed is passed in by the user at the function call.
     * @param errorMessage String passed by the user to be displayed on the Error Message.
     */
    public static void displayError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * This method launches the Inventory Application,
     */
    public static void main(String[] args) {
        launch(args);
    }
}