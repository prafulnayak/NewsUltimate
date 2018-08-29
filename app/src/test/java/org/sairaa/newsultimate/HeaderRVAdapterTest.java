package org.sairaa.newsultimate;

import android.app.LoaderManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class HeaderRVAdapterTest {
    ArrayList<String> headerList = new ArrayList<String>();
    Context context;
    LoaderManager loaderManager;
    RecyclerView bodyRV;
    TextView emptyTextView;
    HeaderRecyclerAdapter.viewHolder viewHoldera;
    HeaderRecyclerAdapter headerRecyclerAdapter;
    @Before
    public void setUp() throws Exception{
        headerList.add("sss");
        headerRecyclerAdapter = new HeaderRecyclerAdapter(headerList,context,loaderManager,bodyRV,emptyTextView);
//        headerRecyclerAdapter.onCreateViewHolder(viewHoldera,1);
    }
    @Test
    public void test11() throws Exception{
//        when(headerRecyclerAdapter.onCreateViewHolder(viewHoldera,))
        assertEquals(1,headerRecyclerAdapter.getItemCount());

    }
}
