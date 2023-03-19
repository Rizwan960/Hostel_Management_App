package com.example.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    homeFragrment homeFragrment=new homeFragrment();
    AdminFragment adminFragment=new AdminFragment();
    AnnoucementFragment annoucementFragment=new AnnoucementFragment();
    SettingsFragment settingsFragment=new SettingsFragment();
    ReportFragment reportFragment=new ReportFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        DrawerLayout drawerLayout;

        getSupportActionBar().setTitle("Hostel Management ");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.prime)));
        drawerLayout = findViewById(R.id.mainDrawerLayout);
        navigationView = findViewById(R.id.navigationViewId);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
        navigationView.setItemIconTintList(ColorStateList.valueOf(Color.BLACK));
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView bottomNavigationView=findViewById(R.id.botoomNavigaitonBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragrment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.HomeBottom:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragrment).commit();
                        return true;
                    case  R.id.AdminBottom:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, adminFragment).commit();
                        return true;
                    case R.id.announcementsBottom:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, annoucementFragment).commit();
                        return true;
                    case R.id.settingsBottom:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId())
                {
                    case R.id.homeDrawer:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragrment).commit();
                        return true;
                    case R.id.messDrawer:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                        return true;
                    case R.id.reportDrawer:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, reportFragment).commit();
                        return true;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomePage.this,LoginActivity.class));
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

}