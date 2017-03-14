package com.m2dl.mapus.mapus;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private Uri imageUri;
    private String imageFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        askPermission(android.Manifest.permission.CAMERA, new String[]{android.Manifest.permission.CAMERA}, 1);
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

    private void askPermission(String sensor, String[] permissions, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, sensor) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    requestCode);
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
                takePictureAnomaly();
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

    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void takePictureAnomaly() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        imageFileName = "ANOMALY_" + timeStamp;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  imageFileName+".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);
                        Log.d("MAIN", "onActivityResult: " + selectedImage.toString());
                        changeFragment(AnomalieFragment.newInstance(selectedImage.toString(), imageFileName));
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("CAMERA", "onRequestPermissionsResult: PERMISSION ACCORDEE");
                    askPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

                } else {
                    Log.d("CAMERA", "onRequestPermissionsResult: CAMERA PERMISSION REFUSEE");Toast.makeText(this, "Vous devez accepter les permissions pour continuer.", Toast.LENGTH_LONG)
                            .show();
                    finish();
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("CAMERA", "onRequestPermissionsResult: STORAGE PERMISSION ACCORDEE");
                    askPermission(android.Manifest.permission.RECORD_AUDIO, new String[]{android.Manifest.permission.RECORD_AUDIO}, 3);

                } else {
                    Log.d("CAMERA", "onRequestPermissionsResult: STORAGE PERMISSION REFUSEE");
                    Toast.makeText(this, "Vous devez accepter les permissions pour continuer.", Toast.LENGTH_LONG)
                            .show();
                    finish();
                }
                return;
            }
            case 3: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.d("CAMERA", "onRequestPermissionsResult: SON PERMISSION ACCORDEE");

                } else {
                    Log.d("CAMERA", "onRequestPermissionsResult: SON PERMISSION REFUSEE");
                    Toast.makeText(this, "Vous devez accepter les permissions pour continuer.", Toast.LENGTH_LONG)
                            .show();
                    finish();
                }
                return;
            }
        }
    }
}
