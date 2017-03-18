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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.PersonalBioManager;
import sg.edu.nus.iss.medipal.pojo.PersonalBio;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by Divahar on 3/13/2017.
 */

public class AddEditPersonalBioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name, dob, idNo, address, postalCode, height, bloodType;
    private PersonalBioManager personalBioManager;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    Calendar selectedDate = Calendar.getInstance();
    Calendar currentCal = Calendar.getInstance();

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
        bloodType = (EditText) findViewById(R.id.bloodTypeEdit);

        PersonalBio personalBio =
                new PersonalBioManager().getpersonalBio(this);

        if (null != personalBio) {
            name.setText(personalBio.getName());
            name.setTag(personalBio.getId());
            dob.setText(MediPalUtility.
                    covertDateToString(personalBio.getDob()));
            idNo.setText(personalBio.getIdNo());
            address.setText(personalBio.getAddress());
            postalCode.setText(String.valueOf(personalBio.getPostalCode()));
            height.setText(String.valueOf(personalBio.getHeight()));
            bloodType.setText(personalBio.getBloodType());
        }

        dob.setOnClickListener(this);
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
            public void onClick(View v) {
                finish();
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

            if (null != name.getTag()) {
                updatePersonalBio();
            } else {
                addPersonalBio();
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
                                              Toast.makeText(AddEditPersonalBioActivity.this, "Success", Toast.LENGTH_LONG).show();
                                          }
                                      },
                    1000);

        }

        return super.onOptionsItemSelected(item);
    }

    private void addPersonalBio() {

        String nameStr = name.getText().toString();
        Date dobDt = MediPalUtility
                .covertStringToDate(dob.getText().toString());
        String idNoStr = idNo.getText().toString();
        String addressStr = address.getText().toString();
        String postalCodeStr = postalCode.getText().toString();
        String heightStr = height.getText().toString();
        String bloodTypeStr = bloodType.getText().toString();

        personalBioManager = new PersonalBioManager();
        personalBioManager.addpersonalBio(nameStr, dobDt, idNoStr,
                addressStr, postalCodeStr, heightStr, bloodTypeStr, this);

    }

    private void updatePersonalBio() {

        String nameStr = name.getText().toString();
        int id = Integer.valueOf(name.getTag().toString());
        Date dobDt = MediPalUtility
                .covertStringToDate(dob.getText().toString());
        String idNoStr = idNo.getText().toString();
        String addressStr = address.getText().toString();
        String postalCodeStr = postalCode.getText().toString();
        String heightStr = height.getText().toString();
        String bloodTypeStr = bloodType.getText().toString();

        personalBioManager = new PersonalBioManager();
        personalBioManager.updatepersonalBio(id, nameStr, dobDt, idNoStr,
                addressStr, postalCodeStr, heightStr, bloodTypeStr, this);

    }
}
