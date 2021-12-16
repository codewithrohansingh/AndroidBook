package com.example.searchyourfavbooks;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {
//    private String convertString(int number){
//        return number.toString;
//    }
    private BookAdapter mBookAdapter;
    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;
    private static final String URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults="+MainActivity.num;
    private static final int BOOK_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_book);

        ListView listView = (ListView) findViewById (R.id.list);
        mEmptyStateTextView = (TextView) findViewById (R.id.empty_view);
        listView.setEmptyView (mEmptyStateTextView);
        mBookAdapter = new BookAdapter (this, new ArrayList<Books> ());

        listView.setAdapter (mBookAdapter);

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Books currentBook = mBookAdapter.getItem (position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse (currentBook.getSelfLink ());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent (Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity (websiteIntent);
            }
        });

        LoaderManager.getInstance (this).initLoader (BOOK_LOADER_ID, null, this);

    }


    @NonNull
    @Override
    public Loader<List<Books>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookLoader (this, URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Books>> loader, List<Books> data) {

        ProgressBar progressBar = (ProgressBar) findViewById (R.id.loading_spinner);
        progressBar.setVisibility (View.GONE);
        //check for internet connection
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo ();
        if (networkInfo == null) {
            //state that there is no internet connection
            mEmptyStateTextView.setText (R.string.no_internet);
        } else if (networkInfo != null && networkInfo.isConnected ()) {
            //There is internet but list is still empty
            mEmptyStateTextView.setText (R.string.no_data);
        }

          // Clear the adapter of previous earthquake data
          mBookAdapter.clear ();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty ()) {
            Log.v ("this", "Setting the data");
            mBookAdapter.addAll (data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Books>> loader) {
        mBookAdapter.clear ();
    }
}
