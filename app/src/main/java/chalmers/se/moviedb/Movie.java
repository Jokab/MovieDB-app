package chalmers.se.moviedb;

import java.io.Serializable;

/**
 * Created by Jakob on 2016-04-06.
 */
public class Movie implements Serializable {

    private final String title;
    private final String releaseDate;
    private final String plot;
    private final String rating;
    private final String posterPath;

    public Movie(String title, String releaseDate, String plot, String rating, String posterPath) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.plot = plot;
        this.rating = rating;
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPlot() {
        return plot;
    }

    public String getRating() {
        return rating;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
