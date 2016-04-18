package com.example.david.reddit_android;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * Created by Jamie Davidson on 13/04/2016.
 * 20099176
 */
public class CommentsLoader {

    // This will be the URL of the comments page, suffixed with .json
    private String url;

    CommentsLoader(String u) {
        url = u;
    }



    // Load various details about the comment
    private Comment loadComment(JSONObject data, int level) {
        Comment comment = new Comment();
        try {
            comment.htmlText = data.getString("body_html");
            comment.author = data.getString("author");
            comment.points = (data.getInt("ups")
                    - data.getInt("downs"))
                    ;
            comment.postedOn = new Date((long) data
                    .getDouble("created_utc"))
                    .toString();
            comment.level = level;
        } catch (Exception e) {
            Log.d("ERROR", "Unable to parse comment : " + e);
        }
        return comment;
    }

    // This is where the comment is actually loaded
    // For each comment, its replies are recursively loaded
    private void process(ArrayList<Comment> comments
            , JSONArray c, int level)
            throws Exception {
        for (int i = 0; i < c.length(); i++) {
            if (c.getJSONObject(i).optString("kind") == null)
                continue;
            if (c.getJSONObject(i).optString("kind").equals("t1") == false)
                continue;
            JSONObject data = c.getJSONObject(i).getJSONObject("data");
            Comment comment = loadComment(data, level);
            if (comment.author != null) {
                comments.add(comment);
                addReplies(comments, data, level + 1);
            }
        }
    }

    // Add replies to the comments
    private void addReplies(ArrayList<Comment> comments,
                            JSONObject parent, int level) {
        try {
            if (parent.get("replies").equals("")) {
                // This means the comment has no replies
                return;
            }
            JSONArray r = parent.getJSONObject("replies")
                    .getJSONObject("data")
                    .getJSONArray("children");
            process(comments, r, level);
        } catch (Exception e) {
            Log.d("ERROR", "addReplies : " + e);
        }
    }

    // Load the comments as an ArrayList, so that it can be
    // easily passed to the ArrayAdapter

    ArrayList<Comment> fetchComments() {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {

            // Fetch the contents of the comments page
            String raw = RemoteData.readContents(url);

            JSONArray r = new JSONArray(raw)
                    .getJSONObject(1)
                    .getJSONObject("data")
                    .getJSONArray("children");

            // All comments at this point are at level 0
            // (i.e., they are not replies)
            process(comments, r, 0);

        } catch (Exception e) {
            Log.d("ERROR", "Could not connect: " + e);
        }
        return comments;
    }
}
