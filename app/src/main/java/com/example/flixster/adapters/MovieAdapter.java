package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>
{
    Context context;    //where adapter is constructed from
    List<Movie> movies; //data

    public MovieAdapter(Context context, List<Movie> movies)
    {
        this.context = context;
        this.movies = movies;
    }

    // inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d("MovieAdapter", "onCreateViewholder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    /* populate data into item through holder
    * put data at "position" into view contained in "holder" */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Log.d("MovieAdapter", "onBindViewholder" + position);
        //get movie at "position"
        Movie movie = movies.get(position);
        //bind movie data to "holder"
        holder.bind(movie);
    }

    // return total count of items in list
    @Override
    public int getItemCount()
    {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivBackdrop;
        int orientation;

        public ViewHolder(@NonNull View itemView)
        {
            //TODO: nice to have: ivPrimaryImage instead of ivPoster & backdrop (https://hackmd.io/@qoEusk2FR0SJB-Q7Rsv-CQ/ryS2Jppz-?type=view)
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);

            orientation = context.getResources().getConfiguration().orientation;

            if (orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                ivPoster = itemView.findViewById(R.id.ivPoster);
            }
            else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                ivBackdrop = itemView.findViewById(R.id.ivBackdrop);
            }

            // add this as the itemView's OnClickListener
            itemView.setOnClickListener(this);
        }

        //populate view from Movie object
        public void bind(Movie movie)
        {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            if (orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                Glide.with(context)
                        .load(movie.getPosterPath())
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .into(ivPoster);
            }
            else if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                Glide.with(context)
                        .load(movie.getBackdropPath())
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .into(ivBackdrop);
            }
        }

        //TODO: understand this function & Intent
        // when the user clicks on a row, show MovieDetailsActivity for the selected movie
        @Override
        public void onClick(View v)
        {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                /* serialize the movie using parceler, use its short name as a key
                * imow: wrap up Movie object to be delivered to the MovieDetailsActivity
                * .class.getSimpleName(): https://www.geeksforgeeks.org/class-getsimplename-method-in-java-with-examples/*/
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
