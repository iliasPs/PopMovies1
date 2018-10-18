package com.example.android.popmovies1.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.android.popmovies1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class JsonUtils {

    private JsonUtils() {
    }

    private final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private final static String IMAGE_SIZE_POSTER = "w500";
    private static String currentImagePath;


    public static ArrayList<Movie> extractFeatureFromJson(String movieJson) {
       if (TextUtils.isEmpty(movieJson)) {
            return null;
        }
        ArrayList<Movie> movies = new ArrayList<>();
        try {

            JSONObject baseJsonResponse = new JSONObject(movieJson);
            JSONArray moviesArray = baseJsonResponse.getJSONArray("results");
            for (int i = 0; i < moviesArray.length(); i++) {

                JSONObject currentMovie = moviesArray.getJSONObject(i);
               String currentTitle = currentMovie.getString("original_title");
                String currentReleaseDate = currentMovie.getString("release_date");
                if (currentMovie.getString("poster_path") != null ){
                  currentImagePath = IMAGE_BASE_URL+ IMAGE_SIZE_POSTER + currentMovie.getString("poster_path");
                }else {
                    currentImagePath = null;
                }
                String currentAvg = currentMovie.getString("vote_average");
                String currentPlot = currentMovie.getString("overview");
                movies.add(new Movie(currentTitle, currentReleaseDate, currentImagePath, currentAvg, currentPlot));
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        return movies;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
