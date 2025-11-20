package com.example.dumke_joseph_option_2.EVENTS;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dumke_joseph_option_2.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList;

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.textEventTitle.setText(event.getTitle());
        holder.textEventDate.setText(event.getDue());

        // Default: hollow circle
        holder.imageCheckCircle.setImageResource(R.drawable.ic_circle_outline);

        holder.imageCheckCircle.setOnClickListener(v -> {
            // Visually show "done" circle
            holder.imageCheckCircle.setImageResource(R.drawable.ic_circle_checked_layer);

            // Delay, then remove
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (position < eventList.size()) {
                    eventList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, eventList.size());
                }
            }, 1000); // 1 second
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView textEventTitle, textEventDate;
        ImageView imageCheckCircle;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textEventTitle = itemView.findViewById(R.id.textEventTitle);
            textEventDate = itemView.findViewById(R.id.textEventDate);
            imageCheckCircle = itemView.findViewById(R.id.imageCheckCircle);
        }
    }
}
