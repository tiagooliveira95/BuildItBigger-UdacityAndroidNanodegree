package com.udacity.android.androidjokeslibrary;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static com.udacity.android.androidjokeslibrary.Joke.ID_KEY;
import static com.udacity.android.androidjokeslibrary.Joke.JOKE_KEY;
import static com.udacity.android.androidjokeslibrary.Joke.TYPE_KEY;
import static com.udacity.android.androidjokeslibrary.Joke.VALUE_KEY;
import static com.udacity.android.androidjokeslibrary.Joke.TYPE_SUCCESS;


/**
 * Created by tiago on 17/09/2017.
 */

public class LiveJokes {
    private static final String JOKES_API_URL = "https://api.icndb.com";
    private static final String PATH_JOKES = "jokes";
    private static final String PATH_RANDOM = "random";



    public static class getLiveJoke extends AsyncTask<Void, Void, Joke>{

        OnJokeReadyListener onJokeReadyListener;

        public getLiveJoke(final OnJokeReadyListener onJokeReadyListener){
            this.onJokeReadyListener = onJokeReadyListener;
        }

            @Override
            protected void onPreExecute() {
                onJokeReadyListener.onStart();
            }

            @Override
            protected Joke doInBackground(Void... voids) {

                Uri builtUri =
                        Uri.parse(JOKES_API_URL).buildUpon()
                        .appendPath(PATH_JOKES)
                        .appendPath(PATH_RANDOM)
                        .build();

                JSONObject object;
                JSONObject values;

                try {
                    object = new JSONObject(getResponseFromHttpUrl(new URL(builtUri.toString())));
                    values = object.getJSONObject(VALUE_KEY);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    return null;
                }


                Joke joke = new Joke();
                try {
                    joke.setJoke(values.getString(JOKE_KEY));
                    joke.setType(object.getString(TYPE_KEY));
                    joke.setId(values.getInt(ID_KEY));
                    return joke;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Joke joke) {
                super.onPostExecute(joke);
                if(joke != null && joke.getType().equals(TYPE_SUCCESS)){
                    onJokeReadyListener.onJokeDownloaded(joke);
                }else{
                    onJokeReadyListener.onError();
                }
            }
        }



    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public interface OnJokeReadyListener{
        void onStart();
        void onJokeDownloaded(Joke joke);
        void onError();
    }
}
