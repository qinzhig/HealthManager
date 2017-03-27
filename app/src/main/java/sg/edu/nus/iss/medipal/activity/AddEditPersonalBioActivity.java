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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.PersonalBioManager;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.PersonalBio;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by Divahar on 3/13/2017.
 * Description: This class handles the addition of personal bio at startup and edit personal bio
 */

public class AddEditPersonalBioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name, dob, idNo, address, postalCode, height;
    private Spinner bloodGrp;
    private PersonalBioManager personalBioManager;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    Calendar selectedDate = Calendar.getInstance();
    Calendar currentCal = Calendar.getInstance();
    private String nameStr, idNoStr, addressStr, postalCodeStr, heightStr, bloodTypeStr;
    private Date dobDt;
    private static String[] BLOOD_GRP = {"O+ve", "O-ve", "A+ve", "A-ve", "B+ve", "B-ve", "AB+ve", "AB-ve"};
    boolean isFirstTime = false;

    private TextInputLayout textInputLayoutName, textInputLayoutDob, textInputLayoutIdno, textInputLayoutAddress, textInputLayoutPostal, textInputLayoutHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_personalbio);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_editpb);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        name = (EditText) findViewById(R.id.nameEdit);
        dob = (EditText) findViewById(R.id.dobEdit);
        idNo = (EditText) findViewById(R.id.idNoEdit);
        address = (EditText) findViewById(R.id.addressEdit);
        postalCode = (EditText) findViewById(R.id.postalEdit);
        height = (EditText) findViewById(R.id.heightEdit);
        bloodGrp = (Spinner) findViewById(R.id.bloodGrpSpinner);

        //get reference to view element layouts
        textInputLayoutName = (TextInputLayout) findViewById(R.id.nameView);
        textInputLayoutDob = (TextInputLayout) findViewById(R.id.dobView);
        textInputLayoutIdno = (TextInputLayout) findViewById(R.id.idNoView);
        textInputLayoutAddress = (TextInputLayout) findViewById(R.id.addressView);
        textInputLayoutPostal = (TextInputLayout) findViewById(R.id.postalView);
        textInputLayoutHeight = (TextInputLayout) findViewById(R.id.heightView);

        //listener is added to clear error when input is given
        clearErrorOnTextInput();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.
                R.layout.simple_dropdown_item_1line, BLOOD_GRP);
        bloodGrp.setAdapter(spinnerAdapter);

        Bundle bundleVal = getIntent().getExtras();
        if (bundleVal.containsKey("firstTime")) {
            isFirstTime = bundleVal.getBoolean("firstTime");
        }

        PersonalBio personalBio =
                new PersonalBioManager().getpersonalBio(this);

        if (null != personalBio) {
            name.setText(personalBio.getName());
            name.setTag(personalBio.getId());
            dob.setText(MediPalUtility.
                    convertDateToString(personalBio.getDob(), "dd MMM yyyy"));
            idNo.setText(personalBio.getIdNo());
            address.setText(personalBio.getAddress());
            postalCode.setText(String.valueOf(personalBio.getPostalCode()));
            height.setText(String.valueOf(personalBio.getHeight()));
            bloodGrp.setSelection(Arrays.asList(bloodGrp).indexOf(personalBio.getBloodType().toString()));
        }

        dob.setOnClickListener(this);
    }

    private void clearErrorOnTextInput() {

        name.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    textInputLayoutName.setError(null);
            }
        });

        dob.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    textInputLayoutDob.setError(null);
            }
        });
        idNo.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    textInputLayoutIdno.setError(null);
            }
        });
        address.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    textInputLayoutAddress.setError(null);
            }
        });

        postalCode.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    textInputLayoutPostal.setError(null);
            }
        });

        height.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    textInputLayoutHeight.setError(null);
            }
        });
    }

    @Override
    public void onClick(View view) {

        if (view == dob) {
            DatePickerDialog.OnDateSetListener onDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            selectedDate = calendar;
                            dob.setText(dateFormatter.format(calendar.getTime()));
                        }
                    };
            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(AddEditPersonalBioActivity.this, onDateSetListener,
                            currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                            currentCal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action_items, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_close);
        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFirstTime) {
                    new AlertDialog.Builder(view.getContext())
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
                    finish();
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_close) {
            finish();
        } else if (id == R.id.action_done) {

            boolean isAdded = actionDone();

            if (isFirstTime && isAdded) {
                Intent addEditHealthBio = new Intent(getApplicationContext(), AddEditHealthBioActivity.class);
                addEditHealthBio.putExtra("isEdit", false);
                addEditHealthBio.putExtra("isFirstTime", true);
                startActivity(addEditHealthBio);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean actionDone() {

        nameStr = name.getText().toString();
        dobDt = MediPalUtility
                .convertStringToDate(dob.getText().toString(), "dd MMM yyyy");
        idNoStr = idNo.getText().toString();
        addressStr = address.getText().toString();
        postalCodeStr = postalCode.getText().toString();
        heightStr = height.getText().toString();
        bloodTypeStr = bloodGrp.getSelectedItem().toString();

        if (validatePersonalBio(nameStr, dob.getText().toString(), idNoStr, addressStr,
                postalCodeStr, heightStr)) {
            if (null != name.getTag()) {
                updatePersonalBio();
            } else {
                addPersonalBio();
                if (isFirstTime) {
                    PreferenceManager prefManager = new
                            PreferenceManager(getApplicationContext());
                    prefManager.setFirstTimeFlag("false");
                    prefManager.setSplashScreenPref("false");
                }

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
                                          }
                                      },
                    1000);

            return true;

        } else {
            return false;
        }

    }

    private void addPersonalBio() {

        personalBioManager = new PersonalBioManager();
        personalBioManager.addpersonalBio(nameStr, dobDt, idNoStr,
                addressStr, postalCodeStr, heightStr, bloodTypeStr, this);

    }

    private void updatePersonalBio() {

        personalBioManager = new PersonalBioManager();
        personalBioManager.updatepersonalBio(Integer.valueOf(name.getTag().toString()), nameStr, dobDt, idNoStr,
                addressStr, postalCodeStr, heightStr, bloodTypeStr, this);

    }

    private boolean validatePersonalBio(String nameStr, String dobDt, String idNoStr,
                                        String addressStr, String postalCodeStr, String heightStr) {
        boolean isValid = true;

        if (nameStr.isEmpty()) {
            textInputLayoutName.setError("Please enter Name");
            isValid = false;
        } else {
            textInputLayoutName.setError(null);
        }

        if (dobDt.isEmpty()) {
            textInputLayoutDob.setError("Please select Date of Birth");
            isValid = false;
        } else if (!MediPalUtility.isNotFutureDate(dobDt)) {
            textInputLayoutDob.setError("Date of Birth cannot be in future");
            isValid = false;
        } else {
            textInputLayoutDob.setError(null);
        }

        if (idNoStr.isEmpty()) {
            textInputLayoutIdno.setError("Please enter ID Number");
            isValid = false;
        } else {
            textInputLayoutIdno.setError(null);
        }

        if (addressStr.isEmpty()) {
            textInputLayoutAddress.setError("Please enter Address");
            isValid = false;
        } else {
            textInputLayoutAddress.setError(null);
        }

        if (postalCodeStr.isEmpty()) {
            textInputLayoutPostal.setError("Please enter Postal Code");
            isValid = false;
        } else {
            textInputLayoutPostal.setError(null);
        }

        if (heightStr.isEmpty()) {
            textInputLayoutHeight.setError("Please enter Height");
            isValid = false;
        } else {
            textInputLayoutHeight.setError(null);
        }

        return isValid;
    }
}
