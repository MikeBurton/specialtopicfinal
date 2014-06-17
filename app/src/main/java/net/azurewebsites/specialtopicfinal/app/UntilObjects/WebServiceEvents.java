package net.azurewebsites.specialtopicfinal.app.UntilObjects;

import android.graphics.Bitmap;

/**
 * Created by Mike on 08/05/2014.
 */
public interface WebServiceEvents {
    public void WebServiceStartedRequest();

    /**
     * Used to inform that the webservice finished getting data (AsyncTask)
     * @param METHOD_NAME Webservice method name
     * @param WEB_SERVICE_RESULT result
     */
    public void WebServiceFinished(String METHOD_NAME, String WEB_SERVICE_RESULT);

    /**
     * Fires when a exception is thrown (AsyncTask)
     * @param WEB_SERVICE_EXCEPTION EXCEPTION
     */
    public void WebServiceFinishedWithException(Exception WEB_SERVICE_EXCEPTION
    );

    /**
     * Fires when web service call is completely finished (AsyncTask)
     */
    public void WebServiceEndRequest();

    /**
     * Used to send images (AsyncTask), Note: make sure you contact the UI Thread when changing images
     * @param WEB_SERVICE_IMAGE  Bitmap image
     * @param METHOD_NAME Webservice method name
     */
    public void WebServiceImages(Bitmap WEB_SERVICE_IMAGE,String METHOD_NAME
    );

}
