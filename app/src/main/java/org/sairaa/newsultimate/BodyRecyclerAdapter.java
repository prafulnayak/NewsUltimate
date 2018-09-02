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
    private ArrayList<News> bodyList = new ArrayList<News>();
    public BodyRecyclerAdapter(ArrayList<News> headerList) {
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
        final News news = bodyList.get(position);
        holder.sectionT.setText(news.getSectionName()+"  >>");
        holder.headingT.setText(news.getWebTitle());
        holder.dateT.setText(convertStringToDate(news.getWebPublicationDate()));
        holder.sourceT.setText(news.getTagContributor());
        if(news.getFieldThumbnail() != null)
            holder.imageViewN.setImageBitmap(news.getFieldThumbnail());
        else
            holder.imageViewN.setBackgroundResource(R.drawable.ic_launcher_background);
    }

    private String convertStringToDate(String dateString) {
        return dateString.substring(0,10);
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
