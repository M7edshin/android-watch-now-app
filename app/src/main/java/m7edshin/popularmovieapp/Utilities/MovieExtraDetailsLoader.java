package m7edshin.popularmovieapp.Utilities;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import m7edshin.popularmovieapp.Models.MovieExtraDetails;

/**
 * Created by Mohamed Shahin on 26/02/2018.
 * Loading data of the movie extra details (Trailer & Reviews) in the background
 */

public class MovieExtraDetailsLoader extends AsyncTaskLoader<MovieExtraDetails> {

    private final String url;

    public MovieExtraDetailsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public MovieExtraDetails loadInBackground() {
        if(url == null) return null;

        List<String> reviewsList;
        List<String> videosList;

        reviewsList = NetworkingRequest.fetchMovieReviews(url);
        videosList = NetworkingRequest.fetchMovieVideosKeys(url);

        return new MovieExtraDetails(reviewsList, videosList);
    }

}
