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
import android.widget.TextView;
import android.widget.Toast;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.CartItem;
import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UI.Activity.MainActivity;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebServiceEvents;

/**
 * Created by Mike on 31/05/2014.
 */
public class ProductDesFragment extends Fragment implements WebServiceEvents {
    Button btnAddToCart;
    TextView txtProductStock;
    MainActivity mainActivity;

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public ProductDesFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_product_description,container,false);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity.getWebService().getWEB_SERVICE_EVENT_MEMBERS().add(this);
        TextView txtProductName = (TextView) mainActivity.findViewById(R.id.proDes_txtProductName);
        TextView txtProductPrice = (TextView) mainActivity.findViewById(R.id.proDes_txtProductPrice);
        txtProductStock = (TextView) mainActivity.findViewById(R.id.proDes_txtProductStock);
        TextView txtProductDescription = (TextView) mainActivity.findViewById(R.id.proDes_txtProductDescription);
        txtProductName.setText("Product Name: "+ DataStore.CURRENT_PRODUCT.getName());
        txtProductPrice.setText(" $"+DataStore.CURRENT_PRODUCT.getPrice()+" ");
        txtProductStock.setText(" "+DataStore.CURRENT_PRODUCT.getStockCount()+" Available ");
        txtProductDescription.setText("Product Description: "+DataStore.CURRENT_PRODUCT.getDescription());
        btnAddToCart = (Button) mainActivity.findViewById(R.id.proDes_btnAddToCart);
        btnAddToCart.setOnClickListener(buttonAddOnClickListener);
        checkButton();
        Bitmap image;
        if (DataStore.CURRENT_PRODUCT.getImage() == null)
        {
            image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        }else
        {
            image = DataStore.CURRENT_PRODUCT.getImage();
        }
        ImageView imageView = (ImageView)mainActivity.findViewById(R.id.proDes_ivIcon);
        imageView.setImageBitmap(image);

    }

    @Override
    public void WebServiceStartedRequest() {

    }

    @Override
    public void WebServiceFinished(String METHOD_NAME, String WEB_SERVICE_RESULT) {
        String str = WEB_SERVICE_RESULT;
        if(METHOD_NAME == "AddProductToCart")
        {
            checkButton();
            mainActivity.updateCartCount();
            mainActivity.ACTION_BAR.setDisplayHomeAsUpEnabled(true);
            mainActivity.loadingScreen(false);
            //Toast.makeText(mainActivity, "Hire ID: " + DataStore.HIRE_CURRENT_HIRE.getHireID() + " Cart Count: " + DataStore.CURRENT_CART_COUNT, Toast.LENGTH_LONG).show();
            Toast.makeText(mainActivity, "Added to Cart", Toast.LENGTH_SHORT).show();

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


    private void checkButton()
    {
        int productCount = 0;
       for (CartItem cartItem : DataStore.ARRAYLIST_CURRENT_CARTIEMS)
       {

           if (cartItem.getProductID() == DataStore.CURRENT_PRODUCT.getProductID())
           {
               productCount += 1;

           }
       }
        if (productCount == DataStore.CURRENT_PRODUCT.getStockCount())
        {
            btnAddToCart.setEnabled(false);
            return;
        }
        if (DataStore.CURRENT_PRODUCT.getStockCount() < 1)
        {
            btnAddToCart.setEnabled(false);
        }else
        {
            btnAddToCart.setEnabled(true);
        }
    }
    @Deprecated
    private void updateStock()
    {
        if (DataStore.CURRENT_PRODUCT.getStockCount() > 0)
        {
            int count = DataStore.CURRENT_PRODUCT.getStockCount();
            count -= 1;
            DataStore.CURRENT_PRODUCT.setStockCount(count);
            txtProductStock.setText(" "+DataStore.CURRENT_PRODUCT.getStockCount()+" Available ");
        }
    }
    Button.OnClickListener buttonAddOnClickListener  = new Button.OnClickListener(){
        @Override
        public void onClick(View btn) {
            switch (btn.getId()) {
                case R.id.proDes_btnAddToCart:
                    addProductToCart();
                    break;
            }
        }
    };

    public void addProductToCart()
    {
        if (!mainActivity.isNetworkAvailable())
        {
            Toast.makeText(mainActivity, "No internet connection, check your connection and try again", Toast.LENGTH_LONG).show();
            return;
        }

        mainActivity.ACTION_BAR.setDisplayHomeAsUpEnabled(false);
        mainActivity.loadingScreen(true);
        //connect to webservice and add product to cart
        mainActivity.getWebService().AddProductToCart(DataStore.HIRE_CURRENT_HIRE.getHireID(),DataStore.CURRENT_PRODUCT.getProductID());

    }
}
