package com.example.obrero;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewCommAdapter extends RecyclerView.Adapter<RecyclerViewCommAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Commande> mData ;
    RequestOptions option;


    public RecyclerViewCommAdapter(Context mContext, List<Commande> mData) {
        this.mContext = mContext;
        this.mData = mData;
        // Request option for Glide
         option = new RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cardviewcomm,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, commandeView.class);
                i.putExtra("comm_id", mData.get(viewHolder.getAdapterPosition()).getIdC());
                i.putExtra("commande_img",mData.get(viewHolder.getAdapterPosition()).getPhoto());
                i.putExtra("commande_name",mData.get(viewHolder.getAdapterPosition()).getNomP());
                i.putExtra("commande_description",mData.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("commande_idPres",mData.get(viewHolder.getAdapterPosition()).getIdP());
                i.putExtra("commande_idU",mData.get(viewHolder.getAdapterPosition()).getIdUs());
                i.putExtra("commande_adresse",mData.get(viewHolder.getAdapterPosition()).getAdresse());
                      i.putExtra("commande_tarif",mData.get(viewHolder.getAdapterPosition()).getTarif());
                mContext.startActivity(i);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getNomP());
        holder.tv_desc.setText(mData.get(position).getAdresse());


        // Load Image from the internet and set it into Imageview using Glide

        Glide.with(mContext).load("http://10.0.2.2:3000/img/"+mData.get(position).getPhoto()).apply(option).into(holder.img_thumbnail);

    }



    @Override
    public int getItemCount() {
        return  mData.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name ;
        TextView tv_desc ;
        TextView tv_category;
        ImageView img_thumbnail;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.containerimagec);
            tv_name = itemView.findViewById(R.id.nomPrec);
            tv_desc = itemView.findViewById(R.id.descPrec);
            img_thumbnail = itemView.findViewById(R.id.image_viewc);

        }
    }


}
