package com.dev.moviesearch.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.dev.moviesearch.R;

/**
 * DetailActivity : Activity responsible for rendering Fragment carrying Movie Details.
 */

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.cust_toolbar);
        toolbar.setTitle(getResources().getString(R.string.detail_page_title));
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        MovieDetailFragment mdf = new MovieDetailFragment();
        mdf.setArguments(getIntent().getExtras());
        fm.beginTransaction().add(R.id.movieDetailContainer,mdf).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu,menu);
        menu.removeItem(R.id.menu_search);
        return true;
    }



    @Override
    public void onBackPressed() {
      this.finish();
      super.onBackPressed();
    }
}
