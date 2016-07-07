package com.example.twitterclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twitterclient.Database.Follower;
import com.example.twitterclient.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Eng-Karim on 7/6/2016.
 */

public class FollowersListAdapater extends ArrayAdapter<Follower> {
    private List<Follower> followers;

    private FollowersListAdapater.ViewHolder viewHolder;

    public FollowersListAdapater(Context context, int sowar_index_row, List<Follower> followers) {
        super(context, sowar_index_row, followers);
        this.followers = followers;
    }

    private static class ViewHolder {

        public TextView fullNameTv;
        public TextView userScreenTv;
        public TextView followerBioTv;
        public ImageView userImg;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.followers_list_row, parent, false);
            viewHolder = new FollowersListAdapater.ViewHolder();
            viewHolder.fullNameTv = (TextView) convertView.findViewById(R.id.followersListRow_fullNameTv);
            viewHolder.userScreenTv = (TextView) convertView.findViewById(R.id.followersListRow_userScreenTv);
            viewHolder.followerBioTv = (TextView) convertView.findViewById(R.id.followersListRow_followerBioTv);
            viewHolder.userImg = (ImageView) convertView.findViewById(R.id.followersListRow_userImg);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (FollowersListAdapater.ViewHolder) convertView.getTag();
        }

        Follower item = getItem(position);
        if (item != null) {
            viewHolder.fullNameTv.setText(item.getFullName());
            viewHolder.userScreenTv.setText(item.getUserScreen());
            if (item.getUserBio() != null && item.getUserImgUrl() != "") {
                viewHolder.followerBioTv.setVisibility(View.VISIBLE);
                viewHolder.followerBioTv.setText(item.getUserBio());
            } else {
                viewHolder.followerBioTv.setVisibility(View.GONE);
            }
            if (item.getUserImgUrl() != null)
                Picasso.with(getContext()).load(item.getUserImgUrl()).into(viewHolder.userImg);


        }
        return convertView;
    }

}