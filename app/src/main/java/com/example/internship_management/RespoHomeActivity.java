package com.example.internship_management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RespoHomeActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    Bundle bundle = new Bundle();
    Long value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respo_home);
        bottomNavigationView = findViewById(R.id.respoBottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        value = getIntent().getLongExtra("id", 0);
        bundle.putLong("id", value);
        Fragment fragment = new RespoDashboardFragment();
        fragment.setArguments(bundle);
        loadFragment(fragment);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.meetings:
                bundle.putLong("id", value);
                fragment = new RespoMeetingsFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.profile:
                bundle.putLong("id", value);
                fragment = new RespoProfileFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.etud:
                bundle.putLong("id", value);
                fragment = new RespoDashboardFragment();
                fragment.setArguments(bundle);
                break;
        }
        if (fragment != null) {
            loadFragment(fragment);
        }
        return true;
    }

    void loadFragment(Fragment fragment) {
        //to attach fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
    }
}