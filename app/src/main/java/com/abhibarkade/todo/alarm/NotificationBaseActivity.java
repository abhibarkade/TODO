package com.abhibarkade.todo.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.abhibarkade.todo.R;

public class NotificationBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_base);
    }
}