package com.abhibarkade.todo.auth.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.abhibarkade.todo.pojo.POJO_User;

public class SessionHelper {
    public static void signIn(Context context, POJO_User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id", user.getId());
        editor.putString("username", user.getusername());
        editor.putString("phone", user.getPassword());
        editor.putString("fullName", user.getFullName());

        editor.apply();
    }

    public static void signOut(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id", null);
        editor.putString("username", null);
        editor.putString("phone", null);
        editor.putString("fullName", null);

        editor.apply();
    }

    public static POJO_User getCurrentUser(Context context) {
        POJO_User user = new POJO_User();

        SharedPreferences sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);

        user.setId(sharedPreferences.getString("id", ""));
        user.setFullName(sharedPreferences.getString("fullName", ""));
        user.setusername(sharedPreferences.getString("username", ""));
        user.setPhone(sharedPreferences.getString("phone", ""));

        return user;
    }
}
