package com.example.android.popmovies1;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.popmovies1.model.Movie;
import com.example.android.popmovies1.utils.JsonUtils;
import com.example.android.popmovies1.utils.MovieRecycleViewAdapter;
import com.example.android.popmovies1.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity  {


    @BindView(R.id.noDataTv)
    TextView noDataTv;
    @BindView(R.id.rv_movies)
    RecyclerView mMovieslist;

    private String userOption = "Most Popular";
    private ArrayList<Movie> mMovies;
    private MovieRecycleViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionBar)));

        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mMovieslist.setLayoutManager(gridLayoutManager);

        mMovieslist.setHasFixedSize(true);
        PopulateMoviesTask task = new PopulateMoviesTask();
        task.execute();
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PopulateMoviesTask newTask = new PopulateMoviesTask();
        switch (item.getItemId()) {
            case R.id.optionPopular:
                setTitle(R.string.option_popular);
                userOption = "Most Popular";
                newTask.execute();
                return true;
            case R.id.optionRate:
                setTitle(R.string.option_highest_rated);
                userOption = "Highest Rated";
                newTask.execute();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private class PopulateMoviesTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = NetworkUtils.createUrl(userOption);
            String jsonString = "";
            try {
                jsonString = NetworkUtils.makeHttpRequest(searchUrl);
            } catch (IOException e) {
                Log.e("Main Activity", "Problem making the HTTP request.", e);
            }
            return jsonString;
        }

        @Override
        protected void onPostExecute(String jsonString) {
            if (jsonString == null) {
                mMovieslist.setVisibility(View.GONE);
                noDataTv.setVisibility(View.VISIBLE);
            } else {
                mMovieslist.setVisibility(View.VISIBLE);
                noDataTv.setVisibility(View.GONE);
                mMovies = JsonUtils.extractFeatureFromJson(jsonString);
            }
            mAdapter = new MovieRecycleViewAdapter(MainActivity.this, mMovies, new MovieRecycleViewAdapter.ListItemClickListener() {
                @Override
                public void onListItemClick(int clickedItemIndex) {

                }
            });

            mMovieslist.setAdapter(mAdapter);
        }
    }

}
