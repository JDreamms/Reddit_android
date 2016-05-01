package com.example.david.reddit_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity  {

    Button loginBtn;
    EditText usernameTxt;
    EditText passwordTxt;
    String username;
    String password;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginBtn = (Button) findViewById(R.id.login_button);
        usernameTxt = (EditText) findViewById(R.id.username);
        passwordTxt = (EditText) findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                login(username,password);

            }
        });

    }
    private final String REDDIT_LOGIN_URL = "https://ssl.reddit.com/api/login";


    private String redditCookie = "";

    private boolean login(String username, String password){
        HttpURLConnection connection = getConnection(REDDIT_LOGIN_URL);

        if(connection == null)
            return false;
        String data="user="+username+"&passwd="+password;
        System.out.println("added to login");

        if(!writeToConnection(connection, data))
            return false;

        String cookie=connection.getHeaderField("set-cookie");

        if(cookie==null)
            return false;
        System.out.println("cookie is empty :(");

        cookie=cookie.split(";")[0];
        if(cookie.startsWith("reddit_first")){
            Log.d("Error", "Unable to login.");
            System.out.println("unable to log in");
            return false;
        }else if(cookie.startsWith("reddit_session")){
            Log.d("Success", cookie);
            redditCookie = cookie;
            System.out.println("logged in");
            return true;

        }
        return false;
    }

    private HttpURLConnection getConnection(String url){
        URL u = null;
        try{
            u = new URL(url);
        }catch(MalformedURLException e){
            Log.d("Invalid URL", url);
            return null;
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)u.openConnection();
        } catch (IOException e) {
            Log.d("Unable to connect", url);
            return null;
        }
        // Timeout after 10 seconds
        connection.setReadTimeout(10000);
        connection.setDoOutput(true);
        return connection;
    }

    private boolean writeToConnection(HttpURLConnection con, String data){
        try{
            PrintWriter pw=new PrintWriter(
                    new OutputStreamWriter(
                            con.getOutputStream()
                    )
            );
            pw.write(data);
            pw.close();
            return true;
        }catch(IOException e){
            Log.d("Unable to write", e.toString());
            return false;
        }
    }

}
