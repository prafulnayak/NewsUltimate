package org.sairaa.newsultimate;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
//import android.support.v4.content.AsyncTaskLoader;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String LOG_NEWS_LOADER = NewsLoader.class.getName();
    private String url;
    public Context context;
    DialogAction dialogAction;
    List<News> newsListT;
    public NewsLoader(Context context, String guardianRequestUrl) {
        super(context);
        url = guardianRequestUrl;
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

    }

    @Override
    public List<News> loadInBackground() {
        List<News> newsList = new ArrayList<News>();
        newsListT = newsList;
        newsList = new QueryUtil().QueryUtilsForNewtwork(url,context);

        return newsList;
    }

}
