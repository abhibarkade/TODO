package com.abhibarkade.todo.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.abhibarkade.todo.roomdb.DaoTask;
import com.abhibarkade.todo.roomdb.DatabaseTodo;
import com.abhibarkade.todo.roomdb.EntityTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TaskRepository {
    Context context;
    FirebaseFirestore firestore;
    DatabaseTodo db;
    DaoTask dao;
    static final String TASK = "Tasks";

    public TaskRepository(Context context) {
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
        db = Room.databaseBuilder(context, DatabaseTodo.class, DatabaseTodo.DATABASE_NAME).allowMainThreadQueries().build();
        dao = db.daoTask();

//        liveFetch();
//        fetchAll();
    }

    public void liveFetch() {
        firestore.collection(TASK)
                .addSnapshotListener((value, error) -> {
                    //Toast.makeText(context, "Firestore changed", Toast.LENGTH_SHORT).show();
                    dao.truncateTable();
                    for (EntityTask snap : value.toObjects(EntityTask.class)) {
                        dao.insertTask(snap);
                    }
                });
    }

    public void fetchAll() {
        firestore.collection(TASK)
                .whereEqualTo("userId", "abhibarkade")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        dao.truncateTable();
                        for (QueryDocumentSnapshot snap : task.getResult()) {
                            dao.insertTask(snap.toObject(EntityTask.class));
                        }
                    }
                });
    }

    public void updateTask(EntityTask task) {
        firestore.collection(TASK).document(task.getId()).set(task);
    }

    public void removeTask(EntityTask task) {
        firestore.collection(TASK).document(task.getId()).delete(); // delete
    }

    public void addTask(EntityTask todo) {
        firestore.collection(TASK).document(todo.getId()).set(todo).addOnCompleteListener(task -> {
            Log.e("Task Success", "TRUE");
        }).addOnFailureListener(e -> {
            Log.e("Task Failure", e.getLocalizedMessage());
        });
    }
}













