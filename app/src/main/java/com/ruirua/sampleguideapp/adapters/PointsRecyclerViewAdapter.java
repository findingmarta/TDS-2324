package com.ruirua.sampleguideapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.model.PointOfInterest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/*

public class PointsRecyclerViewAdapter extends RecyclerView.Adapter<PointsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<PointOfInterest> points;


    // Class Constructor
    public PointsRecyclerViewAdapter(ArrayList<PointOfInterest> new_points) {
        this.points = new_points;
    }

    @NonNull
    @Override
    /*
    *  Um ViewHolder é um wrapper sobre a View que contém o layout the cada elemento da lista (fragment_item).
    *  Associamos a ViewHolder ao fragment_item e inicializamos.
    */
    /*public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_point, parent, false);

        return new ViewHolder(view);
    }

    @Override*/
    /*
    * Preenchemos as views com a informação daa API de dados.
    */
    //public void onBindViewHolder(final ViewHolder holder, int position) {
        // Given a list's position, get a Point from the list of points
        /*PointOfInterest point = points.get(position).getPoint();

        // Set the trail's info on the view
        holder.premiumTrailName.setText(point.getTrailName().toUpperCase());
        Picasso.get()
                .load(points.get(position).getPointImage().replace("http:", "https:"))
                .into(holder.premiumTrailImage);

        */

    /*    // Set a Listener
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
*/
    /*
    * ViewHolder's Constructor
    * Inicializamos o layout do fragment_item e cada view presente no mesmo.
    */
    /*public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        //public final TextView pointName;
        //public final TextView pointDesc;



        private final LinearLayout item;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;

            // Other variables
            //pointName = view.findViewById(R.id.)

            item = view.findViewById(R.id.pointItem);
        }
    }

    public void setPoints(ArrayList<PointOfInterest> points) {
        this.points = points;
    }
}
*/