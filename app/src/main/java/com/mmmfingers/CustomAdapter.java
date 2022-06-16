package com.mmmfingers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(Context context, String[] items) {
        super(context, R.layout.custom_view, items);
    }


    /**
     * @param position the position of the next item in the list
     * @param convertView convert this custom view
     * @return single Item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        // one custom view
        View customView = layoutInflater.inflate(R.layout.custom_view, parent, false);

        // now we have to have a reference to listViewItems, the image , and the text next to image
        String singleItem = getItem(position);
        TextView textView = customView.findViewById(R.id.myTextView);
        // ImageView image = customView.findViewById(R.id.myImageView);

        // set resources on views
//        switch (position) {
//            case 0:
//                // gold medal
//                image.setImageResource(aImages[0]);
//                break;
//            case 1:
//                image.setImageResource(aImages[1]);
//                break;
//            case 2:
//                image.setImageResource(aImages[2]);
//                break;
//
//            default:
//                image.setImageResource(aImages[3]);
//                break;
//        }


        // this will change text dynamically to each one of items
        textView.setText(singleItem);

        return customView;

    }
}
