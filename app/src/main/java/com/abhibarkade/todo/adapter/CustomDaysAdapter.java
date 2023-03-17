package com.abhibarkade.todo.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhibarkade.todo.databinding.DayTileBinding;
import com.abhibarkade.todo.pojo.CustomDays;

import java.util.ArrayList;

public class CustomDaysAdapter extends RecyclerView.Adapter<CustomDaysAdapter.ViewHolder> {

    ArrayList<CustomDays> list;
    public static AdapterView.OnItemClickListener mListener;

    public CustomDaysAdapter(ArrayList<CustomDays> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DayTileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DayTileBinding binding = holder.binding;

        binding.monthday.setText("" + list.get(position).getMonthDay());
        binding.weekday.setText(list.get(position).getWeekDay());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        DayTileBinding binding;

        public ViewHolder(@NonNull DayTileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
