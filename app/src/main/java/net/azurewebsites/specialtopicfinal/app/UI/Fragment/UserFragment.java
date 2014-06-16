package net.azurewebsites.specialtopicfinal.app.UI.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UI.Activity.MainActivity;
import net.azurewebsites.specialtopicfinal.app.UI.ArrayAdapter.UserArrayAdapter;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebServiceEvents;

/**
 * Created by Mike on 31/05/2014.
 */
public class UserFragment extends Fragment implements WebServiceEvents
{
    MainActivity mainActivity;
    UserArrayAdapter userArrayAdapter;
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

    }
    /*
  * Button listener
  * Handles all the on click events for the UserFragment
  * */
    Button.OnClickListener buttonAddOnClickListener  = new Button.OnClickListener(){
        @Override
        public void onClick(View btn) {

        }
    };
    @Override
    public void WebServiceStartedRequest() {

    }

    @Override
    public void WebServiceFinished(String METHOD_NAME, String WEB_SERVICE_RESULT) {

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
