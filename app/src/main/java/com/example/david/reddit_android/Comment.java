package com.example.david.reddit_android;

/**
 * Created by Jamie Davidson on 13/04/2016.
 * 20099176
 */
public class Comment {
    String htmlText;
    String author;
    int points;
    String postedOn;

    // The 'level' field indicates how deep in the hierarchy
    // this comment is. A top-level comment has a level of 0
    // where as a reply has level 1, and reply of a reply has
    // level 2 and so on...

    int level;

    String getAuthor(){
        String details=author;

        return author;
    }

    String getHtmlText(){
        return htmlText;
    }

    String getPoints(){
        return Integer.toString(points);
    }
    String getPostedOn() {return postedOn;}


}
