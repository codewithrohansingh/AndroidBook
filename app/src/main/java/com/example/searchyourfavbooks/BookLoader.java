package com.example.searchyourfavbooks;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Books>> {

    /**
     * Query URL
     */
    private String mUrl;
    public BookLoader(@NonNull Context context, String url) {
        super (context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
//        Log.e ("MainActivity.this", " onStartLoading method");

        forceLoad ();
    }

    @Override
    public List<Books> loadInBackground() {
    Log.e ("MainActivity.this", " loadInBackground method");

        if (mUrl == null) {
            return null;
        }
        // Perform the HTTP request for earthquake data and process the response.
        List<Books> books = QueryUtils.fetchEarthquakeData (mUrl);
        return books;
    }
}
