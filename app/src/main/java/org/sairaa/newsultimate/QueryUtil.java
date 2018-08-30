package org.sairaa.newsultimate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

class QueryUtil {
    private static final String LOG_TAG_QUERY_UTIL = QueryUtil.class.getName();
    private  List<News> newsList1 = new ArrayList<News>();
    private Context ctx;
    public  List<News> QueryUtilsForNewtwork(String mUrl, Context ctx) {
        this.ctx = ctx;
        URL url;
        url = CreateUrl(mUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractFeatureFromJson(jsonResponse);

    }

    private List<News> extractFeatureFromJson(String newsJson) {
        List<News> newsData = new ArrayList<News>();
//        newsData = null;
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(newsJson);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or news).
            JSONObject newsObject = baseJsonResponse.getJSONObject(ctx.getString(R.string.response));
            JSONArray newsArray = newsObject.getJSONArray(ctx.getString(R.string.results));

            for (int i = 0; i < newsArray.length(); i++) {

                JSONObject currentNews = newsArray.getJSONObject(i);

                String id = currentNews.getString(ctx.getString(R.string.id));
                String type = currentNews.getString(ctx.getString(R.string.type));
                String sectionId = currentNews.getString(ctx.getString(R.string.sectionId));
                String sectionName = currentNews.getString(ctx.getString(R.string.sectionName));
                String webPublicationDate = currentNews.getString(ctx.getString(R.string.webPublicationDate));
                String webTitle = currentNews.getString(ctx.getString(R.string.webTitle));
                String webUrl = currentNews.getString(ctx.getString(R.string.webUrl));
                String apiUrl = currentNews.getString(ctx.getString(R.string.apiUrl));

                String thumbnailUrl;
                try {
                    JSONObject fields = currentNews.getJSONObject(ctx.getString(R.string.fields));
                    thumbnailUrl = fields.getString(ctx.getString(R.string.thumbnail));
                } catch (JSONException e) {
                    Log.e(LOG_TAG_QUERY_UTIL, "Problem parsing the news JSON results", e);
                    thumbnailUrl = null;
                }
                Bitmap thumbnail = null;
                try {
                    if(thumbnailUrl != null)
                        thumbnail = getBitmapFromUrl(thumbnailUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                    thumbnail = null;
                }
                String publisher = "";
                JSONArray tags = currentNews.getJSONArray(ctx.getString(R.string.tags));
                for(int j = 0; j<tags.length() ; j++){
                    JSONObject tagObject = tags.getJSONObject(j);

                    publisher = tagObject.getString(ctx.getString(R.string.webTitle));
                }
                String isHosted = currentNews.getString(ctx.getString(R.string.isHosted));

                newsData.add(new News(id,type,sectionId,sectionName,webPublicationDate,webTitle,webUrl,apiUrl,thumbnail,
                        publisher,isHosted));

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return newsData;
    }

    private Bitmap getBitmapFromUrl(String thumbnailUrl) throws IOException {
        Log.i(LOG_TAG_QUERY_UTIL,"Test : getBitmapUri( ) is called");
        URLConnection connection = null;
        try {
            connection = new URL(thumbnailUrl).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream((InputStream)connection.getContent(), null, null);
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(20000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG_QUERY_UTIL, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (EOFException e){
            Log.e(LOG_TAG_QUERY_UTIL, "EOFException Problem retrieving the News JSON results.", e);
        } catch (IOException e) {
            //Log.e(LOG_TAG, "Problem retrieving the News JSON results."+ urlConnection.getResponseCode() );
            Log.e(LOG_TAG_QUERY_UTIL, "Problem retrieving the News JSON results.", e);
            //Log.e(LOG_TAG, "Problem retrieving the News JSON results."+ urlConnection.getResponseCode() );
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;


    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private URL CreateUrl(String urlNews) {
        URL url2 = null;
        try{
            url2 = new URL(urlNews);
        }catch (MalformedURLException e) {
            Log.e(LOG_TAG_QUERY_UTIL, "Error with creating URL ", e);
        }
        return url2;
    }
}
