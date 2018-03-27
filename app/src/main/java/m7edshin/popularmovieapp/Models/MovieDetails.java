package m7edshin.popularmovieapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed Shahin on 17/02/2018.
 * MovieDetails Class for each movie's details
 */

public class MovieDetails implements Parcelable {

    private String id;
    private String title;
    private String releaseDate;
    private String poster;
    private String vote;
    private String synopsis;

    public MovieDetails(String id, String title, String releaseDate, String poster, String vote, String synopsis) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.poster = poster;
        this.vote = vote;
        this.synopsis = synopsis;
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public String getPoster() {
        return poster;
    }
    public String getVote() {
        return vote;
    }
    public String getSynopsis() {
        return synopsis;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(poster);
        dest.writeString(vote);
        dest.writeString(synopsis);
    }

    private MovieDetails(Parcel in){
        this.id = in.readString();
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.poster = in.readString();
        this.vote = in.readString();
        this.synopsis = in.readString();
    }

    public static final Parcelable.Creator<MovieDetails> CREATOR = new Parcelable.Creator<MovieDetails>(){

        @Override
        public MovieDetails createFromParcel(Parcel source) {
            return new MovieDetails(source);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
}
