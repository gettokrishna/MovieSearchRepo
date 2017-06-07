package com.dev.moviesearch.Core;

import android.content.Context;
import android.os.Bundle;

import com.dev.moviesearch.R;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by krishna on 6/05/2017.
 * MovieAPIRequest : This class supplies static methods that can build respective Request objects related to Movie API calls.
 */

public class MovieAPIRequest {

    private MovieAPIRequest(){
    }

    /**
     * createMovieAPIRequest : This method helps in building Request object with all required parameters that Movie Search API expects to pull data from end point.
     * @param bundle : Bundle object carrying user input parameters.
     * @param context : Context object to help reference resources.
     * @return OkHttpRequest : Request object formulated to carry required parameters for Movie Search API.
     */
    public static Request createMovieAPIRequest(Bundle bundle, Context context){

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.themoviedb.org/3/search/movie").newBuilder();
        urlBuilder.addQueryParameter("api_key", context.getResources().getString(R.string.movie_api_token));
        urlBuilder.addQueryParameter("language", "en-US");
        urlBuilder.addQueryParameter("query",bundle.getString("Query"));
        urlBuilder.addQueryParameter("page",String.valueOf(bundle.getInt("Page_No",1)));
        String url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();

        return request;

    }
}
