package sg.edu.nus.iss.medipal.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

    private Integer _id = null;
    private String _nameStr;
    private String _contactNoStr;
    private Integer _contactType;
    private String _descriptionStr;
    private Integer _priority = null;

    private static String[] CONTACT_TYPE = {"NOK", "GP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ice_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        _nameEdit = (EditText) findViewById(R.id.icename_edit);
        _contactNoEdit = (EditText) findViewById(R.id.icecontactnumber_edit);
        _contactTypeSpinner = (Spinner) findViewById(R.id.icecontacttype_spinner);
        _descriptionEdit = (EditText) findViewById(R.id.icedescription_edit);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CONTACT_TYPE);
        _contactTypeSpinner.setAdapter(spinnerAdapter);

        Bundle intentExtras = getIntent().getExtras();
        boolean isEdit = intentExtras.getBoolean("isEdit");

        if (isEdit) {
            _id = intentExtras.getInt("id");
            _nameEdit.setText(intentExtras.get("name").toString());
            _contactNoEdit.setText(intentExtras.get("contactNo").toString());

            if (intentExtras.getInt("contactType") == 0) {
                _contactTypeSpinner.setSelection(0);
            } else if (intentExtras.getInt("contactType") == 1) {
                _contactTypeSpinner.setSelection(1);
            }

            _descriptionEdit.setText(intentExtras.get("description").toString());
            _priority = intentExtras.getInt("priority");
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
                if(_id != null) {
                    updateIce(_id, _nameStr, _contactNoStr, _contactType, _descriptionStr, _priority);
                } else {
                    addIce(_nameStr, _contactNoStr, _contactType, _descriptionStr);
                }

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
                        500);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void addIce(String name, String contactNo, Integer contactType, String description) {
        IceManager iceManager = new IceManager();
        Integer priority = iceManager.getIces(this).size();
        iceManager.addIce(name, contactNo, contactType, description, priority, this);
    }

    private void updateIce(Integer id, String name, String contactNo, Integer contactType, String description, Integer priority) {
        IceManager iceManager = new IceManager();
        iceManager.updateIce(id , name, contactNo, contactType, description, priority, this);
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
