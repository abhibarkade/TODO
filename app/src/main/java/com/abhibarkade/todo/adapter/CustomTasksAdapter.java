package com.abhibarkade.todo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhibarkade.todo.databinding.SingleTaskSquareBinding;
import com.abhibarkade.todo.roomdb.EntityTask;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CustomTasksAdapter extends RecyclerView.Adapter<CustomTasksAdapter.ViewHolder> {
    List<EntityTask> list;
    Context context;

    public CustomTasksAdapter(Context context, List<EntityTask> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(SingleTaskSquareBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SingleTaskSquareBinding binding = holder.binding;

        binding.root.setOnLongClickListener(view -> {
//            updateTask(position);
            Snackbar.make(binding.root, "Task Updated", Snackbar.LENGTH_SHORT).show();
            return false;
        });

        EntityTask todo = list.get(position);
        binding.title.setText(todo.getTitle());
        binding.subtitle.setText(todo.getDescription());
        binding.time.setText(todo.getStartTime());
        binding.status.setText("" + todo.getStatus());
    }
//
//    private void updateTask(int position) {
//        BottomSheetDialog dialog = new BottomSheetDialog(context);
//        CreateTodoTileBinding view = CreateTodoTileBinding.inflate((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
//        dialog.setContentView(view.getRoot());
//
//        view.startTime.setOnFocusChangeListener((v, b) -> {
//            if (b) {
//                MaterialTimePicker timePicker = new MaterialTimePicker.Builder().setTitleText("Select Start time").setTimeFormat(TimeFormat.CLOCK_12H).build();
//
//                timePicker.addOnPositiveButtonClickListener(view1 -> {
//                    int hour = timePicker.getHour();
//                    String amOrPm = (hour < 12) ? "AM" : "PM";
//                    view.startTime.setText(timePicker.getHour() + ":" + timePicker.getMinute() + " " + amOrPm);
//                });
//                timePicker.show(context.getSystemService(FRAGME), null);
//            }
//        });
//        view.endTime.setOnFocusChangeListener((v, b) -> {
//            if (b) {
//                MaterialTimePicker timePicker = new MaterialTimePicker.Builder().setTitleText("Select End time").setTimeFormat(TimeFormat.CLOCK_12H).build();
//                timePicker.addOnPositiveButtonClickListener(view1 -> {
//                    int hour = timePicker.getHour();
//                    String amOrPm = (hour < 12) ? "AM" : "PM";
//                    view.endTime.setText(timePicker.getHour() + ":" + timePicker.getMinute() + " " + amOrPm);
//                });
//                timePicker.show(getChildFragmentManager(), null);
//            }
//        });
//
//        view.btnCreate.setOnClickListener(v -> {
//            for (int i = 0; i < 10; i++) {
//                DBHelper.addTask(view, new POJO_Todo(view.edTitle.getText().toString().trim(), view.edDescription.getText().toString().trim(), "abhibarkade", "" + System.currentTimeMillis(), view.startTime.getText().toString().trim(), view.endTime.getText().toString().trim(), Priority.Important, Status.Incomplete, Category.Personal));
//            }
//        });
//
//        dialog.show();
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public EntityTask remove(int index) {
        return list.remove(index);
    }

    public void add(EntityTask task) {
        list.add(task);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleTaskSquareBinding binding;

        public ViewHolder(@NonNull SingleTaskSquareBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
