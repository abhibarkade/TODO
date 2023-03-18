package com.abhibarkade.todo.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.abhibarkade.todo.adapter.CustomTasksAdapter;
import com.abhibarkade.todo.roomdb.DatabaseTodo;
import com.abhibarkade.todo.roomdb.EntityTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint("StaticFieldLeak")
public class HomeViewModel extends ViewModel {
    CustomTasksAdapter tasksAdapter;
    public Context context;

    public List<EntityTask> getTasksList() {
        DatabaseTodo db = Room.databaseBuilder(context, DatabaseTodo.class, DatabaseTodo.DATABASE_NAME).allowMainThreadQueries().build();
        return db.daoTask().getTasks();
    }

    public CustomTasksAdapter getTasksAdapter() {
        tasksAdapter = new CustomTasksAdapter(context, getTasksList());
        return tasksAdapter;
    }

    public EntityTask remove(int index) {
        EntityTask task = tasksAdapter.remove(index);
        return task;
    }


    public void add(int index, EntityTask task) {
        tasksAdapter.add(index, task);
    }

    public String getToday() {
        Calendar cal = Calendar.getInstance();
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        SimpleDateFormat weekFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        String currentMonth = monthFormat.format(currentDate);
        return weekFormat.format(currentDate) + ", " + cal.get(Calendar.DAY_OF_MONTH) + " " + currentMonth;
    }
}


















