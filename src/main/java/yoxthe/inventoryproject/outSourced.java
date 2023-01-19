package yoxthe.inventoryproject;

/**
 * This class creates the OutSourced subclass of the Part superclass.
 */
public class outSourced extends Part {
    /**
     * String for the name of the Company that produces the OutSourced Part.
     */
    private String companyName;

    /**
     * Initializes the values of the OutSourced Part by calling Super class Part constructor.
     * @param id The OutSourced Part ID
     * @param name The OutSourced Part Name
     * @param price The OutSourced Part Price
     * @param stock The OutSourced Part Stock
     * @param min The OutSourced Part Minimum Stock
     * @param max The OutSourced Part Maximum Stock
     * @param companyName The OutSourced Part Company Name
     */
    public outSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the OutSourced Parts Manufacturing Company Name to the given String.
     * @param companyName The OutSourced Part Manufacturing Company Name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Returns the Company Name.
     */
    public String getCompanyName() {
        return companyName;
    }
}
