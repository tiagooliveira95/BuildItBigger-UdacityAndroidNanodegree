package com.udacity.android.androidjokeslibrary;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class JokerActivity extends AppCompatActivity {

    TextView tvJoke;
    public static final String EXTRA_JOKE = "extra_joke";
    Joke joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joker_class);

        tvJoke = findViewById(R.id.androidJokesLibrary_tvJoke);

        Intent extras = getIntent();
        if(extras.hasExtra(EXTRA_JOKE)){
            joke = extras.getParcelableExtra(EXTRA_JOKE);
            tvJoke.setText(joke.getJoke());
        }else{
            showError();
        }

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showError(){
        tvJoke.setText(R.string.joke_loading_error);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}

