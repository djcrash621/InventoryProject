package yoxthe.inventoryproject;

/**
 * This class creates the InHouse subclass of the Part superclass.
 */
public class inHouse extends Part {
    /**
     * Int for the value of the Machine used in House for the Part.
     */
    private int machineId;

    /**
     * Initializes the values of the InHouse Part by calling Super class Part constructor.
     * @param id The InHouse Part ID
     * @param name The InHouse Part Name
     * @param price The InHouse Part Price
     * @param stock The InHouse Part Stock
     * @param min The InHouse Part Minimum Stock
     * @param max The InHouse Part Maximum Stock
     * @param machineId The InHouse Part Machine ID
     */
    public inHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Sets the InHouse Parts Machine ID to the given ID.
     * @param machineId The InHouse Part Machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Returns the Machine ID for the given Part.
     */
    public int getMachineId() {
        return machineId;
    }

}
