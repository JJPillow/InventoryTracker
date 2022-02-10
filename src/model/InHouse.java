
package model;

/**
 * In-house Part class inherits from the abstract Part class and adds a machine 
 * ID integer field and methods to set and retrieve the machine ID.
 * 
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class InHouse extends Part
{
    
    private int machineId;
    
    /**
     * Class constructor initializes an In-House part. 
     * This constructor calls the constructor for the superclass Part and adds the machine ID number. 
     * 
     * @param id The part ID
     * @param name The part name
     * @param price The price of the part
     * @param stock The current inventory amount for the part
     * @param min The minimum level of inventory for the part
     * @param max The maximum level of inventory for the part
     * @param machineId The machine ID number for the In-house part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) 
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    
    /**
     * Accesses the machine ID number for this In-House Part.
     * 
     * @return machineId Returns the Machine ID for the In-house part.
     */
    public int getMachineId() 
    {
        return machineId;
    }

    /**
     * Changes the Machine ID value.
     * 
     * @param machineId the new value for the Machine ID.  Replaces the previous value.
     */
    public void setMachineId(int machineId) 
    {
        this.machineId = machineId;
    }
}
