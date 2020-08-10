package com.example.parkingsystem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myAdapter extends FirebaseRecyclerAdapter<bookingDetails,myAdapter.myviewholder> {

    public myAdapter(@NonNull FirebaseRecyclerOptions<bookingDetails> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull bookingDetails model) {
        holder.start.setText(model.getStartDate());
        holder.cost.setText(model.getCost());
        holder.duration.setText(model.getDuration());
        holder.end.setText(model.getEndDate());
        holder.index.setText(Integer.toString(position + 1));
        holder.sTime.setText(model.getStartTime());
        holder.eTime.setText(model.getEndTime());
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView start, end, cost, duration, index, sTime, eTime;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            duration = (TextView)itemView.findViewById(R.id.duration);
            cost = (TextView)itemView.findViewById(R.id.cost);
            start = itemView.findViewById(R.id.startDateTime);
            end = itemView.findViewById(R.id.endDateTime);
            index = itemView.findViewById(R.id.index);
            sTime = itemView.findViewById(R.id.startTime);
            eTime = itemView.findViewById(R.id.endTime);
        }
    }
}
