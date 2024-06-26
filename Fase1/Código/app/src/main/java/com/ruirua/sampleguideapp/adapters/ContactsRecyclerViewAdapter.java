package com.ruirua.sampleguideapp.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.model.Contact;

import java.util.List;
import java.util.Locale;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder>{
    private List<Contact> contacts;

    // Class Constructor
    public ContactsRecyclerViewAdapter(List<Contact> new_contacts) {
        this.contacts = new_contacts;
    }

    @NonNull
    @Override
    /*
     *  Um ViewHolder é um wrapper sobre a View que contém o layout the cada elemento da lista (fragment_item).
     *  Associamos a ViewHolder ao fragment_item e inicializamos.
     */
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_contact, parent, false);

        return new ViewHolder(view);
    }

    @Override
    /*
     * Preenchemos as views com a informação da API de dados.
     */
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Given a list's position, get a Point from the list of points
        Contact contact = contacts.get(position);

        // Set the trail's info on the view
        holder.contactName.setText(contact.getContact_name().toUpperCase(Locale.ROOT));
        holder.contactNumber.setText(contact.getContact_phone());
        holder.contactURL.setText(contact.getContact_url());
        holder.contactEmail.setText(contact.getContact_mail());

    }



    @Override
    public int getItemCount() {
        return contacts.size();
    }

    /*
     * ViewHolder's Constructor
     * Inicializamos o layout do fragment_item e cada view presente no mesmo.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView contactName;
        public final TextView contactNumber;
        public final TextView contactURL;

        public final TextView contactEmail;
        public final Button call;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;

            // Other variables
            contactNumber = view.findViewById(R.id.contact_phone);
            contactURL = view.findViewById(R.id.contact_url);
            contactEmail = view.findViewById(R.id.contac_email);
            contactName = view.findViewById(R.id.contact_name);

            call = view.findViewById(R.id.callbutton);
            call.setOnClickListener(view1 -> {
                String phone = contactNumber.getText().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone));
                view1.getContext().startActivity(callIntent);
            });
        }
    }
}
