module yoxthe.inventoryproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens yoxthe.inventoryproject to javafx.fxml;
    exports yoxthe.inventoryproject;
}