package com.taocoder.readsign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnFragmentChangeListener {

    private DrawerLayout drawerLayout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        setupNavigation();
        changeFragment(new HomeFragment(), false);
    }

    private void setupNavigation() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Intent intent = null;

                switch (item.getItemId()) {

                    case R.id.homeNav:
                        changeFragment(new HomeFragment(), true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.inform:
                        changeFragment(new InfoFragment(), true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.quiz:
                        changeFragment(new GameFragment(), true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.contact:
                        changeFragment(new ContactFragment(), true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.about:
                        changeFragment(new AboutFragment(), true);
                        drawerLayout.closeDrawers();
                        return true;
                }

                if (!item.isChecked())
                    item.setChecked(true);

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeFragment(Fragment fragment, boolean backTrack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.container, fragment);
        if (backTrack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void onFragmentChange(Fragment fragment) {
        changeFragment(fragment, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 40:
                if (grantResults[0] != Activity.RESULT_OK) {
                    Utils.showMessage(getApplicationContext(), "Location Permission is required");
                }
                break;
        }
    }
}