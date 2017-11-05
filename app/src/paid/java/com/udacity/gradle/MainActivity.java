package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.udacity.android.androidjokeslibrary.Joke;
import com.udacity.android.androidjokeslibrary.JokerActivity;
import com.udacity.android.androidjokeslibrary.LiveJokes;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.progressBar2)
    ProgressBar progressBar;

    @BindView(R.id.localJokeButton)
    Button localJokeButton;

    @BindView(R.id.liveJokeButton)
    Button liveJokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @SuppressWarnings("unchecked")
    public void localJoke(View view) {
        new EndpointsAsyncTask().setOnEndpointsAsyncTaskListener(new EndpointsAsyncTask.OnEndpointsAsyncTaskListener() {
            @Override
            public void onPreExecute() {
                toggleProgressBar(true);
            }

            @Override
            public void onPostExecute(Joke joke) {
                final Intent intent = new Intent(MainActivity.this, JokerActivity.class);
                intent.putExtra(JokerActivity.EXTRA_JOKE, joke);
                toggleProgressBar(false);
                startActivity(intent);
            }
        }).execute();
    }


    public void onlineJoke(final View v){
        if(!isNetworkAvailable()) {
            Snackbar.make(v, getString(R.string.internet_not_available), Snackbar.LENGTH_LONG)
                    .show();
            return;
        }



        new LiveJokes.getLiveJoke(new LiveJokes.OnJokeReadyListener() {
            @Override
            public void onStart() {
                toggleProgressBar(true);
            }

            @Override
            public void onJokeDownloaded(Joke joke) {
                final Intent intent = new Intent(MainActivity.this, JokerActivity.class);
                intent.putExtra(JokerActivity.EXTRA_JOKE, joke);
                toggleProgressBar(false);
                startActivity(intent);


            }

            @Override
            public void onError() {
                toggleProgressBar(false);
                Snackbar.make(v, "An error accored while loading the Joke", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        }).execute();
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void toggleProgressBar(boolean state){
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
        liveJokeButton.setVisibility(!state ? View.VISIBLE : View.GONE);
        localJokeButton.setVisibility(!state ? View.VISIBLE : View.GONE);
    }
}

