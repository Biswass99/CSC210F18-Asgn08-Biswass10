package com.test.webpageasyncloader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The WhoWroteIt app queries the Book Search API for books based
 * on a user's search.  It uses an AsyncTask to run the search task in
 * the background.
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{


    private EditText mURLInput;
    private TextView sourceCode;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mURLInput = (EditText)findViewById(R.id.urlText);
        sourceCode = (TextView)findViewById(R.id.textFromWeb);
        mRadioGroup = findViewById(R.id.radioGroup);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }


    }

    public void checkButton (View v){

    }

    public void fetchCode(View v){
        // Get the url string from the url input field.
        String urlString = mURLInput.getText().toString();
        // Get protocol string (http or https) from RadioGroup.
        // Get the checked radio button
        mRadioButton = findViewById(mRadioGroup.getCheckedRadioButtonId()); // Picks selected button
        // Get the text of selected radio button (http or https)
        String protocolString = mRadioButton.getText().toString();
        // Get the string for complete url string (ex-https://www.google.com)
        String completeURLString = protocolString + "://" + urlString;
        //
        // Hide the keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If the network is available, connected, and the search field
        // is not empty, start a WebpageLoader AsyncTaskLoader.
        if (networkInfo != null && networkInfo.isConnected()
                && urlString.length() != 0) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString("completeURLString", completeURLString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            sourceCode.setText(R.string.fetching);
        }// end if

        // Otherwise update the TextView to tell the user there is no
        // connection, or no search term.
        else {
            if (urlString.length() == 0) {

                sourceCode.setText(R.string.no_search_term);

            } else {

                sourceCode.setText(R.string.no_network);
            }
        }


    }// end fetchcode

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String completeURLString = "";

        if (args != null) {
            completeURLString = args.getString("completeURLString");
        }

        return new WebpageLoader(this, completeURLString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String result) {
       try {
           if (result != null) {
               sourceCode.setText(result);
           }
           else {
               sourceCode.setText(R.string.no_results);
           }
       }// end try

       catch (Exception e){
         sourceCode.setText(R.string.no_results);
         Log.e("ERROR IN THREAD", e.toString());
       }// end catch

    }// end onLoadFinished


    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Do nothing.  Required by interface.
    }





}// end class MainActivity
