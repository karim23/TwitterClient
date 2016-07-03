package com.example.twitterclient.control;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.twitterclient.R;

/**
 * Created by Eng-Karim on 7/3/2016.
 */
public class ConnectionControl {

        Context context;

        public ConnectionControl(Context context) {
            this.context = context;
        }

        public void showNoInternetConnectionMessage() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.no_internet_connection, null, false);

            final PopupWindow pw = new PopupWindow(view, FrameLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);

            TextView ok = (TextView) view.findViewById(R.id.noConnection_okTv);
            ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pw.dismiss();
                }
            });

            pw.showAtLocation(view, Gravity.CENTER, 0, 0);
        }


        public boolean checkInternetConnection() {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // ARE WE CONNECTED TO THE NET
            if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                return true;
            } else {
                return false;
            }
        }

    }

