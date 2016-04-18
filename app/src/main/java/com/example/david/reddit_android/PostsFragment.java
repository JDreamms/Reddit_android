package com.example.david.reddit_android;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jamie Davidson
 *   20099176
 * RGU - CASD year 4
 * Honours
 */
public class PostsFragment extends Fragment implements View.OnClickListener {
    ListView postsList;
    ArrayAdapter<Post> adapter;
    Handler handler;


    String subreddit;
    List<Post> posts;
    PostsHolder postsHolder;




    public PostsFragment(){
        handler=new Handler();
        posts=new ArrayList<Post>();
    }

    public ListView getPostsList() {
        return postsList;
    }


    public static Fragment newInstance(String subreddit){
        PostsFragment pf=new PostsFragment();
        pf.subreddit=subreddit;
        pf.postsHolder=new PostsHolder(pf.subreddit);
        return pf;

    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.posts
                , container
                , false);
        postsList=(ListView)v.findViewById(R.id.posts_list);
        return v;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        postsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Intent intent = new Intent(getActivity(), CommentFragment.class);
                startActivity(intent);


            }
        });
    }

    private void initialize(){
        // This should run only once for the fragment as the
        // setRetainInstance(true) method has been called on
        // this fragment

        if(posts.size()==0){

            // Must execute network tasks outside the UI
            // thread. So create a new thread.

            new Thread(){
                public void run(){
                    posts.addAll(postsHolder.fetchPosts());

                    // UI elements should be accessed only in
                    // the primary thread, so we must use the
                    // handler here.

                    handler.post(new Runnable(){
                        public void run(){
                            createAdapter();
                        }
                    });
                }
            }.start();
        }else{
            createAdapter();
        }
    }

    /**
     * This method creates the adapter from the list of posts
     * , and assigns it to the list.
     */
    private void createAdapter(){


        if(getActivity()==null) return;

        adapter=new ArrayAdapter<Post>(getActivity()
                ,R.layout.post_item
                , posts){
            @Override
            public View getView(int position,
                                View convertView,
                                ViewGroup parent) {

                if(convertView==null){
                    convertView=getActivity()
                            .getLayoutInflater()
                            .inflate(R.layout.post_item, null);
                }

                TextView postTitle;
                postTitle=(TextView)convertView
                        .findViewById(R.id.post_title);

                TextView postDetails;
                postDetails=(TextView)convertView
                        .findViewById(R.id.post_details);

                TextView postScore;
                postScore=(TextView)convertView
                        .findViewById(R.id.post_score);

                postTitle.setText(posts.get(position).title);
                postDetails.setText(posts.get(position).getDetails());
                postScore.setText(posts.get(position).getScore());
                return convertView;
            }
        };
        postsList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }


}
