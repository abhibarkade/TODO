package com.abhibarkade.todo.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.abhibarkade.todo.MainActivity;
import com.abhibarkade.todo.R;
import com.abhibarkade.todo.auth.helper.SessionHelper;
import com.abhibarkade.todo.roomdb.DaoTask;
import com.abhibarkade.todo.roomdb.DatabaseTodo;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DatabaseTodo db = Room.databaseBuilder(this, DatabaseTodo.class, DatabaseTodo.DATABASE_NAME).build();
        DaoTask daoTask = db.daoTask();
//        daoTask.insertTask(todo);
//        Toast.makeText(this, "" + daoTask.getTasks().size(), Toast.LENGTH_SHORT).show();

        if (SessionHelper.getCurrentUser(this).getId().length() > 0) {
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        }
    }
}