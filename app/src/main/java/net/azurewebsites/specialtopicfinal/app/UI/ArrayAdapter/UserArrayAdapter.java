package net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Category;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Hire;
import net.azurewebsites.specialtopicfinal.app.BusinessObjects.User;
import net.azurewebsites.specialtopicfinal.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 13/06/2014.
 */
public class UserArrayAdapter extends ArrayAdapter<Hire> {

    Activity myActivity;
    ArrayList<Hire> hires;
    public UserArrayAdapter(Activity activity, int resource, ArrayList<Hire> list) {
        super(activity, resource, list);
        this.myActivity = activity;
        this.hires = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null)
        {
            itemView = myActivity.getLayoutInflater().inflate(R.layout.listview_item_user,parent,false);

        }

        return itemView;
    }
}
