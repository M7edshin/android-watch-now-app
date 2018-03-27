package m7edshin.popularmovieapp.Fragments;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import m7edshin.popularmovieapp.BuildConfig;
import m7edshin.popularmovieapp.CustomAdapters.MoviesRecyclerAdapter;
import m7edshin.popularmovieapp.InterfaceUtilities.ColumnsFitting;
import m7edshin.popularmovieapp.InterfaceUtilities.RecyclerViewTouchListener;
import m7edshin.popularmovieapp.Models.MovieDetails;
import m7edshin.popularmovieapp.MovieDetailsActivity;
import m7edshin.popularmovieapp.R;
import m7edshin.popularmovieapp.Utilities.MovieDetailsLoader;

import static m7edshin.popularmovieapp.Utilities.Constants.API_PAR_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.LAYOUT_MANAGER_STATE_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.LOADER_MANAGER_ID;
import static m7edshin.popularmovieapp.Utilities.Constants.MOVIES_DISCOVER_MAIN_URL;
import static m7edshin.popularmovieapp.Utilities.Constants.MOVIE_API_URL;
import static m7edshin.popularmovieapp.Utilities.Constants.MOVIE_INTENT_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.PERSONAL_MOVIES_DB_API_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.QUERY_REGION;
import static m7edshin.popularmovieapp.Utilities.Constants.QUERY_SORTING_POPULAR;
import static m7edshin.popularmovieapp.Utilities.Constants.REGION_PAR_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.SORTING_PAR_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.YEAR_PAR_KEY;
import static m7edshin.popularmovieapp.Utilities.Constants.getCurrentYear;

/**
 * Created by Mohamed Shahin on 08/03/2018.
 * Popular Movies Fragment implementation
 */

public class PopularMoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MovieDetails>> {

    @BindView(R.id.recycle_view_movies) RecyclerView recycle_view_movies;
    @BindView(R.id.tv_no_connection) TextView tv_no_connection;

    private MoviesRecyclerAdapter moviesRecyclerAdapter;
    private GridLayoutManager layoutManager;


    private List<MovieDetails> moviesList;

    private Parcelable layoutManagerState;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            layoutManagerState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE_KEY);
        }

        View rootView = inflater.inflate(R.layout.fragment_movies, container,false);
        ButterKnife.bind(this, rootView);
        tv_no_connection.setVisibility(View.INVISIBLE);

        //RecyclerView setup
        int numberOfColumns = ColumnsFitting.calculateNoOfColumns(getActivity());
        layoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        recycle_view_movies.setLayoutManager(layoutManager);

        fetchMovieData();

        recycle_view_movies.addOnItemTouchListener(new RecyclerViewTouchListener
                (getActivity(),recycle_view_movies, new RecyclerViewTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        MovieDetails movieDetails = moviesList.get(position);
                        Context context = getActivity();
                        Intent movieDetailsIntent = new Intent(context, MovieDetailsActivity.class);
                        movieDetailsIntent.putExtra(MOVIE_INTENT_KEY, movieDetails);
                        startActivity(movieDetailsIntent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

        return rootView;
    }


    @Override
    public Loader<List<MovieDetails>> onCreateLoader(int id, Bundle args) {
        String year = getCurrentYear();
        String query = createUrlRequest(year);
        return new MovieDetailsLoader(getActivity(), query);
    }

    @Override
    public void onLoadFinished(Loader<List<MovieDetails>> loader, List<MovieDetails> data) {
        if (data != null && !data.isEmpty()) {
            moviesList = data;
            moviesRecyclerAdapter = new MoviesRecyclerAdapter(moviesList);
            recycle_view_movies.setAdapter(moviesRecyclerAdapter);
            layoutManager.onRestoreInstanceState(layoutManagerState);
        }else{
            tv_no_connection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieDetails>> loader) {
        moviesRecyclerAdapter = new MoviesRecyclerAdapter(new ArrayList<MovieDetails>());
    }

    private void fetchMovieData() {

        boolean isConnected = checkInternetConnection();

        if (isConnected) {
            tv_no_connection.setVisibility(View.INVISIBLE);
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_MANAGER_ID, null, PopularMoviesFragment.this);
        }else{
            tv_no_connection.setVisibility(View.VISIBLE);
        }

    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private String createUrlRequest(String year) {
        Uri baseUri = Uri.parse(MOVIES_DISCOVER_MAIN_URL);
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendQueryParameter(API_PAR_KEY, PERSONAL_MOVIES_DB_API_KEY)
                .appendQueryParameter(REGION_PAR_KEY, QUERY_REGION)
                .appendQueryParameter(SORTING_PAR_KEY, QUERY_SORTING_POPULAR)
                .appendQueryParameter(YEAR_PAR_KEY, year);
        return builder.build().toString();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(outState!=null){
            if(layoutManager!=null){
                layoutManagerState = layoutManager.onSaveInstanceState();
                outState.putParcelable(LAYOUT_MANAGER_STATE_KEY, layoutManagerState);
            }
        }
    }

}
