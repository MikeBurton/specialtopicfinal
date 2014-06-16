package net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Product;
import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;


import org.json.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Created by Mike on 20/05/2014.
 */
public class ProductArrayAdapter extends ArrayAdapter<Product> {

    Activity myActivity;
    ArrayList<Product> myProducts;
    ArrayList<ProductView> myViews;

    public ArrayList<Product> getMyProducts() {
        return myProducts;
    }

    public ArrayList<ProductView> getMyViews() {
        return myViews;
    }

    public ProductArrayAdapter(Activity activity, int resource, ArrayList<Product> list) {
        super(activity, resource, list);
        this.myActivity = activity;
        this.myProducts = list;
        myViews = new ArrayList<ProductView>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null)
        {
            itemView = myActivity.getLayoutInflater().inflate(R.layout.listview_item_product,parent,false);

        }
        myViews.add(new ProductView(itemView,position));
        Product currentProduct =  myProducts.get(position);
        TextView txtProductName = (TextView)itemView.findViewById(R.id.item_txtProductName);
        TextView txtStock = (TextView)itemView.findViewById(R.id.item_txtStockCount);
        TextView txtPrice = (TextView)itemView.findViewById(R.id.item_txtPrice);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.item_ivIcon);
        Bitmap image;
        if (currentProduct.getImage() == null)
        {
            image = BitmapFactory.decodeResource(myActivity.getResources(),R.drawable.ic_launcher);
        }else
        {
            image = currentProduct.getImage();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        imageView.setImageBitmap(image);
        txtProductName.setText(currentProduct.getName());
        txtPrice.setText(" $"+df.format(currentProduct.getPrice())+" ");
        txtStock.setText(" "+currentProduct.getStockCount()+" Available ");
        return itemView;
    }
    public void updateStock(String jsonString)throws Exception
    {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("A");
        String string = jsonArray.getString(0);
        JSONObject jsonObject1 = new JSONObject(string);
        JSONArray jsonArray1 = jsonObject1.getJSONArray("SignalRProducts");
        for (int i = 0; i<jsonArray1.length();++i) {
            JSONObject updateProducts = new JSONObject(jsonArray1.getString(i));
            for (ProductView productView : myViews) {
                int productID = updateProducts.getInt("ProductID");
                Product currentProduct = myProducts.get(productView.getIndex());
                if (currentProduct.getProductID() == productID) {

                    View view = productView.getView();
                    if (view != null)
                    {
                        TextView txtStockCount = (TextView) view.findViewById(R.id.item_txtStockCount);
                        txtStockCount.setText(" "+updateProducts.getInt("StockCount")+" Available ");

                    }
                }
            }
        }
    }
}
