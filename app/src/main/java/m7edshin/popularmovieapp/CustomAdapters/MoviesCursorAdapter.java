package m7edshin.popularmovieapp.CustomAdapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import m7edshin.popularmovieapp.R;

import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_POSTER_PATH;
import static m7edshin.popularmovieapp.Utilities.Constants.POSTER_PATH;

/**
 * Created by Mohamed Shahin on 03/03/2018.
 * Hold/Display data from the database
 */

public class MoviesCursorAdapter extends RecyclerView.Adapter<MoviesCursorAdapter.FavMoviesViewHolder>{

    private Cursor cursor;

    public MoviesCursorAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public MoviesCursorAdapter.FavMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_recycler_item,
                parent, false);
        return new FavMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesCursorAdapter.FavMoviesViewHolder holder, int position) {
        if(cursor.moveToPosition(position)){
            int posterPathColumnIndex = cursor.getColumnIndex(COLUMN_POSTER_PATH);
            String posterPath = cursor.getString(posterPathColumnIndex);
            holder.bind(posterPath);
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class FavMoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_movie_image;

        public FavMoviesViewHolder(View itemView) {
            super(itemView);
            iv_movie_image = itemView.findViewById(R.id.iv_movie_image);
        }

        public void bind(String posterPath){
            String path = POSTER_PATH + posterPath;
            Picasso.with(itemView.getContext()).load(path).into(iv_movie_image);
        }
    }

}
