package com.test.webpageasyncloader;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by salil on 11/18/18.
 */

public class WebpageLoader extends AsyncTaskLoader<String> {


    private String mQueryString;

    // constructor
    WebpageLoader(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {

        return NetworkConn.getSourceCodeInfo(mQueryString);
    }
}

