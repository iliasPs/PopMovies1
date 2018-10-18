package com.example.android.popmovies1;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmovies1.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.movieTitle)
    TextView movieTitleTv;
    @BindView(R.id.movieReleaseDate)
    TextView movieReleaseDateTv;
    @BindView(R.id.movieAvg)
    TextView movieAvgTv;
    @BindView(R.id.moviePlot)
    TextView moviePlotTv;
    @BindView(R.id.moviePoster)
    ImageView moviePosterIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionBar)));


        ButterKnife.bind(this);

        Intent i = getIntent();
        if (i.hasExtra(Intent.EXTRA_TEXT)){
            Movie movieData = (Movie) i.getParcelableExtra(i.EXTRA_TEXT);

            Picasso.with(this)
                    .load(movieData.getmMoviePoster())
                    .into(moviePosterIv);

            movieTitleTv.setText(movieData.getmTitle());
            movieReleaseDateTv.setText(movieData.getmReleaseDate());
            movieAvgTv.setText(movieData.getmAvgVote());
            moviePlotTv.setText(movieData.getmPlot());
        }
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
