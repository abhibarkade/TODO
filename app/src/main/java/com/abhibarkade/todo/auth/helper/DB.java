package com.abhibarkade.todo.auth.helper;

import android.content.Context;

import com.abhibarkade.todo.R;
import com.abhibarkade.todo.pojo.POJO_User;
import com.abhibarkade.todo.utils.dialogs.MessageDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.FirebaseFirestore;

public class DB {
    public static final FirebaseAuth auth = FirebaseAuth.getInstance();
    public static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public static String USER = "User";

    public static void addUser(Context context, POJO_User user) {
        firestore.collection(USER)
                .document("U" + user.getPhone())
                .set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        MessageDialog.showDialog(context, R.raw.otp_sent, "User Added Successfully ", "Continue");
                    }
                }).addOnFailureListener(e -> MessageDialog.showDialog(context, R.raw.otp_sent, e.getLocalizedMessage(), "Continue"));
    }
}