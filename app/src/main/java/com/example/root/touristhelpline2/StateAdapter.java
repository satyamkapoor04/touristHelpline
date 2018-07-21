package com.example.root.touristhelpline2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    String[] list;
    Context context;

    public StateAdapter (String[] list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext()).inflate(R.layout.states,parent,false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String myState = list[position];
        int rainbow = context.getResources().getIntArray(R.array.rainbow)[position%4];
        holder.state.setBackgroundColor(rainbow);
        holder.state.setText(myState);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView state;

        public ViewHolder(View itemView) {
            super(itemView);
            state = itemView.findViewById(R.id.state);
        }
    }
}
