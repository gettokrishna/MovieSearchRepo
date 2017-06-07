package com.dev.moviesearch.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.moviesearch.Model.MoviesResponse;
import com.dev.moviesearch.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pavuluri on 06/05/2017.
 * MovieListAdapter : List Adapter responsible for creating views and populate child views with respective data in the list using the data feed supplied.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.viewHolder> {

    private List<MoviesResponse.Result> mDataFeed;
    private Context mcontext;
    private FragmentManager mfm;
    private final String ImageBaseURL = "https://image.tmdb.org/t/p/original";


    public MovieListAdapter(List<MoviesResponse.Result> dataFeed, Context context, FragmentManager fm){
        this.mDataFeed = dataFeed;
        this.mcontext = context;
        this.mfm = fm;
    }

    public static class viewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout mViewGroup;
        public TextView mMovieNameView;
        public ImageView mMovieImageView;
        public TextView mMovieOverview,mMovieVoteCount,mMovieRating;


        public viewHolder(View v){
            super(v);
            mViewGroup = (RelativeLayout)v.findViewById(R.id.movieViewGroup);
            mMovieNameView = (TextView) v.findViewById(R.id.movie_name);
            mMovieImageView = (ImageView) v.findViewById(R.id.movie_image);
            mMovieOverview = (TextView) v.findViewById(R.id.movie_overview);
            mMovieVoteCount = (TextView)v.findViewById(R.id.likes_dislikes_count);
            mMovieRating = (TextView) v.findViewById(R.id.votes_rating);
        }
    }

    @Override
    public MovieListAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_view,parent,false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        String encodedImage = null;

        final MoviesResponse.Result rd =  mDataFeed.get(position);
        holder.mMovieNameView.setText(rd.getOriginalTitle());
        holder.mMovieOverview.setText(rd.getOverview());
        holder.mMovieVoteCount.setText(String.valueOf(rd.getVoteCount()));
        holder.mMovieRating.setText(rd.getVoteAverage()+" / 10");

        String image_url = ImageBaseURL+rd.getPosterPath();

        Picasso.with(mcontext)
                .load(image_url)
                .placeholder(R.mipmap.loading_image)
                .error(R.mipmap.ic_launcher)
                .resize(135, 135)
                .centerCrop()
                .into(holder.mMovieImageView);

        holder.mViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("Overview",rd.getOverview());
                bundle.putString("Language",rd.getOriginalLanguage());
                bundle.putString("ReleaseDate",rd.getReleaseDate());
                bundle.putString("Title",rd.getOriginalTitle());
                bundle.putString("VoteCount",String.valueOf(rd.getVoteCount()));
                bundle.putString("VoteAvg",rd.getVoteAverage()+" / 10");
                bundle.putString("Path",rd.getPosterPath());

                // Checking if device type is Tablet and managing Detail view display
                if(mcontext.getResources().getBoolean(R.bool.isTablet)){
                    MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                    movieDetailFragment.setArguments(bundle);
                    if(mfm.findFragmentByTag("MovieDetailFragment")==null)
                    mfm.beginTransaction().add(R.id.movieDetailLayoutContainer,movieDetailFragment,"MovieDetailFragment").commit();
                    else
                    mfm.beginTransaction().replace(R.id.movieDetailLayoutContainer,movieDetailFragment,"MovieDetailFragment").commit();
                }
                else {
                    Intent intent = new Intent(mcontext, DetailActivity.class);
                    intent.putExtras(bundle);
                    mcontext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataFeed.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Method accessed by other components to intimate data feed changes to the Adapter
     * @param newDataFeed
     */
    public void dataFeedChanged(List<MoviesResponse.Result> newDataFeed){
        int oldPoistion = mDataFeed.size();
        int newPosition = newDataFeed.size()-1;
        mDataFeed =  newDataFeed;
        notifyItemRangeInserted(oldPoistion,newPosition-oldPoistion);
    }
}
