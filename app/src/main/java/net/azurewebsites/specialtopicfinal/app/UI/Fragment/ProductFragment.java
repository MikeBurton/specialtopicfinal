package net.azurewebsites.specialtopicfinal.app.UI.Fragment;



import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import net.azurewebsites.specialtopicfinal.app.BusinessObjects.Product;
import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UI.Activity.MainActivity;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;


public class ProductFragment extends ListFragment implements AdapterView.OnItemClickListener
{
    MainActivity activity;
    public void setActivity(MainActivity activity)
    {
        this.activity = activity;
    }
    ArrayAdapter arrayAdapter;
    public void setArrayAdapter(ArrayAdapter arrayAdapter)
    {
        this.arrayAdapter = arrayAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product,container,false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Product clickedProduct =  DataStore.ARRAYLIST_CURRENT_PRODUCTS.get(i);
        DataStore.CURRENT_PRODUCT = clickedProduct;

       activity.getProductDescriptionFragment();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        arrayAdapter.notifyDataSetChanged();
        setListAdapter(arrayAdapter);
        getListView().invalidateViews();
        getListView().setOnItemClickListener(this);
    }
    public void refresh(){
        arrayAdapter.notifyDataSetChanged();
        setListAdapter(arrayAdapter);
        getListView().invalidateViews();
    }
}