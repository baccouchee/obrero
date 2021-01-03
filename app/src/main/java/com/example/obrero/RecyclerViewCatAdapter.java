package com.example.obrero;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RecyclerViewCatAdapter extends RecyclerView.Adapter<RecyclerViewCatAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Categories> mData ;
    RequestOptions option;

    public RecyclerViewCatAdapter(Context mContext, List<Categories> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cardviewcat,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, MainActivity.class);
                i.putExtra("cat_name",mData.get(viewHolder.getAdapterPosition()).getNom());
                mContext.startActivity(i);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_name.setText(mData.get(position).getNom());

        // Load Image from the internet and set it into Imageview using Glide

        Glide.with(mContext).load("http://10.0.2.2:3000/img/"+mData.get(position).getIcon()).apply(option).into(holder.img_thumbnail);

    }

    @Override
    public int getItemCount() {
        System.out.println(mData.size());
        return mData.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name ;
        ImageView img_thumbnail;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.imagecat);
            tv_name = itemView.findViewById(R.id.nomcatt);
            img_thumbnail = itemView.findViewById(R.id.image_viewcat);

        }
    }

}
