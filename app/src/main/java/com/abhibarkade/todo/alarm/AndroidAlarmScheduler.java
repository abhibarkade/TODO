package com.abhibarkade.todo.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.time.ZoneId;

public class AndroidAlarmScheduler implements AlarmScheduler {
    Context context;
    private AlarmManager alarmManager;

    public AndroidAlarmScheduler(Context context) {
        this.context = context;
        alarmManager = context.getSystemService(AlarmManager.class);
    }

    @Override
    public void schedule(AlarmItem item) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("MSG", item.msg);

        alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                item.dateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                PendingIntent.getBroadcast(
                        context,
                        item.hashCode(),
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                )
        );
    }

    @Override
    public void cancel(AlarmItem item) {
        alarmManager.cancel(PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                new Intent(context, AlarmReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        ));
    }
}
