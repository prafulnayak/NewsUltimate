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
    private static int firstTimeNews = 0;
    private CheckConnection checkConnection;
    private RecyclerView bodyRV;
    //Context for mainActivity
    private Context context;
    private android.app.LoaderManager loaderManager;
    private ArrayList<String> headerList = new ArrayList<String>();
    private ArrayList<String> bodyList = new ArrayList<String>();
    private ArrayList<News> newsList = new ArrayList<News>();
    private TextView emptyTextView;
    private DialogAction dialogAction;
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
        checkConnection = new CheckConnection(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int positionOfView) {
        final int position = positionOfView;
        holder.headerItem.setText(headerList.get(positionOfView));
//        dialogAction = new DialogAction(context);
        if(firstTimeNews == 0){
            if(checkConnection.isConnected()){

//                dialogAction.showDialog(context.getString(R.string.app_name),context.getString(R.string.fatch));
                loaderManager.initLoader(NEWS_LOADER_TOP100,null,HeaderRecyclerAdapter.this).forceLoad();
                firstTimeNews = 1;
            }

        }


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
//                dialogAction = new DialogAction(context);
//                dialogAction.showDialog(context.getString(R.string.app_name),context.getString(R.string.fatch));
                if(checkConnection.isConnected()){
//                    dialogAction.showDialog(context.getString(R.string.app_name),context.getString(R.string.fatch));
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
    public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {
        dialogAction = new DialogAction(context);
        dialogAction.showDialog(context.getString(R.string.app_name),context.getString(R.string.fatch));
        Log.i(LOG_HEADER_RV,"on CreateLoader");
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", "15");

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
        emptyTextView.setText("No News Information");
        Log.i(LOG_HEADER_RV,"On LoadFinished");
        newsList.clear();
//        news = null;
        if(news != null && !news.isEmpty()){
            newsList.addAll(news);
        }else
            emptyTextView.setText("No News Information");
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
        // We'll use this field to showcase matching the holder from the test.
        private boolean mIsInTheMiddle = false;
        public viewHolder(View itemView) {
            super(itemView);
            headerItem = itemView.findViewById(R.id.header_item_name);
        }
        TextView getTextView() {
            return headerItem;
        }

        boolean getIsInTheMiddle() {
            return mIsInTheMiddle;
        }

        void setIsInTheMiddle(boolean isInTheMiddle) {
            mIsInTheMiddle = isInTheMiddle;
        }
    }
}
