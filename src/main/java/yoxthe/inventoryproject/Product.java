package yoxthe.inventoryproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class to Initialize and create Public Methods for the Product type for the Inventory Application.
 */
public class Product {
    /**
     * Observable List of the Product's Associated Parts.
     */
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Product ID of Int Type
     */
    private int id;
    /**
     * Product Name of String Type
     */
    private String name;
    /**
     * Product Price of Double Type
     */
    private double price;
    /**
     * Product Stock of Int type.
     */
    private int stock;
    /**
     * Product Minimum Stock of Int Type
     */
    private int min;
    /**
     * Product Maximum Stock of Int Type
     */
    private int max;

    /**
     * Product Constructor.
     * @param id Product ID
     * @param name Product Name
     * @param price Product Price
     * @param stock Product Stock
     * @param min Product Minimum Stock
     * @param max Product Maximum Stock
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Change the Given Product's ID value.
     * @param id Product's New ID
     */
    public void setId(int id) { this.id = id;}

    /**
     * Return the given Product's ID number.
     * @return Product ID
     */
    public int getId() { return id; }

    /**
     * Change the Given Product's Name Value.
     * @param name Product's New Name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Return the given Product's Name Value.
     * @return Product Name
     */
    public String getName() { return name; }

    /**
     * Change the Given Product's Price Value.
     * @param price Product's New Price
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Return the Given Product's Price Value.
     * @return Product Price
     */
    public double getPrice() { return price; }

    /**
     * Change the Given Product's Stock Value.
     * @param stock Product's New Stock
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * Return the Given Product's Stock Value.
     * @return Product Stock
     */
    public int getStock() { return stock; }

    /**
     * Change the Given Product's Minimum Stock Value
     * @param min Product New Minimum Stock
     */
    public void setMin(int min) { this.min = min; }

    /**
     * Return the Given Product's Minimum Stock.
     * @return Product Minimum Stock
     */
    public int getMin() { return min; }

    /**
     * Change the Given Product's Maximum Stock Value.
     * @param max Product New Maximum Stock
     */
    public void setMax(int max) { this.max = max; }

    /**
     * Return the Given Product's Maximum Stock Value.
     * @return Product Maximum Stock
     */
    public int getMax() { return max; }

    /**
     * Add a Part to the Product's ObservableList of Associated Parts.
     * @param part New Associated Part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes the Associated Part from the Product's Observable List of Associated Parts.
     * @param part Part to be removed.
     * @return True/False Based on Whether the Part was removed.
     */
    public Boolean deleteAssociatedPart (Part part) {
        return associatedParts.remove(part);
    }

    /**
     * Returns the Observable List of the Given Product's Associated Parts
     * @return ObservableList of Associated Parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}

