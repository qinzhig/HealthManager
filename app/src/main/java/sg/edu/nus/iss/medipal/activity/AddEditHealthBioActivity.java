package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.HealthBioManager;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;


/**
 * Created by Divahar on 3/15/2017.
 */

public class AddEditHealthBioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText condition, startDate;
    private Spinner conditionType;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    Calendar selectedDate = Calendar.getInstance();
    Calendar currentCal = Calendar.getInstance();

    static String[] CONDITION_TYPE = {"Condition", "Allergy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_healthbio);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_addhb);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        condition = (EditText) findViewById(R.id.conditionEdit);
        startDate = (EditText) findViewById(R.id.startDateEdit);
        conditionType = (Spinner) findViewById(R.id.conditionTypeSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.
                R.layout.simple_dropdown_item_1line, CONDITION_TYPE);
        conditionType.setAdapter(spinnerAdapter);

        Bundle bundleVal = getIntent().getExtras();
        boolean isEdit = bundleVal.getBoolean("isEdit");

        if (isEdit) {
            condition.setText(bundleVal.get("condition").toString());
            condition.setTag(bundleVal.get("id").toString());
            startDate.setText(bundleVal.get("startDate").toString());
            if (bundleVal.get("conditionType").toString().
                    equalsIgnoreCase("Condition")) {
                conditionType.setSelection(0);
            } else {
                conditionType.setSelection(1);
            }
        }
        startDate.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action_items, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_close);
        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        return true;
    }

    @Override
    public void onClick(View view) {

        if (view == startDate) {
            DatePickerDialog.OnDateSetListener onDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            selectedDate = calendar;
                            startDate.setText(dateFormatter.format(calendar.getTime()));
                        }
                    };
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(AddEditHealthBioActivity.this, onDateSetListener,
                            currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                            currentCal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_close) {
            finish();
        } else if (id == R.id.action_done) {

            if (null != condition.getTag()) {
                updateHealthBio();
            } else {
                addHealthBio();
            }

            final ProgressDialog progressDialog = new ProgressDialog(this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Saving...");
            progressDialog.show();

            new Handler().postDelayed(new Runnable() {
                                          @Override
                                          public void run() {
                                              progressDialog.dismiss();
                                              finish();
                                              Toast.makeText(AddEditHealthBioActivity.this, "Success", Toast.LENGTH_LONG).show();
                                          }
                                      },
                    1000);

        }

        return super.onOptionsItemSelected(item);
    }

    private void addHealthBio() {

        String conditionStr = condition.getText().toString();
        Date startDt = MediPalUtility
                .covertStringToDate(startDate.getText().toString());
        String conditionTypeStr = conditionType.getSelectedItem().toString();

        HealthBioManager healthBioManager
                = new HealthBioManager();
        healthBioManager.addHealthBio(conditionStr
                , startDt, conditionTypeStr.charAt(0), this);
    }

    private void updateHealthBio() {

        String conditionStr = condition.getText().toString();
        Date startDt = MediPalUtility
                .covertStringToDate(startDate.getText().toString());
        String conditionTypeStr = conditionType.getSelectedItem().toString();

        HealthBioManager healthBioManager
                = new HealthBioManager();
        healthBioManager.updateHealthBio(condition.getTag().toString(), conditionStr
                , startDt, conditionTypeStr.charAt(0), this);
    }
}
