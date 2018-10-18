package com.example.android.popmovies1.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private String mTitle;
    private String mReleaseDate;
    private String mMoviePoster;
    private String mAvgVote;
    private String mPlot;

    public Movie(String title, String releaseDate, String moviePoster, String avgVote, String plot) {

        mTitle = title;
        mReleaseDate = releaseDate;
        mMoviePoster = moviePoster;
        mAvgVote = avgVote;
        mPlot = plot;

    }

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mMoviePoster = in.readString();
        mAvgVote = in.readString();
        mPlot = in.readString();
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmMoviePoster() {
        return mMoviePoster;
    }

    public String getmAvgVote() {
        return mAvgVote;
    }

    public String getmPlot() {
        return mPlot;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mMoviePoster);
        dest.writeString(mAvgVote);
        dest.writeString(mPlot);
    }
}