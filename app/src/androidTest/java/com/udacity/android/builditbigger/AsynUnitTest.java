package com.udacity.android.builditbigger;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.udacity.android.androidjokeslibrary.Joke;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AsynUnitTest {

    @Test
    public void isStringEmpty() throws Exception {
        new EndpointsAsyncTask().setOnEndpointsAsyncTaskListener(new EndpointsAsyncTask.OnEndpointsAsyncTaskListener() {
            @Override
            public void onPreExecute() {}

            @Override
            public void onPostExecute(Joke joke) {
                assertNotNull(joke);
                assertNotEquals("",joke.getJoke().trim());
                Log.d(AsynUnitTest.class.getSimpleName(), "Retrieved a non-empty string successfully: " + joke.getJoke());
            }
        }).execute();
    }
}