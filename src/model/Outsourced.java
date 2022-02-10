
package model;

/**
 * Outsourced Part class inherits from the abstract Part class and adds a Company 
 * Name string field and methods to set and retrieve the name.
 * 
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class Outsourced extends Part
{
    private String companyName;
    
    /**
     * Class constructor initializes an Outsourced part. 
     * This constructor calls the constructor for the superclass Part and adds 
     * the name of the company that supplied the part. 
     * 
     * @param id The part ID
     * @param name The part name
     * @param price The price of the part
     * @param stock The current inventory amount for the part
     * @param min The minimum level of inventory for the part
     * @param max The maximum level of inventory for the part
     * @param companyName The name of the company that supplied the part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max,String companyName) 
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
/**
 * Retrieves the name of the company that supplied the part
 * @return companyName the name of the company that supplied the part 
 */
    public String getCompanyName() 
    {
        return companyName;
    }
/**
 * Changes the value held in the company name field
 * @param companyName the new value for the company name. Replaces the old value.
 */
    public void setCompanyName(String companyName) 
    {
        this.companyName = companyName;
    }   
}
