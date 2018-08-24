package org.sairaa.newsultimate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class BodyRecyclerAdapter extends RecyclerView.Adapter<BodyRecyclerAdapter.viewHolder> {
    private ArrayList<String> bodyList = new ArrayList<String>();
    public BodyRecyclerAdapter(ArrayList<String> headerList) {
        this.bodyList = headerList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.body_item,parent,false);
        viewHolder recyclerViewHolder = new viewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.sectionT.setText(bodyList.get(position));
    }

    @Override
    public int getItemCount() {
        return bodyList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewN;
        TextView sectionT, headingT,dateT,sourceT;
        public viewHolder(View itemView) {
            super(itemView);
            sectionT = itemView.findViewById(R.id.section_name);
            imageViewN = itemView.findViewById(R.id.imageView);
            headingT = itemView.findViewById(R.id.heading);
            dateT = itemView.findViewById(R.id.date);
            sourceT = itemView.findViewById(R.id.source);
        }
    }
}
