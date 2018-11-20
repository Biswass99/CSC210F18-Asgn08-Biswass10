package com.test.webpageasyncloader;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by salil on 11/18/18.
 */

public class NetworkConn {
    private static final String LOG_TAG = NetworkConn.class.getSimpleName();



    /**
     * Static method to get the source code from a web page.
     *
     * @param queryString the query string.
     * @return the result containing the source code.
     */
    static String getSourceCodeInfo(String queryString) {


        // Set up variables for the try block that need to be closed in the
        // finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        //String bookJSONString = null;
        String result = null;

        try {
            URL url = new URL(queryString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result = result + line;
                result = result + "\n";
            }
            conn.disconnect();
        } catch (Exception e) {
            Log.e("ERROR FETCHING", e.toString());
        }

        return result;

    }// end getSourceCodeInfo


  }// end class NetworkConn

