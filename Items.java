import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Items
{
    // instance variables - replace the example below with your own
    private String description2;
    private HashMap<String, Items> Item;

    /**
     * Constructor for objects of class Item
     */
    public Items(String description)
    {
        this.description2 = description2;
        Item = new HashMap<String, Items>();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param name, what the name of the item is.
     * @param use, what the item is used for.
     */
    public void setItem(String name, Items use)
    {
        Item.put(name, use);
    }
    public String getShortDescription2()
    {
        return description2;
    }
    public String getLongDescription()
    {
        return "You are " + description2 + ".\n" + getItemsString();
    }
    private String getItemsString()
    {
        String returnString2 = "Item:";
        Set<String> key2 = Item.keySet();
        for(String item : key2) {
            returnString2 += " " + item;
        }
        return returnString2;
    }
    public Items getItems(String use) 
    {
        return Item.get(use);
    }
}
