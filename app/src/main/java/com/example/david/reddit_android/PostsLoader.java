package com.example.david.reddit_android;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * Created by Jamie Davidson
 *   20099176
 * RGU - CASD year 4
 * Honours
 */
public class PostsLoader {
    private final String URL_TEMPLATE =
            "http://www.reddit.com/"
                    + ".json"
                    + "?x=t3_AFTER";
    String afterx;
    String subreddit;
    String url;
    String x;
    String r = ".com/r/";
    String count = ".json?count=25&";
    //List list;
    List<Post> list = new ArrayList<Post>();

    PostsLoader(String sr) {
        subreddit = sr;
        x = "";
        afterx = "x";
        genURL(x,afterx);
    }

    //generate url
    private void genURL(String after, String afterx) {
        url = "" + URL_TEMPLATE.replace("x", subreddit);
        //if there is a subreddit selected
        if (subreddit.equals("")) {
            url = url.replace("AFTER", after);
            url = url.replace(".json", count);
            url = url.replace("?x", afterx);
        //if no subreddit selected
        } else {
            url = url.replace(".com/", r);
            url = url.replace("/r/", "/r/" + subreddit);
            url = url.replace("AFTER", after);
            url = url.replace(".json", count);
            url = url.replace("?x", afterx);

        }
    }

    //returns posts
    List<Post> getPosts() {

        String raw = GetData.Read(url);
        if (list.size() < 1) {



            try {
                JSONObject data = new JSONObject(raw)
                        .getJSONObject("data");
                JSONArray children = data.getJSONArray("children");


                // gets next set of data
                for (int i = 0; i < children.length(); i++) {
                    JSONObject current = children.getJSONObject(i)
                            .getJSONObject("data");
                    Post x = new Post();
                    x.title = current.optString("title");
                    x.subreddit = current.optString("subreddit");
                    x.numComments = current.optInt("num_comments");
                    x.points = current.optInt("score");
                    x.permalink = current.optString("permalink");
                    x.url = current.optString("url");
                    x.author = current.optString("author");
                    x.domain = current.optString("domain");
                    x.id = current.optString("id");
                    if (current.optString("thumbnail").equals("") || current.optString("thumbnail").equals("self") || current.optString("thumbnail").contains("self.")) {
                        x.preview = "";
                    } else {
                        x.preview = current.optString("thumbnail");
                    }
                    if (x.title != null)
                        list.add(x);


                }
            } catch (Exception e) {
            }
            return list;
        }
        else {

            try {
                JSONObject data = new JSONObject(raw)
                        .getJSONObject("data");
                JSONArray children = data.getJSONArray("children");
                for (int i = 0; i < children.length(); i++) {
                    JSONObject current = children.getJSONObject(i)
                            .getJSONObject("data");
                    Post x = new Post();
                    x.title = current.optString("title");
                    x.points = current.optInt("score");
                    x.author = current.optString("author");
                    x.subreddit = current.optString("subreddit");
                    x.domain = current.optString("domain");
                    x.url = current.optString("url");
                    x.numComments = current.optInt("num_comments");

                    x.permalink = current.optString("permalink");
                    x.id = current.optString("id");
                    if (current.optString("thumbnail").equals("") || current.optString("thumbnail").equals("self") || current.optString("thumbnail").contains("self.")) {
                        x.preview = "";
                    } else {
                        x.preview = current.optString("thumbnail");
                    }


                    if (x.title != null)
                        list.add(x);


                }
            } catch (Exception e) {
                Log.e("getPosts()", e.toString());
            }
            return list;

        }




    }
    public List<Post> getMorePosts(String after, String afterx){
        genURL(after, afterx);
        getPosts();
        return getPosts();
    }

}