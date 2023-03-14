package com.abhibarkade.todo.auth.helper;

import android.content.Context;
import android.widget.Toast;

import com.abhibarkade.todo.pojo.POJO_User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DB {
    public static final FirebaseAuth auth = FirebaseAuth.getInstance();
    public static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public static String USER = "User";

    public static boolean checkUserExists(Context context, String phone) {
        return false;
    }

    public static void addUser(Context context, POJO_User user) {
        firestore.collection(USER).document("U:" + user.getPhone()).set(user).addOnSuccessListener(documentReference -> Toast.makeText(context, "User Added", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }
}
