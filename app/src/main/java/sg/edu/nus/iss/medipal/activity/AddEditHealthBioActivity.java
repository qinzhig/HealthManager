package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

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
    private TextInputLayout textInputLayoutCondition, textInputLayoutDate;
    private Spinner conditionType;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    Calendar selectedDate = Calendar.getInstance();
    Calendar currentCal = Calendar.getInstance();
    private String conditionStr, conditionTypeStr;
    private Date startDt;
    private static String[] CONDITION_TYPE = {"Illness", "Allergy"};
    private boolean isFirst = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_healthbio);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_addhb);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        condition = (EditText) findViewById(R.id.conditionEdit);
        startDate = (EditText) findViewById(R.id.startDateEdit);

        //get reference to view element layouts
        textInputLayoutCondition = (TextInputLayout) findViewById(R.id.conditionView);
        textInputLayoutDate = (TextInputLayout) findViewById(R.id.startDateView);

        conditionType = (Spinner) findViewById(R.id.conditionTypeSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.
                R.layout.simple_dropdown_item_1line, CONDITION_TYPE);
        conditionType.setAdapter(spinnerAdapter);
        //listener is added to clear error when input is given
        clearErrorOnTextInput();
        Bundle bundleVal = getIntent().getExtras();
        boolean isEdit = bundleVal.getBoolean("isEdit");
        isFirst = bundleVal.getBoolean("isFirstTime");

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

    private void clearErrorOnTextInput() {

        condition.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    textInputLayoutCondition.setError(null);
            }
        });

        startDate.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    textInputLayoutDate.setError(null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action_items, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_close);
        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirst) {
                    Intent mainActivity = new Intent(AddEditHealthBioActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
                else{
                    finish();
                }
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

            boolean isAdded = actionDone();

            if (isAdded) {
                new AlertDialog.Builder(this)
                        .setMessage("Do you want to add more Health Bio?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent addEditHealthBio = new Intent(AddEditHealthBioActivity.this, AddEditHealthBioActivity.class);
                                addEditHealthBio.putExtra("isEdit", false);
                                if (isFirst) {
                                    addEditHealthBio.putExtra("isFirstTime", true);
                                } else {
                                    addEditHealthBio.putExtra("isFirstTime", false);
                                }
                                startActivity(addEditHealthBio);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(AddEditHealthBioActivity.this, MainActivity.class));
                                finish();
                            }
                        })
                        .show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean actionDone() {
        conditionStr = condition.getText().toString();
        startDt = MediPalUtility
                .convertStringToDate(startDate.getText().toString(), "dd MMM yyyy");
        conditionTypeStr = conditionType.getSelectedItem().toString();

        if (validateHealthBio(conditionStr, startDate.getText().toString())) {
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
                                          }
                                      },
                    1000);

            return true;

        } else {
            return false;
        }
    }

    private void addHealthBio() {

        HealthBioManager healthBioManager
                = new HealthBioManager();
        healthBioManager.addHealthBio(conditionStr
                , startDt, conditionTypeStr.charAt(0), this);
    }

    private void updateHealthBio() {

        HealthBioManager healthBioManager
                = new HealthBioManager();
        healthBioManager.updateHealthBio(condition.getTag().toString(), conditionStr
                , startDt, conditionTypeStr.charAt(0), this);
    }

    private boolean validateHealthBio(String conditionInp, String startDateInp) {
        boolean isValid = true;

        if (conditionInp.isEmpty()) {
            textInputLayoutCondition.setError("Please enter Condition title");
            isValid = false;
        } else {
            textInputLayoutCondition.setError(null);
        }

        if (startDateInp.isEmpty()) {
            textInputLayoutDate.setError("Please select Start Date of the Condition");
            isValid = false;
        } else if (!MediPalUtility.isNotFutureDate(startDateInp)) {
            textInputLayoutDate.setError("Start Date cannot be in future");
            isValid = false;
        } else {
            textInputLayoutDate.setError(null);
        }

        return isValid;
    }

}
