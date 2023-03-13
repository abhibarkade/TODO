package com.abhibarkade.todo.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abhibarkade.todo.databinding.CreateTodoTileBinding;
import com.abhibarkade.todo.databinding.FragmentHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;

public class Fragment_Home extends Fragment {

    FragmentHomeBinding binding;

    public Fragment_Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.chipCreateTask.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.chipCreateTask.setOnClickListener(view1 -> showTaskCreationDialog());
    }

    void showTaskCreationDialog() {
        CreateTodoTileBinding todoTileBinding = CreateTodoTileBinding.inflate(getLayoutInflater());

        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(todoTileBinding.getRoot());

        // Listeners
        todoTileBinding.btnPickDueDate.setOnClickListener(view1 -> {
            MaterialDatePicker.Builder<Long> build = MaterialDatePicker.Builder.datePicker();
            final MaterialDatePicker<Long> materialDatePicker = build.build();
            materialDatePicker.addOnPositiveButtonClickListener(
                    selection -> {
                        Toast.makeText(getActivity(), "" + materialDatePicker.getHeaderText(), Toast.LENGTH_SHORT).show();
                        final MaterialTimePicker.Builder builder1 = new MaterialTimePicker.Builder();
                        builder1.build().show(getChildFragmentManager(), null);
                    });

            materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");
        });

        dialog.show();
    }

}