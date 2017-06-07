package com.dev.moviesearch.UI;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;


/**
 * Created by Krishna on 6/6/2017.
 * MovieLazyLoader : This class responsible for Loading additional data as the user scrolls the list view and when the specified Threshold limit is reached.
 */

public abstract class MovieLazyLoader extends RecyclerView.OnScrollListener {


    private int endResultCount = 0;
    private int page = 1;
    private int Threshold = 5;
    private int firstItemPosition,visibleItemCnt,itemCntOnListView,oldCnt = 0;
    private LinearLayoutManager mLayoutMgr= null;

    MovieLazyLoader(int totalEndResults, LinearLayoutManager layoutMgr){
        endResultCount = totalEndResults;
        mLayoutMgr = layoutMgr;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        itemCntOnListView = mLayoutMgr.getItemCount();
        if(!(itemCntOnListView >= endResultCount)){
            firstItemPosition = mLayoutMgr.findFirstVisibleItemPosition();
            visibleItemCnt = recyclerView.getChildCount();
            if(itemCntOnListView > 0 && itemCntOnListView > oldCnt ){
                if(firstItemPosition+visibleItemCnt+Threshold >= itemCntOnListView){
                    page++;
                    pullNextSetOfData(page);
                    oldCnt = itemCntOnListView;
                }
            }
        }
    }

    public abstract void pullNextSetOfData(int nextPage);
}
