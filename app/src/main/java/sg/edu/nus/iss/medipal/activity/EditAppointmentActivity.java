package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.AppointmentManager;

/**
 * Created by : Navi on 16-03-2017.
 * Description : This is the main view for editing Appointment
 * Modified by :
 * Reason for modification :
 */

public class EditAppointmentActivity extends AppCompatActivity  implements View.OnClickListener{

    private EditText appointmentTitle,
                     appointmentLocation,
                     appointmentdate,
                     appointmentTime,
                     appointmentDesc;
    private TextView toolbarTitle;

    private Spinner appointmentRemainder;

    private int day,month,year,hour,minute;

    static String[] SPINNERLIST = {"No Remainder",
                                   "15 Minutes Before",
                                   "30 Minutes Before",
                                   "1 Hour Before",
                                   "4 Hours Before",
                                   "12 Hours Before",
                                   "1 Day Before"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_add);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.tb_addapp);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        appointmentTitle = (EditText)findViewById(R.id.title);
        appointmentLocation = (EditText)findViewById(R.id.location);
        appointmentdate = (EditText)findViewById(R.id.date);
        appointmentTime = (EditText)findViewById(R.id.time);
        appointmentDesc = (EditText)findViewById(R.id.description);
        appointmentRemainder = (Spinner) findViewById(R.id.remainder);
        toolbarTitle = (TextView) findViewById(R.id.tb_app_title);

        toolbarTitle.setText("Edit Appointment");
        populateRemainderSpinner();

        appointmentdate.setOnClickListener(this);
        appointmentTime.setOnClickListener(this);

    }

    private void populateRemainderSpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,SPINNERLIST);
        appointmentRemainder.setAdapter(spinnerAdapter);

    }

    @Override
    public void onClick(View v) {
        final Calendar calender;

        if (v == appointmentdate) {
            calender = Calendar.getInstance();
            day = calender.get(Calendar.DAY_OF_MONTH);
            month = calender.get(Calendar.MONTH);
            year = calender.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    appointmentdate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            }, day, month, year);
            datePicker.updateDate(year, month, day);
            datePicker.show();
        }
        else if(v == appointmentTime)
        {
            calender = Calendar.getInstance();
            hour = calender.get(Calendar.HOUR_OF_DAY);
            minute = calender.get(Calendar.MINUTE);

            TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    int hour = hourOfDay % 12;
                    appointmentTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                            minute, hourOfDay < 12 ? "AM" : "PM"));
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
            saveAppointmentDetails();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveAppointmentDetails() {
        String title = appointmentTitle.getText().toString();
        String location = appointmentLocation.getText().toString();
        String date = appointmentdate.getText().toString();
        String time = appointmentTime.getText().toString();
        String description = appointmentDesc.getText().toString();
        String remainderTime = appointmentRemainder.getSelectedItem().toString();

        if(validate(title,location,date,time,description,remainderTime))
        {
            final ProgressDialog progressDialog = new ProgressDialog(this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Saving...");
            progressDialog.show();

            String datetime = date+ " " +time;
            AppointmentManager appointmentManager = new AppointmentManager(title,location,datetime,description,remainderTime,this);
            if(appointmentManager.addAppointment())
            {
                new Handler().postDelayed(new Runnable() {
                                              @Override
                                              public void run() {
                                                  progressDialog.dismiss();
                                                  finish();
                                                  Toast.makeText(EditAppointmentActivity.this,"Success",Toast.LENGTH_LONG).show();
                                              }
                                          },
                        1000);
            }
            else{
                Toast.makeText(this,"something went wrong :-(",Toast.LENGTH_LONG).show();
            }
        }
    }


    private boolean validate(String title, String location, String date, String time, String desc, String remainder) {
        boolean valid = true;

        if (title.isEmpty()) {
            appointmentTitle.setError("Please enter a title for appointment");
            valid = false;
        } else {
            appointmentTitle.setError(null);
        }

        if (location.isEmpty()) {
            appointmentLocation.setError("Please enter the location for appointment");
            valid = false;
        } else {
            appointmentLocation.setError(null);
        }

        if (date.isEmpty()) {
            appointmentdate.setError("Please select a date");
            valid = false;
        } else {
            appointmentdate.setError(null);
        }

        if (time.isEmpty()) {
            appointmentTime.setError("Please select a time");
            valid = false;
        } else {
            appointmentTime.setError(null);
        }

        if (desc.isEmpty()) {
            appointmentDesc.setError("Please enter notes about the appointements");
            valid = false;
        } else {
            appointmentDesc.setError(null);
        }

        return valid;
    }
}
