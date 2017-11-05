package com.udacity.gradle.builditbigger;


import android.os.AsyncTask;
import com.example.tiago.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.android.androidjokeslibrary.Joke;


import java.io.IOException;

/**
 * Created by tiago on 10/09/2017.
 */


public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private OnEndpointsAsyncTaskListener listener;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(listener != null)
            listener.onPreExecute();
    }

    @Override
    protected final String doInBackground(Void... params) {

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)

                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }


        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Joke joke = new Joke();
        joke.setJoke(result);
        if(listener != null) listener.onPostExecute(joke);

    }

    public interface OnEndpointsAsyncTaskListener{
        void onPreExecute();
        void onPostExecute(Joke joke);
    }

    public EndpointsAsyncTask setOnEndpointsAsyncTaskListener(OnEndpointsAsyncTaskListener listener) {
        this.listener = listener;
        return this;
    }
}