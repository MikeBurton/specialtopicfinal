package net.azurewebsites.specialtopicfinal.app.UI.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.CartItem;
import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UI.Activity.MainActivity;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.Util;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebServiceEvents;

import java.sql.Date;


/**
 * Created by Mike on 01/06/2014.
 */
public class CartDesFragment extends Fragment implements WebServiceEvents
{
    TextView txtProductName;
    TextView txtProductAmount;
    TextView txtDailyRate;
    NumberPicker noOfDays;
    Button update;
    Button remove;
    MainActivity mainActivity;
    ImageView imageView;
    public void setActivity(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart_des,container,false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.getWebService().getWEB_SERVICE_EVENT_MEMBERS().add(this);
        txtProductName = (TextView)mainActivity.findViewById(R.id.txt_ProductName_CartDes);
        txtProductAmount = (TextView)mainActivity.findViewById(R.id.txt_Amount_CartDes);
        txtDailyRate = (TextView)mainActivity.findViewById(R.id.txt_DailyRate_CartDes);
        noOfDays = (NumberPicker)mainActivity.findViewById(R.id.np_NoOfDays_CartDes);
        update = (Button)mainActivity.findViewById(R.id.btn_NoOfDays_CartDes);
        remove = (Button)mainActivity.findViewById(R.id.btn_Remove_CartDes);
        imageView = (ImageView)mainActivity.findViewById(R.id.iv_Icon_CartDes);
        txtProductName.setText("Product Name: "+DataStore.CARTITEM_CURRENT.getProduct().getName());
        txtProductAmount.setText("Product Amount: "+DataStore.CARTITEM_CURRENT.getAmount());
        txtDailyRate.setText("Daily Rate: $" + DataStore.CARTITEM_CURRENT.getProduct().getPrice() * DataStore.CARTITEM_CURRENT.getAmount());
        String[] nums = new String[100];
        for(int i=0; i<nums.length; i++)
            nums[i] = Integer.toString(i);

        noOfDays.setMinValue(0);
        noOfDays.setMaxValue(98);
        noOfDays.setWrapSelectorWheel(false);
        noOfDays.setDisplayedValues(nums);
        noOfDays.setValue(Util.numOfDays(DataStore.CARTITEM_CURRENT.getEndDate(),DataStore.CARTITEM_CURRENT.getStartDate())+1);
        update.setOnClickListener(buttonAddOnClickListener);
        remove.setOnClickListener(buttonAddOnClickListener);
        Bitmap image;
        if (DataStore.CARTITEM_CURRENT.getProduct().getImage() == null)
        {
            image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        }else
        {
            image = DataStore.CARTITEM_CURRENT.getProduct().getImage();
        }
        imageView.setImageBitmap(image);
    }

    Button.OnClickListener buttonAddOnClickListener  = new Button.OnClickListener(){
        @Override
        public void onClick(View btn) {
            switch (btn.getId()) {
                case R.id.btn_Remove_CartDes:
                    removeItem();
                    break;
                case R.id.btn_NoOfDays_CartDes:
                    updateEndDate();
                    break;
            }
        }
    };

    /**
     * Updates the end date of a selected product already added to the cart
     */
    public void updateEndDate()
    {
        if (!mainActivity.isNetworkAvailable())
        {
            Toast.makeText(mainActivity, "No internet connection, check your connection and try again", Toast.LENGTH_LONG).show();
            return;
        }
        if (noOfDays.getValue() == 0)
        {
            Toast.makeText(mainActivity,"Must be at least one day",Toast.LENGTH_LONG).show();
            return;
        }
        mainActivity.loadingScreen(true);
        java.util.Date date = new java.util.Date();
        System.out.println("Number Picker Value: "+noOfDays.getValue());
        date = Util.addDays(date,noOfDays.getValue()-1);
        Date today = new Date(date.getTime());
        DataStore.CARTITEM_CURRENT.setEndDate(today);

        mainActivity.getWebService().changeHireDetailEndDate(DataStore.CARTITEM_CURRENT.getHireDetailID(), DataStore.CARTITEM_CURRENT.getEndDate());
    }

    /**
     * Removes a product from the cart
     */
    public void removeItem()
    {
        if (!mainActivity.isNetworkAvailable())
        {
            Toast.makeText(mainActivity, "No internet connection, check your connection and try again", Toast.LENGTH_LONG).show();
            return;
        }
        mainActivity.loadingScreen(true);

        mainActivity.getWebService().removeProductFromCart(DataStore.CARTITEM_CURRENT.getHireDetailID());
    }
    @Override
    public void WebServiceStartedRequest() {

    }

    @Override
    public void WebServiceFinished(String METHOD_NAME, String WEB_SERVICE_RESULT) {
        if(METHOD_NAME == "RemoveProductFromCart")
        {
            mainActivity.updateCartCount();
            for (int i = 0; i<DataStore.ARRAYLIST_CURRENT_CARTIEMS.size();i++)
            {
                CartItem cartItem = DataStore.ARRAYLIST_CURRENT_CARTIEMS.get(i);
                if (cartItem.getHireDetailID() == DataStore.CARTITEM_CURRENT.getHireDetailID())
                {
                    DataStore.ARRAYLIST_CURRENT_CARTIEMS.remove(i);
                    break;
                }
            }
            mainActivity.getCartFragment();
            mainActivity.loadingScreen(false);
            Toast.makeText(mainActivity, WEB_SERVICE_RESULT,Toast.LENGTH_SHORT).show();
        }else if (METHOD_NAME == "GetChangeHireDetailEndDate")
        {
            Toast.makeText(mainActivity, WEB_SERVICE_RESULT,Toast.LENGTH_SHORT).show();
            mainActivity.loadingScreen(false);
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
