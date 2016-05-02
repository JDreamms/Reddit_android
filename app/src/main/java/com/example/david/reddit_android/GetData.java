package com.example.david.reddit_android;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;
        import android.util.Log;

public class GetData {


    public static HttpURLConnection getCon(String url){
        System.out.println("URL: "+url);
        HttpURLConnection hcon = null;
        try {
            hcon=(HttpURLConnection)new URL(url).openConnection();
            hcon.setReadTimeout(30000); // Timeout at 30 seconds
            hcon.setRequestProperty("JD", "Reddit_Android");
        } catch (MalformedURLException e) {
            Log.e("getCon()", e.toString());
        } catch (IOException e) {
            Log.e("getCon()", e.toString());
        }
        return hcon;
    }

    public static String Read(String url){

        byte[] t= Cache.read(url);
        String cached=null;
        if(t!=null) {
            cached=new String(t);
            t=null;
        }
        if(cached!=null) {
            Log.d("MSG","Using cache for "+url);
            return cached;
        }



        HttpURLConnection hcon= getCon(url);
        if(hcon==null) return null;
        try{
            StringBuffer sb=new StringBuffer(8192);
            String tmp="";
            BufferedReader br=new BufferedReader(
                    new InputStreamReader(
                            hcon.getInputStream()
                    )
            );
            while((tmp=br.readLine())!=null)
                sb.append(tmp).append("\n");
            br.close();
            Cache.write(url, sb.toString());
            return sb.toString();
        }catch(IOException e){
            Log.d("READ FAILED", e.toString());
            return null;
        }
    }
}

