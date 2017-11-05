package com.udacity.gradle.builditbigger;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AdView mAdView = findViewById(R.id .adView);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitialAdUnitId));
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());

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

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d(MainActivity.class.getSimpleName(), "The interstitial wasn't loaded yet.");
                }
                mInterstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
                        toggleProgressBar(false);
                        startActivity(intent);
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        toggleProgressBar(false);
                        startActivity(intent);
                    }
                });

            }
        }).execute();
    }


    public void onlineJoke(final View v){
        new AlertDialog.Builder(this)
                .setMessage(R.string.deluxe)
                .setMessage(R.string.online_jokes_free_version_message)
                .setPositiveButton(R.string.go_to_play_store, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://store")));
                        } catch (ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/")));
                        }
                    }
                }).setNegativeButton(R.string.cancel, null).show();
    }

    private void toggleProgressBar(boolean state){
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
        liveJokeButton.setVisibility(!state ? View.VISIBLE : View.GONE);
        localJokeButton.setVisibility(!state ? View.VISIBLE : View.GONE);
    }
}

