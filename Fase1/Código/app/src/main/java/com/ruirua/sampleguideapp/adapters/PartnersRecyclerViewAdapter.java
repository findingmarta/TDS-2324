package com.ruirua.sampleguideapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.model.Partner;

import java.util.List;
import java.util.Locale;


public class PartnersRecyclerViewAdapter extends RecyclerView.Adapter<PartnersRecyclerViewAdapter.ViewHolder> {
    private List<Partner> partners;

    // Class Constructor
    public PartnersRecyclerViewAdapter(List<Partner> new_partners) {
        this.partners = new_partners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_partner, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Given a list's position, get a Partner from the list of partners
        Partner partner = partners.get(position);

        holder.partnerName.setText(partner.getPartner_name().toUpperCase(Locale.ROOT));
        holder.partnerPhone.setText(partner.getPartner_phone());
        holder.partnerUrl.setText(partner.getPartner_url());
        holder.partnerEmail.setText(partner.getPartner_mail());
    }



    @Override
    public int getItemCount() {
        return partners.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView partnerName;
        public final TextView partnerPhone;
        public final TextView partnerUrl;
        public final TextView partnerEmail;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;

            partnerName = view.findViewById(R.id.partner_name);
            partnerPhone = view.findViewById(R.id.partner_phone);
            partnerUrl = view.findViewById(R.id.partner_url);
            partnerEmail = view.findViewById(R.id.partner_email);
        }
    }
}