package com.m2dl.mapus.mapus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Firebase firebase;
    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.firebase = new Firebase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            GeolocalisationFragment geolocalisationFragment = GeolocalisationFragment.newInstance("Valeur de l'argument 1", "Valeur de l'argument 2");

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, geolocalisationFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_geoloc:
                GeolocalisationFragment geolocFragment = GeolocalisationFragment.newInstance("Var 1", "Var 2");
                changeFragment(geolocFragment);
                break;
            case R.id.nav_anomalie:
                AnomalieFragment anomalieFragment = AnomalieFragment.newInstance("Var 1", "Var 2");
                changeFragment(anomalieFragment);
                break;
            case R.id.nav_ru:
                OccupationRuFragment occupationRuFragment = OccupationRuFragment.newInstance("Var 1", "Var 2");
                changeFragment(occupationRuFragment);
                break;
            case R.id.nav_edt:
                EmploiDuTempsFragment emploiDuTempsFragment = EmploiDuTempsFragment.newInstance("Var 1", "Var 2");
                changeFragment(emploiDuTempsFragment);
                break;
            case R.id.nav_qrcode:
                QrCodeFragment qrCodeFragment = QrCodeFragment.newInstance("Var 1", "Var 2");
                changeFragment(qrCodeFragment);
                break;
            case R.id.nav_settings:
                SettingsFragment settingsFragment = SettingsFragment.newInstance("Var 1", "Var 2");
                changeFragment(settingsFragment);
                break;
            case R.id.nav_informations:
                InformationsFragment informationsFragment = InformationsFragment.newInstance();
                changeFragment(informationsFragment);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
