package com.example.david.reddit_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView commentList = (ListView)findViewById(R.id.commentsList);
        Bundle bundle = getIntent().getExtras();
        String url;

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
