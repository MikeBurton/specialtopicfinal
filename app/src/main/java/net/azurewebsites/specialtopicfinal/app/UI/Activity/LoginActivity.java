package net.azurewebsites.specialtopicfinal.app.UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import net.azurewebsites.specialtopicfinal.app.R;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.DataStore;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebService;
import net.azurewebsites.specialtopicfinal.app.UntilObjects.WebServiceEvents;


public class LoginActivity extends Activity implements WebServiceEvents {
    EditText txtPassword;
    EditText txtUserName;
    TextView txtError;
    Button btnLogin;
    Button btnRegister;
    RelativeLayout content;
    RelativeLayout main;
    ProgressBar loading;
    TextView txtLoadingMessage;
    WebService webService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtUserName = (EditText) findViewById(R.id.txtEmail);
        content = (RelativeLayout) findViewById(R.id.content);
        main = (RelativeLayout)findViewById(R.id.main);
        loading = (ProgressBar) findViewById(R.id.loading);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(buttonAddOnClickListener);
        btnRegister.setOnClickListener(buttonAddOnClickListener);
        txtError = (TextView) findViewById(R.id.txtError);
        txtLoadingMessage = (TextView) findViewById(R.id.txtLoadingMessage);
        content.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        txtLoadingMessage.setVisibility(View.GONE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.other, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_cartCount) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if(webService != null)
        {
            webService.getCurrentAsyncTask().cancel(true);
        }

        super.onBackPressed();
    }
    /*
  * Button listener
  * Handles all the on click events for the login activity
  * */
    Button.OnClickListener buttonAddOnClickListener  = new Button.OnClickListener(){
        @Override
        public void onClick(View btn) {
            switch (btn.getId()) {
                case R.id.btnLogin:
                    sendLoginRequest(String.valueOf(txtUserName.getText()), String.valueOf(txtPassword.getText()));
                    break;
                case R.id.btnRegister:
                    navRegisterActivity();
                    break;
            }
        }
    };

    /**
     * Handles login
     *Contracts webservice with a Request
     *Uses WebService object
     * @param user username
     * @param pass password
     */
     public void sendLoginRequest(String user, String pass)
    {
        if (!isNetworkAvailable())
        {
            Toast.makeText(LoginActivity.this, "No internet connection, check your connection and try again", Toast.LENGTH_LONG).show();
            return;
        }
        //check for empty fields
        if (user.matches(""))
        {
            displayFailedLogin("Username can't be empty");

        }else if (pass.matches(""))
        {
            displayFailedLogin("Password can't be empty");

        }else
        {
            //save current username to data store
            DataStore.CURRENT_USER_ID = user;
            //call webservice
            webService = new WebService(this,6000,false);
            webService.loginRequest(user, pass);
            //start LOADING_PROGRESS_BAR screen
            loadingScreen();
        }


    }
    /**
   * Receives webservice response
   * Contracts webservice
   *@param response Response determines if login was successful
   * */
    public void loginResponse(String response)
    {

        if (response.matches("Gy_-9W_W_Hr6-___C8-jf7CB5S-T97b8-_xbl9Hh--__"))
        {

            navMainActivity();

        } else if (response.matches("b_Fe7x4M2zuUp7fd-k15__-17wics_E6X9uRM_o9v-P-"))
        {
            loadingScreen();

            displayFailedLogin("Password is incorrect");

        } else if (response.matches( "C1FQU4_723I_I_Q_7Fw_92M_jg0_O-N_-_o_U_7Qa3s5")) {

            loadingScreen();

            displayFailedLogin("Username doesn't exist");

        }
    }

    /**
     * Navigates to the MainActivity
     */
    public void navMainActivity()
    {
        final Context context = this;
        txtError.setText("");
        Intent intent = new Intent(context,MainActivity.class);
        startActivity(intent);
        this.finish();


    }

    /**
     * Displays error message if login request was unsuccessful
     * @param message error message
     */
    public void displayFailedLogin(String message)
    {
        txtError.setText(message);

    }

    /**
     * Navigates to the RegisterActivity
     */
    public void navRegisterActivity(){
        final Context context = this;
        Intent intent = new Intent(context,RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Used to hide and show loading screen
     */
    public  void loadingScreen()
    {
        if(content.getVisibility() == View.VISIBLE)
        {
            content.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            txtLoadingMessage.setVisibility(View.VISIBLE);
        }else
        {
            content.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            txtLoadingMessage.setVisibility(View.GONE);

        }

    }
    @Override
    public void WebServiceStartedRequest() {

    }

    @Override
    public void WebServiceFinished(String METHOD_NAME, String WEB_SERVICE_RESULT) {

        if(METHOD_NAME == "LoginRequest")
        {
           loginResponse(WEB_SERVICE_RESULT);
        }

    }

    @Override
    public void WebServiceFinishedWithException(Exception ex) {
        loadingScreen();
        Toast.makeText(getApplicationContext(), ex.toString(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void WebServiceEndRequest() {

    }

    /**
     * Check if an internet connection exists
     * @return boolean
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void WebServiceImages(Bitmap WEB_SERVICE_IMAGE, String METHOD_NAME) {

    }
}
