package com.abhibarkade.todo.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhibarkade.todo.adapter.CustomDaysAdapter;
import com.abhibarkade.todo.auth.helper.SessionHelper;
import com.abhibarkade.todo.databinding.CreateTodoTileBinding;
import com.abhibarkade.todo.databinding.FragmentHomeBinding;
import com.abhibarkade.todo.pojo.CustomDays;
import com.abhibarkade.todo.repository.TaskRepository;
import com.abhibarkade.todo.roomdb.EntityTask;
import com.abhibarkade.todo.utils.enums.Category;
import com.abhibarkade.todo.utils.enums.Priority;
import com.abhibarkade.todo.utils.enums.Status;
import com.abhibarkade.todo.viewmodel.HomeViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.UUID;

public class Fragment_Home extends Fragment {

    FragmentHomeBinding binding;
    HomeViewModel viewModel;

    public Fragment_Home() {
    }

    @Override
    public void onStart() {
        super.onStart();
        TaskRepository.getOnlineData(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.context = getContext();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindDaysRecyclerView();
        binding.setViewmodel(viewModel);
        binding.createTaskChip.setOnClickListener(v -> createTodo());

        Toast.makeText(getActivity(), "" + viewModel.getTasksList().size(), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void createTodo() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        CreateTodoTileBinding view = CreateTodoTileBinding.inflate(getLayoutInflater());
        dialog.setContentView(view.getRoot());

        view.startTime.setOnFocusChangeListener((v, b) -> {
            if (b) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder().setTitleText("Select Start time").setTimeFormat(TimeFormat.CLOCK_12H).build();

                timePicker.addOnPositiveButtonClickListener(view1 -> {
                    int hour = timePicker.getHour();
                    String amOrPm = (hour < 12) ? "AM" : "PM";
                    view.startTime.setText(timePicker.getHour() + ":" + timePicker.getMinute() + " " + amOrPm);
                });
                timePicker.show(getChildFragmentManager(), null);
            }
        });
        view.endTime.setOnFocusChangeListener((v, b) -> {
            if (b) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder().setTitleText("Select End time").setTimeFormat(TimeFormat.CLOCK_12H).build();
                timePicker.addOnPositiveButtonClickListener(view1 -> {
                    int hour = timePicker.getHour();
                    String amOrPm = (hour < 12) ? "AM" : "PM";
                    view.endTime.setText(timePicker.getHour() + ":" + timePicker.getMinute() + " " + amOrPm);
                });
                timePicker.show(getChildFragmentManager(), null);
            }
        });
        view.btnCreate.setOnClickListener(v -> {
            TaskRepository.addTask(new EntityTask(
                    "TASK:" + UUID.randomUUID(),
                    view.edTitle.getText().toString().trim(),
                    view.edDescription.getText().toString().trim(),
                    SessionHelper.getCurrentUser(getContext()).id,
                    "" + System.currentTimeMillis(),
                    view.startTime.getText().toString().trim(),
                    view.endTime.getText().toString().trim(),
                    "" + Priority.Casual,
                    "" + Status.Completed,
                    "" + Category.Home
            ));
//            TaskRepository.getOnlineData(getContext());
        });

        dialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    void bindDaysRecyclerView() {
        ArrayList<CustomDays> list = new ArrayList<>();
        list.add(new CustomDays("Sun", 1));
        list.add(new CustomDays("Mon", 2));
        list.add(new CustomDays("Tue", 3));
        list.add(new CustomDays("Wed", 4));
        list.add(new CustomDays("Thu", 5));
        list.add(new CustomDays("Fri", 6));
        list.add(new CustomDays("Sat", 7));

        binding.recyclerDays.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerDays.setAdapter(new CustomDaysAdapter(list));

        binding.recyclerviewDailyTasks.setAdapter(viewModel.getTasksAdapter());
        // Tasks

//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            }
//        }).attachToRecyclerView(binding.recyclerTodayTasks);
//
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("ShowToast")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                EntityTask task = viewModel.remove(position);
                showUndoSnackBar(position, task);
            }
        }).attachToRecyclerView(binding.recyclerviewDailyTasks);
    }

    private void showUndoSnackBar(int position, EntityTask task) {
        Snackbar.make(binding.root, "Task Deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo", view -> viewModel.add(position, task)).show();
    }
}



















