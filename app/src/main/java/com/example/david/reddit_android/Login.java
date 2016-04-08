package com.example.david.reddit_android;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jamie Davidson on 03/04/2016.
 * 20099176
 */
public class Login {

    private final String REDDIT_LOGIN_URL = "https://ssl.reddit.com/api/login";


    private String redditCookie = "";

    private boolean login(String username, String password){
        HttpURLConnection connection = getConnection(REDDIT_LOGIN_URL);

        if(connection == null)
            return false;
        String data="user="+username+"&passwd="+password;

        if(!writeToConnection(connection, data))
            return false;

        String cookie=connection.getHeaderField("set-cookie");

        if(cookie==null)
            return false;

        cookie=cookie.split(";")[0];
        if(cookie.startsWith("reddit_first")){
            Log.d("Error", "Unable to login.");
            return false;
        }else if(cookie.startsWith("reddit_session")){
            Log.d("Success", cookie);
            redditCookie = cookie;
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
