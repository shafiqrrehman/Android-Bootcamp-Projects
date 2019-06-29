package com.wayn.theatermoviemanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wayn.theatermoviemanager.R;
import com.wayn.theatermoviemanager.activities.MovieDetailActivity;
import com.wayn.theatermoviemanager.models.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    List<Movie> movies;
    Context context;

    public MovieRecyclerViewAdapter(Context context, List<Movie> movies) {

        this.movies = movies;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(v);
    }

    private Context getContext() {
        return context;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        Movie movie = movies.get(position);

        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        Picasso.with(getContext())
                .load(movie.getFosterPath())
                .into(viewHolder.ivMovieImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivMovieImage)
        ImageView ivMovieImage;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvOverview)
        TextView tvOverview;

        @BindView(R.id.cvMovie)
        CardView cvMovie;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Movie movie = movies.get(getAdapterPosition());

            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra("MOVIE", movie);
            getContext().startActivity(intent);
        }
    }
}
