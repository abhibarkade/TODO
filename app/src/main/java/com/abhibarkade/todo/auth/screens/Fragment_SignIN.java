package com.abhibarkade.todo.auth.screens;

import static com.abhibarkade.todo.auth.helper.DB.USER;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abhibarkade.todo.auth.helper.DB;
import com.abhibarkade.todo.databinding.FragmentSignINBinding;
import com.abhibarkade.todo.pojo.POJO_User;

public class Fragment_SignIN extends Fragment {

    private FragmentSignINBinding binding;
    private ProgressDialog dialog;

    public Fragment_SignIN() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignINBinding.inflate(inflater);

        dialog = new ProgressDialog(getActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btn.setOnClickListener(v -> {
            dialog.show();
            DB.firestore.collection(USER)
                    .whereEqualTo("username", binding.username.getText().toString())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (!task.getResult().isEmpty()) {
                            POJO_User user = task.getResult().toObjects(POJO_User.class).get(0);
                            if (binding.password.getText().toString().trim().equals(user.getPassword()))
                                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getActivity(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "No User Found!!", Toast.LENGTH_SHORT).show();
                        }
                        if (dialog.isShowing())
                            dialog.dismiss();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                        if (dialog.isShowing())
                            dialog.dismiss();
                    });
        });
    }
}














