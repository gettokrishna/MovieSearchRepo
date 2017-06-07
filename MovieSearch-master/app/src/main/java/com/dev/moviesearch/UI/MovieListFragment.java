package com.dev.moviesearch.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.moviesearch.Core.MovieAPIDataHandler;
import com.dev.moviesearch.Model.MoviesResponse;
import com.dev.moviesearch.Network.NetworkManager;
import com.dev.moviesearch.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by pavuluri on 06/06/2017.
 * MovieListFragment : This Fragment class is responsible for rendering List View by supplying adapter with the data pulled out from the End point.
 */

public class MovieListFragment extends Fragment {


    public MovieListFragment(){}

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutMgr;
    private MovieListAdapter mAdapter;
    private MovieLazyLoader loaderOnScrollListner = null;
    private int endPointItemCnt = 0;
    private String searchQuery = "";
    private List<MoviesResponse.Result> listItemData = new ArrayList<MoviesResponse.Result>();
    private OnSearchDataListner mCallback = null;

    // Interface acts as a bridge between Activity using this Fragment class, helps in updating data extraction status from end point.
    public interface OnSearchDataListner{
        void searchDataStatusUpdate(String status);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          return inflater.inflate(R.layout.fragment_movie_list_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutMgr    = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutMgr);
      }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback =(OnSearchDataListner) context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * getMovieResponse : This method is being called from Activity every time user enters a new Search criteria and submits for search.
     *                    It handles all clean up activities and prepares input data required before initiating a data fetch.
     * @param query : Search string input from user
     */
    public void getMovieResponse(String query){
        searchQuery = query;
        final Bundle bundle = new Bundle();
        bundle.putString("Query",query);
        bundle.putInt("Page_No",1);
        loaderOnScrollListner = null;
        mAdapter = null;
        listItemData.clear();
        DataAsyncExtractor dataAsync = new DataAsyncExtractor();
        dataAsync.execute(bundle);
    }

    /**
     * DataAsyncExtractor : This class responsible for handling the data extraction on a background thread and update the results back to UI layer
     */
    private class DataAsyncExtractor extends AsyncTask<Bundle, String, MoviesResponse> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected MoviesResponse doInBackground(Bundle... params) {
            MoviesResponse response = MovieAPIDataHandler.getMovieAPIData(params[0],MovieListFragment.this.getContext());
            return response;
        }


        @Override
        protected void onPostExecute(MoviesResponse result) {
            // execution of result of Long time consuming operation
            if(result == null){
               mCallback.searchDataStatusUpdate(getResources().getString(R.string.data_response_failure_msg));
            }
            else {
                if(mAdapter == null){
                    FragmentManager fm = getFragmentManager();
                    listItemData = result.getResults();
                    if(listItemData.size()>0){
                        mAdapter = new MovieListAdapter(listItemData,MovieListFragment.this.getContext(),fm);
                        mRecyclerView.setAdapter(mAdapter);
                        mCallback.searchDataStatusUpdate("Success");
                        endPointItemCnt = result.getTotalResults();
                        if(loaderOnScrollListner == null){
                            loaderOnScrollListner = new MovieLazyLoader(endPointItemCnt,mLayoutMgr){
                                @Override
                                public void pullNextSetOfData(int nextPage) {
                                    DataAsyncExtractor dataAsync = new DataAsyncExtractor();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Query",searchQuery);
                                    bundle.putInt("Page_No",nextPage);
                                    dataAsync.execute(bundle);
                                }
                            };
                            mRecyclerView.addOnScrollListener(loaderOnScrollListner);

                        }
                    }
                    else{
                        mCallback.searchDataStatusUpdate(getResources().getString(R.string.no_records_msg));
                    }
                }
                else{
                    for (MoviesResponse.Result item: result.getResults()) {
                        listItemData.add(item);
                    }
                    mAdapter.dataFeedChanged(listItemData);
                }
            }
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }
    }




}