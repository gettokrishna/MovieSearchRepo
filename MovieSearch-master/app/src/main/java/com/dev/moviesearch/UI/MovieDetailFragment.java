package com.dev.moviesearch.UI;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.moviesearch.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by krishna on 6/05/2017.
 * MovieDetailFragment : This Fragment class is responsible for rendering Movie details.
 */

public class MovieDetailFragment extends Fragment {

    private Bundle mData;
    private final String BaseURL = "https://image.tmdb.org/t/p/original";

    public MovieDetailFragment(){

    }

    public TextView mMovieNameView;
    public ImageView mMovieImageView;
    public TextView mMovieOverview,mMovieVoteCount,mMovieRating,mMovieLang,mMovieReleaseDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          return inflater.inflate(R.layout.movie_details_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMovieNameView = (TextView) getView().findViewById(R.id.title);
        mMovieImageView = (ImageView) getView().findViewById(R.id.detail_view_image);
        mMovieOverview = (TextView) getView().findViewById(R.id.overview);
        mMovieLang = (TextView) getView().findViewById(R.id.language);
        mMovieReleaseDate = (TextView) getView().findViewById(R.id.releaseDate);
        mMovieVoteCount = (TextView)getView().findViewById(R.id.likes_dislikes_count);
        mMovieRating = (TextView) getView().findViewById(R.id.votes_avg);
        mData = this.getArguments();
        mMovieNameView.setText(mData.getString("Title"));

        String image_url = BaseURL+mData.getString("Path");

        Picasso.with(this.getContext())
                .load(image_url)
                .placeholder(R.mipmap.loading_image)
                .error(R.mipmap.ic_launcher)
                .into(mMovieImageView);

        mMovieOverview.setText(mData.getString("Overview"));
        mMovieLang.setText(mData.getString("Language"));
        mMovieReleaseDate.setText(mData.getString("ReleaseDate"));
        mMovieVoteCount.setText(mData.getString("VoteCount"));
        mMovieRating.setText(mData.getString("VoteAvg"));
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

}