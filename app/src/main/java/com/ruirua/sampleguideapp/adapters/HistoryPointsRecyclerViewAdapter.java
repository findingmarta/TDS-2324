package com.ruirua.sampleguideapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.PointActivity;
import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.model.History_Point;
import com.ruirua.sampleguideapp.model.Point;
import com.ruirua.sampleguideapp.model.PointWith;
import com.ruirua.sampleguideapp.viewModel.PointViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;


public class HistoryPointsRecyclerViewAdapter extends RecyclerView.Adapter<HistoryPointsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<History_Point> historyPoints;
    private Activity activity;


    // Class Constructor
    public HistoryPointsRecyclerViewAdapter(ArrayList<History_Point> new_points, Activity new_activity) {
        this.historyPoints = new_points;
        this.activity = new_activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_history_point, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Given a list's position, get a History_Point
        History_Point history_point = historyPoints.get(position);

        // Given an id, get point from the points table in the database
        int point_id = history_point.getPoint_id();
        PointViewModel pvm = new ViewModelProvider((ViewModelStoreOwner) activity).get(PointViewModel.class);
        pvm.setPointViewModel(point_id);
        LiveData<PointWith> pointData = pvm.getPointWith();
        pointData.observe((LifecycleOwner) this.activity, new_point -> {
            //@Override
            //public void onChanged(PointWith pointWith) {                          // TODO verificar isto
            if (new_point != null) {
                Point point = new_point.getPoint();

                // Set the points's info on the view
                holder.pointName.setText(point.getPoint_name().toUpperCase());

                String desc = point.getPoint_desc();
                // To limit the amount of characters on the item
                String descritionLimit = desc;
                if (desc.length() > 200){
                    descritionLimit = desc.substring(0, 200) + "...";
                }
                holder.pointDesc.setText(descritionLimit);

                // Convert Date to String
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String date = df.format(history_point.getDate());
                holder.pointDate.setText(date);



                // Set a Listener
                holder.item.setOnClickListener(view -> {
                    // Start a Point Of Interest Activity
                    Intent intent = new Intent(activity, PointActivity.class);

                    // Send the trail's ID to the activity
                    intent.putExtra("point_id", point_id);
                    activity.startActivity(intent);
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyPoints.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView pointName;
        public final TextView pointDesc;
        public final TextView pointDate;
        private final LinearLayout item;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;

            // Other variables
            pointName = view.findViewById(R.id.history_point_name);
            pointDesc = view.findViewById(R.id.history_point_desc);
            pointDate = view.findViewById(R.id.history_point_date);

            item = view.findViewById(R.id.historyPointItem);
        }
    }

    public void setPoints(ArrayList<History_Point> points) {
        this.historyPoints = points;
    }
}