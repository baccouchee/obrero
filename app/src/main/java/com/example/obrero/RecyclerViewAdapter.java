package com.example.obrero;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Prestation> mData ;
    RequestOptions option;


    public RecyclerViewAdapter(Context mContext, List<Prestation> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
         option = new RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cardview,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, GetPro.class);
                i.putExtra("campaign_img",mData.get(viewHolder.getAdapterPosition()).getPhoto());
                i.putExtra("campaign_name",mData.get(viewHolder.getAdapterPosition()).getNomP());
                i.putExtra("campaign_description",mData.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("campaign_tarif",mData.get(viewHolder.getAdapterPosition()).getTarif());
                i.putExtra("campaign_idPres",mData.get(viewHolder.getAdapterPosition()).getIdPres());
                i.putExtra("campaign_idU",mData.get(viewHolder.getAdapterPosition()).getIdU());
                mContext.startActivity(i);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getNomP());
        holder.tv_desc.setText(mData.get(position).getDescription());


        // Load Image from the internet and set it into Imageview using Glide

        Glide.with(mContext).load("http://10.0.2.2:3000/img/"+mData.get(position).getPhoto()).apply(option).into(holder.img_thumbnail);

    }

    @Override
    public int getItemCount() {
        System.out.println(mData.size());
        return mData.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name ;
        TextView tv_desc ;
        TextView tv_category;
        ImageView img_thumbnail;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.containerimage);
            tv_name = itemView.findViewById(R.id.nomPre);
            tv_desc = itemView.findViewById(R.id.descPre);
            img_thumbnail = itemView.findViewById(R.id.image_view);

        }
    }

}
