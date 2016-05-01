package com.example.david.reddit_android;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    boolean fin = false;
    Button loadmore;

    String subreddit;
    List<Post> posts;
    PostsHolder postsHolder;
    private FragmentManager supportFragmentManager;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.posts, container, false);
        //find postsList
        postsList=(ListView)v.findViewById(R.id.posts_list);
        loadmore=(Button)v.findViewById(R.id.btn);
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
                Post clickedObject = (Post) adapter.getItem(position);

                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("url",clickedObject.getPermalink());

                startActivity(intent);


            }
        });

        postsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                Post clickedObject = (Post) adapter.getItem(position);
                Uri uri = Uri.parse(clickedObject.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                return true;
            }
        });

        loadmore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int l = (posts.size());
                l = l -1;
                String x = posts.get(l).getId();
                String afterx = "after";
                postsHolder.fetchMorePosts(x,afterx);

                posts.addAll(postsHolder.fetchPosts());
                createAdapter();




            }
        });

    }




    private void initialize(){
        if(posts.size()==0) new Thread() {
            public void run() {
                posts.addAll(postsHolder.fetchPosts());

                // UI elements should be accessed only in
                // the primary thread, so we must use the
                // handler here.

                handler.post(new Runnable() {public void run() {createAdapter();}});}}.start();
        else{
           createAdapter();
        }
    }



    private void createAdapter(){



        if(getActivity()==null) return;



            adapter = new ArrayAdapter<Post>(getActivity()
                    , R.layout.post_item
                    , posts) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    Bitmap bmp;
                    URL url;
                    //// TODO: 30/04/2016 add in infinite scrolling
                    if (convertView == null) {

                        convertView = getActivity().getLayoutInflater().inflate(R.layout.post_item, null);
                    }



                        TextView postTitle;
                        postTitle = (TextView) convertView.findViewById(R.id.post_title);

                        TextView postDetails;
                        postDetails = (TextView) convertView.findViewById(R.id.post_details);

                        ImageView thumbnail;
                        thumbnail = (ImageView) convertView.findViewById(R.id.post_image);

                        TextView postScore;
                        postScore = (TextView) convertView.findViewById(R.id.post_score);

                        postTitle.setText(posts.get(position).title);
                        postDetails.setText(posts.get(position).getDetails());
                        postScore.setText(posts.get(position).getScore());
                        try {
                                url = new URL(posts.get(position).getPreview());
                                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                thumbnail.setImageBitmap(image);
                        } catch (IOException e) {
                               // e.printStackTrace();
                                Bitmap x = BitmapFactory.decodeResource(getResources(),R.drawable.reddit);
                                thumbnail.setImageBitmap(x);
                        }
                        posts.get(position).setPreview("");
                        //todo fix me
                        //depreciated
                        //prints preview / thumbnail
                        /*System.out.println(posts.get(position).getPreview() + " this is a thumbnail for " + position);
                        if (posts.get(position).getPreview().contains("self") || posts.get(position).getPreview().contains("self.")) {
                            System.out.println(posts.get(position).getPreview() + " THIS IS A SELF POST");
                                new DownloadImageTask((ImageView) thumbnail)
                                        .execute("http://vectorlogo4u.com/wp-content/uploads/2015/12/Reddit-Icon-vector.png");

                        }
                        else if (posts.get(position).getPreview().equals("nsfw")) {
                                new DownloadImageTask((ImageView) thumbnail)
                                        .execute("http://ih0.redbubble.net/image.23213896.8080/flat,800x800,070,f.jpg");
                                return convertView;

                        }
                        else if (posts.get(position).getPreview().equals("")) {
                                new DownloadImageTask((ImageView) thumbnail)
                                        .execute("http://vectorlogo4u.com/wp-content/uploads/2015/12/Reddit-Icon-vector.png");
                                return convertView;



                        }
                        else if (posts.get(position).getPreview().contains(".jpg")) {

                                System.out.println("RAN POS: " + position + posts.get(position).getPreview());
                                new DownloadImageTask((ImageView) thumbnail)
                                        .execute(posts.get(position).getPreview());
                                return convertView;

                        }*/

                return convertView;
            }



            };

            postsList.setAdapter(adapter);



    }



    @Override
    public void onClick(View v) {

    }

    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }

    //depreciated
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
