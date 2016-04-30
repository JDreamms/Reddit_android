package com.example.david.reddit_android;

/**
 * Created by Jamie Davidson
 *   20099176
 * RGU - CASD year 4
 * Honours
 */
public class Post {
    String subreddit;
    String title;
    String author;
    int points;
    int numComments;
    String permalink;
    String url;
    String domain;
    String id;
    String preview;
    String media;

    String getMedia(){
        return media;
    }
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
    public String postToString(){
        System.out.println("id: " + id + " URL " + url + " title " + title);
        return id;
    }
}

