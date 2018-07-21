package com.example.root.touristhelpline2;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    Context context;
    List<Places> list;
    ImageLoader mImageLoader;

    public PlaceAdapter (List<Places> list, Context context) {
        this.list = list;
        this.context = context;
        mImageLoader = AppSingleton.getInstance(context).getImageLoader();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.places,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageUrl("http://"+list.get(position).getImageUrl(),mImageLoader);
        holder.name.setText(list.get(position).getName());
        holder.location.setText(list.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        NetworkImageView imageView;
        TextView name;
        TextView location;
        ImageView share;
        ImageView upanddown;
        TextView upvotes;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            share = itemView.findViewById(R.id.share);
            upanddown = itemView.findViewById(R.id.upanddown);
            upvotes = itemView.findViewById(R.id.upvotes);

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Hello World", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
