package com.abhibarkade.todo.auth.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abhibarkade.todo.auth.PhoneVerification;
import com.abhibarkade.todo.databinding.FragmentSignUPBinding;
import com.abhibarkade.todo.pojo.POJO_User;

public class Fragment_SignUP extends Fragment {

    private FragmentSignUPBinding binding;

    public Fragment_SignUP() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUPBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.txtSignUp.setOnClickListener(view1 -> {

        });

        binding.signup.setOnClickListener(v -> addUser());
    }

    void addUser() {
        POJO_User user = new POJO_User(
                binding.username.getText().toString().trim(),
                binding.fullname.getText().toString().trim(),
                ""
        );

        Intent intent = new Intent(getActivity(), PhoneVerification.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("User", user);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}