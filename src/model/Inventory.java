
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The inventory class stores and accesses the inventory of parts and products.
 * 
 * @author Jennifer Pillow jpillo2@wgu.edu
 */
public class Inventory 
{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
    
    /**
     * Places a part into the inventory
     * 
     * @param part the part to add to the inventory
     */
    public static void addPart(Part part)
    {
        allParts.add(part);
    }
    
    /**
     * Places a product into the inventory
     * @param product the product to add to the inventory
     */
    public static void addProduct(Product product)
    {
        allProducts.add(product);
    }
    
    /**
     * Searches the parts inventory by part ID
     * 
     * @param partId Id of the part being searched for
     * @return the part with the matching part ID number.  Returns null if no 
     * match found.
     */
    public static Part lookupPart(int partId)
    {
        Part result = null;
        for(Part part : allParts)
        {
            if (part.getId() == partId)
                result = part;
        }
        return result;
    }
    
    /**
     * Searches the part inventory by name
     * 
     * @param partName the name of the part being searched for
     * @return a list of parts with the matching strings in the part name field.  
     * Returns the entire part list if a match wasn't found
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        if(!(filteredParts.isEmpty()))
            filteredParts.clear();
        for(Part part : allParts)
        {
            if (part.getName().contains(partName) )
                filteredParts.add(part);
        }
            return filteredParts;
    }
    
    /**
     * Searches the product inventory by Product ID
     * @param productId the id of the product being searched for
     * @return the product with the matching product ID number.  Returns null if no 
     * match found.
     */
    public static Product lookupProduct(int productId)
    {
        Product result = null;
        for(Product product : allProducts)
        {
            if (product.getId() == productId)
                result = product;
        }
        return result;
    }
    
    /**
     * Searches the product inventory by name
     * @param productName string to search for in product name field
     * @return a list of products with the matching strings in the product name field.  
     * Returns the entire product list if a match wasn't found
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        if(!(filteredProducts.isEmpty()))
            filteredProducts.clear();
        for(Product product : allProducts)
        {
            if (product.getName().contains(productName) )
                filteredProducts.add(product);
        }
            return filteredProducts;
    }
    
    /**
     * Replaces a part in the inventory 
     * @param index the index of the part in the ObservableList
     * @param selectedPart a part holding the updated values
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }
    
    /**
     * Replaces a product in the inventory
     * @param index the index of the product in the ObservableList
     * @param newProduct a product holding the updated values
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }
    
    /**
     * Removes a part from the inventory
     * @param selectedPart The part to be removed
     * @return true if part successfully removed
     */
    public static boolean deletePart(Part selectedPart)
    {
        
        for (Part part : allParts)
        {
            if(part.getId() == selectedPart.getId())
                return allParts.remove(part);
        }
        return false;
    }
    
    /**
     * Removes a product from the inventory
     * @param selectedProduct the product to be removed
     * @return true if product successfully removed
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        for (Product product : allProducts)
        {
            if(product.getId() == selectedProduct.getId())
                return allProducts.remove(product);
        }
        return false;
    }
    
    /**
     * Accesses the list of all parts in the inventory
     * @return an ObservableList of all parts in inventory 
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }
    
    /**
     * Accesses the list of all products in the inventory
     * @return an ObservableList of all products in inventory
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

}
