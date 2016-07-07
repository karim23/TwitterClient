package com.example.twitterclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twitterclient.Database.Tweet;
import com.example.twitterclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Eng-Karim on 7/6/2016.
 */

public class TweetsListAdapter extends ArrayAdapter<Tweet> {
    private List<Tweet> tweets;
    private ViewHolder viewHolder;

    private static class ViewHolder {


        public TextView fullNameTv;
        public TextView userScreenTv;
        public TextView updateTextTv;
        public TextView updateTimeTv;
        public ImageView userImg;
        public ImageView rowMediaIm;
    }


    public TweetsListAdapter(Context context, int search_num_row, List<Tweet> tweets) {
        super(context, search_num_row, tweets);
        this.tweets = tweets;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.tweets_list_row, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.fullNameTv = (TextView) convertView.findViewById(R.id.tweetsListRow_fullNameTv);
            viewHolder.userScreenTv = (TextView) convertView.findViewById(R.id.tweetsListRow_userScreenTv);
            viewHolder.updateTextTv = (TextView) convertView.findViewById(R.id.tweetsListRow_updateTextTv);
            viewHolder.updateTimeTv = (TextView) convertView.findViewById(R.id.tweetsListRow_updateTimeTv);
            viewHolder.userImg = (ImageView) convertView.findViewById(R.id.tweetsListRow_userImg);
            viewHolder.rowMediaIm = (ImageView) convertView.findViewById(R.id.tweetsListRow_rowMediaIm);

        }

        Tweet item = getItem(position);
        if (item != null) {
            viewHolder.fullNameTv.setText(item.getTweetName());
            viewHolder.userScreenTv.setText(item.getUserScreen());
            viewHolder.updateTextTv.setText(item.getTweetText());
            viewHolder.updateTimeTv.setText(item.getUpdateTime());
            if (item.getUserImg() != null && item.getUserImg() != "")
                Picasso.with(getContext()).load(item.getUserImg()).into(viewHolder.userImg);
            if (item.getTweetMediaURL() != null && item.getTweetMediaURL() != "") {
                viewHolder.rowMediaIm.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(item.getTweetMediaURL()).into(viewHolder.rowMediaIm);
            } else {
                viewHolder.rowMediaIm.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}
