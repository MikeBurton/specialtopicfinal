package net.azurewebsites.specialtopicfinal.app.UI.Fragment;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebServiceEvents;

/**
 * Created by Mike on 31/05/2014.
 */
public class UserFragment extends Fragment implements WebServiceEvents
{
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
