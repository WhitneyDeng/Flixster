package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import org.parceler.Parcel;

@Parcel // annotation indicates class is Parcelable
public class Movie
{
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    Double voteAverage;
    Integer id;

    // no-arg, empty constructor required for Parceler
    public Movie()  {}

    public Movie(JSONObject jsonObject) throws JSONException
    {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException
    {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++)
        {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public Integer getId()
    {
        return id;
    }

    public String getPosterPath()
    {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath()
    {
        return String.format("https://image.tmdb.org/t/p/w500/%s", backdropPath);
    }

    public String getTitle()
    {
        return title;
    }

    public String getOverview()
    {
        return overview;
    }

    public Double getVoteAverage()
    {
        return voteAverage;
    }
}
