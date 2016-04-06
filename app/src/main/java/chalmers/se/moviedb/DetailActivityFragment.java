package chalmers.se.moviedb;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        if(intent != null && intent.hasExtra("movie_detail")) {
            Movie movie = (Movie) intent.getSerializableExtra("movie_detail");

            ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_image);
            Picasso.with(getActivity()).load(movie.getPosterPath()).into(imageView);

            TextView titleView = (TextView) rootView.findViewById(R.id.detail_title);
            titleView.setText(movie.getTitle());

            TextView yearView = (TextView) rootView.findViewById(R.id.detail_year);
            yearView.setText(movie.getReleaseDate().substring(0,4));

            // TODO: Requires separate api call. Wait with this.
//            TextView runtimeView = (TextView) rootView.findViewById(R.id.detail_year);
//            runtimeView.setText(movie.get());

            TextView ratingView = (TextView) rootView.findViewById(R.id.detail_rating);
            ratingView.setText(movie.getRating() + " / 10");

            Button favoriteButton = (Button) rootView.findViewById(R.id.detail_favorite_button);

        }


        return rootView;
    }
}
