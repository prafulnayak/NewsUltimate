package org.sairaa.newsultimate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.sairaa.newsultimate.IdlingResource.SimpleIdlingResource;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextSetterOnRV.DelayerCallback{
    //Header to display which body part to be shown
    private RecyclerView headerRV;
    //Body of recycler view
    private RecyclerView bodyRV;
    //Header Adapter
    private RecyclerView.Adapter headerAdapter;
    //Layout manager for header adapter
    private RecyclerView.LayoutManager headerLayoutManager;
    //empty text view
    private TextView emptyTextView;
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headerRV = findViewById(R.id.header_recycler_view);
        bodyRV = findViewById(R.id.body_recycler_view);
        emptyTextView = findViewById(R.id.empty_text_view);
        // Get the IdlingResource instance
        getIdlingResource();
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextSetterOnRV.textSetter(this,MainActivity.this,mIdlingResource);
    }

    @Override
    public void onDone(ArrayList<String> headerList) {
        headerAdapter = new HeaderRecyclerAdapter(headerList,MainActivity.this, getLoaderManager(),bodyRV,emptyTextView);
        headerLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        headerRV.setLayoutManager(headerLayoutManager);
        headerRV.setHasFixedSize(true);
        headerRV.setAdapter(headerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
