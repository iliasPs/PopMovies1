package com.example.android.popmovies1.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmovies1.MovieDetailActivity;
import com.example.android.popmovies1.R;
import com.example.android.popmovies1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecycleViewAdapter extends RecyclerView.Adapter<MovieRecycleViewAdapter.MoviesViewHolder>  {

    private List<Movie> mMovies;

    final private ListItemClickListener mOnClickListener;
    private Context mContext;
    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public MovieRecycleViewAdapter(Context c, List<Movie> myMovieData, ListItemClickListener listener) {
        mContext = c;
        mOnClickListener = listener;
        mMovies =myMovieData;

    }


    @NonNull
    @Override
    public MovieRecycleViewAdapter.MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MoviesViewHolder viewHolder = new MoviesViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecycleViewAdapter.MoviesViewHolder holder, int position) {
        Movie movie = this.mMovies.get(position);

        Picasso.with(mContext)
                .load(movie.getmMoviePoster())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.moviePosterIv);

        holder.movieTitleTv.setText(movie.getmTitle());
    }

    @Override
    public int getItemCount() {
        return (null != mMovies ? mMovies.size() : 0);
    }

    /**
     * Cache of the children views for a list item.
     */
   public class MoviesViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView moviePosterIv;
        TextView movieTitleTv;

        public MoviesViewHolder(View itemView) {
            super(itemView);

            moviePosterIv = (ImageView) itemView.findViewById(R.id.moviePoster);
            movieTitleTv = (TextView) itemView.findViewById(R.id.movieTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

            Intent i = new Intent(mContext, MovieDetailActivity.class);
            i.putExtra(Intent.EXTRA_TEXT, mMovies.get(clickedPosition));
            mContext.startActivity(i);
        }
    }
}
