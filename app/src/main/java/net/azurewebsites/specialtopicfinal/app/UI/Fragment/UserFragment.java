package net.azurewebsites.specialtopicfinal.app.UI.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UI.Activity.MainActivity;
import net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter.UserArrayAdapter;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebService;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebServiceEvents;

/**
 * Created by Mike on 31/05/2014.
 */
public class UserFragment extends Fragment implements WebServiceEvents
{
    MainActivity mainActivity;
    UserArrayAdapter userArrayAdapter;
    TextView txtName;
    TextView txtEmail;
    Button btnMyOrders;
    ListView listView;
    ProgressBar loading;
    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setUserArrayAdapter(UserArrayAdapter userArrayAdapter) {
        this.userArrayAdapter = userArrayAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnMyOrders = (Button) mainActivity.findViewById(R.id.btn_hires_user);
        btnMyOrders.setOnClickListener(buttonAddOnClickListener);
        txtEmail = (TextView)  mainActivity.findViewById(R.id.txt_email_user);
        txtName = (TextView)  mainActivity.findViewById(R.id.txt_name_user);
        txtName.setText("Name: "+ DataStore.CURRENT_USER.getFirstName()+" "+DataStore.CURRENT_USER.getLastName());
        txtEmail.setText("Email: "+DataStore.CURRENT_USER.getUserName());
        loading = (ProgressBar) mainActivity.findViewById(R.id.loading_user);
        loading.setVisibility(View.INVISIBLE);
        listView = (ListView) mainActivity.findViewById(R.id.listView);
        listView.setAdapter(userArrayAdapter);
        mainActivity.getWebService().getWEB_SERVICE_EVENT_MEMBERS().add(this);
    }
    /*
  * Button listener
  * Handles all the on click events for the UserFragment
  * */
    Button.OnClickListener buttonAddOnClickListener  = new Button.OnClickListener(){
        @Override
        public void onClick(View btn) {
            switch (btn.getId()) {
                case R.id.btn_hires_user :
                    loading.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    mainActivity.getWebService().getHiresByEmail(DataStore.CURRENT_USER_ID);
                    break;
            }
        }
    };

    @Override
    public void WebServiceStartedRequest() {

    }

    @Override
    public void WebServiceFinished(String METHOD_NAME, String WEB_SERVICE_RESULT) {

            userArrayAdapter.notifyDataSetChanged();
            listView.setAdapter(userArrayAdapter);
            listView.invalidateViews();
            loading.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void WebServiceFinishedWithException(Exception ex) {

    }

    @Override
    public void WebServiceEndRequest() {

    }

    @Override
    public void WebServiceImages(Bitmap WEB_SERVICE_IMAGE, String METHOD_NAME) {

    }
}
