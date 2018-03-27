package m7edshin.popularmovieapp.Utilities;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import m7edshin.popularmovieapp.Models.MovieDetails;

/**
 * Created by Mohamed Shahin on 18/02/2018.
 * Loading data of the movie details in the background
 */

public class MovieDetailsLoader extends AsyncTaskLoader<List<MovieDetails>> {

    private final String url;

    public MovieDetailsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieDetails> loadInBackground() {
        if (url == null) return null;
        return NetworkingRequest.fetchMovieData(url);
    }
}
