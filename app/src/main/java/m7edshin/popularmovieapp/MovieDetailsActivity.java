package m7edshin.popularmovieapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import m7edshin.popularmovieapp.CustomAdapters.TrailersRecyclerAdapter;
import m7edshin.popularmovieapp.Models.MovieDetails;
import m7edshin.popularmovieapp.Models.MovieExtraDetails;
import m7edshin.popularmovieapp.Utilities.MovieExtraDetailsLoader;
import mehdi.sakout.fancybuttons.FancyButton;

import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_POSTER_PATH;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_RATING;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_RELEASE_DATE;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_SYNOPSIS;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_TITLE;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.CONTENT_URI;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry._ID;
import static m7edshin.popularmovieapp.Utilities.Constants.API_PAR_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.APPEND_TO_RESPONSE_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.LAYOUT_MANAGER_STATE_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.LOADER_MANAGER_ID;
import static m7edshin.popularmovieapp.Utilities.Constants.MOVIE_API_URL;
import static m7edshin.popularmovieapp.Utilities.Constants.MOVIE_INTENT_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.POSTER_PATH;
import static m7edshin.popularmovieapp.Utilities.Constants.QUERY_VIDEOS_REVIEWS;

public class MovieDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieExtraDetails> {

    @BindView(R.id.tv_synopsis) ReadMoreTextView tv_synopsis;
    @BindView(R.id.tv_release_date) TextView tv_release_date;
    @BindView(R.id.iv_poster) ImageView iv_poster;
    @BindView(R.id.rv_trailers) RecyclerView rv_trailers;
    @BindView(R.id.rating_bar) RatingBar rating_bar;
    @BindView(R.id.btn_reviews) FancyButton btn_reviews;

    private String movieID;
    private String movieTitle;
    private String movieReleaseDate;
    private String movieRating;
    private String moviePoster;
    private String movieSynopsis;

    private static final String MOVIE_API_KEY = BuildConfig.API_KEY;

    private LinearLayoutManager layoutManager;
    private TrailersRecyclerAdapter trailersRecyclerAdapter;

    private List<String> videoKeyList;

    private String reviews;

    private Parcelable saveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        layoutManager =  new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_trailers.setLayoutManager(layoutManager);

        //Getting the data passed from the MoviesActivity
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(MOVIE_INTENT_KEY)) {
            MovieDetails movieDetails = intentThatStartedThisActivity.getParcelableExtra(MOVIE_INTENT_KEY);
            movieID = movieDetails.getId();
            movieTitle = movieDetails.getTitle();
            movieReleaseDate = movieDetails.getReleaseDate();
            movieRating = movieDetails.getVote();
            moviePoster = movieDetails.getPoster();
            movieSynopsis = movieDetails.getSynopsis();
            populateMovieDetails();
            populateExtraMovieDetails();
        }


    }

    private void populateMovieDetails() {
        String createPosterPath = POSTER_PATH + moviePoster;

        tv_synopsis.setText(movieSynopsis);
        tv_release_date.setText(movieReleaseDate);
        rating_bar.setRating(Float.parseFloat(movieRating)/2);

        Picasso.with(this).load(createPosterPath).into(iv_poster);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(movieTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        } else if (id == R.id.action_share) {
            share();
        }else if(id == R.id.action_favorite){
            saveMovieDetails();
        }
        return super.onOptionsItemSelected(item);
    }

    private String createAPIUrl(String movieID){

        Uri baseUri = Uri.parse(MOVIE_API_URL);
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendPath(movieID)
                .appendQueryParameter(API_PAR_KEY, MOVIE_API_KEY)
                .appendQueryParameter(APPEND_TO_RESPONSE_KEY, QUERY_VIDEOS_REVIEWS);
        return builder.build().toString();
    }

    @Override
    public Loader<MovieExtraDetails> onCreateLoader(int id, Bundle args) {
        String query = createAPIUrl(movieID);
        return new MovieExtraDetailsLoader(this, query);
    }

    @Override
    public void onLoadFinished(Loader<MovieExtraDetails> loader, MovieExtraDetails data) {

        for(String s : data.getReviewsList()){
            reviews = s + "\n";
        }

        btn_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(reviews.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.reviews_not_available, Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(MovieDetailsActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(MovieDetailsActivity.this);
                    }
                    builder.setTitle(R.string.movie_reviews)
                            .setMessage(reviews)

                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_menu_info_details)
                            .show();
                }

            }
        });

        videoKeyList = new ArrayList<>(data.getVideosList());
        trailersRecyclerAdapter = new TrailersRecyclerAdapter(videoKeyList);
        rv_trailers.setAdapter(trailersRecyclerAdapter);

        if(saveState!=null){
            layoutManager.onRestoreInstanceState(saveState);
        }
    }

    private void populateExtraMovieDetails(){
        LoaderManager loaderManager = this.getLoaderManager();
        loaderManager.initLoader(LOADER_MANAGER_ID, null, MovieDetailsActivity.this);
    }

    @Override
    public void onLoaderReset(Loader<MovieExtraDetails> loader) {

    }


    private void saveMovieDetails(){

        ContentResolver resolver = getContentResolver();
        String[] projection = {_ID, COLUMN_TITLE, COLUMN_SYNOPSIS, COLUMN_POSTER_PATH, COLUMN_RATING, COLUMN_RELEASE_DATE};
        String contain = movieTitle;
        Cursor cursor = resolver.query(CONTENT_URI, projection, COLUMN_TITLE + " LIKE ?", new String[]{contain}, null);

        if(cursor.getCount() > 0){
            cursor.close();
            Toast.makeText(getApplicationContext(), R.string.message_saved_movie, Toast.LENGTH_SHORT).show();
        }else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, movieTitle);
            values.put(COLUMN_SYNOPSIS, movieSynopsis);
            values.put(COLUMN_RATING, movieRating);
            values.put(COLUMN_RELEASE_DATE, movieReleaseDate);
            values.put(COLUMN_POSTER_PATH, moviePoster);

            Uri newUri = getContentResolver().insert(CONTENT_URI, values);

            if(newUri == null){
                Toast.makeText(this, R.string.message_error_in_save_movie, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, R.string.message_saved, Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }

    }

    private void share(){
        String key = videoKeyList.get(0);
        if(!key.isEmpty()){
            String shareBody = "http://www.youtube.com/watch?v=" + videoKeyList.get(0);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Youtube");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Enjoy"));

        }else{
            Toast.makeText(getApplicationContext(), R.string.no_trailer_available, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState = layoutManager.onSaveInstanceState();
        outState.putParcelable(LAYOUT_MANAGER_STATE_KEY, saveState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            saveState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE_KEY);
        }
    }

}
