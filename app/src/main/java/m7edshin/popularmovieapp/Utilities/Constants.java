package m7edshin.popularmovieapp.Utilities;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import m7edshin.popularmovieapp.BuildConfig;

/**
 * Created by Mohamed Shahin on 03/03/2018.
 * List of all the declared final variables
 */

public class Constants {

    //Personal API Key
    public static final String PERSONAL_MOVIES_DB_API_KEY = BuildConfig.API_KEY;

    //API Related
    public static final String POSTER_PATH = "http://image.tmdb.org/t/p/w500";
    public static final String MOVIE_API_URL = "https://api.themoviedb.org/3/movie/";
    public static final String API_URL_ADD_NOW_PLAYING = "now_playing";

    public static final String MOVIES_DISCOVER_MAIN_URL = "https://api.themoviedb.org/3/discover/movie";

    public static final String QUERY_VIDEOS_REVIEWS = "videos,reviews";
    public static final String QUERY_REGION = "gb";
    public static final String QUERY_SORTING_AVERAGE = "vote_average.desc";
    public static final String QUERY_SORTING_POPULAR = "popularity.desc";
    public static final String QUERY_VOTE_AVERAGE = "10";

    public static final String API_PAR_KEY = "api_key";
    public static final String APPEND_TO_RESPONSE_KEY = "append_to_response";
    public static final String REGION_PAR_KEY = "region";
    public static final String SORTING_PAR_KEY = "sort_by";
    public static final String VOTE_AVERAGE_FROM_PAR_KEY = "vote_average.gte";
    public static final String YEAR_PAR_KEY = "primary_release_year";
    public static final String PRIMARY_RELEASE_DATE_FROM_PAR_KEY = "primary_release_date.gte";
    public static final String PRIMARY_RELEASE_DATE_TO_PAR_KEY = "primary_release_date.lte";
    public static final String RELEASE_DATE_FROM_PAR_KEY = "release_date.gte";
    public static final String RELEASE_DATE_TO_PAR_KEY = "release_date.lte";

    //Others
    public static final String NO_DATA = "N/A";
    public static final String MOVIE_INTENT_KEY = "movie";
    public static final String LAYOUT_MANAGER_STATE_KEY = "state";

    public static final int LOADER_MANAGER_ID = 1;
    public static final int NO_OF_DAYS = 30;
    public static final int YEARS = -10;



    public static String getCurrentYear(){
        return new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
    }
}
