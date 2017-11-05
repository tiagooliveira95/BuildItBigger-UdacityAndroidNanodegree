package com.udacity.android.androidjokeslibrary;


import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by tiago on 16/09/2017.
 */

public class Joke implements Parcelable {

    private Integer id = -1;
    private String type;
    private String category;
    private String joke;

    public static final String TYPE_KEY = "type";
    public static final String VALUE_KEY = "value";
    public static final String JOKE_KEY = "joke";
    public static final String ID_KEY = "id";
    public static final String CATEGORYS = "categories";

    public static final String TYPE_SUCCESS = "success";

    private ContentValues contentValues;

    public static Joke buildFromJson(JSONObject o){
        JSONObject values;

        try {
            values = o.getJSONObject(VALUE_KEY);
        } catch (JSONException  e) {
            e.printStackTrace();
            return null;
        }


        Joke joke = new Joke();
        try {
            joke.setJoke(values.getString(JOKE_KEY));
            joke.setType(o.getString(TYPE_KEY));
            joke.setId(values.getInt(ID_KEY));
            return joke;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Joke() {
    }

    protected Joke(Parcel in) {
        type = in.readString();
        category = in.readString();
        joke = in.readString();
        contentValues = in.readParcelable(ContentValues.class.getClassLoader());
        id = in.readInt();
    }

    public static final Creator<Joke> CREATOR = new Creator<Joke>() {
        @Override
        public Joke createFromParcel(Parcel in) {
            return new Joke(in);
        }

        @Override
        public Joke[] newArray(int size) {
            return new Joke[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(category);
        parcel.writeString(joke);
        parcel.writeParcelable(contentValues, i);
        parcel.writeInt(id);
    }
}

