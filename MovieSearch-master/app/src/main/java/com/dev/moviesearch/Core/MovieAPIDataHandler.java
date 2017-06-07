package com.dev.moviesearch.Core;

import android.content.Context;
import android.os.Bundle;

import com.dev.moviesearch.Model.MoviesResponse;
import com.dev.moviesearch.Network.NetworkManager;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by krishna on 06/05/2017.
 * MovieAPIDataHandler : This class serves as a Single point of access by exposing static methods that can serve different API calls and returns respective Response objects.
 */

public class MovieAPIDataHandler {

    private MovieAPIDataHandler(){}

    /**
     * getMovieAPIData : This method takes care of initiating Network connection, make a call to Movie API by supplying expected parameters and on return the response recieved is parsed
     *                   into MovieResponse object and supplied to calling class.
     * @param bundle : Bundle object carrying user input parameters.
     * @param context : Context object to help reference resources.
     * @return : MoviesResponse model object parsed from API response.
     */
    public static MoviesResponse getMovieAPIData(Bundle bundle, Context context){

        MoviesResponse response = null;

        OkHttpClient client = NetworkManager.getConnector(context);

        final Gson gson = new Gson();

        Request request = MovieAPIRequest.createMovieAPIRequest(bundle,context);

        try {
            Response clientResp = client.newCall(request).execute();

            if(clientResp.isSuccessful()){
                response = gson.fromJson(clientResp.body().charStream(), MoviesResponse.class);
                clientResp.close();
                return response;
            }
            else{
                clientResp.close();
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return response;
        }
    }
}
