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
public class PostsHolder {
    private final String URL_TEMPLATE =
            "http://www.reddit.com/"
                    + ".json"
                    + "?after=t3_AFTER";
    String afterx;
    String subreddit;
    String url;
    String after;
    String r = ".com/r/";
    String count = ".json?count=25&";
    //List list;
    List<Post> list = new ArrayList<Post>();

    PostsHolder(String sr) {
        subreddit = sr;
        after = "";
        afterx = "after";
        generateURL(after,afterx);
    }

    /**
     * Generates the actual URL from the template based on the
     * subreddit name and the 'after' property.
     */
    private void generateURL(String after, String afterx) {
        url = "" + URL_TEMPLATE.replace("x", subreddit);
        if (subreddit.equals("")) {
            url = url.replace("AFTER", after);
            url = url.replace(".json", count);
            url = url.replace("?after", afterx);

        } else {
            url = url.replace(".com/", r);
            url = url.replace("AFTER", after);
        }
    }

    /**
     * Returns a list of Post objects after fetching data from
     * Reddit using the JSON API.
     */
    List<Post> fetchPosts() {

        String raw = RemoteData.readContents(url);
        if (list.size() < 1) {


            //List<Post> list = new ArrayList<Post>();
            try {
                JSONObject data = new JSONObject(raw)
                        .getJSONObject("data");
                JSONArray children = data.getJSONArray("children");
                System.out.println("adding to adapter ");
                System.out.println(url);

                //Using this property we can fetch the next set of
                //posts from the same subreddit
                //after=data.getString("after");

                for (int i = 0; i < children.length(); i++) {
                    JSONObject current = children.getJSONObject(i)
                            .getJSONObject("data");
                    Post x = new Post();
                    x.title = current.optString("title");
                    x.url = current.optString("url");
                    x.numComments = current.optInt("num_comments");
                    x.points = current.optInt("score");
                    x.author = current.optString("author");
                    x.subreddit = current.optString("subreddit");
                    x.permalink = current.optString("permalink");
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
                Log.e("fetchPosts()", e.toString());
            }
            return list;
        }
        else {

            try {
                JSONObject data = new JSONObject(raw)
                        .getJSONObject("data");
                JSONArray children = data.getJSONArray("children");
                System.out.println("adding to json reader ");

                System.out.println(url);

                //Using this property we can fetch the next set of
                //posts from the same subreddit
                //after=data.getString("after");

                for (int i = 0; i < children.length(); i++) {
                    JSONObject current = children.getJSONObject(i)
                            .getJSONObject("data");
                    Post x = new Post();
                    x.title = current.optString("title");
                    x.url = current.optString("url");
                    x.numComments = current.optInt("num_comments");
                    x.points = current.optInt("score");
                    x.author = current.optString("author");
                    x.subreddit = current.optString("subreddit");
                    x.permalink = current.optString("permalink");
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
                Log.e("fetchPosts()", e.toString());
            }
            return list;

        }




    }
    public List<Post> fetchMorePosts (String after, String afterx){
        generateURL(after, afterx);
        fetchPosts();
        return fetchPosts();
    }

}