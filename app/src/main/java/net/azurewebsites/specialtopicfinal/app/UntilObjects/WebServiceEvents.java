package net.azurewebsites.specialtopicfinal.app.UntilObjects;

import android.graphics.Bitmap;

/**
 * Created by Mike on 08/05/2014.
 */
public interface WebServiceEvents {
    public void WebServiceStartedRequest();

    public void WebServiceFinished(String METHOD_NAME, String WEB_SERVICE_RESULT);

    public void WebServiceFinishedWithException(Exception WEB_SERVICE_EXCEPTION
    );
    public void WebServiceEndRequest();

    public void WebServiceImages(Bitmap WEB_SERVICE_IMAGE,String METHOD_NAME
    );

}
