package com.abhibarkade.todo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.abhibarkade.todo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    BottomNavigationView navView;
    AppBarConfiguration appBarConfiguration;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpNav();

//        NavController navController = Navigation.findNavController(this, R.id.frag);
//        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        final NavController navController = navHostFragment.getNavController();
//
////        NavigationUI.setupWithNavController(binding.menu, navController);
//
//        binding.menu.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.fragment_Home:
//                    navController.navigate(R.id.fragment_Home);
//                    break;
//                case R.id.fragment_Profile:
//                    navController.navigate(R.id.fragment_Profile);
//                    break;
//                case R.id.fragment_History:
//                    navController.navigate(R.id.fragment_History);
//                    break;
//                case R.id.fragment_Notification:
//                    navController.navigate(R.id.fragment_Notification);
//                    break;
//                case R.id.fragment_Setting:
//                    navController.navigate(R.id.fragment_Setting);
//                    break;
//            }
//            return false;
//        });
    }

    private void setUpNav(){
        navView = binding.menu;
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView,navController);
    }
}
















