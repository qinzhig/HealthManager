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
import android.widget.TextView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.fragment.AppointmentFragment;
import sg.edu.nus.iss.medipal.fragment.AppointmentsTabFragment;
import sg.edu.nus.iss.medipal.fragment.ConsumptionFragment;
import sg.edu.nus.iss.medipal.fragment.HealthBioFragment;
import sg.edu.nus.iss.medipal.fragment.HomeFragment;
import sg.edu.nus.iss.medipal.fragment.IceFragment;
import sg.edu.nus.iss.medipal.fragment.MeasurementFragment;
import sg.edu.nus.iss.medipal.fragment.MedicineFragment;
import sg.edu.nus.iss.medipal.fragment.PersonalBioFragment;
import sg.edu.nus.iss.medipal.fragment.ReportFragment;
import sg.edu.nus.iss.medipal.manager.PersonalBioManager;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.PersonalBio;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private boolean refreshHealthBioFragment;
    private boolean refreshAppointmentFragment;
    private boolean refreshPersonalBioFragment;
    private boolean refreshMedicineFragment;
    private boolean refreshHomeFragment;
    private boolean refreshMeasurement;
    private boolean refreshICEFragment;
    private Boolean inHomeFragment;
    private PersonalBioManager personalBioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inHomeFragment = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Log.d("Activity", "started");
        //setup toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set up navigation drawer for showing the menu items
        setupNavDrawer();

        personalBioManager = new PersonalBioManager();
        PersonalBio personalBio =
                personalBioManager.getpersonalBio(this);

        //setup listener for navigation drawer menu item clicks
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);

        TextView name = (TextView) headerLayout.findViewById(R.id.name);
        if(personalBio!=null)
            name.setText(personalBio.getName());

        navigationView.setNavigationItemSelectedListener(this);

        Bundle intentExtras = getIntent().getExtras();

        if (intentExtras != null) {
            Log.d("Activity", "Bundle");
            //Get Appointment reminder notification intent
            String notificationContent = intentExtras.getString("notification");
            String notificationId = intentExtras.getString("Id");

            //Get the Medicine reminder notification intent
            int meidicineNumber = intentExtras.getInt("medicineId",0);

            Log.d("intent args", notificationContent + " " + notificationId);

            if (notificationContent != null && notificationId != null) {
                showFragment("Appointment", notificationContent, notificationId);
                getIntent().removeExtra("notification");
                getIntent().removeExtra("Id");
            }
            if(meidicineNumber != 0){

                showFragment("Medications", "Medicine need to be consumed now!", Integer.toString(meidicineNumber));
                getIntent().removeExtra("medicineId");
            }
        } else {
            loadHomeFragment();
        }
    }

    private void loadHomeFragment() {
        Log.d("Activity", "fragment home load");
        //use the appointment view to show in the main page
        resetTitle("Home");
        Fragment fragment = new HomeFragment();
        inHomeFragment = true;
        //move this to outside when all other modules are implemented using fragments
        //populate the selected view(fragment) in the main page using fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
    }

    private void showFragment(String fragmentType, String notificationContent, String notificationId) {
        inHomeFragment = false;
        if (fragmentType.equalsIgnoreCase("Appointment")) {
            Log.d("Activity", "fragment load");
            //use the appointment view to show in the main page
            Fragment fragment = new AppointmentsTabFragment();
            Bundle bundle = new Bundle();
            bundle.putString("notification", notificationContent);
            bundle.putString("Id", notificationId);
            fragment.setArguments(bundle);
            //move this to outside when all other modules are implemented using fragments
            //populate the selected view(fragment) in the main page using fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        }else if(fragmentType.equalsIgnoreCase("Medications"))
        {
            Log.v("Main Activity","Medication fragement load");
            Fragment fragment = new MedicineFragment();

            Bundle bundle = new Bundle();
            bundle.putString("notification",notificationContent);
            bundle.putInt("medicineId",Integer.valueOf(notificationId));
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        }

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
        inHomeFragment = false;

        if (id == R.id.nav_home) {
            resetTitle("Home");
            fragment = new HomeFragment();
            inHomeFragment = true;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (id == R.id.nav_personalBio) {
            resetTitle("Personal Bio");
            fragment = new PersonalBioFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (id == R.id.nav_healthBio) {
            resetTitle("Health Bio");
            fragment = new HealthBioFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (id == R.id.nav_appointments) {
            resetTitle("Appointments");
            //use the appointment view to show in the main page
            fragment = new AppointmentFragment();
            //move this to outside when all other modules are implemented using fragments
            //populate the selected view(fragment) in the main page using fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (id == R.id.nav_medicine) {
            toolbar.findViewById(R.id.sv_medicine).setVisibility(View.VISIBLE);
           // Intent intent_medicine = new Intent(getApplicationContext(), MedicineFragment.class);
            //startActivity(intent_medicine);
            resetTitle("Medications");
            //use the appointment view to show in the main page
            fragment = new MedicineFragment();
            //move this to outside when all other modules are implemented using fragments
            //populate the selected view(fragment) in the main page using fragment manager
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (id == R.id.nav_ice) {
            resetTitle("Contacts(In Case Emergency");
            fragment = new IceFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (id == R.id.nav_measurement) {
            resetTitle("Measurements");
            fragment = new MeasurementFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (id == R.id.nav_reports) {
            resetTitle("Reports");
            fragment = new ReportFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (id == R.id.nav_consumption) {
           // Intent intent_consumption = new Intent(getApplicationContext(), ConsumptionActivity.class);
            //startActivity(intent_consumption);
            resetTitle("Consumptions");
            fragment = new ConsumptionFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        }

        if(id != R.id.nav_medicine){
            toolbar.findViewById(R.id.sv_medicine).setVisibility(View.GONE);
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
        toolbar.findViewById(R.id.sv_medicine).setVisibility(View.GONE);
        //super.onBackPressed();
        if (inHomeFragment == true) {
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
        } else {
            loadHomeFragment();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment fragment = null;
        if (refreshAppointmentFragment) {
            refreshAppointmentFragment = false;
            fragment = new AppointmentFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (refreshHealthBioFragment) {
            refreshHealthBioFragment = false;
            fragment = new HealthBioFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        } else if (refreshPersonalBioFragment) {
            refreshPersonalBioFragment = false;
            fragment = new PersonalBioFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        }
        else if (refreshMedicineFragment) {
            refreshMedicineFragment = false;
            fragment = new MedicineFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        }
        else if(refreshHomeFragment){
            //  if(inHomeFragment)
            loadHomeFragment();
        }
        else if(refreshMeasurement){
            refreshMeasurement=false;
            fragment = new MeasurementFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        }
        else if(refreshICEFragment){
            refreshICEFragment=false;
            fragment = new IceFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.viewplaceholder, fragment).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("here ", Integer.toString(requestCode) + " " + Integer.toString(resultCode));
        if (requestCode == 101 || requestCode == 102) {
            if (resultCode == 0) {
                refreshAppointmentFragment = true;
            }
        } else if (requestCode == 1 || requestCode == 2) {
            if (resultCode == 0) {
                refreshHealthBioFragment = true;
            }
        } else if (requestCode == 3) {
            if (resultCode == 0) {
                refreshPersonalBioFragment = true;
            }
        }else if(requestCode == 201 || requestCode == 202 || requestCode == 302){
            if (resultCode == 0) {
                refreshMedicineFragment = true;
            }
        }else if(requestCode == 301){
            if (resultCode == 0) {
                refreshHomeFragment = true;
            }
        }else if(requestCode == 404){
            if (resultCode == 0) {
                refreshICEFragment = true;
            }
        }
        else if(requestCode == 5){
            if (resultCode == 0) {
                refreshMeasurement = true;
            }
        }

    }

    public void setActionBarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        PreferenceManager prefManager = new
                PreferenceManager(getApplicationContext());

        if (null != prefManager.getSplashScreenPref() &&
                prefManager.getSplashScreenPref().equals("true")) {
            menu.findItem(R.id.settings_togglehelp).setChecked(true);
        } else {
            menu.findItem(R.id.settings_togglehelp).setChecked(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        PreferenceManager prefManager = new
                PreferenceManager(getApplicationContext());

        if (id == R.id.settings_togglehelp) {

            if (null != prefManager.getSplashScreenPref() &&
                    prefManager.getSplashScreenPref().equals("true")) {
                item.setChecked(false);
                prefManager.setSplashScreenPref("false");
            } else {
                item.setChecked(true);
                prefManager.setSplashScreenPref("true");
            }
        }

        return super.onOptionsItemSelected(item);
    }

}