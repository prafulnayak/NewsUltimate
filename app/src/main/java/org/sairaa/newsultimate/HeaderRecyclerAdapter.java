package org.sairaa.newsultimate;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.annotation.Nullable;

import android.content.Context;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class HeaderRecyclerAdapter extends RecyclerView.Adapter<HeaderRecyclerAdapter.viewHolder> implements LoaderManager.LoaderCallbacks<List<News>>{
    private static final int NEWS_LOADER_TOP100 = 11;
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?api-key=08c7211d-6fb6-4f1e-a865-190be5274e9f&order-by=newest";
    private static final String LOG_HEADER_RV = HeaderRecyclerAdapter.class.getName();
    private RecyclerView bodyRV;
    //Context for mainActivity
    private Context context;
    private android.app.LoaderManager loaderManager;
    private ArrayList<String> headerList = new ArrayList<String>();
    private ArrayList<String> bodyList = new ArrayList<String>();
    private ArrayList<News> newsList = new ArrayList<News>();
    public HeaderRecyclerAdapter(ArrayList<String> headerList, Context context, android.app.LoaderManager loaderManager, RecyclerView bodyRV) {
        this.headerList = headerList;
        this.context = context;
        this.bodyRV = bodyRV;
        this.loaderManager = loaderManager;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        holder.headerItem.setText(headerList.get(position));
//        loaderManager.initLoader(NEWS_LOADER_TOP100,null,HeaderRecyclerAdapter.this).forceLoad();

        holder.headerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyList.clear();
                bodyList.add(headerList.get(position));
                bodyList.add(headerList.get(position));
                bodyList.add(headerList.get(position));
                bodyList.add(headerList.get(position));
//                newsList.add(new News(headerList.get(position),
//                        headerList.get(position),
//                        headerList.get(position),
//                        headerList.get(position),
//                        headerList.get(position),
//                        headerList.get(position),
//                        headerList.get(position),
//                        headerList.get(position),
//                        null,headerList.get(position),
//                        headerList.get(position)));
                loaderManager.initLoader(NEWS_LOADER_TOP100,null,HeaderRecyclerAdapter.this).forceLoad();
//                bodyAdapter = new BodyRecyclerAdapter(newsList);
//                bodyLayoutManager = new LinearLayoutManager(context);
//                bodyRV.setLayoutManager(bodyLayoutManager);
//                bodyRV.setHasFixedSize(true);
//                bodyRV.setAdapter(bodyAdapter);
                Log.i(LOG_HEADER_RV,"Clicked on : "+headerList.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return headerList.size();
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_HEADER_RV,"on CreateLoader");
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
//
        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", "15");
        return new NewsLoader(context,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        newsList.addAll(news);
        RecyclerView.Adapter bodyAdapter = new BodyRecyclerAdapter(newsList);
        RecyclerView.LayoutManager bodyLayoutManager = new LinearLayoutManager(context);
        bodyRV.setLayoutManager(bodyLayoutManager);
        bodyRV.setHasFixedSize(true);
        bodyRV.setAdapter(bodyAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }


//    @NonNull
//    @Override
//    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
//        Log.e("error","running");
//        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
//
//        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
//        Uri.Builder uriBuilder = baseUri.buildUpon();
//        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
//        uriBuilder.appendQueryParameter("show-tags", "contributor");
//        uriBuilder.appendQueryParameter("page-size", "15");
//        return new NewsLoader(context,uriBuilder.toString());
//
//    }

//    @Override
//    public void onLoadFinished(@NonNull android.support.v4.content.Loader<List<News>> loader, List<News> data) {
//        newsList.addAll(data);
//        bodyAdapter = new BodyRecyclerAdapter(newsList);
//        bodyLayoutManager = new LinearLayoutManager(context);
//        bodyRV.setLayoutManager(bodyLayoutManager);
//        bodyRV.setHasFixedSize(true);
//        bodyRV.setAdapter(bodyAdapter);
//
//    }
//
//    @Override
//    public void onLoaderReset(@NonNull android.support.v4.content.Loader<List<News>> loader) {
//
//    }



    public static class viewHolder extends RecyclerView.ViewHolder{
        private TextView headerItem;
        public viewHolder(View itemView) {
            super(itemView);
            headerItem = itemView.findViewById(R.id.header_item_name);
        }
    }
}
