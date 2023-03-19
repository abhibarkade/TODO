package com.abhibarkade.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.abhibarkade.todo.roomdb.DatabaseTodo;
import com.abhibarkade.todo.roomdb.EntityTask;

import java.util.List;

public class ViewModelHome extends AndroidViewModel {

    DatabaseTodo db;

    MutableLiveData<List<EntityTask>> entities;

    public ViewModelHome(@NonNull Application application) {
        super(application);
        db = Room.databaseBuilder(application, DatabaseTodo.class, DatabaseTodo.DATABASE_NAME).allowMainThreadQueries().build();
    }

    public LiveData<List<EntityTask>> getDailyTasks() {
        return db.daoTask().getTasks();
    }

    public MutableLiveData<List<EntityTask>> getEntities() {
        return entities;
    }

    public void setEntities() {
        entities.setValue(db.daoTask().getTasks().getValue());
    }
}
