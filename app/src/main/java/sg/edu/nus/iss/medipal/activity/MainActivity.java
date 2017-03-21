package sg.edu.nus.iss.medipal.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.fragment.AppointmentFragment;
import sg.edu.nus.iss.medipal.fragment.HealthBioFragment;
import sg.edu.nus.iss.medipal.fragment.IceFragment;
import sg.edu.nus.iss.medipal.fragment.MeasurementFragment;
import sg.edu.nus.iss.medipal.fragment.PersonalBioFragment;
import sg.edu.nus.iss.medipal.fragment.ReportFragment;
import sg.edu.nus.iss.medipal.fragment.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        IceFragment.OnListFragmentInteractionListener, MeasurementFragment.OnListFragmentInteractionListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private boolean refreshHealthBioFragment;
    private boolean refreshAppointmentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //setup toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up navigation drawer for showing the menu items
        setupNavDrawer();

        //setup listener for navigation drawer menu item clicks
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setupNavDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String Title;
        Fragment fragment;
        if (id == R.id.nav_personalBio) {
            /*Intent personalBioIntent = new Intent(getApplicationContext(), AddEditPersonalBioActivity.class);
            startActivity(personalBioIntent);*/
            fragment = new PersonalBioFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder,fragment).commit();
        } else if (id == R.id.nav_healthBio) {
            fragment = new HealthBioFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder,fragment).commit();
        } else if (id == R.id.nav_appointments) {
            resetTitle("Appointments");
            //use the appointment view to show in the main page
            fragment = new AppointmentFragment();
            //move this to outside when all other modules are implemented using fragments
            //populate the selected view(fragment) in the main page using fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder,fragment).commit();

        } else if(id == R.id.nav_medicine) {
            Intent intent_medicine= new Intent(getApplicationContext(), MedicineActivity.class);
            startActivity(intent_medicine);
        } else if (id == R.id.nav_measurement) {
            fragment = new MeasurementFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (id == R.id.nav_ice) {
            fragment = new IceFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        }
        else if(id == R.id.nav_reports){
            fragment = new ReportFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        }

        //close drawer when an item is clicked.
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void resetTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    @Override protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment = null;
        if(refreshAppointmentFragment)
        {
            refreshAppointmentFragment=false;
            fragment = new AppointmentFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder,fragment).commit();
        }
        else if(refreshHealthBioFragment){
            refreshHealthBioFragment = false;
            fragment = new HealthBioFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder,fragment).commit();
        }
    }

    @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("here ", Integer.toString(requestCode) + " " + Integer.toString(resultCode));
        if (requestCode == 101 || requestCode == 102) {
            if (resultCode == 0) {
                refreshAppointmentFragment = true;
            }
        }
        else if(requestCode == 1 || requestCode == 2){
            if (resultCode == 0) {
                refreshHealthBioFragment = true;
            }
        }
    }

    public void setActionBarTitle(String title){
        toolbar.setTitle(title);
    }

    public void onIceSelected(DummyContent.DummyItem item)
    {

    }

    public void onMeasurementSelected(DummyContent.DummyItem item)
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.helpswitch)
        {
            item.setChecked(!item.isChecked());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
