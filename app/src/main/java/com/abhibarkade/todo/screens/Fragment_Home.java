package com.abhibarkade.todo.screens;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.abhibarkade.todo.adapter.CustomDaysAdapter;
import com.abhibarkade.todo.adapter.CustomTasksAdapter;
import com.abhibarkade.todo.auth.helper.SessionHelper;
import com.abhibarkade.todo.databinding.CreateTodoTileBinding;
import com.abhibarkade.todo.databinding.FragmentHomeBinding;
import com.abhibarkade.todo.firestore.DBHelper;
import com.abhibarkade.todo.notification.CustomNotificationReceiver;
import com.abhibarkade.todo.pojo.CustomDays;
import com.abhibarkade.todo.roomdb.DatabaseTodo;
import com.abhibarkade.todo.roomdb.EntityTask;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Fragment_Home extends Fragment {

    FragmentHomeBinding binding;
    DatabaseTodo db;

    public Fragment_Home() {
    }

    @Override
    public void onStart() {
        super.onStart();
        DBHelper.getTasksList(SessionHelper.getCurrentUser(getActivity()).id, getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);

        db = Room.databaseBuilder(getContext(), DatabaseTodo.class, DatabaseTodo.DATABASE_NAME)
                .allowMainThreadQueries().build();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindDaysRecyclerView();

        binding.createTaskChip.setOnClickListener(v -> createTodo());
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
//            for (int i = 0; i < 10; i++) {
//                DBHelper.addTask(view, new POJO_Todo(view.edTitle.getText().toString().trim(), view.edDescription.getText().toString().trim(), "abhibarkade", "" + System.currentTimeMillis(), view.startTime.getText().toString().trim(), view.endTime.getText().toString().trim(), Priority.Important, Status.Incomplete, Category.Personal));
//            }
            AlarmManager manager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getContext(), CustomNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 5);
            calendar.set(Calendar.MINUTE, 46);

            long startTime = calendar.getTimeInMillis();
            manager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, 1000 * 60 * 60 * 24, pendingIntent);
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
        List<EntityTask> list1 = db.daoTask().getTasks();
        Toast.makeText(getActivity(), "" + list1.size(), Toast.LENGTH_SHORT).show();
        binding.recyclerTodayTasks.setLayoutManager(new LinearLayoutManager(getActivity()));
        CustomTasksAdapter tasksAdapter = new CustomTasksAdapter(getContext(), list1);
        binding.recyclerTodayTasks.setAdapter(tasksAdapter);

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
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @SuppressLint("ShowToast")
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                EntityTask task = tasksAdapter.remove(viewHolder.getAdapterPosition());
//                tasksAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//                Snackbar.make(binding.root, "Task Deleted", Snackbar.LENGTH_SHORT)
//                        .setAction("UNDO", view -> {
//                            tasksAdapter.add(task);
//                            tasksAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
//                        }).show();
//            }
//        }).attachToRecyclerView(binding.recyclerTodayTasks);
    }
}



















