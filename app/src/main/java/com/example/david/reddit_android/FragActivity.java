package com.example.david.reddit_android;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Jamie Davidson
 * 20099176
 * RGU - CASD year 4
 * Honours
 */
public class FragActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment();
    }

    void addFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_holder
                        , PostsFragment.newInstance("Main"))
                .commit();
    }

}
