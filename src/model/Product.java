
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class simulates a product
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class Product 
{   
   private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
   private int id;
   private String name;
   private double price;
   private int stock;
   private int min;
   private int max;

   /**
    * Constructs a new product
    * @param id the product ID number
    * @param name the name of the product
    * @param price the price of the product
    * @param stock the current inventory of the product
    * @param min the minimum allowable level of the product inventory
    * @param max the maximum allowable level of the product inventory
    */
    public Product(int id, String name, double price, int stock, int min, int max) 
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Accesses the list of parts that are associated with the product
     * @return the ObservableList of parts that are included with the product
     */
    public ObservableList<Part> getAssociatedParts() 
    {
        return associatedParts;
    }

    /**
     * Associates an ObservableList of parts with a product
     * @param associatedParts the list of parts to add to the product
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) 
    {
        this.associatedParts = associatedParts;
    }

    /**
     * Accesses the ID number of the product
     * @return the product ID number 
     */
    public int getId() 
    {
        return id;
    }

    /**
     * Changes the ID number of the product
     * @param id the product ID number
     */
    public void setId(int id) 
    {
        this.id = id;
    }
    
    /**
     * Accesses the name of the product
     * @return the name of the product
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * Changes the product name
     * @param name the new name of the product
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * Accesses the price of the product
     * @return the price of the product
     */
    public double getPrice() 
    {
        return price;
    }

    /**
     * Changes the price of the product
     * @param price the new price of the product
     */
    public void setPrice(double price) 
    {
        this.price = price;
    }

    /**
     * Accesses the number of currently available stock of the product
     * @return the available stock of the product
     */
    public int getStock() 
    {
        return stock;
    }

    /**
     * Changes the inventory level of the product
     * @param stock the new amount of stock for the product
     */
    public void setStock(int stock) 
    {
        this.stock = stock;
    }

    /**
     * Accesses the minimum acceptable inventory level for the product
     * @return the minimum acceptable inventory level for the product
     */
    public int getMin() 
    {
        return min;
    }

    /**
     * Changes the minimum acceptable inventory level for the product
     * @param min the minimum acceptable inventory level for the product
     */
    public void setMin(int min) 
    {
        this.min = min;
    }

    /**
     * Accesses the maximum acceptable inventory level for the product
     * @return the maximum acceptable inventory level for the product
     */
    public int getMax() 
    {
        return max;
    }

    /**
     * Changes the maximum acceptable inventory level for the product
     * @param max the maximum acceptable inventory level for the product
     */
    public void setMax(int max) 
    {
        this.max = max;
    }
   
    /**
     * Add a part to the list of associated parts for the product
     * @param part the part to add to the associated part list
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }
   
    /**
     * Removes a part from the list of associated parts for the product
     * @param selectedAssociatedPart the part to remove from the associated parts list
     * @return true if the part was successfully removed from the list
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart)
    {
        for (Part part : associatedParts)
        {
            if(part.getId() == selectedAssociatedPart.getId())
                return associatedParts.remove(part);
        }
        return false;
    }
   
   
}
