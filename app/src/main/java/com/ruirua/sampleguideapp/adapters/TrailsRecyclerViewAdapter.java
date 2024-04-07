package com.ruirua.sampleguideapp.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruirua.sampleguideapp.PremiumTrailActivity;
import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.model.Trail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TrailsRecyclerViewAdapter extends RecyclerView.Adapter<TrailsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Trail> trails;
    private Activity activity;


    // Class Constructor
    public TrailsRecyclerViewAdapter(ArrayList<Trail> new_trails, Activity new_activity) {
        this.trails = new_trails;
        this.activity = new_activity;
    }

    @NonNull
    @Override
    /*
    *  Um ViewHolder é um wrapper sobre a View que contém o layout the cada elemento da lista (fragment_item).
    *  Associamos a ViewHolder ao fragment_item e inicializamos.
    */
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_trail, parent, false);

        return new ViewHolder(view);
    }

    @Override
    /*
    * Preenchemos as views com a informação daa API de dados.
    */
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Given a list's position, get a Trail from the list of trails
        Trail trail = trails.get(position).getTrail();

        // Set the trail's info on the view
        String durationString = trail.getTrailDuration() + " minutes";
        holder.trailName.setText(trail.getTrailName().toUpperCase());
        holder.trailDuration.setText(durationString);
        holder.trailDifficulty.setText(trail.getTrailDifficulty());
        Picasso.get()
                .load(trails.get(position).getTrailImage().replace("http:", "https:"))
                .into(holder.trailImage);

        // Set a Listener
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                // Check if the user is premium or standard

                // Start the trail's activity if premium
                intent = new Intent(activity, PremiumTrailActivity.class);

                // Start the trail's activity if standard
                //intent = new Intent(activity, StandardTrailActivity.class);      // TODO Fazer a activity StandardTrailActivity

                // Send the trail's ID to the activity
                intent.putExtra("trail_id", trail.getTrailId());
                activity.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return trails.size();
    }

    /*
    * ViewHolder's Constructor
    * Inicializamos o layout do fragment_item e cada view presente no mesmo.
    */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView trailName;
        public final TextView trailDuration;
        public final TextView trailDifficulty;
        public final ImageView trailImage;
        private final LinearLayout item;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;
            trailName = view.findViewById(R.id.trail_name);
            trailDuration = view.findViewById(R.id.trail_duration);
            trailDifficulty = view.findViewById(R.id.trail_difficulty);
            trailImage = view.findViewById(R.id.trail_image);
            item = view.findViewById(R.id.trailItem);
        }
    }

    public void setTrails(ArrayList<Trail> trails) {
        this.trails = trails;
    }
}