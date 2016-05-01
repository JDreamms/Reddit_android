package com.example.david.reddit_android;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class CommentActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //handles which theme loads
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);
        if(useDarkTheme) {
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
        //oncreate method starts here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView commentList = (ListView)findViewById(R.id.commentsList);
        Bundle bundle = getIntent().getExtras();
        String url;
        //check if save state is empty
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                url= null;
            } else {
                url= extras.getString("url");
                url = "http://www.reddit.com" + url + ".json";
                addFragment(url);
            }
        } else {
            url= (String) savedInstanceState.getSerializable("url");
        }
    }


    void addFragment(String url){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_holder
                        , CommentFragment.newInstance(url))
                .commit();
    }

}
