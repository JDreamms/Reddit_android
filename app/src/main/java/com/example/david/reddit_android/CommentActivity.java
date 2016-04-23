package com.example.david.reddit_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ListView commentList = (ListView)findViewById(R.id.commentsList);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("url")!= null)
        {

            //TODO here get the string stored in the string variable and do
            // setText() on userName
        }
    }


    void addFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_holder
                        , PostsFragment.newInstance("Comment"))
                .commit();
    }
}
