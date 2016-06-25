package com.codepath.nytimessearch1.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.codepath.nytimessearch1.Article;
import com.codepath.nytimessearch1.ArticleAdapterRV;
import com.codepath.nytimessearch1.EndlessRecyclerViewScrollListener;
import com.codepath.nytimessearch1.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements EditNameDialogFragment.EditNameDialogListener, DatePickerDialog.OnDateSetListener{
    EditNameDialogFragment editNameDialogFragment;
    //GridView gvResults;
    ArrayList<Article> articles = new ArrayList<>();
    ArticleAdapterRV adapter = new ArticleAdapterRV(this, articles);
    String dateString1;
    @BindView(R.id.rvArticles) RecyclerView rvArticles;
    @BindView(R.id.custom_font) TextView txt;
    //TextView txt = (TextView) findViewById(R.id.custom_font);
    //RecyclerView rvArticles;
    //rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
    //ArticleArrayAdapter adapter;
    String searchQuery;
    String beginMonth;
    String beginDay;
    public String dateString;
    //EditNameDialogFragment editNameDialogFragment;
    private final int REQUEST_CODE = 20;
    public String sort;

    private boolean isTopStories;

    public boolean fashion;
    public boolean arts;
    public boolean sports;

    @BindView(R.id.toolbar) Toolbar toolbar;

    //FRAGMENT STUFF
    public void openFragment() {
        showEditDialog();
    }
    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        editNameDialogFragment = EditNameDialogFragment.newInstance("Filter Results");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }



    public void dismiss(View view) {
        editNameDialogFragment.dismiss();
    }

    // 3. This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    @Override
    public void onFinishEditDialog(String inputText, String inputText2, boolean fshn, boolean artz, boolean sportz) {
        fashion = fshn;
        arts = artz;
        sports = sportz;
        sort = inputText2;
        isTopStories = false;
        articles.clear();
        adapter.notifyDataSetChanged();

        sameMethod(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setupViews();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("The New York Times");

        //TextView txt = (TextView) findViewById(R.id.custom_font);
        Typeface font = Typeface.createFromAsset(getAssets(), "ENGLISHT.TTF");
        txt.setTypeface(font);
        topStories();

//        RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
//
//        // Initialize contacts
//        articles = new ArrayList<>();
//        // Create adapter passing in the sample user data
//        ArticleAdapterRV adapter = new ArticleAdapterRV(this, articles);
//        //ContactsAdapter adapter = new ContactsAdapter(contacts);
//        // Attach the adapter to the recyclerview to populate items
//        rvArticles.setAdapter(adapter);
//        // Set layout manager to position the items
//        rvArticles.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                isTopStories = false;
                articles.clear();
                adapter.notifyDataSetChanged();
                //resetFilters();
                //recycler.clearOnScrollListeners();
                searchQuery = query;

//                //RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
//                rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
//
//                // Initialize contacts
//                articles = new ArrayList<>();
//                // Create adapter passing in the sample user data
//                adapter = new ArticleAdapterRV(getApplicationContext(), articles);
//                //ContactsAdapter adapter = new ContactsAdapter(contacts);
//                // Attach the adapter to the recyclerview to populate items
//                rvArticles.setAdapter(adapter);
//                // Set layout manager to position the items
//                StaggeredGridLayoutManager gridLayoutManager =
//                        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                rvArticles.setLayoutManager(gridLayoutManager);
////                articles = new ArrayList<>();
////                adapter = new ArticleArrayAdapter(getApplicationContext(), articles);
////                gvResults.setAdapter(adapter);

                sameMethod(0);

//                if (dateString != null) {
//                    params.put("begin_date", dateString);
//                }
//
//                Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
//                String text = "";
//                if (mySpinner != null && mySpinner.getSelectedItem() != null) {
//                    text = mySpinner.getSelectedItem().toString();
//                }
//                if (text != "") { recency = text; }


                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_filter) {
            openFragment();
        }

        return super.onOptionsItemSelected(item);
    }


    public void setupViews() {
//        gvResults = (GridView) findViewById(R.id.gvResults);
//        //btnSearch = (Button) findViewById(R.id.btnSearch);
//        articles = new ArrayList<>();
//        adapter = new ArticleArrayAdapter(this, articles);
//        gvResults.setAdapter(adapter);
        //RecyclerView rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
        //rvArticles = (RecyclerView) findViewById(R.id.rvArticles);


        // Initialize contacts
        //articles = new ArrayList<>();
        // Create adapter passing in the sample user data
        //adapter = new ArticleAdapterRV(this, articles);
        //ContactsAdapter adapter = new ContactsAdapter(contacts);
        // Attach the adapter to the recyclerview to populate items
        rvArticles.setAdapter(adapter);
        // Set layout manager to position the items
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvArticles.setLayoutManager(gridLayoutManager);

        rvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                if (!isTopStories) {
                    customLoadMoreDataFromApi(page);
                }
            }
        });

        //hook up listener for grid click
//        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                // do it
//            }
//        });



//        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //create an intent to display article
//                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
//                //get the article to display
//                Article article = articles.get(position);
//                //i.putExtra("url", article.getWebUrl());
//
//                //pass in article into intent
//                i.putExtra("article", article);
//                //launch activity
//                startActivity(i);
//            }
//        });
//
//        gvResults.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public boolean onLoadMore(int page, int totalItemsCount) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to your AdapterView
//                customLoadMoreDataFromApi(page);
//
//                // or customLoadMoreDataFromApi(totalItemsCount);
//                return true; // ONLY if more data is actually being loaded; false otherwise.
//            }
//        });
    }

    public void customLoadMoreDataFromApi(int offset) {
        sameMethod(offset);
    }

//    // attach to an onclick handler to show the date picker
//    public void showDatePickerDialog(View v) {
//        DatePickerFragment newFragment = new DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
//    }

    // handle the date selected
//    @Override
//    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        // store the values selected into a Calendar instance
//        final Calendar c = Calendar.getInstance();
//        c.set(Calendar.YEAR, year);
//        c.set(Calendar.MONTH, monthOfYear);
//        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        Log.d("DEBUG", "" + year);
//        if (dayOfMonth <= 10) {
//            beginDay = "0" + (1 + dayOfMonth);
//        }
//        else {
//            beginDay = "" + dayOfMonth;
//        }
//        if (monthOfYear <= 10) {
//            beginMonth = "0" + (1 + monthOfYear);
//        }
//        else {
//            beginMonth = "" + monthOfYear;
//        }
//        dateString = year + beginMonth + beginDay;
//        Log.d("YEAR", dateString);
//
//        //searchQuery = query;
//        // perform query here
//
//        articles = new ArrayList<>();
//        adapter = new ArticleArrayAdapter(getApplicationContext(), articles);
//        gvResults.setAdapter(adapter);
//        //String query = etQuery.getText().toString();
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
//        RequestParams params = new RequestParams();
//        params.put("api-key", "9c6c0376437f4ed4a175eb6fcafce1e6");
//        params.put("page", 0);
//        params.put("q", searchQuery);
//        //dateString = year + monthOfYear + dayOfMonth + "";
//        params.put("begin_date", "" + dateString);
//        Log.d("DEBUG", dateString + searchQuery);
//        //params.put("page", 0);
//        //params.put("q", query);
//        client.get(url, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                //Log.d("DEBUG", response.toString());
//                JSONArray articleJsonResults = null;
//                try {
//                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
//                    adapter.clear();
//                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
//
//                    //Log.d("DEBUG", articles.toString());
//                } catch(JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

//    public void showEditDialog(View view) {
//        FragmentManager fm = getSupportFragmentManager();
//        editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
//        editNameDialogFragment.show(fm, "fragment_edit_name");
//    }
//
//    public void dismiss(View view) {
//        editNameDialogFragment.dismiss();
//    }


    public void launchComposeView(View view) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(SearchActivity.this, FilterActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        //startActivity(i); // brings up the second activity
    }

    public void sameMethod(int offset) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key", "9c6c0376437f4ed4a175eb6fcafce1e6");
        params.put("page", offset);

        if (searchQuery != null) {
            params.put("q", searchQuery);
        }
        if (dateString != null && dateString != "" && dateString.length() > 4) {
            params.put("begin_date", dateString);
        }
        if (sort != null) {
            params.put("sort", sort);
        }
        //params.put("fq", "news_desk:(\"Fashion & Style\")");

        if (fashion) {
            params.put("fq", "news_desk:(\"Fashion & Style\")");
        }

        if (sports) {
            params.put("fq", "news_desk:(\"Sports\")");
        }

        if (arts) {
            params.put("fq", "news_desk:(\"Arts\")");
        }
        //if (((CheckBox) findViewById(R.id.checkBox)).isChecked())lfdekhrldkefguikibdeghhlgvenbjck {
          //  params.put("fq", "news_desk:(\"Fashion & Style\")");
        //}

//        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
//        String text = "";
//        if (mySpinner != null && mySpinner.getSelectedItem() != null) {
//            text = mySpinner.getSelectedItem().toString();
//        }
//        //if (text != "") { Log.d("debug", text); }

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJSONArray(articleJsonResults));
                    adapter.notifyDataSetChanged();

                    //adapter.addAll(Article.fromJSONArray(articleJsonResults));
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        isTopStories = false;

        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            // Extract name value from result extras
            String name = data.getExtras().getString("dateString");

            if (name != "HI") {
                dateString = name;
            }


            String sortMethod = data.getExtras().getString("sort");
            sort = sortMethod;


            //news_desk checkboxes
            fashion = data.getExtras().getBoolean("fashion");
            //Log.d("fashion", String.valueOf(fashion));
            sports = data.getExtras().getBoolean("sports");
            arts = data.getExtras().getBoolean("arts");


            articles.clear();
            adapter.notifyDataSetChanged();
            sameMethod(0);
        }
    }

    public void topStories() {
        isTopStories = true;
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/topstories/v2/home.json";
        RequestParams params = new RequestParams();
        params.put("api-key", "9c6c0376437f4ed4a175eb6fcafce1e6");


        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONArray("results");
                    //articleJsonResults = ArticleResponse.parseJSON(response);
                    articles.addAll(Article.fromJSONArrayTop(articleJsonResults));
                    adapter.notifyDataSetChanged();

                    //adapter.addAll(Article.fromJSONArray(articleJsonResults));
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void resetFilters() {
        fashion = false;
        arts = false;
        sports = false;
        dateString = "";
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (dayOfMonth <= 10) {
            beginDay = "0" + (1 + dayOfMonth);
        }
        else {
            beginDay = "" + dayOfMonth;
        }
        if (monthOfYear <= 10) {
            beginMonth = "0" + (1 + monthOfYear);
        }
        else {
            beginMonth = "" + monthOfYear;
        }
        dateString = year + beginMonth + beginDay;
    }

}
