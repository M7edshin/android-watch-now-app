package m7edshin.popularmovieapp.Utilities;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static m7edshin.popularmovieapp.Utilities.Constants.NO_DATA;

/**
 * Created by Mohamed Shahin on 25/02/2018.
 * Json movie extra details (Trailer & Reviews) data extracting
 */

public class JsonMovieExtraDetails {

    private final static String LOG_TAG = JsonMovieExtraDetails.class.getName();

    private static final String KEY_VIDEOS = "videos";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_VIDEO_KEY = "key";

    private static final String KEY_REVIEWS = "reviews";
    private static final String KEY_CONTENT = "content";


    public static List<String> extractJsonMovieVideoKey(String json){

        if(TextUtils.isEmpty(json)) return null;

        List<String> videosList = new ArrayList<>();
        String videoKey;

        try {
            JSONObject base = new JSONObject(json);
            JSONObject videosObj = base.getJSONObject(KEY_VIDEOS);
            JSONArray resultsArray = videosObj.getJSONArray(KEY_RESULTS);

            for(int i = 0; i <resultsArray.length(); i++){
                JSONObject videoKeyObj = resultsArray.getJSONObject(i);
                videoKey = videoKeyObj.optString(KEY_VIDEO_KEY);
                videosList.add(videoKey);
            }
        } catch (JSONException e) {
            Log.v(LOG_TAG, "Error: Problem occurred during extracting VideosKeys");
        }
        return videosList;
    }

    public static List<String> extractJsonMovieReviews(String json){

        if(TextUtils.isEmpty(json)) return null;

        List<String> movieReviewsList = new ArrayList<>();
        String review = NO_DATA;

        try {
            JSONObject base = new JSONObject(json);
            JSONObject reviewsObj = base.getJSONObject(KEY_REVIEWS);
            JSONArray reviewsArray = reviewsObj.getJSONArray(KEY_RESULTS);

            for(int i = 0; i < reviewsArray.length(); i++){
                JSONObject obj = reviewsArray.getJSONObject(i);

                if(obj.has(KEY_CONTENT)){
                    review = obj.optString(KEY_CONTENT);
                }
                movieReviewsList.add(review);
            }

        } catch (JSONException e) {
            Log.v(LOG_TAG, "Error: Problem occurred during extracting Reviews");

        }
        return movieReviewsList;
    }


}
