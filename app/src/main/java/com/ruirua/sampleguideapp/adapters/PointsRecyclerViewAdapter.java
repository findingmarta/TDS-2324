package com.ruirua.sampleguideapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.model.Point;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PointsRecyclerViewAdapter extends RecyclerView.Adapter<PointsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Point> points;


    // Class Constructor
    public PointsRecyclerViewAdapter(ArrayList<Point> new_points) {
        this.points = new_points;
    }

    @NonNull
    @Override
    /*
    *  Um ViewHolder é um wrapper sobre a View que contém o layout the cada elemento da lista (fragment_item).
    *  Associamos a ViewHolder ao fragment_item e inicializamos.
    */
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_point, parent, false);

        return new ViewHolder(view);
    }

    @Override
    /*
    * Preenchemos as views com a informação da API de dados.
    */
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Given a list's position, get a Point from the list of points
        Point point = points.get(position);

        // Set the trail's info on the view
        holder.pointName.setText(point.getPoint_name().toUpperCase());
        holder.pointDesc.setText(point.getPoint_desc().toUpperCase());

        // Set a Listener
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;

                // Start a Point Of Interest Activity
                //intent = new Intent(, PointOfInterestActivity.class);
                //startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return points.size();
    }

    /*
    * ViewHolder's Constructor
    * Inicializamos o layout do fragment_item e cada view presente no mesmo.
    */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView pointName;
        public final TextView pointDesc;
        private final LinearLayout item;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;

            // Other variables
            pointName = view.findViewById(R.id.point_name);
            pointDesc = view.findViewById(R.id.point_desc);

            item = view.findViewById(R.id.pointItem);
        }
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }
}