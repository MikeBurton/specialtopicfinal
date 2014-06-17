package net.azurewebsites.specialtopicfinal.app.UI.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Product;
import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter.CartArrayAdapter;
import net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter.CategoryArrayAdapter;
import net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter.ProductArrayAdapter;
import net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter.UserArrayAdapter;
import net.azurewebsites.specialtopicfinal.app.UI.Fragment.CartDesFragment;
import net.azurewebsites.specialtopicfinal.app.UI.Fragment.CartFragment;
import net.azurewebsites.specialtopicfinal.app.UI.Fragment.CategoryFragment;
import net.azurewebsites.specialtopicfinal.app.UI.Fragment.ProductDesFragment;
import net.azurewebsites.specialtopicfinal.app.UI.Fragment.ProductFragment;
import net.azurewebsites.specialtopicfinal.app.UI.Fragment.UserFragment;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.SignalR;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebService;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebServiceEvents;


public class MainActivity extends FragmentActivity implements WebServiceEvents,SearchView.OnQueryTextListener {
    View ACTION_BAR_VIEW;
    Button BTN_ACTION_BAR_CART;
    Button BTN_ACTION_BAR_USER;
    FrameLayout LIST_FRAME_LAYOUT;
    FrameLayout LOADING_FRAME_LAYOUT;
    ProgressBar LOADING_PROGRESS_BAR;
    public ActionBar ACTION_BAR;
    WebService WEB_SERVICE;
    ProductArrayAdapter PRODUCT_ARRAY_ADAPTER;
    int FRAGMENT_ID = 0;
    int CURRENT_FRAGMENT_ID = 1;
    SignalR SIGNALR_CONNECTION;
    SearchView searchView;
    private static int BACK_TIME_OUT = 100;


    public WebService getWebService() {
        return WEB_SERVICE;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //clear categories
        DataStore.ARRAYLIST_CURRENT_CATEGORIES.clear();
        //add fragment to main activity container
        WEB_SERVICE = new WebService(this,6000,true);
        LOADING_PROGRESS_BAR = (ProgressBar)findViewById(R.id.loading);
        LIST_FRAME_LAYOUT = (FrameLayout) findViewById(R.id.listContainer);
        LOADING_FRAME_LAYOUT = (FrameLayout) findViewById(R.id.loadingContainer);
        ACTION_BAR = getActionBar();
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);


        loadingScreen(true);

        getCategories();
        SIGNALR_CONNECTION = new SignalR(this);
        try{
            SIGNALR_CONNECTION.signalRConnection();
        }catch (Exception ex)
        {
            //Toast.makeText(MainActivity.this,ex.toString(),Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ACTION_BAR_VIEW = menu.findItem(R.id.action_cartCount).getActionView();
        BTN_ACTION_BAR_CART = (Button) ACTION_BAR_VIEW.findViewById(R.id.notif_count);
        BTN_ACTION_BAR_CART.setText(String.valueOf(DataStore.CURRENT_CART_COUNT));
        BTN_ACTION_BAR_CART.setOnClickListener(buttonAddOnClickListener);
        ACTION_BAR_VIEW = menu.findItem(R.id.action_User).getActionView();
        BTN_ACTION_BAR_USER = (Button) ACTION_BAR_VIEW.findViewById(R.id.user_button);
        BTN_ACTION_BAR_USER.setOnClickListener(buttonAddOnClickListener);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                return  navBackFragments();
            case   R.id.action_User:
                Toast.makeText(MainActivity.this,"User Clicked",Toast.LENGTH_LONG).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

    if (WEB_SERVICE != null){
        WEB_SERVICE.getCurrentAsyncTask().cancel(true);
        System.out.println("ASYNCTASK HAS STOPPED: " + WEB_SERVICE.getCurrentAsyncTask().isCancelled());

    }


        new Handler().postDelayed(new Runnable() {

            /*
             * Delay the back button by 100ms
             * to make sure AsyncTask has stoped
             * TODO:This Code needs to be removed
             */

            @Override
            public void run() {

                navBackFragments();
            }
        }, BACK_TIME_OUT);
        loadingScreen(false);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        loadingScreen(true);
        WEB_SERVICE.getProductsBySearchString(s);
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void WebServiceStartedRequest() {

    }

    @Override
    public void WebServiceFinished(String METHOD_NAME, String WEB_SERVICE_RESULT) {
        if(METHOD_NAME == "GetCategories")
        {
            getUserDetails();
            getCategoryFragment(false);

        }else if(METHOD_NAME == "ProductsByCategoryID")
        {
            getProductFragment();

        }else if (METHOD_NAME == "ProductsBySearchString")
        {
            getProductFragment();
        }
    }

    @Override
    public void WebServiceFinishedWithException(Exception EXCEPTION) {
        Toast.makeText(MainActivity.this, EXCEPTION.toString(), Toast.LENGTH_LONG).show();
       loadingScreen(false);
    }

    @Override
    public void WebServiceEndRequest() {
        loadingScreen(false);
    }

    @Override
    public void WebServiceImages(Bitmap WEB_SERVICE_IMAGE, String METHOD_NAME) {

    }


    /*
          * Button listener
          * Handles all the on click events for the login activity
          * */
    Button.OnClickListener buttonAddOnClickListener  = new Button.OnClickListener(){
        @Override
        public void onClick(View btn) {
            switch (btn.getId()) {
                case R.id.notif_count:
                    getCartFragment();
                    break;
                case R.id.user_button:
                    getUserFragment();
                    break;

            }
        }
    };
    private void getCategoryFragment(boolean isBack)
    {
        CategoryArrayAdapter categoryArrayAdapter = new CategoryArrayAdapter(this,R.layout.listview_item_category,DataStore.ARRAYLIST_CURRENT_CATEGORIES);
        CategoryFragment categoryFragment =  new CategoryFragment();

            if (isBack)
            {
                categoryFragment.setMainActivity(this);
                categoryFragment.setArrayAdapter(categoryArrayAdapter);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listContainer, categoryFragment)
                                // Add this transaction to the back stack
                        .addToBackStack(null)
                        .commit();
            }else
            {
                categoryFragment.setMainActivity(this);
                categoryFragment.setArrayAdapter(categoryArrayAdapter);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.listContainer,categoryFragment)
                                // Add this transaction to the back stack
                        .addToBackStack(null)
                        .commit();
            }


        ACTION_BAR.setDisplayHomeAsUpEnabled(false);
        FRAGMENT_ID = 1;
        CURRENT_FRAGMENT_ID =1;


    }

    private void navLogin()
    {
        final Context context = this;
        Intent intent = new Intent(context,LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void getUserFragment()
    {
        UserArrayAdapter userArrayAdapter = new UserArrayAdapter(this,R.layout.listview_item_user,DataStore.ARRAYLIST_CURRENT_HIRES);
        UserFragment userFragment = new UserFragment();


            userFragment.setMainActivity(this);
            userFragment.setUserArrayAdapter(userArrayAdapter);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, userFragment)
                            // Add this transaction to the back stack
                    .addToBackStack(null)
                    .commit();

        ACTION_BAR.setDisplayHomeAsUpEnabled(true);
        FRAGMENT_ID = 6;
    }
    public void getCartDesFragment()
    {
        CartDesFragment cartDesFragment = new CartDesFragment();
        cartDesFragment.setActivity(this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, cartDesFragment)
                            // Add this transaction to the back stack
                    .addToBackStack(null)
                    .commit();

        ACTION_BAR.setDisplayHomeAsUpEnabled(true);
        FRAGMENT_ID = 5;
    }
    public void getCartFragment() {
        CartArrayAdapter cartArrayAdapter = new CartArrayAdapter(this, R.layout.listview_item_cart, DataStore.ARRAYLIST_CURRENT_CARTIEMS);
        CartFragment cartFragment = new CartFragment();


        cartFragment.setArrayAdapter(cartArrayAdapter);
        cartFragment.setActivity(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listContainer, cartFragment)
                        // Add this transaction to the back stack
                .addToBackStack(null)
                .commit();

        ACTION_BAR.setDisplayHomeAsUpEnabled(true);
        FRAGMENT_ID = 4;
    }
    public void getProductFragment()
    {
       PRODUCT_ARRAY_ADAPTER = new ProductArrayAdapter(this,R.layout.listview_item_product,DataStore.ARRAYLIST_CURRENT_PRODUCTS);
          ProductFragment  productFragment = new ProductFragment();


                productFragment.setArrayAdapter(PRODUCT_ARRAY_ADAPTER);
                productFragment.setActivity(this);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listContainer, productFragment)
                                // Add this transaction to the back stack
                        .addToBackStack(null)
                        .commit();

        ACTION_BAR.setDisplayHomeAsUpEnabled(true);
        FRAGMENT_ID = 2;
        CURRENT_FRAGMENT_ID = 2;
        //productFragment.setArrayAdapter(PRODUCT_ARRAY_ADAPTER);

    }

    public void getProductDescriptionFragment()
    {
        ProductDesFragment productDesFragment = new ProductDesFragment();

            productDesFragment.setMainActivity(this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.listContainer, productDesFragment)
                            // Add this transaction to the back stack
                    .addToBackStack(null)
                    .commit();

        ACTION_BAR.setDisplayHomeAsUpEnabled(true);
        FRAGMENT_ID = 3;
        CURRENT_FRAGMENT_ID = 3;
    }
    private void getCategories()
    {
        if (!isNetworkAvailable())
        {
            Toast.makeText(MainActivity.this, "No internet connection, check your connection and try again", Toast.LENGTH_LONG).show();
            return;
        }
        if(DataStore.ARRAYLIST_CURRENT_CATEGORIES.isEmpty())
        {
            WEB_SERVICE.getCategories();
        }else
        {
           // getCategoryFragment();
            loadingScreen(false);
        }
    }
    public void getProducts(int categoryID) {

        if (!isNetworkAvailable())
        {
            Toast.makeText(MainActivity.this, "No internet connection, check your connection and try again", Toast.LENGTH_LONG).show();
            return;
        }
        DataStore.ARRAYLIST_CURRENT_PRODUCTS.clear();
        DataStore.CURRENT_PRODUCT = new Product();

        FRAGMENT_ID = 2;
        CURRENT_FRAGMENT_ID = 2;
        loadingScreen(true);
        DataStore.CURRENT_CATEGORY_ID = categoryID;
        WEB_SERVICE.getProductsByCategoryID(DataStore.CURRENT_CATEGORY_ID);

    }
    public void loadingScreen(boolean isVisible)
    {
        if(isVisible)
        {
            LIST_FRAME_LAYOUT.setVisibility(View.INVISIBLE);
            LOADING_FRAME_LAYOUT.setVisibility(View.VISIBLE);
        }else
        {
            LIST_FRAME_LAYOUT.setVisibility(View.VISIBLE);
            LOADING_FRAME_LAYOUT.setVisibility(View.INVISIBLE);

        }
    }
    public void getUserDetails()
    {
        loadingScreen(true);
        //mainActivity.getWebService().getUserByEmail(DataStore.CURRENT_USER_ID);
        WEB_SERVICE.getUserByEmail(DataStore.CURRENT_USER_ID);
    }
    public void updateCartCount()
    {
        invalidateOptionsMenu();
    }
    public boolean navBackFragments()
    {
        switch (FRAGMENT_ID)
        {
            case 1:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                final Context context = this;
                Intent intent = new Intent(context,LoginActivity.class);
                startActivity(intent);
                this.finish();

                return true;
            case 2:
                getCategoryFragment(true);
                return true;
            case 3:
                getProductFragment();
                return true;
            case 4:
                switch (CURRENT_FRAGMENT_ID)
                {
                    case 1:
                        getCategoryFragment(true);
                        return true;
                    case 2:
                        getProductFragment();
                        return true;
                    case 3:
                        getProductDescriptionFragment();
                        return true;
                }
                return true;
            case 5:
                getCartFragment();
                return true;
            case 6:
                switch (CURRENT_FRAGMENT_ID)
                {
                    case 1:
                        getCategoryFragment(true);
                        return true;
                    case 2:
                        getProductFragment();
                        return true;
                    case 3:
                        getProductDescriptionFragment();
                        return true;
                }
                return true;
        }
        return true;
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void updateStockCount(final String jsonResponse)throws Exception{

        if (PRODUCT_ARRAY_ADAPTER != null)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        PRODUCT_ARRAY_ADAPTER.updateStock(jsonResponse);
                    }catch (Exception ex)
                    {
                        System.out.println(ex.toString());
                    }
                }
            });
        }
    }

}
