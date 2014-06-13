package net.azurewebsites.specialtopicfinal.app.UI.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.CartItem;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Hire;
import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UI.Activity.MainActivity;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.Util;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebServiceEvents;

import java.sql.Date;
import java.text.DecimalFormat;

/**
 * Created by Mike on 31/05/2014.
 */
public class CartFragment extends Fragment implements  AdapterView.OnItemClickListener,WebServiceEvents{
    MainActivity mainActivity;
    public void setActivity(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }
    ArrayAdapter arrayAdapter;
    Button checkout;
    ListView cart;
    TextView grandTotal;


    public void setArrayAdapter(ArrayAdapter arrayAdapter)
    {
        this.arrayAdapter = arrayAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart,container,false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Toast.makeText(mainActivity,"Cart item clicked",Toast.LENGTH_LONG).show();
        CartItem cartItem = DataStore.ARRAYLIST_CURRENT_CARTIEMS.get(i);
        DataStore.CARTITEM_CURRENT = cartItem;
        mainActivity.getCartDesFragment();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.getWEB_SERVICE().getWEB_SERVICE_EVENT_MEMBERS().add(this);
        cart = (ListView) mainActivity.findViewById(R.id.lvCart);
        checkout = (Button) mainActivity.findViewById(R.id.btnCheckOut);
        grandTotal = (TextView)mainActivity.findViewById(R.id.txt_GrandTotal);
        cart.setAdapter(arrayAdapter);
       // setListAdapter(ARRAY_ADAPTER);

       cart.setOnItemClickListener(this);
        checkout.setOnClickListener(buttonAddOnClickListener);
       grandTotal.setText("Grand Total: $"+getGrandTotal());
    }
    /*
  * Button listener
  * Handles all the on click events for the login MAIN_ACTIVITY
  * */
    Button.OnClickListener buttonAddOnClickListener  = new Button.OnClickListener(){
        @Override
        public void onClick(View btn) {
            switch (btn.getId()) {
                case R.id.btnCheckOut:
                    checkOutCart();
                    break;

            }
        }
    };

    private String getGrandTotal() {
        double total = 0;
        for (CartItem ci : DataStore.ARRAYLIST_CURRENT_CARTIEMS)
        {
            double productPrice = ci.getProduct().getPrice();
            Date startDate = ci.getStartDate();
            Date endDate = ci.getEndDate();
            //System.out.println("Start Date: " + startDate.toString());
           // System.out.println("End Date: " + endDate.toString());
            int numOfDays = Util.numOfDays(endDate, startDate)+1 ;
          //  System.out.println("Number of Days: "+numOfDays);
            total += numOfDays * productPrice;
           // System.out.println("Total: $"+total);
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(total);
    }

    public void checkOutCart()
    {
        if (!mainActivity.isNetworkAvailable())
        {
            Toast.makeText(mainActivity, "No internet connection, check your connection and try again", Toast.LENGTH_LONG).show();
            return;
        }
        if (DataStore.ARRAYLIST_CURRENT_CARTIEMS.isEmpty())
        {
            Toast.makeText(mainActivity,"Cart is empty",Toast.LENGTH_SHORT).show();
        }else
        {
            mainActivity.loadingScreen(true);
            mainActivity.getWEB_SERVICE().checkOut(DataStore.HIRE_CURRENT_HIRE.getHireID());

        }

    }

    @Override
    public void WebServiceStartedRequest() {

    }

    @Override
    public void WebServiceFinished(String METHOD_NAME, String WEB_SERVICE_RESULT) {
        if (METHOD_NAME == "CheckOut")
        {
            mainActivity.loadingScreen(false);
           // System.out.println(result);
            DataStore.ARRAYLIST_CURRENT_CARTIEMS.clear();
            DataStore.CARTITEM_CURRENT = new CartItem();
            DataStore.HIRE_CURRENT_HIRE = new Hire();
            DataStore.CURRENT_CART_COUNT = 0;
            mainActivity.updateCartCount();

           Toast.makeText(mainActivity,"Thank you for your order",Toast.LENGTH_SHORT).show();
            mainActivity.getProductFragment();
        }
    }


    @Override
    public void WebServiceFinishedWithException(Exception ex) {
        Toast.makeText(mainActivity, ex.toString(), Toast.LENGTH_LONG).show();
        mainActivity.loadingScreen(false);
    }

    @Override
    public void WebServiceEndRequest() {
        mainActivity.loadingScreen(false);
    }

    @Override
    public void WebServiceImages(Bitmap WEB_SERVICE_IMAGE, String METHOD_NAME) {

    }
}
