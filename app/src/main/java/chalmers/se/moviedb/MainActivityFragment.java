package chalmers.se.moviedb;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(MainActivityFragment.class.getSimpleName(), "Creating fragment view");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        List<Movie> movies = new ArrayList<>();

        GridView grid = (GridView) rootView.findViewById(R.id.main_movie_grid_view);
        grid.setAdapter(new ImageAdapter(getActivity(), movies));

        try {
            movies.addAll(new FetchMovieData().execute().get());
            for(Movie movie : movies) {
                Log.v(MainActivityFragment.class.getSimpleName(), movie.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private class FetchMovieData extends AsyncTask<String, Void, List<Movie>> {

        private final String LOG_TAG = FetchMovieData.class.getSimpleName();

        @Override
        protected List<Movie> doInBackground(String... params) {

            final String BASE_API_URL = "http://api.themoviedb.org/3/movie/";
            final String POSTER_PATH_BASE_URL = "http://image.tmdb.org/t/p/w185";

            HttpURLConnection conn = null;
            BufferedReader reader = null;
            String result = "";

            try {
                Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                        .appendPath("popular")
                        .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DATABASE_API_KEY)
                        //.appendQueryParameter("page", "10")
                        .build();

                URL url = new URL(uri.toString());

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream stream = conn.getInputStream();
                if(stream == null) {
                    return null;
                }

                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line;
                while((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0) {
                    return null;
                }

                result = buffer.toString();

                Log.v(LOG_TAG, buffer.toString());
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

            try {
                return getMovieDataFromJson(result, POSTER_PATH_BASE_URL);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage());
            }

            return null;
        }

        private List<Movie> getMovieDataFromJson(String result, String posterPathBaseURL) throws JSONException {
            final String TMDB_RESULTS = "results";
            final String POSTER_PATH = "poster_path";
            final String ORIGINAL_TITLE = "original_title";
            final String RATING = "vote_average";
            final String OVERVIEW = "overview";
            final String RELEASE_DATE = "release_date";

            JSONObject list = new JSONObject(result);
            JSONArray results = list.getJSONArray("results");

            List<Movie> downloadedMovies = new ArrayList<>();

            for(int i = 0; i < results.length(); ++i) {
                JSONObject movieJson = results.getJSONObject(i);
                String title = movieJson.getString(ORIGINAL_TITLE);
                String posterPath = posterPathBaseURL + movieJson.getString(POSTER_PATH);
                String rating = movieJson.getString(RATING);
                String plot = movieJson.getString(OVERVIEW);
                String releaseDate = movieJson.getString(RELEASE_DATE);

                Movie movie = new Movie(title, releaseDate, plot, rating, posterPath);
                downloadedMovies.add(movie);
            }

            return downloadedMovies;
        }


    }
}
