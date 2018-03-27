package m7edshin.popularmovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import m7edshin.popularmovieapp.Models.MovieDetails;

import static m7edshin.popularmovieapp.Utilities.Constants.POSTER_PATH;

public class FavMovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_fav_poster)
    ImageView iv_fav_poster;
    @BindView(R.id.tv_fav_title)
    TextView tv_fav_title;
    @BindView(R.id.tv_synopsis)
    ReadMoreTextView tv_synopsis;
    @BindView(R.id.rating_bar)
    RatingBar rating_bar;
    @BindView(R.id.tv_fav_release_date)
    TextView tv_fav_release_date;

    String movieTitle;
    String movieReleaseDate;
    String movieRating;
    String moviePoster;
    String movieSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movie_details);
        ButterKnife.bind(this);

        //Getting the data passed from the MoviesActivity
        Intent intentThatStartedThisActivity = getIntent();
        String MOVIE_INTENT_KEY = "movie";
        if (intentThatStartedThisActivity.hasExtra(MOVIE_INTENT_KEY)) {
            MovieDetails movieDetails = intentThatStartedThisActivity.getParcelableExtra(MOVIE_INTENT_KEY);
            movieTitle = movieDetails.getTitle();
            movieReleaseDate = movieDetails.getReleaseDate();
            movieRating = movieDetails.getVote();
            moviePoster = movieDetails.getPoster();
            movieSynopsis = movieDetails.getSynopsis();

            String path = POSTER_PATH + moviePoster;
            Picasso.with(this).load(path).into(iv_fav_poster);
            tv_fav_title.setText(movieTitle);
            tv_synopsis.setText(movieSynopsis);
            tv_fav_release_date.setText(getString(R.string.release)+movieReleaseDate);
            rating_bar.setRating(Float.parseFloat(movieRating) / 2);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
