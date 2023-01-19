package yoxthe.inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that Initializes the Inventory Application's Inventory of Parts and Products.
 */
public class Inventory {
    /**
     * Observable List of the Inventory's Parts.
     */
    static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Observable List of the Inventory's Products.
     */
    static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new Part to the Inventory's allParts ObservableList.
     * @param part Part to be added.
     */
    static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Adds a new Product to the Inventory's allProducts ObservableList.
     * @param product Product to be added.
     */
    static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * Searches the Inventory for the Part with the corresponding ID.
     * @param partId The Part ID of the Part to Look Up.
     * @return The Part that was looked up, else NULL.
     */
    static Part lookupPart(int partId) {
        for (Part a : allParts) {
            if (partId == a.getId()) {
                return a;
            }
        }
        InventoryApplication.displayError("Part with ID " + partId + " not found.");
        return null;
    }

    /**
     * Searches the Inventory for the Product with the corresponding ID.
     * @param productId The Product ID of the Product to look up.
     * @return The Product that was looked up, else NULL.
     */
    static Product lookupProduct(int productId) {
        for (Product a : allProducts) {
            if (productId == a.getId()) {
                return a;
            }
        }
        InventoryApplication.displayError("Product with ID " + productId + " not found.");
        return null;
    }

    /**
     * Searches the Inventory for the Part with a Name containing the given string.
     * @param partName The Part Name of the Part to look up.
     * @return ObservableList of Parts with names containing the given string.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        for (Part search : allParts) {
            if (search.getName().contains(partName)) {
                searchedParts.add(search);
            }
        }
        return searchedParts;
    }

    /**
     * Searches the Inventory for the Product with a Name containing the given string.
     * @param productName The Part Name of the Product to look up.
     * @return ObservableList of Products with names containing the given string.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        for (Product search : allProducts) {
            if (search.getName().contains(productName)) {
                searchedProducts.add(search);
            }
        }
        return searchedProducts;
    }

    /**
     * Updates the Part with the given index to the new given Part.
     * @param index Index of the Part to be Updated.
     * @param selectPart Part to replace the Part being updated.
     */
    public static void updatePart(int index, Part selectPart) {
        allParts.set(index, selectPart);
    }

    /**
     * Updates the Product with the given index to the new given Product.
     * @param index Index of the Product being updated.
     * @param newProduct Product to replace the Product being updated.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes the given Part from the Inventory.
     * @param selectedPart The Part to be deleted from the Inventory.
     * @return True / False that the Item was deleted.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes the given Product from the Inventory.
     * @param selectedProduct The Product to be deleted from the Inventory.
     * @return True / False that the Item was deleted.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Returns all Parts in the Inventory.
     * @return Observable List of all the Inventory's Parts.
     */
   public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Returns all Products in the Inventory.
     * @return Observable List of all the Inventory's Products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }





}

