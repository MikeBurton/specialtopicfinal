package net.azurewebsites.specialtopicfinal.app.UI.Fragment;



import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Category;
import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UI.Activity.MainActivity;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;


/**
 * Created by Mike on 19/05/2014.
 */
public class CategoryFragment extends ListFragment implements AdapterView.OnItemClickListener
{
    MainActivity MAIN_ACTIVITY;
    public void setMainActivity(MainActivity MAIN_ACTIVITY)
    {
        this.MAIN_ACTIVITY = MAIN_ACTIVITY;
    }
    ArrayAdapter ARRAY_ADAPTER;
    public void setArrayAdapter(ArrayAdapter arrayAdapter)
    {
        this.ARRAY_ADAPTER = arrayAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category,container,false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Category CATEGORY_CLICKED =  DataStore.ARRAYLIST_CURRENT_CATEGORIES.get(i);

        //MAIN_ACTIVITY.navProducts(CATEGORY_CLICKED.getId());
        MAIN_ACTIVITY.getProducts(CATEGORY_CLICKED.getId());

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       setListAdapter(ARRAY_ADAPTER);
       getListView().setOnItemClickListener(this);
    }

}
