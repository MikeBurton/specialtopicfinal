package net.azurewebsites.specialtopicfinal.app.UntilObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.CartItem;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Category;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Hire;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.HireDetail;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Image;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Mike on 15/04/2014.
 */

public class WebService {
    private static String WEB_SERVICE_URL = "http://producthireservicest.cloudapp.net/ProductHireService.svc";
    private static String WEB_SERVICE_ACCESS_KEY = "o13<QMDXqCThQfYoZaw[4zJ_,";
    Fragment CURRENT_FRAGMENT;
    AsyncTask CURRENT_ASYNC_TASK;
    int TIMEOUT;
    WebServiceEvents WEB_SERVICE_EVENT;
    ArrayList<WebServiceEvents> WEB_SERVICE_EVENT_MEMBERS;
    boolean USE_ALL_MEMBERS = false;

    /**
     * Constructor
     * @param WEB_SERVICE_EVENT
     * @param TIMEOUT
     * @param USE_ALL_MEMBERS
     */
    public WebService(WebServiceEvents WEB_SERVICE_EVENT, int TIMEOUT, boolean USE_ALL_MEMBERS) {
        this.TIMEOUT = TIMEOUT;
        this.WEB_SERVICE_EVENT = WEB_SERVICE_EVENT;
        WEB_SERVICE_EVENT_MEMBERS = new ArrayList<WebServiceEvents>();
        WEB_SERVICE_EVENT_MEMBERS.add(WEB_SERVICE_EVENT);
        this.USE_ALL_MEMBERS = USE_ALL_MEMBERS;
    }

    /**
     * Gets CURRENT_ASYNC_TASK
     * @return AsyncTask
     */
    public AsyncTask getCurrentAsyncTask() {
        return CURRENT_ASYNC_TASK;
    }


    public void setCURRENT_FRAGMENT(Fragment CURRENT_FRAGMENT) {
        this.CURRENT_FRAGMENT = CURRENT_FRAGMENT;
    }
    public void setTIMEOUT(int TIMEOUT) {
        this.TIMEOUT = TIMEOUT;
    }

    /**
     * gets all WEB_SERVICE_EVENT_MEMBERS
     * @return  ArrayList<WebServiceEvents>
     */
    public ArrayList<WebServiceEvents> getWEB_SERVICE_EVENT_MEMBERS() {
        return WEB_SERVICE_EVENT_MEMBERS;
    }

    /**
     * Either contacts all members or just the main member
     * @param CONTACT_TYPE
     * @param WEB_SERVICE_RESULT
     * @param WEB_SERVICE_METHOD_NAME
     * @param WEB_SERVICE_EXCEPTION
     */
    private void contactWebServiceEventMembers(int CONTACT_TYPE, String WEB_SERVICE_RESULT, String WEB_SERVICE_METHOD_NAME, Exception WEB_SERVICE_EXCEPTION) {
        if (USE_ALL_MEMBERS) {
            contactAllMembers(CONTACT_TYPE, WEB_SERVICE_RESULT, WEB_SERVICE_METHOD_NAME, WEB_SERVICE_EXCEPTION);
        } else {
            contactMainMember(CONTACT_TYPE, WEB_SERVICE_RESULT, WEB_SERVICE_METHOD_NAME, WEB_SERVICE_EXCEPTION);
        }

    }

    /**
     * Contacts all WebServiceEventMembers
     * @param CONTACT_TYPE
     * @param WEB_SERVICE_RESULT
     * @param WEB_SERVICE_METHOD_NAME
     * @param WEB_SERVICE_EXCEPTION
     */
    private void contactAllMembers(int CONTACT_TYPE, String WEB_SERVICE_RESULT, String WEB_SERVICE_METHOD_NAME, Exception WEB_SERVICE_EXCEPTION) {
        for (WebServiceEvents events : WEB_SERVICE_EVENT_MEMBERS) {

            switch (CONTACT_TYPE) {
                case 1:
                    events.WebServiceStartedRequest();
                    break;
                case 2:
                    events.WebServiceFinished(WEB_SERVICE_METHOD_NAME, WEB_SERVICE_RESULT);
                    break;
                case 3:
                    events.WebServiceFinishedWithException(WEB_SERVICE_EXCEPTION);
                    break;
                case 4:
                    events.WebServiceEndRequest();
                    break;

            }

        }
    }

    /**
     * Contacts WEB_SERVICE_EVENT MainMember only
     * @param CONTACT_TYPE
     * @param WEB_SERVICE_RESULT
     * @param WEB_SERVICE_METHOD_NAME
     * @param WEB_SERVICE_EXCEPTION
     */
    private void contactMainMember(int CONTACT_TYPE, String WEB_SERVICE_RESULT, String WEB_SERVICE_METHOD_NAME, Exception WEB_SERVICE_EXCEPTION) {
        switch (CONTACT_TYPE) {
            case 1:
                WEB_SERVICE_EVENT.WebServiceStartedRequest();
                break;
            case 2:
                WEB_SERVICE_EVENT.WebServiceFinished(WEB_SERVICE_METHOD_NAME, WEB_SERVICE_RESULT);
                break;
            case 3:
                WEB_SERVICE_EVENT.WebServiceFinishedWithException(WEB_SERVICE_EXCEPTION);
                break;
            case 4:
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                break;

        }
    }

    /**
     * Returns hashed WEB_SERVICE_ACCESS_KEY, Uses SafeSecurity
     * @return hashed WEB_SERVICE_ACCESS_KEY
     * @throws Exception
     */
    private String getWebServiceAccessCode() throws Exception {
        return SafeSecurity.createHash(WEB_SERVICE_ACCESS_KEY);
    }
    private String webUserByEmail(String EMAIL)throws Exception
    {
        String USER_BY_EMAIL_RESULT;
        //encode WEB_SERVICE_ACCESS_KEY
        String ECODE_URL = URLEncoder.encode(getWebServiceAccessCode(), "UTF-8");
        //encode email
        EMAIL =URLEncoder.encode(EMAIL,"UTF-8");
        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/GetUserByEmail/"+EMAIL+"?&key=" + ECODE_URL);
        //open connection to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //set TIMEOUT
        URL_CONNECTION.setConnectTimeout(TIMEOUT);
        //read webservice response
        BufferedReader BUFFERED_READER_RESPONSE = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        //read WEBSERVICE_RESULT to string
        String RESPONSE_STRING = BUFFERED_READER_RESPONSE.readLine();
        //create connection object to store WEBSERVICE_RESULT
        JSONObject JSON_OBJECT_RESPONSE = new JSONObject(RESPONSE_STRING);

        BUFFERED_READER_RESPONSE.close();

        USER_BY_EMAIL_RESULT = "Success!";
        return  USER_BY_EMAIL_RESULT;

    }
    private String webLoginRequest(String EMAIL, String PASSWORD) throws Exception {
        String LOGIN_REQUEST_RESULT;
        //hash login details
        PASSWORD = SafeSecurity.encrypt(PASSWORD);
        //make JSON_OBJECT format
        String JSON_REQUEST = "{'Email':'" + EMAIL + "','Password':'" + PASSWORD + "'}";
        //convert string to a jsonObject
        JSONObject JSON_OBJECT = new JSONObject(JSON_REQUEST);
        String REQUEST_STRING_TO_ENCRYPT;
        //convert JSON_OBJECT object back to a string for encryption
        REQUEST_STRING_TO_ENCRYPT = JSON_OBJECT.toString();
        //encrypt request
        String ENCRYPTED_REQUEST = SafeSecurity.encrypt(REQUEST_STRING_TO_ENCRYPT);
        //encode string to be WEB_SERVICE_URL safe (UTF-8)
        String ENCODED_URL_STRING_UTF8 = URLEncoder.encode(ENCRYPTED_REQUEST, "UTF-8");
        //encode WEB_SERVICE_ACCESS_KEY
        String ENCODE_ACCESS_KEY_UTF8 = URLEncoder.encode(getWebServiceAccessCode(), "UTF-8");
        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/Login?userData=" + ENCODED_URL_STRING_UTF8 + "&key=" + ENCODE_ACCESS_KEY_UTF8);
        //open URL to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //set TIMEOUT
        URL_CONNECTION.setConnectTimeout(TIMEOUT);
        //read webservice response
        BufferedReader BUFFERED_READER_FOR_RESPONSE = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        //read WEBSERVICE_RESULT to string
        String RESPONSE_STRING = BUFFERED_READER_FOR_RESPONSE.readLine();
        //create JSON_OBJECT object to store WEBSERVICE_RESULT
        JSONObject JSON_OBJECT_RESPONSE = new JSONObject(RESPONSE_STRING);
        String RESPONSE_TO_DECRYPT = JSON_OBJECT_RESPONSE.getString("LoginResult");
        //decrypt response
        LOGIN_REQUEST_RESULT = SafeSecurity.decrypt(RESPONSE_TO_DECRYPT);
        //close BUFFERED_READER_FOR_RESPONSE
        BUFFERED_READER_FOR_RESPONSE.close();

        return LOGIN_REQUEST_RESULT;
    }

    /**
     * Gets all categories
     * @return success! string
     * @throws Exception
     */
    private String webCategories() throws Exception {
        String CATEGORY_RESULT;
        //encode WEB_SERVICE_ACCESS_KEY
        String ECODE_URL = URLEncoder.encode(getWebServiceAccessCode(), "UTF-8");
        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/GetCategories?&key=" + ECODE_URL);
        //open connection to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //set TIMEOUT
        URL_CONNECTION.setConnectTimeout(TIMEOUT);
        //read webservice response
        BufferedReader BUFFERED_READER_RESPONSE = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        //read WEBSERVICE_RESULT to string
        String RESPONSE_STRING = BUFFERED_READER_RESPONSE.readLine();
        //create connection object to store WEBSERVICE_RESULT
        JSONObject JSON_OBJECT_RESPONSE = new JSONObject(RESPONSE_STRING);
        //convert connection object into connection array
        JSONArray JSON_ARRAY_CATEGORY_RESULT = JSON_OBJECT_RESPONSE.getJSONArray("GetCategoriesResult");
        //loop through connection array
        for (int i = 0; i < JSON_ARRAY_CATEGORY_RESULT.length(); i++) {
            //parse into java objects here
            JSONObject JSON_OBJECT_CATEGORY = (JSONObject) JSON_ARRAY_CATEGORY_RESULT.get(i);
            Category CATEGORY = new Category(JSON_OBJECT_CATEGORY.getInt("CategoryID"), JSON_OBJECT_CATEGORY.getString("Description"), JSON_OBJECT_CATEGORY.getString("Name"));
            DataStore.ARRAYLIST_CURRENT_CATEGORIES.add(CATEGORY);
        }
        CATEGORY_RESULT = "Success!";
        BUFFERED_READER_RESPONSE.close();
        return CATEGORY_RESULT;
    }

    /**
     * Gets product by id
     * @param PRODUCT_ID
     * @return success! string
     * @throws Exception
     */
    private String webProductByID(int PRODUCT_ID) throws Exception {
        String PRODUCT_BY_ID_RESULT;
        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/GetProductByID/" + "" + PRODUCT_ID);
        //open connection to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //read webservice response
        BufferedReader BUFFERED_READER_RESPONSE = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        //read WEBSERVICE_RESULT to string
        String line = BUFFERED_READER_RESPONSE.readLine();
        //create URL object to store WEBSERVICE_RESULT
        JSONObject JSON_OBJECT_RESPONSE = new JSONObject(line);
        //load JSON_OBJECT_RESPONSE into a JSON object, to access JSON_ARRAY_IMAGES array (Couldn't access it in JSON_OBJECT_RESPONSE,simple solution was what I came up with below making a new JSON object by using JSON_OBJECT_RESPONSE.getJSONObject )
        JSONObject JSON_OBJECT_RESULT = JSON_OBJECT_RESPONSE.getJSONObject("GetProductByIDResult");
        //loop through JSON_ARRAY_IMAGES array
        JSONArray JSON_ARRAY_IMAGES = JSON_OBJECT_RESULT.getJSONArray("Images");
        ArrayList<Image> myImages = new ArrayList<Image>();
        for (int in = 0; in < JSON_ARRAY_IMAGES.length(); in++) {
            JSONObject JSON_OBJECT_IMAGES = (JSONObject) JSON_ARRAY_IMAGES.get(in);
            Image IMAGE = new Image(JSON_OBJECT_IMAGES.getString("ImageID"), JSON_OBJECT_IMAGES.getBoolean("IsDefault"), JSON_OBJECT_IMAGES.getInt("ProductID"));
            myImages.add(IMAGE);
        }
        int PARENT_PRODUCT_ID;
        if (JSON_OBJECT_RESULT.isNull("ParentProductID") == true) {
            PARENT_PRODUCT_ID = 0;
        } else {
            PARENT_PRODUCT_ID = JSON_OBJECT_RESULT.getInt("ParentProductID");
        }
        Product PRODUCT = new Product(JSON_OBJECT_RESULT.getInt("ProductID"), JSON_OBJECT_RESULT.getInt("CategoryID"), JSON_OBJECT_RESULT.getString("Description"), JSON_OBJECT_RESULT.getBoolean("IsCurrent"), JSON_OBJECT_RESULT.getString("Name"), PARENT_PRODUCT_ID, JSON_OBJECT_RESULT.getDouble("Price"), JSON_OBJECT_RESULT.getInt("StockCount"), myImages);
        DataStore.ARRAYLIST_CURRENT_PRODUCTS.add(PRODUCT);
        PRODUCT_BY_ID_RESULT = "Success!";
        BUFFERED_READER_RESPONSE.close();
        return PRODUCT_BY_ID_RESULT;
    }

    /**
     * Get Products by SearchString
     * @param SEARCH_STRING
     * @return success! string
     * @throws Exception
     */
    private String webProductsBySearchString(String SEARCH_STRING) throws Exception {
        String PRODUCTS_BY_SEARCH_STRING_RESULT;
        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/GetProductsBySearchString/" + "" + SEARCH_STRING);
        //open connection to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //read webservice response
        BufferedReader BUFFERED_READER_RESPONSE = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        String RESPONSE_STRING = BUFFERED_READER_RESPONSE.readLine();
        //create JSON object to store WEBSERVICE_RESULT
        JSONObject JSON_OBJECT_RESPONSE = new JSONObject(RESPONSE_STRING);
        //convert JSON object into JSON array
        JSONArray JSON_ARRAY_RESPONSE = JSON_OBJECT_RESPONSE.getJSONArray("GetProductsBySearchStringResult");
        //loop through  array
        for (int i = 0; i < JSON_ARRAY_RESPONSE.length(); i++) {
            JSONObject JSON_OBJECT = (JSONObject) JSON_ARRAY_RESPONSE.get(i);
            JSONArray JSON_ARRAY_IMAGES = JSON_OBJECT.getJSONArray("Images");
            ArrayList<Image> ARRAYLIST_IMAGES = new ArrayList<Image>();
            for (int in = 0; in < JSON_ARRAY_IMAGES.length(); in++) {
                JSONObject JSON_OBJECT_IMAGE = (JSONObject) JSON_ARRAY_IMAGES.get(in);
                Image IMAGE = new Image(JSON_OBJECT_IMAGE.getString("ImageID"), JSON_OBJECT_IMAGE.getBoolean("IsDefault"), JSON_OBJECT_IMAGE.getInt("ProductID"));
                ARRAYLIST_IMAGES.add(IMAGE);
            }
            int PARENT_PRODUCT_ID;
            if (JSON_OBJECT.isNull("ParentProductID") == true) {
                PARENT_PRODUCT_ID = 0;
            } else {
                PARENT_PRODUCT_ID = JSON_OBJECT.getInt("ParentProductID");
            }

            Product PRODUCT = new Product(JSON_OBJECT.getInt("ProductID"), JSON_OBJECT.getInt("CategoryID"), JSON_OBJECT.getString("Description"), JSON_OBJECT.getBoolean("IsCurrent"), JSON_OBJECT.getString("Name"), PARENT_PRODUCT_ID, JSON_OBJECT.getDouble("Price"), JSON_OBJECT.getInt("StockCount"), ARRAYLIST_IMAGES);
            DataStore.ARRAYLIST_CURRENT_PRODUCTS.add(PRODUCT);
        }
        PRODUCTS_BY_SEARCH_STRING_RESULT = "Success!";
        BUFFERED_READER_RESPONSE.close();
        return PRODUCTS_BY_SEARCH_STRING_RESULT;
    }

    /**
     * Gets products by category id
     * @param CATEGORY_ID
     * @return success! string
     * @throws Exception
     */
    private String webProductsByCategoryID(int CATEGORY_ID) throws Exception {
        String PRODUCTS_BY_CATEGORY_RESULT;
        //encode WEB_SERVICE_ACCESS_KEY
        String ENCODE_KEY_UTF8 = URLEncoder.encode(getWebServiceAccessCode(), "UTF-8");
        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/GetProductsByCategoryID/" + CATEGORY_ID + "?&key=" + ENCODE_KEY_UTF8);
        //open connection to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //read webservice response
        BufferedReader BUFFERED_READER_RESPONSE = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        //read WEBSERVICE_RESULT to string
        String RESPONSE_STRING = BUFFERED_READER_RESPONSE.readLine();
        //create json object to store WEBSERVICE_RESULT
        JSONObject JSON_OBJECT_RESPONSE = new JSONObject(RESPONSE_STRING);
        //convert json object into json array
        JSONArray JSON_ARRAY_RESPONSE = JSON_OBJECT_RESPONSE.getJSONArray("GetProductsByCategoryIDResult");
        //clear current products
        DataStore.ARRAYLIST_CURRENT_PRODUCTS.clear();
        //loop through  array
        for (int i = 0; i < JSON_ARRAY_RESPONSE.length(); i++) {
            JSONObject JSON_OBJECT_PRODUCT = (JSONObject) JSON_ARRAY_RESPONSE.get(i);
            JSONArray images = JSON_OBJECT_PRODUCT.getJSONArray("Images");
            ArrayList<Image> ARRAYLIST_IMAGES = new ArrayList<Image>();
            for (int in = 0; in < images.length(); in++) {
                JSONObject JSON_OBJECT_IMAGE = (JSONObject) images.get(in);
                Image IMAGE = new Image(JSON_OBJECT_IMAGE.getString("ImageID"), JSON_OBJECT_IMAGE.getBoolean("IsDefault"), JSON_OBJECT_IMAGE.getInt("ProductID"));
                ARRAYLIST_IMAGES.add(IMAGE);
            }
            int PARENT_PRODUCT_ID;
            if (JSON_OBJECT_PRODUCT.isNull("ParentProductID") == true) {
                PARENT_PRODUCT_ID = 0;
            } else {
                PARENT_PRODUCT_ID = JSON_OBJECT_PRODUCT.getInt("ParentProductID");
            }

            Product PRODUCT = new Product(JSON_OBJECT_PRODUCT.getInt("ProductID"), JSON_OBJECT_PRODUCT.getInt("CategoryID"), JSON_OBJECT_PRODUCT.getString("Description"), JSON_OBJECT_PRODUCT.getBoolean("IsCurrent"), JSON_OBJECT_PRODUCT.getString("Name"), PARENT_PRODUCT_ID, JSON_OBJECT_PRODUCT.getDouble("Price"), JSON_OBJECT_PRODUCT.getInt("StockCount"), ARRAYLIST_IMAGES);
            Image IMAGE_MAIN = new Image();
            for (Image IMAGE : PRODUCT.getImages()) {
                if (IMAGE.isDefault()) {
                    IMAGE_MAIN = IMAGE;
                }

            }
            if (IMAGE_MAIN.getImageID().length() > 0) {
                URL URL_IMAGES = new URL("http://samshire.azurewebsites.net/Images/" + IMAGE_MAIN.getImageID());
                Bitmap BITMAP_IMAGE;
                BITMAP_IMAGE = BitmapFactory.decodeStream(URL_IMAGES.openConnection().getInputStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                BITMAP_IMAGE.compress(Bitmap.CompressFormat.JPEG, 0, out);
                PRODUCT.setImage(BITMAP_IMAGE);
            }
            DataStore.ARRAYLIST_CURRENT_PRODUCTS.add(PRODUCT);
        }
        PRODUCTS_BY_CATEGORY_RESULT = "Success!";
        BUFFERED_READER_RESPONSE.close();

        return PRODUCTS_BY_CATEGORY_RESULT;
    }

    /**
     * Adds a product to the cart
     * @param HIRE_ID
     * @param PRODUCT_ID
     * @return success! string
     * @throws Exception
     */
    private String webAddProductToCart(int HIRE_ID, int PRODUCT_ID) throws Exception {
        String ADD_PRODUCT_TO_CART_RESULT;
        //encode WEB_SERVICE_ACCESS_KEY
        String ENCODE_KEY_UTF8 = URLEncoder.encode(getWebServiceAccessCode(), "UTF-8");
        java.util.Date DATE_TODAY = new java.util.Date();

        Date SQLDATE_TODAY = new Date(DATE_TODAY.getTime());

        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/AddProductToCart/" + HIRE_ID + "/" + PRODUCT_ID + "/" + DataStore.CURRENT_USER_ID + "/" + SQLDATE_TODAY.toString() + "/" + SQLDATE_TODAY.toString() + "?&key=" + ENCODE_KEY_UTF8);
        //open connection to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //set TIMEOUT
        URL_CONNECTION.setConnectTimeout(TIMEOUT);
        //read webservice response
        BufferedReader BUFFERED_READER_RESPONSE = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        //read WEBSERVICE_RESULT to string
        String RESPONSE_STRING = BUFFERED_READER_RESPONSE.readLine();
        //create connection object to store WEBSERVICE_RESULT
        JSONObject JSON_OBJECT_RESPONSE = new JSONObject(RESPONSE_STRING);
        JSONArray JSON_ARRAY_RESPONSE = JSON_OBJECT_RESPONSE.getJSONArray("AddProductToCartResult");
        int CART_COUNT = JSON_ARRAY_RESPONSE.getInt(1);
        int CURRENT_HIRE_ID = JSON_ARRAY_RESPONSE.getInt(0);
        int CURRENT_HIREDETAIL_ID = JSON_ARRAY_RESPONSE.getInt(2);
        DataStore.CURRENT_CART_COUNT = CART_COUNT;
        Hire HIRE_CURRENT;
        if (DataStore.HIRE_CURRENT_HIRE.getHireID() == 0) {
            HIRE_CURRENT = new Hire(CURRENT_HIRE_ID, DataStore.CURRENT_USER_ID);

        } else {
            HIRE_CURRENT = DataStore.HIRE_CURRENT_HIRE;
        }
        DataStore.HIRE_CURRENT_HIRE = HIRE_CURRENT;
        HireDetail HIREDETAIL = new HireDetail(DataStore.HIRE_CURRENT_HIRE.getHireID(), PRODUCT_ID);
        Product PRODUCT_CURRENT = new Product();
        for (Product PRODUCT : DataStore.ARRAYLIST_CURRENT_PRODUCTS) {
            if (PRODUCT.getProductID() == PRODUCT_ID) {
                PRODUCT_CURRENT = PRODUCT;
            }

        }
        CartItem CARTITEM_CURRENT = new CartItem(PRODUCT_ID, DataStore.HIRE_CURRENT_HIRE.getHireID(), CURRENT_HIREDETAIL_ID, PRODUCT_CURRENT, 1, SQLDATE_TODAY, SQLDATE_TODAY);
        DataStore.ARRAYLIST_CURRENT_CARTIEMS.add(CARTITEM_CURRENT);

        DataStore.HIRE_CURRENT_HIRE.getHireDetails().add(HIREDETAIL);
        ADD_PRODUCT_TO_CART_RESULT = "Success!";
        BUFFERED_READER_RESPONSE.close();
        return ADD_PRODUCT_TO_CART_RESULT;

    }

    /**
     * Changes hire detail end date
     * @param HIREDETAIL_ID
     * @param DATE_ENDDATE
     * @return success! string
     * @throws Exception
     */
    private String webChangeHireDetailEndDate(int HIREDETAIL_ID, Date DATE_ENDDATE) throws Exception {
        String CHANGE_HIRE_RESULT;
        //encode WEB_SERVICE_ACCESS_KEY
        String ECODE_KEY_UTF8 = URLEncoder.encode(getWebServiceAccessCode(), "UTF-8");
        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/ChangeHireDetailEndDate/" + HIREDETAIL_ID + "/" + DATE_ENDDATE.toString() + "?&key=" + ECODE_KEY_UTF8);
        //open connection to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //read webservice response
        BufferedReader BUFFERED_READER_RESPONSE = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        //read WEBSERVICE_RESULT to string
        String line = BUFFERED_READER_RESPONSE.readLine();
        //create json object to store WEBSERVICE_RESULT
        JSONObject jsonResponse = new JSONObject(line);
        DataStore.ARRAYLIST_CACHE_JSON_OBJECTS.add(jsonResponse);
        CHANGE_HIRE_RESULT = "Success!";
        BUFFERED_READER_RESPONSE.close();
        return CHANGE_HIRE_RESULT;
    }

    /**
     * Removes product from cart and changes cart count
     * @param hireDetailID
     * @return success! string
     * @throws Exception
     */
    private String webRemoveProductFromCart(int hireDetailID) throws Exception {
        String REMOVE_PRODUCT_FROM_CART_RESULT;
        //encode WEB_SERVICE_ACCESS_KEY
        String ENCODE_KEY_UTF8 = URLEncoder.encode(getWebServiceAccessCode(), "UTF-8");
        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/RemoveProductFromCart/" + hireDetailID + "?&key=" + ENCODE_KEY_UTF8);
        //open connection to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //read webservice response
        BufferedReader BUFFERED_READER_RESPONSE = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        //read WEBSERVICE_RESULT to string
        String RESPONSE_STRING = BUFFERED_READER_RESPONSE.readLine();
        //create json object to store WEBSERVICE_RESULT
        JSONObject JSON_OBJECT_RESPONSE = new JSONObject(RESPONSE_STRING);
        //convert json object into json array
        JSONArray JSON_ARRAY_RESPONSE = JSON_OBJECT_RESPONSE.getJSONArray("RemoveProductFromCartResult");
        DataStore.CURRENT_CART_COUNT = JSON_ARRAY_RESPONSE.getInt(0); //changes cart count
        REMOVE_PRODUCT_FROM_CART_RESULT = "Success!";
        return REMOVE_PRODUCT_FROM_CART_RESULT;
    }

    /**
     * Checks out current hire using the hire id
     * @param HIRE_ID
     * @return success! string
     * @throws Exception
     */
    private String webCheckOut(int HIRE_ID) throws Exception {
        String CHECK_OUT_RESULT;
        //encode WEB_SERVICE_ACCESS_KEY
        String ENCODE_KEY_UTF8 = URLEncoder.encode(getWebServiceAccessCode(), "UTF-8");
        // add URL object
        URL URL = new URL(WEB_SERVICE_URL + "/Checkout/" + HIRE_ID + "?&key=" + ENCODE_KEY_UTF8);
        //open connection to web service
        URLConnection URL_CONNECTION = URL.openConnection();
        //read webservice response
        BufferedReader BUFFERED_READER_RESULT = new BufferedReader(new InputStreamReader(URL_CONNECTION.getInputStream()));
        //read WEBSERVICE_RESULT to string
        String RESPONSE_STRING = BUFFERED_READER_RESULT.readLine();
        //create json object to store WEBSERVICE_RESULT
        JSONObject JSON_OBJECT_RESPONSE = new JSONObject(RESPONSE_STRING);
        //convert json object into json array
        JSONArray JSON_ARRAY_RESPONSE = JSON_OBJECT_RESPONSE.getJSONArray("CheckoutResult");
        for (int i = 0; i < JSON_ARRAY_RESPONSE.length(); i++) {
            JSONObject JSON_OBJECT_PRODUCT_ID;
            for (Product PRODUCT : DataStore.ARRAYLIST_CURRENT_PRODUCTS) {
                JSON_OBJECT_PRODUCT_ID = new JSONObject(JSON_ARRAY_RESPONSE.getString(i));
                if (PRODUCT.getProductID() == JSON_OBJECT_PRODUCT_ID.getInt("ProductID")) {
                    PRODUCT.setStockCount(JSON_OBJECT_PRODUCT_ID.getInt("StockCount"));
                }

            }
        }
        CHECK_OUT_RESULT = RESPONSE_STRING;
        return CHECK_OUT_RESULT;
    }

    /**
     * Creates a AsyncTask calls webAddProductToCart
     * @param HIRE_ID
     * @param PRODUCT_ID
     */
    public void AddProductToCart(final int HIRE_ID, final int PRODUCT_ID) {

        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }

        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute() {
                contactWebServiceEventMembers(1, null, null, null);
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";
                try {
                    result = webAddProductToCart(HIRE_ID, PRODUCT_ID);
                } catch (Exception ex) {

                    // WEB_SERVICE_EVENT.WebServiceFinishedWithException(ex);
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != "") {

                    contactWebServiceEventMembers(2, result, "AddProductToCart", null);
                } else {
                    contactWebServiceEventMembers(3, null, null, new Exception("Cart request failed!"));
                }

            }
        }.execute();

    }

    /**
     * Creates a AsyncTask calls webLoginRequest
     * @param EMAIL
     * @param PASSWORD
     */
    public void loginRequest(final String EMAIL, final String PASSWORD) {

        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }

        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                contactWebServiceEventMembers(1, null, null, null);
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";
                try {
                    result = webLoginRequest(EMAIL, PASSWORD);
                } catch (Exception ex) {

                    // WEB_SERVICE_EVENT.WebServiceFinishedWithException(ex);
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                if (result != "") {

                    contactWebServiceEventMembers(2, result, "LoginRequest", null);
                } else {
                    contactWebServiceEventMembers(3, null, null, new Exception("Login request failed!"));

                }

            }
        }.execute();

    }

    /**
     * Creates a AsyncTask calls webProductByID
     * @param PRODUCT_ID
     */
    public void getProductByID(final int PRODUCT_ID) {
        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }

        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                contactWebServiceEventMembers(1, null, null, null);
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";

                try {
                    result = webProductByID(PRODUCT_ID);
                } catch (Exception ex) {
                    return result;

                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                if (result != "") {

                    contactWebServiceEventMembers(2, result, "ProductByID", null);
                } else {

                    contactWebServiceEventMembers(3, null, null, new Exception("ProductByID Request failed!"));

                }
            }
        }.execute();

    }

    /**
     * Creates a AsyncTask calls webProductsBySearchString
     * @param SEARCH_STRING
     */
    public void getProductsBySearchString(final String SEARCH_STRING) {
        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }

        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                contactWebServiceEventMembers(1, null, null, null);
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";

                try {
                    result = webProductsBySearchString(SEARCH_STRING);
                } catch (Exception ex) {
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                if (result != "") {

                    contactWebServiceEventMembers(2, result, "ProductsBySearchString", null);
                } else {
                    contactWebServiceEventMembers(3, null, null, new Exception("ProductsBySearchString Request failed!"));
                }
            }
        }.execute();

    }

    /**
     * Creates a AsyncTask calls webProductsByCategoryID
     * @param CATEGORY_ID
     */
    public void getProductsByCategoryID(final int CATEGORY_ID) {
        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }


        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                contactWebServiceEventMembers(1, null, null, null);
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";

                try {
                    result = webProductsByCategoryID(CATEGORY_ID);
                } catch (Exception ex) {
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                if (result != null) {

                    contactWebServiceEventMembers(2, result, "ProductsByCategoryID", null);
                } else {
                    contactWebServiceEventMembers(3, null, null, new Exception("ProductsByCategoryID Request Failed!"));
                }
            }
        }.execute();

    }

    /**
     * Creates a AsyncTask calls webRemoveProductFromCart
     * @param HIREDETAIL_ID
     */
    public void removeProductFromCart(final int HIREDETAIL_ID) {
        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }

        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                contactWebServiceEventMembers(1, null, null, null);
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";

                try {
                    result = webRemoveProductFromCart(HIREDETAIL_ID);
                } catch (Exception ex) {
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                if (result != "") {

                    contactWebServiceEventMembers(2, result, "RemoveProductFromCart", null);
                } else {
                    contactWebServiceEventMembers(3, null, null, new Exception("\"Remove product from cart request failed!"));
                }
            }
        }.execute();

    }

    /**
     * Creates a AsyncTask calls webChangeHireDetailEndDate
     * @param HIREDETAIL_ID
     * @param ENDDATE
     */
    public void changeHireDetailEndDate(final int HIREDETAIL_ID, final Date ENDDATE) {

        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }

        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                contactWebServiceEventMembers(1, null, null, null);
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";

                try {
                    result = webChangeHireDetailEndDate(HIREDETAIL_ID, ENDDATE);
                } catch (Exception ex) {
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                if (result != "") {

                    contactWebServiceEventMembers(2, result, "GetChangeHireDetailEndDate", null);
                } else {
                    contactWebServiceEventMembers(3, null, null, new Exception("Get change hire detail end date request failed!"));
                }
            }
        }.execute();


    }
    /**
     * Creates a AsyncTask calls webCategories
     */
    public void getUserByEmail(final String EMAIL) {

        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }

        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                WEB_SERVICE_EVENT.WebServiceStartedRequest();
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";

                try {
                    result = webUserByEmail(EMAIL);
                } catch (Exception ex) {
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                if (result != "") {
                    contactWebServiceEventMembers(2, result, "GetUserByEmail", null);
                } else {
                    contactWebServiceEventMembers(3, null, null, new Exception("Get User request failed!"));
                }
            }
        }.execute();

    }
    /**
     * Creates a AsyncTask calls webCategories
     */
    public void getCategories() {

        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }

        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                WEB_SERVICE_EVENT.WebServiceStartedRequest();
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";

                try {
                    result = webCategories();
                } catch (Exception ex) {
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                if (result != "") {
                    contactWebServiceEventMembers(2, result, "GetCategories", null);
                } else {
                    contactWebServiceEventMembers(3, null, null, new Exception("Get Categories request failed!"));
                }
            }
        }.execute();

    }

    /**
     * Creates a AsyncTask calls webCheckOut
     * @param HIRE_ID
     */
    public void checkOut(final int HIRE_ID) {

        if (WEB_SERVICE_EVENT == null) {
            throw new NullPointerException();
        }

        CURRENT_ASYNC_TASK = new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                contactWebServiceEventMembers(1, null, null, null);
            }

            ;

            @Override
            protected String doInBackground(Void... params) {
                String result = "";

                try {
                    result = webCheckOut(HIRE_ID);
                } catch (Exception ex) {
                    return result;
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                WEB_SERVICE_EVENT.WebServiceEndRequest();
                if (result != "") {
                    contactWebServiceEventMembers(2, result, "CheckOut", null);
                } else {
                    contactWebServiceEventMembers(3, null, null, new Exception("CheckOut request failed!"));
                }
            }
        }.execute();

    }


}

