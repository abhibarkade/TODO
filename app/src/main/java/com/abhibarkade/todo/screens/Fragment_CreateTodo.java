package com.abhibarkade.todo.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.abhibarkade.todo.databinding.FragmentCreateTodoBinding;

public class Fragment_CreateTodo extends Fragment {

    FragmentCreateTodoBinding binding;

    public Fragment_CreateTodo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTodoBinding.inflate(inflater);
        return binding.btnPickDueDate.getRootView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.setTitle("Hey there ....");
    }
}