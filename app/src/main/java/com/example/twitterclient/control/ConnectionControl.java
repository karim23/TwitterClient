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
import android.widget.Toast;

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

        Toast.makeText(context, context.getString(R.string.NoInternetConnection), Toast.LENGTH_LONG).show();
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

