package com.example.android.popmovies1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.android.popmovies1.model.Movie;
import com.example.android.popmovies1.utils.JsonUtils;
import com.example.android.popmovies1.utils.MovieRecycleViewAdapter;
import com.example.android.popmovies1.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    Toolbar mToolBar;
    @BindView(R.id.prefSpinnner)
    Spinner prefSpinner;
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

        mToolBar = findViewById(R.id.toolbar);

        ButterKnife.bind(this);
        mToolBar.setTitle(getResources().getString(R.string.app_name));

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.pref_spinner_item_list, getResources().getStringArray(R.array.userPrefs));
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prefSpinner.setAdapter(spinAdapter);

        prefSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PopulateMoviesTask newTask = new PopulateMoviesTask();
                userOption = prefSpinner.getSelectedItem().toString();
                newTask.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                PopulateMoviesTask newTask = new PopulateMoviesTask();
                newTask.execute();
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mMovieslist.setLayoutManager(gridLayoutManager);

        mMovieslist.setHasFixedSize(true);
        PopulateMoviesTask task = new PopulateMoviesTask();
        task.execute();
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
