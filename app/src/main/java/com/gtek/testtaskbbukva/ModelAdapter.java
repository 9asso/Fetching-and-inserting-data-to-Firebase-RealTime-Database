package com.gtek.testtaskbbukva;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ModelAdapter extends FirebaseRecyclerAdapter<Model, ModelAdapter.modelViewHolder> {

    Activity activity;

    public ModelAdapter(@NonNull FirebaseRecyclerOptions<Model> options, Activity activity)
    {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void
    onBindViewHolder(@NonNull modelViewHolder holder, int position, @NonNull Model model)
    {
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        Glide.with(activity).load(model.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.url);
    }

    @NonNull
    public modelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item, parent, false);
        return new modelViewHolder(view);
    }

    static class modelViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description;
        public LinearLayout content;
        public ImageView url;
        public modelViewHolder(@NonNull View itemView)
        {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            url = itemView.findViewById(R.id.url);
        }
    }

}
