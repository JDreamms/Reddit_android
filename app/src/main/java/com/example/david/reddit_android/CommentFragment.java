package com.example.david.reddit_android;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie Davidson on 14/04/2016.
 * 20099176
 */
public class CommentFragment extends Fragment implements View.OnClickListener    {

        ListView commentsList;
        ArrayAdapter<Comment> adapter;
        String url;
        Handler handler;
        List<Comment> comments;
        CommentsLoader commentsHolder;



       public CommentFragment(){
            handler=new Handler();
            comments =new ArrayList<Comment>();
        }

    public ListView getCommentsList() {return  commentsList;}


    public static Fragment newInstance(String url){
        CommentFragment cf=new CommentFragment();
        cf.url=url;
        cf.commentsHolder=new CommentsLoader(cf.url);
        System.out.println(cf);
        return cf;


    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.comments
                , container
                , false);
        //find postsList
        commentsList=(ListView)v.findViewById(R.id.commentsList);
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
        commentsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
            {
                /*Post clickedObject = (Post) adapter.getItem(position);

                Intent intent = new Intent(getActivity(), CommentActivity.class);
                intent.putExtra("url",clickedObject.getTitle());

                startActivity(intent);*/
                //todo add in view profile/image linked
            }
        });
    }

    private void initialize(){
        // This should run only once for the fragment as the
        // setRetainInstance(true) method has been called on
        // this fragment

        if(comments.size()==0){

            // Must execute network tasks outside the UI
            // thread. So create a new thread.

            new Thread(){
                public void run(){
                    comments.addAll(commentsHolder.fetchComments());

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

            adapter=new ArrayAdapter<Comment>(getActivity()
                    ,R.layout.comment_item
                    , comments){
                @Override
                public View getView(int position,
                                    View convertView,
                                    ViewGroup parent) {

                    if(convertView==null){
                        convertView=getActivity()
                                .getLayoutInflater()
                                .inflate(R.layout.comment_item, null);
                    }

                    TextView htmlText;
                    htmlText=(TextView)convertView
                            .findViewById(R.id.htmlText);
                    System.out.println(htmlText);
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

    @Override
    public void onClick(View v) {

    }
}

