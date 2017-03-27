package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Calendar;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.AppointmentManager;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;


/**
 * Created by : Navi on 04-03-2017.
 * Description : This is the main view for adding Appointment
 * Modified by :
 * Reason for modification :
 */

public class AddEditAppointmentActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText appointmentTitle,
                     appointmentLocation,
                     appointmentDate,
                     appointmentTime,
                     appointmentDesc;

    private TextInputLayout textInputLayoutTitle,textInputLayoutLocation,textInputLayoutDate,textInputLayoutTime,textInputLayoutDesc;


    private Spinner appointmentRemainderOne;
    private Spinner appointmentRemainderTwo;

    private Switch switchOne;
    private Switch switchTwo;

    private boolean isEdit;
    private String id, remainderOne,remainderTwo;

    private Boolean switchOneValue, switchTwoValue;

    static String[] SPINNERLISTTWO = {"15 Minutes Before",
                                   "30 Minutes Before",
                                   "1 Hour Before",
                                   "4 Hours Before",
                                   "12 Hours Before",
                                   "1 Day Before"};

    static String[] SPINNERLISTONE = {"12 Hours Before",
            "1 Day Before",
            "2 Day Before",
            "4 Day Before",
            "1 Week Before"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView toolbarTitle;
       String title, location, date, time, desc;

        //same layout is used for both add and edit
        setContentView(R.layout.appointment_add);

        //Get the info from parent aboout which view to show: add or edit
        Bundle intentExtras = getIntent().getExtras();
        isEdit = intentExtras.getBoolean("isEdit");

        //get reference to toolbar so as to set appropriate title
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_addapp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        //get reference to view element layouts
        textInputLayoutTitle = (TextInputLayout) findViewById(R.id.edit_text_title);
        textInputLayoutLocation = (TextInputLayout) findViewById(R.id.edit_text_location);
        textInputLayoutDate = (TextInputLayout) findViewById(R.id.edit_text_date);
        textInputLayoutTime = (TextInputLayout) findViewById(R.id.edit_text_time);
        textInputLayoutDesc = (TextInputLayout) findViewById(R.id.edit_text_desc);

        //get reference to view elements
        appointmentTitle = (EditText)findViewById(R.id.title);
        appointmentLocation = (EditText)findViewById(R.id.location);
        appointmentDate = (EditText)findViewById(R.id.date);
        appointmentTime = (EditText)findViewById(R.id.time);
        appointmentDesc = (EditText)findViewById(R.id.description);
        switchOne = (Switch)findViewById(R.id.remainder_switch1);
        switchTwo = (Switch)findViewById(R.id.remainder_switch2);
        appointmentRemainderOne = (Spinner) findViewById(R.id.remainder_1);
        appointmentRemainderTwo = (Spinner) findViewById(R.id.remainder_2);

        //listener is added to clear error when input is given
        clearErrorOnTextInput();

        //populate the down down values for two spinners
        populateRemainderSpinner();

        //if edit view is to be shown then get data sent from parent through intent and populate view elements
        if(isEdit)
        {
            id = intentExtras.getString("Id");
            title = intentExtras.getString("title");
            location = intentExtras.getString("location");
            date = intentExtras.getString("date");
            time = intentExtras.getString("time");
            desc = intentExtras.getString("desc");
            remainderOne = intentExtras.getString("remainderOne");
            remainderTwo = intentExtras.getString("remainderTwo");

            Log.d("rem 1 and 2",remainderOne+remainderTwo);

            if(!remainderOne.equalsIgnoreCase("No Pre-Test Remainders set.")) {
                switchOne.setChecked(true);
                switchOneValue = true;
                appointmentRemainderOne.setEnabled(true);
                int pos = Arrays.asList(SPINNERLISTONE).indexOf(remainderOne);
                appointmentRemainderOne.setSelection(pos);
            }
            if(!remainderTwo.equalsIgnoreCase("No Appointment Remainders set.")) {
                switchTwo.setChecked(true);
                switchTwoValue = true;
                appointmentRemainderTwo.setEnabled(true);
                int pos = Arrays.asList(SPINNERLISTTWO).indexOf(remainderTwo);
                appointmentRemainderTwo.setSelection(pos);
            }

            toolbarTitle = (TextView) findViewById(R.id.tb_app_title);
            toolbarTitle.setText(R.string.edit_appointment_title);

            appointmentTitle.setText(title);
            appointmentLocation.setText(location);
            appointmentDate.setText(date);
            appointmentTime.setText(time);
            appointmentDesc.setText(desc);
        }

        //listeners for date and time pickers
        appointmentDate.setOnClickListener(this);
        appointmentTime.setOnClickListener(this);

        switchOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchOneValue = isChecked;
                appointmentRemainderOne.setEnabled(isChecked);
            }
        });

        switchTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchTwoValue = isChecked;
                appointmentRemainderTwo.setEnabled(isChecked);
            }
        });
    }

    private void clearErrorOnTextInput() {

        appointmentTitle.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    textInputLayoutTitle.setError(null);
            }
        });

        appointmentLocation.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    textInputLayoutLocation.setError(null);
            }
        });
        appointmentDate.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    textInputLayoutDate.setError(null);
            }
        });
        appointmentTime.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    textInputLayoutTime.setError(null);
            }
        });

        appointmentDesc.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    textInputLayoutDesc.setError(null);
            }
        });
    }

    //used to populate the remainder spinner
    private void populateRemainderSpinner() {
        ArrayAdapter<String> spinnerAdapterOne = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,SPINNERLISTONE);
        ArrayAdapter<String> spinnerAdapterTwo = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,SPINNERLISTTWO);
        appointmentRemainderOne.setAdapter(spinnerAdapterOne);
        appointmentRemainderTwo.setAdapter(spinnerAdapterTwo);
        switchOneValue = false;
        switchTwoValue = false;
        appointmentRemainderOne.setEnabled(false);
        appointmentRemainderTwo.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        final Calendar calender;
        int day,month,year,hour,minute;
        if (v == appointmentDate) {
            appointmentDate.setError(null);
            calender = Calendar.getInstance();
            day = calender.get(Calendar.DAY_OF_MONTH);
            month = calender.get(Calendar.MONTH);
            year = calender.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    appointmentDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            }, day, month, year);
            datePicker.updateDate(year, month, day);
            datePicker.show();
        }
        else if(v == appointmentTime)
        {
            appointmentTime.setError(null);
            calender = Calendar.getInstance();
            hour = calender.get(Calendar.HOUR_OF_DAY);
            minute = calender.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    int hour = hourOfDay % 12;
                    String period;

                    if(hour == 0)
                        hour = 12;
                    if(hourOfDay < 12)
                        period = "AM";
                    else
                        period = "PM";

                    appointmentTime.setText(String.format("%02d:%02d %s",hour, minute, period));
                }
            }, hour,minute,false);
            timePicker.updateTime(hour,minute);
            timePicker.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.toolbar_action_items,menu);

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

        if(id == R.id.action_close)
        {
            finish();
        }
        else if (id == R.id.action_done)
        {
            //this to bring down the keyboard when action is done. so that dialog will not be messed by the keyboard
            View view = this.getCurrentFocus();
            if(view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            saveAppointmentDetails();
        }

        return super.onOptionsItemSelected(item);
    }

    //used to save the entered appointment details
    private void saveAppointmentDetails() {
        //get entered values
        String title = appointmentTitle.getText().toString();
        String location = appointmentLocation.getText().toString();
        String date = appointmentDate.getText().toString();
        String time = appointmentTime.getText().toString();
        String description = appointmentDesc.getText().toString();
        String[] remainderTime = {"No Pre-Test Remainders set.","No Appointment Remainders set."};

        if(switchOneValue)
            remainderTime[0] = appointmentRemainderOne.getSelectedItem().toString();

        if(switchTwoValue)
            remainderTime[1] = appointmentRemainderTwo.getSelectedItem().toString();

        Log.d("rem-1 and-2",remainderTime[0]+remainderTime[1]);
        //apply input validations
        if(validate(title,location,date,time,description))
        {
            String datetime = date+ " " +time;
            //setup appointment manager with input values
            AppointmentManager appointmentManager = new AppointmentManager(title,location,datetime,description,remainderTime,this);

            //saveAppointment for updating / addAppointment for adding
            if(isEdit)
                appointmentManager.saveAppointment(id);
            else
                appointmentManager.addAppointment();
        }
    }

    //input validations are done here
    private boolean validate(String title, String location, String date, String time, String desc) {
        boolean valid = true;

        if (title.isEmpty()) {

            textInputLayoutTitle.setError("Please enter a title for appointment");
            valid = false;
        } else {
            textInputLayoutTitle.setError(null);
        }

        if (location.isEmpty()) {
            textInputLayoutLocation.setError("Please enter the location for appointment");
            valid = false;
        } else {
            textInputLayoutLocation.setError(null);
        }

        if (date.isEmpty()) {
            textInputLayoutDate.setError("Please select a date");
            valid = false;
        } else if(!MediPalUtility.isValidDate(date)) {  //to see if date selected is less than current date
            textInputLayoutDate.setError("Please select a future date");
            valid = false;
        }
        else{
            textInputLayoutDate.setError(null);
        }

        if (time.isEmpty()) {
            textInputLayoutTime.setError("Please select a time");
            valid = false;
        }
        else if(!MediPalUtility.isValidTime(date,time)){ //to see if time selected is less than current time
            textInputLayoutTime.setError("Please select a future date");
            valid = false;
        }
        else {
            textInputLayoutTime.setError(null);
        }

        if (desc.isEmpty()) {
            textInputLayoutDesc.setError("Please enter notes about the appointment");
            valid = false;
        } else {
            textInputLayoutDesc.setError(null);
        }

        return valid;
    }


}
