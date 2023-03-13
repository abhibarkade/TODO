package com.abhibarkade.todo.auth.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abhibarkade.todo.databinding.FragmentSignINBinding;

public class Fragment_SignIN extends Fragment {

    private FragmentSignINBinding binding;

    public Fragment_SignIN() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignINBinding.inflate(inflater);
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