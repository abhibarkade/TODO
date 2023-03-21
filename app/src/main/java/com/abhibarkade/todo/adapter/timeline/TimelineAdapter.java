package com.abhibarkade.todo.adapter.timeline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhibarkade.todo.R;
import com.abhibarkade.todo.databinding.TimelineSingleTileBinding;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    Context context;
    List<POJO_Timeline> list;

    public TimelineAdapter(Context context, List<POJO_Timeline> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TimelineSingleTileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        POJO_Timeline timeline = list.get(position);
        TimelineSingleTileBinding binding = holder.binding;

        int randomNumber = (int) (Math.random() * 5);
        for (int i = 0; i < randomNumber; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.ic_app_logo);
            binding.taskHolder.addView(imageView);
        }

        binding.time.setText(timeline.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TimelineSingleTileBinding binding;

        public ViewHolder(@NonNull TimelineSingleTileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
