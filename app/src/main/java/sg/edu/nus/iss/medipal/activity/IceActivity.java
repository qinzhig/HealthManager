package sg.edu.nus.iss.medipal.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.IceManager;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

public class IceActivity extends AppCompatActivity implements View.OnClickListener  {

    private EditText nameEdit;
    private EditText contactNoEdit;
    private Spinner contactTypeSpinner;
    private EditText descriptionEdit;

    private TextInputLayout l_Name,l_Contact,l_Desc;

    private Integer id = null;
    private String nameStr;
    private String contactNoStr;
    private Integer contactType;
    private String descriptionStr;
    private Integer priority = null;

    private static String[] CONTACT_TYPE = {"Emergency Numbers","Next Of Kin", "General Practitioner"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ice_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        nameEdit = (EditText) findViewById(R.id.icename_edit);
        contactNoEdit = (EditText) findViewById(R.id.icecontactnumber_edit);
        contactTypeSpinner = (Spinner) findViewById(R.id.icecontacttype_spinner);
        descriptionEdit = (EditText) findViewById(R.id.icedescription_edit);

        l_Name = (TextInputLayout) findViewById(R.id.icename_view);
        l_Contact = (TextInputLayout) findViewById(R.id.icecontactnumber_view);
        l_Desc = (TextInputLayout) findViewById(R.id.icedescription_view);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CONTACT_TYPE);
        contactTypeSpinner.setAdapter(spinnerAdapter);

        Bundle intentExtras = getIntent().getExtras();
        boolean isEdit = intentExtras.getBoolean("isEdit");

        //listener is added to clear error when input is given
        clearErrorOnTextInput();

        if (isEdit) {
            id = intentExtras.getInt("id");
            nameEdit.setText(intentExtras.get("name").toString());
            contactNoEdit.setText(intentExtras.get("contactNo").toString());

            if (intentExtras.getInt("contactType") == 0) {
                contactTypeSpinner.setSelection(0);
            } else if (intentExtras.getInt("contactType") == 1) {
                contactTypeSpinner.setSelection(1);
            }
            else{
                contactTypeSpinner.setSelection(2);
            }

            descriptionEdit.setText(intentExtras.get("description").toString());
            priority = intentExtras.getInt("priority");
        }
    }

    private void clearErrorOnTextInput() {

        nameEdit.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    l_Name.setError(null);
            }
        });

        contactNoEdit.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    l_Contact.setError(null);
            }
        });
        descriptionEdit.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    l_Desc.setError(null);
            }
        });

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
        int menuId = item.getItemId();

        if (menuId == R.id.action_close) {
            finish();
        } else if (menuId == R.id.action_done) {
            nameStr = nameEdit.getText().toString();
            contactNoStr = contactNoEdit.getText().toString();
            contactType = contactTypeSpinner.getSelectedItemPosition();
            descriptionStr = descriptionEdit.getText().toString();

            if (validateIce(nameStr, contactNoStr, descriptionStr)) {
                if(id != null) {
                    updateIce(id, nameStr, contactNoStr, contactType, descriptionStr, priority);
                } else {
                    addIce(nameStr, contactNoStr, contactType, descriptionStr);
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
            l_Name.setError("Please enter a name");
            valid = false;
        } else {
            l_Name.setError(null);
        }

        if (contactNo.isEmpty()) {
            l_Contact.setError("Please enter a contact number");
            valid = false;
        } else {
            l_Contact.setError(null);
        }

        if (description.isEmpty()) {
            l_Desc.setError("Please enter notes about the contact");
            valid = false;
        } else {
            l_Desc.setError(null);
        }

        return valid;
    }
}
