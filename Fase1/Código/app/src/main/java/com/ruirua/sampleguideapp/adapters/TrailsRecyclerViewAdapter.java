package com.ruirua.sampleguideapp.adapters;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruirua.sampleguideapp.ui.PremiumTrailActivity;
import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.ui.StandardTrailActivity;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.TrailWith;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;


public class TrailsRecyclerViewAdapter extends RecyclerView.Adapter<TrailsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<TrailWith> trails;
    private Activity activity;
    private boolean isPremium;

    // Class Constructor
    public TrailsRecyclerViewAdapter(ArrayList<TrailWith> new_trails, Activity new_activity) {
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
        // Check if the user is premium or standard
        isPremium = getPremium();

        // Given a list's position, get a Trail from the list of trails
        TrailWith trailWith = trails.get(position);

        Trail trail = trailWith.getTrail();

        // Set the trail's info on the view
        String durationString = trail.getTrail_duration() + " minutes";
        holder.trailName.setText(trail.getTrail_name().toUpperCase(Locale.ROOT));
        holder.trailDuration.setText(durationString);
        holder.trailDifficulty.setText(trail.getTrail_difficulty());
        Picasso.get()
                .load(trail.getTrail_image().replace("http:", "https:"))
                .into(holder.trailImage);

        // Set a Listener
        holder.item.setOnClickListener(view -> {
            Intent intent;

            if (isPremium){
                // Start the trail's activity if premium
                intent = new Intent(activity, PremiumTrailActivity.class);
            } else {
                // Start the trail's activity if standard
                intent = new Intent(activity, StandardTrailActivity.class);
            }

            // Send the trail's ID to the activity
            intent.putExtra("trail_id", trail.getTrailId());
            activity.startActivity(intent);
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

    public void setTrails(ArrayList<TrailWith> trails) {
        this.trails = trails;
    }

    public Boolean getPremium () {
        SharedPreferences sp = activity.getSharedPreferences("BraGuia Shared Preferences",MODE_PRIVATE);
        return sp.getBoolean("user_type", false);
    }
}