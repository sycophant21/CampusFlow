package com.example.applicationcreator.web;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationcreator.OnItemClickListener;
import com.example.applicationcreator.R;

import java.util.List;

public class CustomAdapterForApplication extends RecyclerView.Adapter<CustomAdapterForApplication.ViewHolder> {

    private List<Application> mList;
    private  OnItemClickListener clickListener = null;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.category_item, parent, false);

        return new CustomAdapterForApplication.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Application application = mList.get(position);

        holder.txt_log_desc.setText(application.getRequirements().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
    public CustomAdapterForApplication(List<Application> mList, OnItemClickListener clickListener) {
        this.mList = mList;
        this.clickListener = clickListener;
    }
}
