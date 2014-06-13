package net.azurewebsites.specialtopicfinal.app.UntilObjects;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.CartItem;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Category;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Hire;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Product;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mike on 08/05/2014.
 */
public class DataStore {
    public static int CURRENT_CART_COUNT;
    public static int CURRENT_CATEGORY_ID;
    public static String CURRENT_USER_ID = "mikeburton@live.com";
    public static Product CURRENT_PRODUCT = new Product();
    public static ArrayList<Category> ARRAYLIST_CURRENT_CATEGORIES = new ArrayList<Category>();
    public static ArrayList<Product> ARRAYLIST_CURRENT_PRODUCTS = new ArrayList<Product>();
    public static Hire HIRE_CURRENT_HIRE = new Hire();
    public static ArrayList<CartItem> ARRAYLIST_CURRENT_CARTIEMS = new ArrayList<CartItem>();
    public static CartItem CARTITEM_CURRENT = new CartItem();
    public static ArrayList<JSONObject> ARRAYLIST_CACHE_JSON_OBJECTS = new ArrayList<JSONObject>();

    public static void clearAll() {
        CURRENT_CART_COUNT = 0;
        CURRENT_CATEGORY_ID = 0;
        CURRENT_PRODUCT = new Product();
        ARRAYLIST_CURRENT_CATEGORIES.clear();
        ARRAYLIST_CURRENT_PRODUCTS.clear();
        HIRE_CURRENT_HIRE = new Hire();
        ARRAYLIST_CURRENT_CARTIEMS.clear();
        CARTITEM_CURRENT = new CartItem();
    }

}
