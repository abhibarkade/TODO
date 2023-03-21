package com.abhibarkade.todo.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.abhibarkade.todo.adapter.timeline.POJO_Timeline;
import com.abhibarkade.todo.adapter.timeline.TimelineAdapter;
import com.abhibarkade.todo.databinding.FragmentHistoryBinding;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.List;

public class Fragment_History extends Fragment {

    FragmentHistoryBinding binding;

    public Fragment_History() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater);

        bindDatePicker();
        bindRecyclerView();

        return binding.getRoot();
    }

    private void bindRecyclerView() {
        List<Integer> tmp = new ArrayList<>();
        List<POJO_Timeline> list = new ArrayList<>();
        list.add(new POJO_Timeline("09:30 AM", tmp));
        list.add(new POJO_Timeline("09:45 AM", tmp));
        list.add(new POJO_Timeline("10:00 AM", tmp));
        list.add(new POJO_Timeline("10:15 AM", tmp));
        list.add(new POJO_Timeline("10:30 AM", tmp));

        binding.timelineRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.timelineRecyclerview.setAdapter(new TimelineAdapter(getContext(),list));
    }

    private void bindDatePicker() {
        binding.todayRoot.setOnClickListener(v -> {
            MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
            builder.setTitleText("Select Day ...");

            MaterialDatePicker datePicker = builder.build();
            datePicker.addOnPositiveButtonClickListener(selection -> binding.today.setText(datePicker.getHeaderText()));
            datePicker.show(getChildFragmentManager(), null);
        });
    }
}












