package m7edshin.popularmovieapp.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import m7edshin.popularmovieapp.Models.MovieDetails;
import m7edshin.popularmovieapp.R;

import static m7edshin.popularmovieapp.Utilities.Constants.POSTER_PATH;

/**
 * Created by Mohamed Shahin on 02/03/2018.
 * Data Holder for movie details
 */

public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MoviesHolder>{

    private List<MovieDetails> moviesList;

    public MoviesRecyclerAdapter(List<MovieDetails> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_recycler_item, parent, false);
        return new MoviesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {
        MovieDetails movieDetails = moviesList.get(position);
        if(movieDetails.getPoster().isEmpty() || movieDetails.getPoster().equalsIgnoreCase("null")){
            holder.iv_movie_image.setImageResource(R.drawable.no_poster);
        }else{
            String createPosterPath = POSTER_PATH + movieDetails.getPoster();
            Context context = holder.iv_movie_image.getContext();
            Picasso.with(context).load(createPosterPath).into(holder.iv_movie_image);
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MoviesHolder extends RecyclerView.ViewHolder{

        private ImageView iv_movie_image;

        public MoviesHolder(View itemView) {
            super(itemView);
            iv_movie_image = itemView.findViewById(R.id.iv_movie_image);
        }
    }

}
