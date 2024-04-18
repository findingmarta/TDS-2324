package com.ruirua.sampleguideapp.adapters;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruirua.sampleguideapp.R;
import com.ruirua.sampleguideapp.model.App;
import com.ruirua.sampleguideapp.model.Contact;
import com.ruirua.sampleguideapp.model.Point;

import java.util.ArrayList;
import java.util.List;

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
        holder.contactName.setText(contact.getContact_name().toUpperCase());
        holder.contactNumber.setText(contact.getContact_phone().toUpperCase());
        holder.contactURL.setText(contact.getContact_url().toUpperCase());
        holder.contactEmail.setText(contact.getContact_mail().toUpperCase());

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
        private final LinearLayout item;
        public final Button call;

        public ViewHolder(@NonNull View view) {
            super(view);
            mView = view;

            // Other variables
            contactNumber = view.findViewById(R.id.contact_phone);
            contactURL = view.findViewById(R.id.contact_url);
            contactEmail = view.findViewById(R.id.contac_email);
            contactName = view.findViewById(R.id.contact_name);
            item = view.findViewById(R.id.pointItem);

            //TODO Falta o button
            call = view.findViewById(R.id.callbutton);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phone = contactNumber.getText().toString();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(phone));
                    view.getContext().startActivity(callIntent);
                }
            });
        }
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

}
