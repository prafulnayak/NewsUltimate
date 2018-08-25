package org.sairaa.newsultimate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Header to display which body part to be shown
    private RecyclerView headerRV;
    //Body of recycler view
    private RecyclerView bodyRV;
    //Header Adapter
    private RecyclerView.Adapter headerAdapter;
    //Layout manager for header adapter
    private RecyclerView.LayoutManager headerLayoutManager;
    // header List
    private ArrayList<String> headerList;
    //empty text view
    private TextView emptyTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        headerRV = findViewById(R.id.header_recycler_view);
        bodyRV = findViewById(R.id.body_recycler_view);
        emptyTextView = findViewById(R.id.empty_text_view);
        headerList = new ArrayList<String>();
        addItemsToHeaderList();

        headerAdapter = new HeaderRecyclerAdapter(headerList,MainActivity.this, getLoaderManager(),bodyRV,emptyTextView);
        headerLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        headerRV.setLayoutManager(headerLayoutManager);
        headerRV.setHasFixedSize(true);
        headerRV.setAdapter(headerAdapter);
//        headerRV.scrollToPosition(8);
    }

    private void addItemsToHeaderList() {
        headerList.add(getString(R.string.All));
        headerList.add(getString(R.string.sports));
        headerList.add(getString(R.string.technology));
        headerList.add(getString(R.string.business));
        headerList.add(getString(R.string.entertainment));
        headerList.add(getString(R.string.education));
        headerList.add(getString(R.string.politics));
        headerList.add(getString(R.string.culture));

    }
}
