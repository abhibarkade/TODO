package com.abhibarkade.todo.utils.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import com.abhibarkade.todo.databinding.MessageDialogBinding;

public class MessageDialog {
    public static void showDialog(Context context, int lottie, String title, String button) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        MessageDialogBinding binding = MessageDialogBinding.inflate((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        builder.setView(binding.getRoot());

        binding.lottie.setAnimation(lottie);
        binding.title.setText(title);
        binding.btn.setText(button);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.btn.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
