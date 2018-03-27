package m7edshin.popularmovieapp.Fragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import m7edshin.popularmovieapp.CustomAdapters.MoviesCursorAdapter;
import m7edshin.popularmovieapp.FavMovieDetailsActivity;
import m7edshin.popularmovieapp.InterfaceUtilities.ColumnsFitting;
import m7edshin.popularmovieapp.InterfaceUtilities.RecyclerViewTouchListener;
import m7edshin.popularmovieapp.Models.MovieDetails;
import m7edshin.popularmovieapp.MoviesDatabase.DbSQLiteOpenHelper;
import m7edshin.popularmovieapp.R;

import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_POSTER_PATH;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_RATING;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_RELEASE_DATE;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_SYNOPSIS;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_TITLE;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.CONTENT_URI;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.TABLE_NAME;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry._ID;
import static m7edshin.popularmovieapp.Utilities.Constants.LOADER_MANAGER_ID;

public class FavoriteMoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    @BindView(R.id.recycle_view_movies) RecyclerView recycle_view_movies;
    @BindView(R.id.tv_no_connection) TextView tv_no_connection;

    private MoviesCursorAdapter moviesCursorAdapter;
    private LoaderManager loaderManager;
    private Cursor cursor;

    private final String MOVIE_INTENT_KEY = "movie";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, rootView);

        tv_no_connection.setVisibility(View.INVISIBLE);

        //RecyclerView setup
        int numberOfColumns = ColumnsFitting.calculateNoOfColumns(getActivity());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        recycle_view_movies.setLayoutManager(layoutManager);

        loaderManager = getLoaderManager();
        loaderManager.initLoader(LOADER_MANAGER_ID, null, this);

        recycle_view_movies.addOnItemTouchListener(new RecyclerViewTouchListener(getActivity(), recycle_view_movies, new RecyclerViewTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(cursor.moveToPosition(position)){
                    MovieDetails movieDetails = prepareMovieDetails();
                    Intent movieDetailsIntent = new Intent(getActivity(), FavMovieDetailsActivity.class);
                    movieDetailsIntent.putExtra(MOVIE_INTENT_KEY, movieDetails);
                    startActivity(movieDetailsIntent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                if(cursor.moveToPosition(position)){
                    int idColumnIndex = cursor.getColumnIndex(_ID);
                    String id = cursor.getString(idColumnIndex);
                    delete(id);
                    Toast.makeText(getActivity(), R.string.removed, Toast.LENGTH_SHORT).show();
                    loaderManager.restartLoader(LOADER_MANAGER_ID, null, FavoriteMoviesFragment.this);
                }
            }
        }));

        return rootView;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                _ID, COLUMN_TITLE,
                COLUMN_SYNOPSIS,
                COLUMN_POSTER_PATH,
                COLUMN_RATING,
                COLUMN_RELEASE_DATE};

        return new CursorLoader(getActivity(), CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursor = data;
        moviesCursorAdapter = new MoviesCursorAdapter(data);
        recycle_view_movies.setAdapter(moviesCursorAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        moviesCursorAdapter = new MoviesCursorAdapter(null);
    }

    private MovieDetails prepareMovieDetails(){
        int idColumnIndex = cursor.getColumnIndex(_ID);
        int posterPathColumnIndex = cursor.getColumnIndex(COLUMN_POSTER_PATH);
        int titleColumnIndex = cursor.getColumnIndex(COLUMN_TITLE);
        int synopsisColumnIndex = cursor.getColumnIndex(COLUMN_SYNOPSIS);
        int ratingColumnIndex = cursor.getColumnIndex(COLUMN_RATING);
        int releaseDateColumnIndex = cursor.getColumnIndex(COLUMN_RELEASE_DATE);

        String id = cursor.getString(idColumnIndex);
        String posterPath = cursor.getString(posterPathColumnIndex);
        String title = cursor.getString(titleColumnIndex);
        String synopsis = cursor.getString(synopsisColumnIndex);
        String rating = cursor.getString(ratingColumnIndex);
        String releaseDate = cursor.getString(releaseDateColumnIndex);

        return new MovieDetails(id,title,releaseDate,posterPath,rating,synopsis);
    }

    private void delete(String id){
        ContentResolver resolver = getActivity().getContentResolver();
        resolver.delete(CONTENT_URI,_ID + "=?",new String[]{id});
    }


}
