package com.example.applicationcreator.web;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationcreator.OnItemClickListener;
import com.example.applicationcreator.R;


import java.util.List;


public class CustomAdapterForCategory extends RecyclerView.Adapter<CustomAdapterForCategory.ViewHolder> {

    private List<Category> mList;
    private static OnItemClickListener clickListener = null;

    public CustomAdapterForCategory(List<Category> mList, OnItemClickListener clickListener) {
        this.mList = mList;
        CustomAdapterForCategory.clickListener = clickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView txt_log_desc;


        public ViewHolder(View itemView) {

            super(itemView);


            txt_log_desc = (TextView) itemView.findViewById(R.id.contact_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            clickListener.onClick(v,getAdapterPosition());
        }
    }


    @Override
    public CustomAdapterForCategory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.category_item, parent, false);

        return new ViewHolder(contactView);
    }




    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
         Category category = mList.get(position);

        holder.txt_log_desc.setText(category.getCategoryName().toUpperCase());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
