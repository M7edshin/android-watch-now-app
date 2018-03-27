package m7edshin.popularmovieapp.MoviesDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_POSTER_PATH;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_RATING;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_RELEASE_DATE;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_SYNOPSIS;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.COLUMN_TITLE;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry.TABLE_NAME;
import static m7edshin.popularmovieapp.MoviesDatabase.DbContract.DatabaseEntry._ID;

/**
 * Created by Mohamed Shahin on 03/03/2018.
 * Create, open or manage the database
 */

public class DbSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "shahin_movies.db";


    public DbSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_NAME + "( " + _ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_POSTER_PATH + " TEXT NOT NULL, " + COLUMN_SYNOPSIS + " TEXT NOT NULL," +
                COLUMN_RATING + " TEXT NOT NULL, " + COLUMN_RELEASE_DATE + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
