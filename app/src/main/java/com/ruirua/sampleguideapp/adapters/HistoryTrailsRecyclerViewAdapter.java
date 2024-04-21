package com.ruirua.sampleguideapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.PointActivity;
import com.ruirua.sampleguideapp.PremiumTrailActivity;
import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.History_Trail;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;
import com.ruirua.sampleguideapp.model.Trail;
import com.ruirua.sampleguideapp.model.TrailWith;
import com.ruirua.sampleguideapp.viewModel.PointViewModel;
import com.ruirua.sampleguideapp.viewModel.TrailViewModel;
import com.ruirua.sampleguideapp.viewModel.TrailsViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class HistoryTrailsRecyclerViewAdapter extends RecyclerView.Adapter<HistoryTrailsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<History_Trail> historyTrails;
    private Activity activity;

    // Class Constructor
    public HistoryTrailsRecyclerViewAdapter(ArrayList<History_Trail> new_trails, Activity new_activity) {
        this.historyTrails = new_trails;
        this.activity = new_activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_history_trail, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Given a list's position, get a Trail from the list of trails
        History_Trail history_trail = historyTrails.get(position);

        // Given an id, get point from the points table in the database
        int trail_id = history_trail.getTrail_id();
        TrailViewModel tvm = new ViewModelProvider((ViewModelStoreOwner) activity).get(TrailViewModel.class);
        tvm.setTrailViewModel(trail_id);
        LiveData<TrailWith> trailData = tvm.getTrailWith();
        trailData.observe((LifecycleOwner) this.activity, new_trail -> {
            //@Override
            //public void onChanged(PointWith pointWith) {                          // TODO verificar isto
            if (new_trail != null) {
                Trail trail = new_trail.getTrail();

                // Set the points's info on the view
                holder.trailName.setText(trail.getTrail_name().toUpperCase());
                String durationString = trail.getTrail_duration() + " minutes";
                holder.trailDuration.setText(durationString);
                holder.trailDifficulty.setText(trail.getTrail_difficulty());
                Picasso.get()
                        .load(trail.getTrail_image().replace("http:", "https:"))
                        .into(holder.trailImage);

                // Set the travelled distance and time. If they are equal to 0 the default is "-"
                int distance = history_trail.getTravelled_distance();
                String distanceString = "-";
                if (distance > 0){
                    distanceString = String.valueOf(distance) + " meters";
                }
                holder.trailTravelledDistance.setText(distanceString);

                int time = history_trail.getTravelled_time();
                String timeString = "-";
                if (time > 0){
                    timeString = String.valueOf(time) + " minutes";
                }
                holder.trailTravelledTime.setText(timeString);

                // Convert Date to String. If there isn't a Date the default is "-"
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String date = df.format(history_trail.getDate());
                holder.trailDate.setText(date);



                // Set a Listener
                holder.item.setOnClickListener(view -> {
                    // Start a Premium Trail Activity
                    Intent intent = new Intent(activity, PremiumTrailActivity.class);

                    // Send the trail's ID to the activity
                    intent.putExtra("trail_id", trail_id);
                    activity.startActivity(intent);
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return historyTrails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView trailName;
        public final TextView trailDuration;
        public final TextView trailDifficulty;
        public final TextView trailDate;
        public final TextView trailTravelledDistance;
        public final TextView trailTravelledTime;
        public final ImageView trailImage;
        private final LinearLayout item;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;

            trailName = view.findViewById(R.id.history_trail_name);
            trailDuration = view.findViewById(R.id.history_trail_duration);
            trailDifficulty = view.findViewById(R.id.history_trail_difficulty);
            trailDate = view.findViewById(R.id.history_trail_date);
            trailTravelledDistance = view.findViewById(R.id.history_trail_travelled_distance);
            trailTravelledTime = view.findViewById(R.id.history_trail_travelled_time);
            trailImage = view.findViewById(R.id.history_trail_image);

            item = view.findViewById(R.id.historyTrailItem);
        }
    }

    public void setTrails(ArrayList<History_Trail> trails) {
        this.historyTrails = trails;
    }
}