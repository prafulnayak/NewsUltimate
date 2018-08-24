package org.sairaa.newsultimate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class HeaderRecyclerAdapter extends RecyclerView.Adapter<HeaderRecyclerAdapter.viewHolder> {
    private RecyclerView bodyRV;
    //Context for mainActivity
    private Context context;
    //adapter to show body
    private RecyclerView.Adapter bodyAdapter;
    //Layout manager for body adapter
    private RecyclerView.LayoutManager bodyLayoutManager;

    private ArrayList<String> headerList = new ArrayList<String>();
    private ArrayList<String> bodyList = new ArrayList<String>();
    public HeaderRecyclerAdapter(ArrayList<String> headerList, Context context, RecyclerView bodyRV) {
        this.headerList = headerList;
        this.context = context;
        this.bodyRV = bodyRV;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item,parent,false);
        viewHolder recyclerViewHolder = new viewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {

        holder.headerItem.setText(headerList.get(position));
        holder.headerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyList.clear();
                bodyList.add(headerList.get(position));
                bodyList.add(headerList.get(position));
                bodyList.add(headerList.get(position));
                bodyList.add(headerList.get(position));
                bodyAdapter = new BodyRecyclerAdapter(bodyList);
                bodyLayoutManager = new LinearLayoutManager(context);
                bodyRV.setLayoutManager(bodyLayoutManager);
                bodyRV.setHasFixedSize(true);
                bodyRV.setAdapter(bodyAdapter);

            }
        });

    }

    @Override
    public int getItemCount() {
        return headerList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        private TextView headerItem;
        public viewHolder(View itemView) {
            super(itemView);
            headerItem = itemView.findViewById(R.id.header_item_name);
        }
    }
}
