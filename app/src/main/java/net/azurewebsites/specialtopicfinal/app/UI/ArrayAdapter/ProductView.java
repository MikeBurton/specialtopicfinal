package net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter;

import android.view.View;

/**
 * Created by Mike on 03/06/2014.
 */
public class ProductView {
    public View view;
    public int index;

    public View getView() {
        return view;
    }

    public int getIndex() {
        return index;
    }

    public ProductView(View view, int index) {
        this.view = view;
        this.index = index;
    }
}
