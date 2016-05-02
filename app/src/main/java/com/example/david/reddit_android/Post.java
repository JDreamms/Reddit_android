package com.example.david.reddit_android;

/**
 * Created by Jamie Davidson
 *   20099176
 * RGU - CASD year 4
 * Honours
 */
public class Post {
    //post
    int numComments;
    String permalink;
    String url;
    String domain;
    String id;
    String preview;
    String media;
    String subreddit;
    String title;
    String author;
    int points;


    String getMedia(){
        return media;
    }
    //returns what will be shown under posts
    String getDetails(){
        String details=  "User: " + author + " " + numComments +" replies";
        return details;
    }
    String getPreview(){
        return preview;
    }
    public String setPreview(String preview){
        preview = this.preview;
        return preview;
    }
    public String postToString(){
        System.out.println("id: " + id + " URL " + url + " title " + title);
        return id;
    }

    public String getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }

    public String getUrl(){
        return url;
    }
    public String getPermalink(){
        return permalink;
    }

    String getScore(){
        return Integer.toString(points);
    }

    public String getSubreddit(){
        return subreddit;
    }
    public String getAuthor(){
        return author;
    }
    public String getDomain(){
        return domain;
    }

}

