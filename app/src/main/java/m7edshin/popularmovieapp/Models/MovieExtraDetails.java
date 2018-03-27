package m7edshin.popularmovieapp.Models;

import java.util.List;

/**
 * Created by Mohamed Shahin on 26/02/2018.
 * MovieDetails Class for each movie's extra details (Trailer & Reviews)
 */

public class MovieExtraDetails {


    private List<String> reviewsList;
    private List<String> videosList;

    public MovieExtraDetails(List<String> reviewsList, List<String> videosList) {
        this.reviewsList = reviewsList;
        this.videosList = videosList;
    }

    public List<String> getReviewsList() {
        return reviewsList;
    }

    public List<String> getVideosList() {
        return videosList;
    }

}
