package m7edshin.popularmovieapp.MoviesDatabase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.CONTENT_AUTHORITY;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_POSTER_PATH;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_RATING;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_RELEASE_DATE;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_SYNOPSIS;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_TITLE;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.TABLE_NAME;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry._ID;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.PATH_MOVIES;

/**
 * Created by Mohamed Shahin on 03/03/2018.
 * CRUD Implementation
 */

public class DbContentProvider extends ContentProvider {

    private static final String LOG_TAG = DbContentProvider.class.getName();
    private static final int MOVIES = 100;
    private static final int MOVIE_ID = 101;

    private DbSQLiteOpenHelper dbSQLiteOpenHelper;

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES, MOVIES);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES + "/#", MOVIE_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbSQLiteOpenHelper = new DbSQLiteOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database = dbSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor;

        int match = buildUriMatcher().match(uri);

        switch (match){
            case MOVIES:
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case MOVIE_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("The query cannot be performed. Unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Insert a row (Movie) in the table
     * @param uri Uri to query
     * @param values Row values
     * @return Row Path
     */
    private Uri insertMovie(Uri uri, ContentValues values){

        String title = values.getAsString(COLUMN_TITLE);
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("Title has no value");
        }

        String posterPath = values.getAsString(COLUMN_POSTER_PATH);
        if(posterPath == null || posterPath.isEmpty()){
            throw new IllegalArgumentException("PosterPath has no value");
        }

        String synopsis = values.getAsString(COLUMN_SYNOPSIS);
        if(synopsis == null || synopsis.isEmpty()){
            throw new IllegalArgumentException("Synopsis has no value");
        }

        String rating = values.getAsString(COLUMN_RATING);
        if(rating == null || rating.isEmpty()){
            throw new IllegalArgumentException("Rating has no value");
        }

        String releaseDate = values.getAsString(COLUMN_RELEASE_DATE);
        if(releaseDate == null || releaseDate.isEmpty()){
            throw new IllegalArgumentException("ReleaseDate has no value");
        }

        SQLiteDatabase database = dbSQLiteOpenHelper.getWritableDatabase();
        long id = database.insert(TABLE_NAME, null, values);

        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert the values in the row for  " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = buildUriMatcher().match(uri);

        switch (match){

            case MOVIES:
                return insertMovie(uri, values);
            default:
                throw  new IllegalArgumentException("Insertion is failed for " + uri);
        }
    }

    /**
     * Update row(s) in the table
     * @param uri Uri to query
     * @param values values to be updated
     * @param selection Row(s) to be updated
     * @param selectionArgs Replace ? for the "selection" part
     * @return Row(s) updated
     */
    private int updateMovie(Uri uri, ContentValues values, String selection, String[] selectionArgs){

        if(values.containsKey(COLUMN_TITLE)){
            String title = values.getAsString(COLUMN_TITLE);
            if(title == null || title.isEmpty()){
                throw new IllegalArgumentException("Title has no value");
            }
        }

        if(values.containsKey(COLUMN_POSTER_PATH)){
            String posterPath = values.getAsString(COLUMN_POSTER_PATH);
            if(posterPath == null || posterPath.isEmpty()){
                throw new IllegalArgumentException("PosterPath has no value");
            }
        }

        if(values.containsKey(COLUMN_RATING)){
            String rating = values.getAsString(COLUMN_RATING);
            if(rating == null || rating.isEmpty()){
                throw new IllegalArgumentException("Rating has no value");
            }
        }

        if(values.containsKey(COLUMN_SYNOPSIS)){
            String synopsis = values.getAsString(COLUMN_RATING);
            if(synopsis == null || synopsis.isEmpty()){
                throw new IllegalArgumentException("Synopsis has no value");
            }
        }

        if(values.containsKey(COLUMN_RELEASE_DATE)){
            String releaseDate = values.getAsString(COLUMN_RELEASE_DATE);
            if(releaseDate == null || releaseDate.isEmpty()){
                throw new IllegalArgumentException("ReleaseDate has no value");
            }
        }

        if(values.size() == 0){
            return 0;
        }

        SQLiteDatabase database = dbSQLiteOpenHelper.getWritableDatabase();

        int rowsUpdated = database.update(TABLE_NAME, values, selection, selectionArgs);

        if(rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int match = buildUriMatcher().match(uri);

        switch (match){
            case MOVIES:
                return updateMovie(uri, values, selection, selectionArgs);
            case MOVIE_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateMovie(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update cannot be done as not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase database = dbSQLiteOpenHelper.getWritableDatabase();
        int rowsDeleted;
        int match = buildUriMatcher().match(uri);

        switch (match){
            case MOVIES:
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Delete cannot be done as not supported for " + uri);
        }

        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        //Not using this method as my application will not interact with other applications
        return null;
    }
}
