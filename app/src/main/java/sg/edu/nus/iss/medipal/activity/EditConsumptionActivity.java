package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.pojo.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

public class EditConsumptionActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spnMedicine;
    private EditText etDate, etTime;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    private Spinner spinner;
    private TextView view_Medicine;
    private EditText view_Quantity;
    HealthManager healthmanager = new HealthManager();
    Medicine medicine;
    private boolean isEdit = true;

    private int quantity;
    private int medicine_id;
    private int id;
    private String date_time;



    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();

    //get Medicine and get Dosage
    List<String> medinceList = null;
    List<Integer> dosageList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String medicine_name;
        String date = null;
        String time = null;
        String MM = null;
        String time_MM = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consumption);

        etDate = (EditText)findViewById(R.id.et_select_date);
        etTime = (EditText)findViewById(R.id.et_select_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        view_Medicine = (TextView)findViewById(R.id.view_medicine);
        view_Quantity = (EditText) findViewById(R.id.Dosage);
        etDate.setOnClickListener(this);
        etTime.setOnClickListener(this);

        Bundle transfer = getIntent().getExtras();
        medicine_name = transfer.getString("medicine_name");
        Log.v("medicine_name is null or not","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+medicine_name);
        medicine_id = transfer.getInt("medicine_id");
        Log.v("medicineID is null or not","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+medicine_id);
        quantity = transfer.getInt("quantity");
        id = transfer.getInt("id");
        date_time = transfer.getString("date_time");
        Log.v("datetime is null or not","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+date_time);


        String[] datetime_picker_result = date_time.split(" ");
        date = datetime_picker_result[0] ;
        time =datetime_picker_result[1] ;
        MM = datetime_picker_result[2];
        time_MM = time +" " + MM;

        Log.v("datetime","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+datetime_picker_result[0]);
        Log.v("datetime","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+datetime_picker_result[1]);
        Log.v("datetime","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+datetime_picker_result[2]);
        Log.v("datetime","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+time_MM);



        Log.v("datetime","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+datetime_picker_result);
        Log.v("datetime","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+date);
        Log.v("datetime","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+time);
        Log.v("datetime","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+MM);


        Log.v("mateng's date_time","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+"+date_time);

        view_Medicine.setText(medicine_name);
        view_Quantity.setText(Integer.toString(quantity));
        etDate.setText(date);
        etTime.setText(time_MM);


    }

    @Override
    public void onClick(View v) {
        final Calendar calender;
        int day,month,year,hour,minute;
        if (v == etDate) {
            etDate.setError(null);
            calender = Calendar.getInstance();
            day = calender.get(Calendar.DAY_OF_MONTH);
            month = calender.get(Calendar.MONTH);
            year = calender.get(Calendar.YEAR);

            DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    etDate.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            }, day, month, year);
            datePicker.updateDate(year, month, day);
            datePicker.show();
        }
        else if(v == etTime)
        {
            etTime.setError(null);
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

                    etTime.setText(String.format("%02d:%02d %s",hour, minute, period));
                }
            }, hour,minute,false);
            timePicker.updateTime(hour,minute);
            timePicker.show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action_items, menu);
        MenuItem menuItem;
        menuItem = menu.findItem(R.id.action_close);
        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_close) {
            finish();

        }
        if (id == R.id.action_done) {
            updateConsumption();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateConsumption() {
        int quantity = Integer.valueOf(view_Quantity.getText().toString().trim());
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();

        if (input_validate(quantity,date,time)) {
            date_time = date+ " " +time;
            ConsumptionManager consumptionManager = new ConsumptionManager(this);

            consumptionManager.updateConsumption(id,medicine_id,quantity,date_time);
            Toast toast = Toast.makeText(EditConsumptionActivity.this,"Update Consumption Successfully!",Toast.LENGTH_SHORT);
            toast.show();
            Intent i = new Intent(getApplicationContext(),ConsumptionActivity.class);
            startActivity(i);
        }

    }

    public boolean input_validate(int quantity,String date,String time) {
        boolean validate_status = true;
        if (view_Quantity.getText().length() == 0)
        {
            view_Quantity.setError("please input a quantity");
            validate_status = false;
        }
        if (date.isEmpty()) {
            etDate.setError("please select a date");
            validate_status = false;
        } else if (!MediPalUtility.isValidDate(date)) {
            etDate.setError("please select a right date");
            validate_status = false;
        }
        if (time.isEmpty()) {
            etTime.setError("please select a time");
            validate_status = false;
        } else if (MediPalUtility.isValidTime(date,time)) {
            etTime.setError("please select a right time");
            validate_status = false;
        }
        return validate_status;
    }
}
