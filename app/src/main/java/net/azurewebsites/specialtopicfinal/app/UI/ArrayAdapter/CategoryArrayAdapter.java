package net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Category;
import net.azurewebsites.specialtopicfinal.app.R;

import java.util.ArrayList;


/**
 * Created by Mike on 19/05/2014.
 */
public class CategoryArrayAdapter extends ArrayAdapter<Category>  {

    Activity myActivity;
    ArrayList<Category> myCategories;
    public CategoryArrayAdapter(Activity activity, int resource, ArrayList<Category> list) {
        super(activity, resource, list);
        this.myActivity = activity;
        this.myCategories = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null)
        {
            itemView = myActivity.getLayoutInflater().inflate(R.layout.listview_item_category,parent,false);

        }
        Category currentCategory = myCategories.get(position);
        TextView textView = (TextView)itemView.findViewById(R.id.item_txtCategory);
        textView.setText(currentCategory.getName());
        return itemView;
    }
}
