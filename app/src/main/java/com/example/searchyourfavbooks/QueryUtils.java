package com.example.searchyourfavbooks;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    public static final String LOG_TAG = QueryUtils.class.getSimpleName ();

    public static List<Books> fetchEarthquakeData(String requestUrl) {

        //Making to Sleep the background thread for 2 seconds
        try {
            Thread.sleep (1000);
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }

        // Create URL object
        URL url = createUrl (requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = " ";
        try {
            jsonResponse = makeHttpRequest (url);
        } catch (IOException e) {
            Log.e (LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Books> books = extractFeatureFromJson (jsonResponse);

        // Return the {@link Event}
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL (stringUrl);
        } catch (MalformedURLException e) {
            Log.e (LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection ();
            urlConnection.setRequestMethod ("GET");
            urlConnection.setReadTimeout (10000 /* milliseconds */);
            urlConnection.setConnectTimeout (15000 /* milliseconds */);
            urlConnection.connect ();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode () == 200) {
                inputStream = urlConnection.getInputStream ();
                jsonResponse = readFromStream (inputStream);
            } else {
                Log.e (LOG_TAG, "Error response code: " + urlConnection.getResponseCode ());
            }
        } catch (IOException e) {
            Log.e (LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect ();
            }
            if (inputStream != null) {
                inputStream.close ();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder ();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader (inputStream, Charset.forName ("UTF-8"));
            BufferedReader reader = new BufferedReader (inputStreamReader);
            String line = reader.readLine ();
            while (line != null) {
                output.append (line);
                line = reader.readLine ();
            }
        }
        return output.toString ();
    }

    /**
     * Return an {@link Books} object by parsing out information
     * about the first earthquake from the input booksJSON string.
     */
    private static List<Books> extractFeatureFromJson(String booksJSON) {


        ArrayList<Books> books = new ArrayList<> ();
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty (booksJSON)) {
            return null;
        }

        try {

            JSONObject jsonObject = new JSONObject (booksJSON);
            JSONArray jsonArray = jsonObject.getJSONArray ("items");
            for (int i = 0; i < jsonArray.length (); i++) {
                JSONObject firstElementOfArray = jsonArray.getJSONObject (i);
                JSONObject volumeInfo = firstElementOfArray.getJSONObject ("volumeInfo");
                String title = volumeInfo.getString ("title");
                JSONArray author = volumeInfo.getJSONArray ("authors");
                String authorName = author.getString (0);
                String link = volumeInfo.getString ("previewLink");
                String publisher = volumeInfo.getString ("publisher");
                String publishDate = volumeInfo.getString ("publishedDate");
                books.add (new Books (title, authorName, publisher, publishDate, link));
            }


        } catch (JSONException jsonException) {
            jsonException.printStackTrace ();
        }

        // Return the list of books
        return books;

    }
}
