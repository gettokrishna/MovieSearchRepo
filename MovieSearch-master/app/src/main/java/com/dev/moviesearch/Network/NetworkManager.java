package com.dev.moviesearch.Network;

import android.content.Context;
import com.dev.moviesearch.R;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Created by krishna on 06/05/2017.
 * NetworkManager : Class responsible for Connection object creation through static methods.
 */
public class NetworkManager  {

    private static OkHttpClient client;

    /**
     *  This method ensures a Single Client connection object is active at any point of time with respective configured values to maintiain Caching, Connection pooling effectively.
     */
    public static OkHttpClient getConnector(Context context) {
        if(client==null) {
            client = new OkHttpClient.Builder().connectTimeout(context.getResources().getInteger(R.integer.connectionTimeout), TimeUnit.MILLISECONDS).build();
        }
            return client;
    }

}
