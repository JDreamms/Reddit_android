package com.example.david.reddit_android;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.commonsware.cwac.anddown.AndDown;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie Davidson on 14/04/2016.
 * 20099176
 */
public class CommentFragment extends Fragment implements View.OnClickListener    {

        ListView cList;
        ArrayAdapter<Comment> adapter;
        String url;
        Handler handler;
        List<Comment> comments;
        CommentsLoader cHolder;
        AndDown x = new AndDown();


       public CommentFragment(){
            handler=new Handler();
            comments =new ArrayList<Comment>();
        }

    public ListView getcList() {return cList;}


    public static Fragment newInstance(String url){
        CommentFragment comFrag=new CommentFragment();
        comFrag.url=url;
        comFrag.cHolder =new CommentsLoader(comFrag.url);

        return comFrag;


    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.comments
                , container
                , false);
        //find pList
        cList =(ListView)v.findViewById(R.id.commentsList);
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
        cList.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
        if(comments.size()==0){

            //new thread

            new Thread(){
                public void run(){
                    comments.addAll(cHolder.fetchComments());
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




   //creates adapter for comments
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
                    htmlText=(TextView)convertView.findViewById(R.id.htmlText);
                    TextView author;
                    author=(TextView)convertView.findViewById(R.id.author);
                    TextView points;
                    points=(TextView)convertView.findViewById(R.id.points);
                    TextView postedOn;
                    postedOn=(TextView)convertView.findViewById(R.id.postedOn);
                    String hText =  comments.get(position).getHtmlText();
                    Spanned sp = Html.fromHtml(hText);
                    //System.out.println(x.markdownToHtml(comments.get(position).getHtmlText()));
                    //double decoding ahead
                    //had issue with ability to parse comment formatting, issue with decoding only once, solution is to double encode
                    htmlText.setText (Html.fromHtml(hText));
                    htmlText.setText(Html.fromHtml(htmlText.getText().toString()));
                    author.setText(comments.get(position).getAuthor());
                    points.setText(comments.get(position).getPoints());
                    postedOn.setText(comments.get(position).getPostedOn());
                    return convertView;
                }
            };
            cList.setAdapter(adapter);
        }

    @Override
    public void onClick(View v) {

    }
}

