package com.abhibarkade.todo.auth.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abhibarkade.todo.R;
import com.abhibarkade.todo.databinding.FragmentSignINBinding;
import com.abhibarkade.todo.databinding.FragmentSignUPBinding;

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
            Toast.makeText(getActivity(), "HIT", Toast.LENGTH_SHORT).show();
        });
    }
}