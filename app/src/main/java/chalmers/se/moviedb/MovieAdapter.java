package chalmers.se.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jakob on 2016-04-06.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private final Context context;
    private final List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster, null);

        ViewHolder holder = new ViewHolder(view, new MovieAdapter.ViewHolder.IViewHolderClickListener() {
            @Override
            public void onMovieClick(ImageView view, int position) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movie_detail", movies.get(position));
                context.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView image = holder.image;
        Picasso.with(context).load(movies.get(position).getPosterPath()).into(image);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        IViewHolderClickListener clickListener;

        public ViewHolder(View itemView, IViewHolderClickListener clickListener) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.movie_poster);
            this.clickListener = clickListener;
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onMovieClick(image, this.getLayoutPosition());
        }

        public static interface IViewHolderClickListener {
            public void onMovieClick(ImageView view, int position);
        }
    }
}
