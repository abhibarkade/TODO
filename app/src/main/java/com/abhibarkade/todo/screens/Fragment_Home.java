package com.abhibarkade.todo.screens;

import static com.abhibarkade.todo.auth.helper.DB.firestore;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.abhibarkade.todo.adapter.CustomDaysAdapter;
import com.abhibarkade.todo.adapter.CustomTasksAdapter;
import com.abhibarkade.todo.databinding.CreateTodoTileBinding;
import com.abhibarkade.todo.databinding.FragmentHomeBinding;
import com.abhibarkade.todo.pojo.CustomDays;
import com.abhibarkade.todo.repository.TaskRepository;
import com.abhibarkade.todo.roomdb.DaoTask;
import com.abhibarkade.todo.roomdb.DatabaseTodo;
import com.abhibarkade.todo.roomdb.EntityTask;
import com.abhibarkade.todo.utils.enums.Category;
import com.abhibarkade.todo.utils.enums.Priority;
import com.abhibarkade.todo.utils.enums.Status;
import com.abhibarkade.todo.viewmodel.ViewModelHome;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.UUID;

public class Fragment_Home extends Fragment {

    FragmentHomeBinding binding;
    ViewModelHome viewmodel;
    TaskRepository taskRepo;
    DatabaseTodo db;
    DaoTask daoTask;

    public Fragment_Home() {
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);

        db = Room.databaseBuilder(requireContext(), DatabaseTodo.class, DatabaseTodo.DATABASE_NAME).allowMainThreadQueries().build();
        daoTask = db.daoTask();
        taskRepo = new TaskRepository(getContext());

        firestore.collection("Tasks").addSnapshotListener((value, error) -> {
            Toast.makeText(getActivity(), "Firestore changed", Toast.LENGTH_SHORT).show();
            daoTask.truncateTable();
            for (EntityTask snap : value.toObjects(EntityTask.class)) {
                daoTask.insertTask(snap);
            }
        });

        viewmodel = new ViewModelHome(getActivity().getApplication());
        daoTask.getTasks().observe(getViewLifecycleOwner(), entityTasks -> {
            Toast.makeText(getActivity(), "local changed : " + entityTasks.size(), Toast.LENGTH_SHORT).show();
            binding.recyclerviewDailyTasks.setAdapter(new CustomTasksAdapter(entityTasks));
            binding.recyclerviewDailyTasks.getAdapter().notifyDataSetChanged();
        });


        binding.setViewmodel(viewmodel);

        EntityTask entityTask = new EntityTask();
        entityTask.setTitle("" + UUID.randomUUID());
        binding.addLocal.setOnClickListener(v -> daoTask.insertTask(new EntityTask()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.createTaskChip.setOnClickListener(v -> createTodo());

        bindDaysRecyclerView();
        bindRefreshLayout();

//        fetchTasks();
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
            taskRepo.addTask(new EntityTask("TASK:" + UUID.randomUUID(), view.edTitle.getText().toString().trim(), view.edDescription.getText().toString().trim(), "abhibarkade", "" + System.currentTimeMillis(), view.startTime.getText().toString().trim(), view.endTime.getText().toString().trim(), "" + Priority.Casual, "" + Status.Incomplete, "" + Category.Home));
            dialog.dismiss();
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

        // Tasks
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
////                EntityTask task = viewModel.remove(position);
////                updateTask(task.getId());
                Snackbar.make(binding.root, "Marked as completed", Snackbar.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerviewDailyTasks);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("ShowToast")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//                EntityTask task = viewModel.remove(position);
//                showUndoSnackBar(position, task);
//                TaskRepository.getOnlineData(getContext());
                Snackbar.make(binding.root, "Task Deleted", Snackbar.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerviewDailyTasks);
    }

    // Update the Task in both online and offline
    private void updateTask(String taskId) {
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
//            TaskRepository.updateTask(new EntityTask(taskId, view.edTitle.getText().toString().trim(), view.edDescription.getText().toString().trim(), SessionHelper.getCurrentUser(getContext()).id, "" + System.currentTimeMillis(), view.startTime.getText().toString().trim(), view.endTime.getText().toString().trim(), "" + Priority.Casual, "" + Status.Completed, "" + Category.Home));
//            TaskRepository.getOnlineData(getContext());
        });

        dialog.show();
    }

    private void showUndoSnackBar(int position, EntityTask task) {
//        Snackbar.make(binding.root, "Task Deleted", Snackbar.LENGTH_SHORT).setAction("Undo", view -> viewModel.add(position, task)).show();
    }

    private void bindRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener(() -> {
//            CustomTasksAdapter adapter = binding.getViewmodel().getTasksAdapter();
//            adapter.setList(binding.getViewmodel().getTasksList());
            binding.refreshLayout.setRefreshing(false);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void fetchTasks() {
//
//        daoTask.getTasks().observe(getViewLifecycleOwner(), entityTasks -> {
//            Toast.makeText(getContext(), "Local Changed!!", Toast.LENGTH_SHORT).show();
//            CustomTasksAdapter adapter = new CustomTasksAdapter(entityTasks);
//            binding.recyclerviewDailyTasks.setAdapter(adapter);
//        });
    }
}
















