package net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.CartItem;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.HireDetail;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Product;
import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.Util;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Mike on 31/05/2014.
 */
public class CartArrayAdapter extends ArrayAdapter<CartItem> {
    Activity myActivity;
    ArrayList<CartItem> cartItems;
    public CartArrayAdapter(Activity activity, int resource, ArrayList<CartItem> list) {
        super(activity, resource, list);
        this.myActivity = activity;
        this.cartItems = list;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null)
        {
            itemView = myActivity.getLayoutInflater().inflate(R.layout.listview_item_cart,parent,false);

        }
        CartItem cartItem = cartItems.get(position);


        double productPrice = cartItem.getProduct().getPrice();
        int numOfDays = Util.numOfDays(cartItem.getEndDate(),cartItem.getStartDate()) + 1;
        double total = numOfDays * productPrice;
        //get product count

        TextView txtProductName = (TextView)itemView.findViewById(R.id.txt_ProductName_Cart);
        TextView txtProductCount = (TextView)itemView.findViewById(R.id.txt_Count_Cart);
        TextView txtDailyRate = (TextView)itemView.findViewById(R.id.txt_DailyRate_Cart);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_ProductImage_Cart);
        TextView txtTotal = (TextView)itemView.findViewById(R.id.txt_Total_Cart);
        TextView txtNoOfDays = (TextView)itemView.findViewById(R.id.txt_NumberOfDays_Cart);
        Bitmap image;
        if (cartItem.getProduct().getImage() == null)
        {
            image = BitmapFactory.decodeResource(myActivity.getResources(), R.drawable.ic_launcher);
        }else
        {
            image = cartItem.getProduct().getImage();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        imageView.setImageBitmap(image);
        txtProductName.setText(cartItem.getProduct().getName());
        txtProductCount.setText("Amount: "+cartItem.getAmount()+ " ");
        txtDailyRate.setText("Per Day: $" + df.format(productPrice)+" ");
        txtTotal.setText("Total: $"+df.format(total)+ " ");
        txtNoOfDays.setText("NoOfDays: "+numOfDays +" ");
        return itemView;
    }
}
