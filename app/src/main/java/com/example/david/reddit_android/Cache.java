package com.example.david.reddit_android;

import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

/**
 * Created by Jamie Davidson on 12/04/2016.
 * 20099176
 */
public class Cache {

    // CREDIT TO http://www.whycouch.com/2012/12/how-to-create-android-client-for-reddit_25.html HATHY A for providing a cache mechanism

    static private String cacheD = "/Android/data/com.jdepths.redditAndroid/cache/";


    static {
        if(Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED)){
            cacheD =Environment.getExternalStorageDirectory()
                    + cacheD;
            File f=new File(cacheD);
            f.mkdirs();
        }
    }

    static public String convert(String url){
        try {
            MessageDigest digest=MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            byte[] b=digest.digest();
            BigInteger bi=new BigInteger(b);
            return "mycache_"+bi.toString(16)+".cac";
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
            return null;
        }
    }

    private static boolean old(long time){
        long x=new Date().getTime();
        long diff=x-time;
        if(diff>2000*70*70)
            return true;
        return false;
    }

    public static byte[] read(String url){
        try{
            String file= cacheD +"/"+ convert(url);
            File f=new File(file);
            if(!f.exists() || f.length() < 1) return null;
            if(f.exists() && old(f.lastModified())){f.delete();
            }
            byte data[]=new byte[(int)f.length()];
            DataInputStream fis=new DataInputStream(
                    new FileInputStream(f));
            fis.readFully(data);
            fis.close();
            return data;
        }catch(Exception e) { return null; }
    }

    public static void write(String url, String data){
        try{
            String file= cacheD +"/"+ convert(url);
            PrintWriter pw=new PrintWriter(new FileWriter(file));
            pw.print(data);
            pw.close();
        }catch(Exception e) { }
    }
}

