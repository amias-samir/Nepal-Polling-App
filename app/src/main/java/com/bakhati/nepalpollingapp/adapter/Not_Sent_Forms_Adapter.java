package com.bakhati.nepalpollingapp.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
;import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.model.SavedFormParameters;

import java.util.List;


/**
 * Created by Samir on 3/10/2017.
 */

public class Not_Sent_Forms_Adapter extends RecyclerView.Adapter<Not_Sent_Forms_Adapter.ContactViewHolder> {

    private List<SavedFormParameters> colorList;

    public Not_Sent_Forms_Adapter(List<SavedFormParameters> cList) {
        this.colorList = cList;
    }

    @Override
    public int getItemCount() {
        Log.e("SentFormsAdapter", "getItemCount: "+ colorList.size() );
        return colorList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {

        SavedFormParameters ci = colorList.get(i);

        if(ci.formId!= null){



        contactViewHolder.formName.setText(ci.formName);
        contactViewHolder.formDate.setText(ci.date);
        contactViewHolder.formStatus.setText(ci.status);

//        if(ci.status.equals("Not Sent") ){
            contactViewHolder.statusIcon.setImageResource(R.drawable.circuler_background_not_sent);
        }

//        }

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_saved_forms, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        protected TextView formName , formDate, formStatus;
        protected ImageView statusIcon;

        protected CardView cardView;

        public ContactViewHolder(View v) {
            super(v);
            formName = (TextView) v.findViewById(R.id.textViewFormName);
            formDate = (TextView) v.findViewById(R.id.textViewSavedFormDate);
            formStatus = (TextView) v.findViewById(R.id.status_tv);
            statusIcon = (ImageView) v.findViewById(R.id.status_icon);
//            cardView = (CardView) v.findViewById(R.id.cardview);
        }
    }

}

