package com.example.covid_19;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class nationAdapter extends RecyclerView.Adapter<nationAdapter.dataHolder> {
    Context mContext;
    ArrayList<Util> mArrayList = new ArrayList<>();




    public nationAdapter(Context context, ArrayList<Util> arrayList) {
        mContext = context;
        mArrayList = arrayList;


    }

    @NonNull
    @Override
    public dataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        dataHolder data = new dataHolder(v);
        return data;
    }

    @Override
    public void onBindViewHolder(@NonNull dataHolder holder, final int position) {
        holder.nationName.setText(mArrayList.get(position).getCountry());
        Picasso.with(mContext)
                .load(mArrayList.get(position).getImageLinks())
                .placeholder(R.drawable.ic_flag_foreground)
                .into(holder.nationFlag);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondScreen.countryname.setText(mArrayList.get(position).getCountry());
                secondScreen.sDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class dataHolder extends RecyclerView.ViewHolder{
           TextView nationName;
           CircleImageView nationFlag;
           CardView mCardView;
        public dataHolder(@NonNull View itemView) {
            super(itemView);
            nationName = itemView.findViewById(R.id.nationname);
            nationFlag = itemView.findViewById(R.id.countryflag);
            mCardView = itemView.findViewById(R.id.cardviewnation);
        }
    }
}
