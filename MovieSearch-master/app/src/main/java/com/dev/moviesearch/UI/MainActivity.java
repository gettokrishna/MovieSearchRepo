
package com.dev.moviesearch.UI;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.moviesearch.R;

/**
 * MainActivity : Initial activity responsible for Home page layout.
 */

public class MainActivity extends AppCompatActivity implements MovieListFragment.OnSearchDataListner{

    private LinearLayout mSearchLayout,fallbackLayout;
    private EditText mSearchText;
    private Button mSearchBtn;
    private String mSearchQuery = " ";
    private MovieListFragment listFragment;
    private TextView fallbackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.cust_toolbar);
        toolbar.setTitle(getResources().getString(R.string.home_page_title));
        setSupportActionBar(toolbar);
        mSearchLayout = (LinearLayout) findViewById(R.id.searchLayoutView);
        mSearchText  = (EditText) findViewById(R.id.searchTextView);
        mSearchBtn   =  (Button) findViewById(R.id.searchButton);
        fallbackLayout = (LinearLayout)findViewById(R.id.fallback_layout);
        fallbackTextView = (TextView)findViewById(R.id.fallback_textview);
        FragmentManager fm = MainActivity.this.getSupportFragmentManager();
        listFragment  = (MovieListFragment) fm.findFragmentByTag("MovieListFragment");
        if(listFragment== null)
        {
            fallbackLayout.setVisibility(View.VISIBLE);
            listFragment = new MovieListFragment();
            fm.beginTransaction().add(R.id.movieListContainer,listFragment,"MovieListFragment").commit();
        }

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                fallbackTextView.setText(getResources().getString(R.string.home_loading_msg));
                mSearchQuery = mSearchText.getText().toString();
                listFragment.getMovieResponse(mSearchQuery);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
      }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchBtn.setOnClickListener(null);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search : {
                if(mSearchLayout.getVisibility() == View.VISIBLE){
                    mSearchLayout.setVisibility(View.GONE);
                }
                else{
                    mSearchLayout.setVisibility(View.VISIBLE);
                }

                break;
            }
            default : break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
          super.onBackPressed();
    }

    @Override
    public void searchDataStatusUpdate(String statusMsg) {
        if(statusMsg.equalsIgnoreCase("Success")){
            fallbackLayout.setVisibility(View.INVISIBLE);
        }
        else{
            fallbackLayout.setVisibility(View.VISIBLE);
            fallbackTextView.setText(statusMsg);
        }
    }
}
