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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.MeasurementManager;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by levis on 3/23/2017.
 */

public class MeasurementActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText _systolicEdit;
    private EditText _diastolicEdit;
    private EditText _pulseEdit;
    private EditText _temperatureEdit;
    private EditText _weightEdit;
    private EditText _dateEdit;
    private EditText _timeEdit;

    private Integer _id = null;
    private String _systolicStr;
    private String _diastolicStr;
    private String _pulseStr;
    private String _temperatureStr;
    private String _weightStr;
    private String _dateStr;
    private String _timeStr;
    private String _measuredOnStr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_measurement);

        Toolbar toolbar = (Toolbar) findViewById(R.id.measurement_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        _systolicEdit = (EditText) findViewById(R.id.measurementsystolic_edit);
        _diastolicEdit = (EditText) findViewById(R.id.measurementdiastolic_edit);
        _pulseEdit = (EditText) findViewById(R.id.measurementpulse_edit);
        _temperatureEdit = (EditText) findViewById(R.id.measurementtemperature_edit);
        _weightEdit = (EditText) findViewById(R.id.measurementweight_edit);
        _dateEdit = (EditText) findViewById(R.id.measurement_edit_date);
        _timeEdit = (EditText) findViewById(R.id.measurement_edit_time);

        _dateEdit.setOnClickListener(this);
        _timeEdit.setOnClickListener(this);
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
    public void onClick(View v) {
        final Calendar calender;
        int day,month,year,hour,minute;
        if (v == _dateEdit) {
            _dateEdit.setError(null);
            calender = Calendar.getInstance();
            day = calender.get(Calendar.DAY_OF_MONTH);
            month = calender.get(Calendar.MONTH);
            year = calender.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    _dateEdit.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            }, day, month, year);
            datePicker.updateDate(year, month, day);
            datePicker.show();
        }
        else if(v == _timeEdit)
        {
            _timeEdit.setError(null);
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

                    _timeEdit.setText(String.format("%02d:%02d %s",hour, minute, period));
                }
            }, hour,minute,false);
            timePicker.updateTime(hour,minute);
            timePicker.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_close) {
            finish();
        } else if (id == R.id.action_done) {
            _systolicStr = _systolicEdit.getText().toString();
            _diastolicStr = _diastolicEdit.getText().toString();
            _pulseStr = _pulseEdit.getText().toString();
            _temperatureStr = _temperatureEdit.getText().toString();
            _weightStr = _weightEdit.getText().toString();
            _dateStr = _dateEdit.getText().toString();
            _timeStr = _timeEdit.getText().toString();

            if (validateMeasurement(_systolicStr, _diastolicStr, _pulseStr, _temperatureStr, _weightStr, _dateStr, _timeStr)) {
                _measuredOnStr = _dateStr + " " + _timeStr;

                addMeasurement(Integer.parseInt(_systolicStr), Integer.parseInt(_diastolicStr),
                        Integer.parseInt(_pulseStr), Integer.parseInt(_temperatureStr),
                        Integer.parseInt(_weightStr), _measuredOnStr);

                final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Saving...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                                              @Override
                                              public void run() {
                                                  progressDialog.dismiss();
                                                  finish();
                                                  Toast.makeText(MeasurementActivity.this, "Success", Toast.LENGTH_LONG).show();
                                              }
                                          },
                        500);

            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void addMeasurement(Integer systolic, Integer diastolic, Integer pulse, Integer temperature, Integer weight, String measuredOn) {
        MeasurementManager measurementManager = new MeasurementManager();
        measurementManager.addMeasurement(systolic, diastolic, pulse, temperature, weight, measuredOn, this);
    }

    private boolean validateMeasurement(String systolic, String diastolic, String pulse, String temperature, String weight, String date, String time) {
        boolean valid = true;

        if(date.isEmpty()) {
            _dateEdit.setError("Please enter a date.");
            valid = false;
        } else {
            _dateEdit.setError(null);
        }

        if(time.isEmpty()) {
            _timeEdit.setError("Please enter a date.");
            valid = false;
        } else {
            _timeEdit.setError(null);
        }
        /*
        String measuredOn;
        measuredOn = date + " " + time;
        else if (!MediPalUtility.isNotFutureDate(startDateInp)) {
            textInputLayoutDate.setError("Start Date cannot be in future");
            isValid = false;
        }
        */
        if (systolic.isEmpty() && diastolic.isEmpty() && pulse.isEmpty() && temperature.isEmpty() && weight.isEmpty()) {
            _systolicEdit.setError("Please enter a systolic.");
            _diastolicEdit.setError("Please enter a diastolic.");
            _pulseEdit.setError("Please enter a pulse.");
            _temperatureEdit.setError("Please enter a temperature.");
            _weightEdit.setError("Please enter a weight.");

            valid = false;
        } else {
            _systolicEdit.setError(null);
            _diastolicEdit.setError(null);
            _pulseEdit.setError(null);
            _temperatureEdit.setError(null);
            _weightEdit.setError(null);
        }

        return valid;
    }
}
