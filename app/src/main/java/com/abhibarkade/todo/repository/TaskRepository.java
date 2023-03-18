package com.abhibarkade.todo.repository;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.abhibarkade.todo.roomdb.DatabaseTodo;
import com.abhibarkade.todo.roomdb.EntityTask;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class TaskRepository {
    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    static final String TASK = "Tasks";

    // Get Data from Firestore
    public static void getOnlineData(Context context) {
        DatabaseTodo db = Room.databaseBuilder(context, DatabaseTodo.class, DatabaseTodo.DATABASE_NAME).allowMainThreadQueries().build();
        db.daoTask().truncateTable();

        firestore.collection(TASK).whereEqualTo("userId", "abhibarkade").get().addOnCompleteListener(task -> {
            for (QueryDocumentSnapshot snap : task.getResult()) {
                EntityTask tmp = snap.toObject(EntityTask.class);
                db.daoTask().insertTask(tmp);   // insert data into local database (room database)
            }
        });
    }

    public static void addTask(EntityTask todo) {
        firestore.collection(TASK)
                .document(todo.getId())
                .set(todo)
                .addOnCompleteListener(task -> {
                    Log.e("Task Success", "TRUE");
                })
                .addOnFailureListener(e -> {
                    Log.e("Task Failure", e.getLocalizedMessage());
                });
    }
}













