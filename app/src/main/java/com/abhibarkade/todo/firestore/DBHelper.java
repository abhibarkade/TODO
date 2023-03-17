package com.abhibarkade.todo.firestore;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.abhibarkade.todo.databinding.CreateTodoTileBinding;
import com.abhibarkade.todo.databinding.SingleTaskSquareBinding;
import com.abhibarkade.todo.pojo.POJO_Todo;
import com.abhibarkade.todo.roomdb.DaoTask;
import com.abhibarkade.todo.roomdb.DatabaseTodo;
import com.abhibarkade.todo.roomdb.EntityTask;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DBHelper {
    static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    static String TASK = "Tasks";

    public static void addTask(CreateTodoTileBinding binding, POJO_Todo todo) {
        firestore.collection(TASK)
                .document(todo.getId())
                .set(todo)
                .addOnCompleteListener(task -> {
                    Snackbar.make(binding.root, "Task Created", Snackbar.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Snackbar.make(binding.root, "Task Creation failed", Snackbar.LENGTH_SHORT).show();
                });
    }

    public static void update(SingleTaskSquareBinding binding, EntityTask todo) {
//        Map<String, String> tmp = POJO_Todo.toMap(todo);
        firestore.collection(TASK)
                .document(todo.getId())
                .set(todo)
                .addOnCompleteListener(task -> {
                    Snackbar.make(binding.root, "Task Updated", Snackbar.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Snackbar.make(binding.root, "Task Update failed", Snackbar.LENGTH_SHORT).show();
                });
    }

    public static void getTasksList(String userId, Context context) {
        DatabaseTodo db = Room.databaseBuilder(context, DatabaseTodo.class, DatabaseTodo.DATABASE_NAME)
                .allowMainThreadQueries().build();
        DaoTask daoTask = db.daoTask();
        daoTask.truncateTable();
        firestore.collection(TASK)
                .whereEqualTo("userId", userId)
                .get()
                .addOnFailureListener(e -> Log.d("ERROR", e.getLocalizedMessage()))
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot snap : task.getResult()) {
                        POJO_Todo todo = snap.toObject(POJO_Todo.class);
                        addTaskToLocal(context, EntityTask.toEntityTask(todo));
                    }
//                    DatabaseTodo db = Room.databaseBuilder(context, DatabaseTodo.class, DatabaseTodo.DATABASE_NAME).build();
//                    DaoTask daoTask = db.daoTask();
//                    Toast.makeText(context, "" + daoTask.getTasks().size(), Toast.LENGTH_SHORT).show();
                });
    }

    private static void addTaskToLocal(Context context, EntityTask todo) {
        DatabaseTodo db = Room.databaseBuilder(context, DatabaseTodo.class, DatabaseTodo.DATABASE_NAME)
                .allowMainThreadQueries().build();
        DaoTask daoTask = db.daoTask();
        daoTask.insertTask(todo);
//        Toast.makeText(context, "" + daoTask.getTasks().size(), Toast.LENGTH_SHORT).show();
    }
}
