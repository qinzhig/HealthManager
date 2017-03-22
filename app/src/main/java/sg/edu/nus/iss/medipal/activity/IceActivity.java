package sg.edu.nus.iss.medipal.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.IceManager;

public class IceActivity extends AppCompatActivity implements View.OnClickListener  {

    private EditText _nameEdit;
    private EditText _contactNoEdit;
    private Spinner _contactTypeSpinner;
    private EditText _descriptionEdit;

    private String _nameStr;
    private String _contactNoStr;
    private Integer _contactType;
    private String _descriptionStr;

    private static String[] CONTACT_TYPE = {"NOK", "GP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ice);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.ice_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        */

        _nameEdit = (EditText) findViewById(R.id.icename_edit);
        _contactNoEdit = (EditText) findViewById(R.id.icecontactnumber_edit);
        _contactTypeSpinner = (Spinner) findViewById(R.id.icecontacttype_spinner);
        _descriptionEdit = (EditText) findViewById(R.id.icedescription_edit);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CONTACT_TYPE);
        _contactTypeSpinner.setAdapter(spinnerAdapter);

        Bundle intentExtras = getIntent().getExtras();
        boolean isEdit = intentExtras.getBoolean("isEdit");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_close) {
            finish();
        } else if (id == R.id.action_done) {
            _nameStr = _nameEdit.getText().toString();
            _contactNoStr = _contactNoEdit.getText().toString();
            _contactType = _contactTypeSpinner.getSelectedItemPosition();
            _descriptionStr = _descriptionEdit.getText().toString();

            if (validateIce(_nameStr, _contactNoStr, _descriptionStr)) {
                addIce(_nameStr, _contactNoStr, _contactType, _descriptionStr);

                final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Saving...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                                              @Override
                                              public void run() {
                                                  progressDialog.dismiss();
                                                  finish();
                                                  Toast.makeText(IceActivity.this, "Success", Toast.LENGTH_LONG).show();
                                              }
                                          },
                        1000);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void addIce(String name, String contactNo, Integer contactType, String description) {
        IceManager iceManager = new IceManager();
        iceManager.addIce(name, contactNo, contactType, description, this);
    }

    private boolean validateIce(String name, String contactNo, String description) {
        boolean valid = true;

        if (name.isEmpty()) {
            _nameEdit.setError("Please enter a name");
            valid = false;
        } else {
            _nameEdit.setError(null);
        }

        if (contactNo.isEmpty()) {
            _contactNoEdit.setError("Please enter a contact number");
            valid = false;
        } else {
            _contactNoEdit.setError(null);
        }

        if (description.isEmpty()) {
            _descriptionEdit.setError("Please enter notes about the contact");
            valid = false;
        } else {
            _descriptionEdit.setError(null);
        }

        return valid;
    }
}
