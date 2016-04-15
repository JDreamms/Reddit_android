package com.example.david.reddit_android;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie Davidson on 14/04/2016.
 * 20099176
 */
public class CommentFragment extends Fragment {


        ListView commentsList;
        ArrayAdapter<Comment> adapter;
        Handler handler;

        String subreddit;
        List<Comment> comments;
        CommentsLoader commentsHolder;

       public CommentFragment(){
            handler=new Handler();
            comments =new ArrayList<Comment>();
        }




        /**
         * This method creates the adapter from the list of posts
         * , and assigns it to the list.
         */
        private void createAdapter(){


            if(getActivity()==null) return;

            adapter=new ArrayAdapter<Comment>(getActivity()
                    ,R.layout.comments
                    , comments){
                @Override
                public View getView(int position,
                                    View convertView,
                                    ViewGroup parent) {

                    if(convertView==null){
                        convertView=getActivity()
                                .getLayoutInflater()
                                .inflate(R.layout.comments, null);
                    }

                    TextView htmlText;
                    htmlText=(TextView)convertView
                            .findViewById(R.id.htmlText);

                    TextView author;
                    author=(TextView)convertView
                            .findViewById(R.id.author);

                    TextView points;
                    points=(TextView)convertView
                            .findViewById(R.id.points);

                    TextView postedOn;
                    postedOn=(TextView)convertView
                            .findViewById(R.id.postedOn);

                    htmlText.setText(comments.get(position).getHtmlText());
                    author.setText(comments.get(position).getAuthor());
                    points.setText(comments.get(position).getPoints());
                    postedOn.setText(comments.get(position).getPostedOn());
                    return convertView;
                }
            };
            commentsList.setAdapter(adapter);
        }
    }

