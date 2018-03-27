package m7edshin.popularmovieapp.MoviesDatabase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohamed Shahin on 03/03/2018.
 * Constants for Movie Database Table, Columns, etc.
 */

public class DbContract {

    public static final String CONTENT_AUTHORITY = "m7edshin.popularmovieapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    private DbContract(){}

    public static final class DatabaseEntry implements BaseColumns{

        public static final String TABLE_NAME = "Movies";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_POSTER_PATH = "PosterPath";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_RATING = "Rating";
        public static final String COLUMN_RELEASE_DATE = "ReleaseDate";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

    }

}




