package org.sairaa.newsultimate;

import android.app.LoaderManager;
import android.content.Loader;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import org.sairaa.newsultimate.Constant;

import static org.sairaa.newsultimate.Constant.NEWS_LOADER_BUSINESS;
import static org.sairaa.newsultimate.Constant.NEWS_LOADER_CULTURE;
import static org.sairaa.newsultimate.Constant.NEWS_LOADER_EDUCATION;
import static org.sairaa.newsultimate.Constant.NEWS_LOADER_ENTERTAINMENT;
import static org.sairaa.newsultimate.Constant.NEWS_LOADER_POLITICS;
import static org.sairaa.newsultimate.Constant.NEWS_LOADER_SPORTS;
import static org.sairaa.newsultimate.Constant.NEWS_LOADER_TECHNOLOGY;
import static org.sairaa.newsultimate.Constant.NEWS_LOADER_TOP100;

class HeaderRecyclerAdapter extends RecyclerView.Adapter<HeaderRecyclerAdapter.viewHolder> implements LoaderManager.LoaderCallbacks<List<News>>{
    // Loader id for each header description

    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?api-key=08c7211d-6fb6-4f1e-a865-190be5274e9f&order-by=newest";
    private static final String LOG_HEADER_RV = HeaderRecyclerAdapter.class.getName();
    //When First Time opened show "All" catogory News
    private static boolean firstTimeNews = true;
    //Check Connection
    private CheckConnection checkConnection;
    //Recycler view for body containing news
    private RecyclerView bodyRV;
    //Context for mainActivity
    private Context context;
    private android.app.LoaderManager loaderManager;
    private ArrayList<String> headerList;
    private ArrayList<News> newsList = new ArrayList<News>();
    private ArrayList<TextView> holderTextView = new ArrayList<TextView>();
    private TextView emptyTextView;
    private DialogAction dialogAction;
    private int rowIndex;
    public HeaderRecyclerAdapter(ArrayList<String> headerList, Context context, LoaderManager loaderManager, RecyclerView bodyRV, TextView emptyTextView) {
        this.headerList = headerList;
        this.context = context;
        this.bodyRV = bodyRV;
        this.loaderManager = loaderManager;
        this.emptyTextView = emptyTextView;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        holderTextView.clear();
        checkConnection = new CheckConnection(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int positionOfView) {
        final int position = positionOfView;
        holder.headerItem.setText(headerList.get(positionOfView));
//        dialogAction = new DialogAction(context);
//        holderTextView.add(holder.headerItem);
//        Log.i(LOG_HEADER_RV,""+positionOfView);
        if(firstTimeNews){
            //This execute for the first time when the app is launched
            //to show 'All' section of news
            if(checkConnection.isConnected()){
                //Intialize the loader for "All" section
                loaderManager.initLoader(NEWS_LOADER_TOP100,null,HeaderRecyclerAdapter.this).forceLoad();
                firstTimeNews = false;
            }

        }
        holder.headerItem.setTextColor(context.getResources().getColor(R.color.colorPrimary));

        //When other section clicked
        //it initiate Loader accordingly
        holder.headerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rowIndex is used to set color of the view when clicked.
                //the notify the adapter that it has changed.
                rowIndex = positionOfView;
                notifyDataSetChanged();

                if(checkConnection.isConnected()){
                    if(headerList.get(position).equals(context.getString(R.string.All))){
                        loaderManager.initLoader(NEWS_LOADER_TOP100,null,HeaderRecyclerAdapter.this).forceLoad();
                    }
                    if(headerList.get(position).equals(context.getString(R.string.sports))){
                        loaderManager.initLoader(NEWS_LOADER_SPORTS,null,HeaderRecyclerAdapter.this).forceLoad();
                    }
                    if(headerList.get(position).equals(context.getString(R.string.technology)))
                        loaderManager.initLoader(NEWS_LOADER_TECHNOLOGY,null,HeaderRecyclerAdapter.this).forceLoad();
                    if(headerList.get(position).equals(context.getString(R.string.business)))
                        loaderManager.initLoader(NEWS_LOADER_BUSINESS,null,HeaderRecyclerAdapter.this).forceLoad();
                    if(headerList.get(position).equals(context.getString(R.string.entertainment)))
                        loaderManager.initLoader(NEWS_LOADER_ENTERTAINMENT,null,HeaderRecyclerAdapter.this).forceLoad();
                    if(headerList.get(position).equals(context.getString(R.string.education)))
                        loaderManager.initLoader(NEWS_LOADER_EDUCATION,null,HeaderRecyclerAdapter.this).forceLoad();
                    if(headerList.get(position).equals(context.getString(R.string.politics)))
                        loaderManager.initLoader(NEWS_LOADER_POLITICS,null,HeaderRecyclerAdapter.this).forceLoad();
                    if(headerList.get(position).equals(context.getString(R.string.culture)))
                        loaderManager.initLoader(NEWS_LOADER_CULTURE,null,HeaderRecyclerAdapter.this).forceLoad();

                }
            }
        });
        // This changes the color of the header item to let the user know which tab is clicked/ active.
        if(rowIndex == position){
            holder.headerItem.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else {
            holder.headerItem.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

    }

    private void setColorToClickedView(int position) {
        for(int i =0; i<holderTextView.size();i++){

//            if(position == i){
                holderTextView.get(i).setTextColor(context.getResources().getColor(R.color.colorAccent));
//            }else

        }
        holderTextView.get(position).setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public int getItemCount() {
        return headerList.size();
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {
        //Shared preference to store/retrieve setting attribute to display no of news
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        //Retrieving no of news from preference
        String minNoOfNews = sharedPrefs.getString(
                context.getString(R.string.settings_min_news_key),
                context.getString(R.string.settings_min_news_default));
        //Show dialog of processing
        //while retrieving news information from internet
        dialogAction = new DialogAction(context);
        dialogAction.showDialog(context.getString(R.string.app_name),context.getString(R.string.fatch));
//        Log.i(LOG_HEADER_RV,"on CreateLoader");
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", minNoOfNews);
        //According to Loader initiation
        //add query parameter to retrieve corresponding information
        switch (id){
            case NEWS_LOADER_TOP100:
                return new NewsLoader(context,uriBuilder.toString());
            case NEWS_LOADER_SPORTS:
                uriBuilder.appendQueryParameter("q", "sports");
                return new NewsLoader(context,uriBuilder.toString());
            case NEWS_LOADER_TECHNOLOGY:
                uriBuilder.appendQueryParameter("q", "technology");
                return new NewsLoader(context,uriBuilder.toString());
            case NEWS_LOADER_BUSINESS:
                uriBuilder.appendQueryParameter("q", "business");
                return new NewsLoader(context,uriBuilder.toString());
            case NEWS_LOADER_ENTERTAINMENT:
                uriBuilder.appendQueryParameter("q", "entertainment");
                return new NewsLoader(context,uriBuilder.toString());
            case NEWS_LOADER_EDUCATION:
                uriBuilder.appendQueryParameter("q", "education");
                return new NewsLoader(context,uriBuilder.toString());
            case NEWS_LOADER_POLITICS:
                uriBuilder.appendQueryParameter("q", "politics");
                return new NewsLoader(context,uriBuilder.toString());
            case NEWS_LOADER_CULTURE:
                uriBuilder.appendQueryParameter("q", "culture");
                return new NewsLoader(context,uriBuilder.toString());
            default:
                return new NewsLoader(context,uriBuilder.toString());
        }
//        return new NewsLoader(context,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        emptyTextView.setText(R.string.no_news);
//        Log.i(LOG_HEADER_RV,"On LoadFinished");
        newsList.clear();
//        news = null;
        //Check of the news data set is empty or null
        //if null set the empty text view to display user
        // no information available
        if(news != null && !news.isEmpty()){
            newsList.addAll(news);
            emptyTextView.setVisibility(View.INVISIBLE);
        }else
            emptyTextView.setText(R.string.no_news);
        RecyclerView.Adapter bodyAdapter = new BodyRecyclerAdapter(newsList);
        RecyclerView.LayoutManager bodyLayoutManager = new LinearLayoutManager(context);
        bodyAdapter.notifyDataSetChanged();
        bodyRV.setLayoutManager(bodyLayoutManager);
        bodyRV.setHasFixedSize(true);
        bodyRV.setAdapter(bodyAdapter);
        dialogAction.hideDialog();

    }



    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.i(LOG_HEADER_RV,"onResetLoader");
        // when the loader reset is called set 'firstTimeNews to True
        // so that when again the app launches the activity it starts with "All" category of news
        firstTimeNews = true;

    }
    //View holder for Header recycler view
    public static class viewHolder extends RecyclerView.ViewHolder{

        private TextView headerItem;
        public viewHolder(View itemView) {
            super(itemView);
            headerItem = itemView.findViewById(R.id.header_item_name);
        }
    }
}
