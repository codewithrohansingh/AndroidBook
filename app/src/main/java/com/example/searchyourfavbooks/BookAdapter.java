package com.example.searchyourfavbooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Books> {
    public BookAdapter(@NonNull Context context, ArrayList<Books> resource) {
        super (context, 0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from (getContext ()).inflate (
                    R.layout.list_items, parent, false);
        }

        Books books = getItem (position);

        TextView textView = (TextView) listItemView.findViewById (R.id.title);
        textView.setText (books.getTitle ());

        TextView textView2 = (TextView) listItemView.findViewById (R.id.author_name);
        textView2.setText (books.getAuthor ());

        TextView textView3 = (TextView) listItemView.findViewById (R.id.publisher);
        textView3.setText (books.getPublisher ());

        TextView textView4 = (TextView) listItemView.findViewById (R.id.publish_date);
        textView4.setText (books.getPublishDate ());

        return listItemView;
    }
}
