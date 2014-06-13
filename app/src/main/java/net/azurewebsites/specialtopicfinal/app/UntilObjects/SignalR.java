package net.azurewebsites.specialtopicfinal.app.UntilObjects;

import com.google.gson.JsonElement;
import net.azurewebsites.specialtopicfinal.app.UI.Activity.MainActivity;
import java.util.Scanner;
import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.MessageReceivedHandler;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;

/**
 * Created by Mike on 27/05/2014.
 */
public class SignalR {

    public HubConnection conn;
    public HubProxy proxy;
    MainActivity activity;
    //Test Code
    // Create a new console logger
    /*Logger logger = new Logger() {

        @Override
        public void log(String message, LogLevel level) {
            System.out.println(message);
        }
    };
*/

    public SignalR(MainActivity activity) {

        this.activity = activity;
    }

    public void signalRConnection() {

        // Connect to the server
        //HubConnection conn = new HubConnection("http://samshire.azurewebsites.net/","",true,logger);
        HubConnection conn = new HubConnection("http://samshire.azurewebsites.net/");

        // Create the hub proxy
        HubProxy proxy = conn.createHubProxy("ProductHub");

        proxy.subscribe(new Object() {
            @SuppressWarnings("unused")
            public void messageReceived(String name, String message) {
                System.out.println(name + ": " + message);
            }
        });

        // Subscribe to the error WEB_SERVICE_EVENT
        conn.error(new ErrorCallback() {

            @Override
            public void onError(Throwable error) {
                error.printStackTrace();
            }
        });


        // Start the connection
        conn.start()
                .done(new Action<Void>() {

                    @Override
                    public void run(Void obj) throws Exception {
                        System.out.println("Done Connecting!");
                    }
                });

        // Subscribe to the received WEB_SERVICE_EVENT
        conn.received(new MessageReceivedHandler() {

            @Override
            public void onMessageReceived(JsonElement json) {
                System.out.println("RAW received message: " + json.toString());
                try {
                    activity.upDateStock(json.toString());
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }


            }
        });

// Read lines and send them as messages.
        Scanner inputReader = new Scanner(System.in);
        String line = inputReader.nextLine();
        while (!"exit".equals(line)) {
            proxy.invoke("send", "Console", line).done(new Action<Void>() {

                @Override
                public void run(Void obj) throws Exception {
                    System.out.println("SENT!");
                }
            });

            line = inputReader.next();
        }
        inputReader.close();

        conn.stop();

    }

    public void stopConnection() {
        conn.stop();
    }

}
