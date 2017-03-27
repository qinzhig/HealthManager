package sg.edu.nus.iss.medipal.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.manager.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.pojo.Reminder;
import sg.edu.nus.iss.medipal.service.RemindAlarmReceiver;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

public class AddConsumption extends AppCompatActivity implements View.OnClickListener {

    private EditText etDate, etTime;
    TextInputLayout l_Date,l_Time;
    private TextView view_Medicine;
    private TextView view_Quantity;
    HealthManager healthmanager = new HealthManager();
    private int quantity;
    private int medicine_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String medicine_name;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consumption);

        //initial a medicine

        etDate = (EditText)findViewById(R.id.et_select_date);
        etTime = (EditText)findViewById(R.id.et_select_time);
        l_Date = (TextInputLayout)findViewById(R.id.tv_date);
        l_Time = (TextInputLayout)findViewById(R.id.tv_time);

        Calendar calender = Calendar.getInstance();
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);

        etDate.setText(day+"-"+(month+1)+"-"+year);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        view_Medicine = (TextView)findViewById(R.id.view_medicine);
        view_Quantity = (TextView) findViewById(R.id.Dosage);
        etTime.setOnClickListener(this);


        Bundle bundleMedicine = getIntent().getExtras();
        medicine_id = bundleMedicine.getInt("medicine_id");


        medicine_name = healthmanager.getMedicine(medicine_id,getApplicationContext()).getMedicine_name();
        quantity = healthmanager.getMedicine(medicine_id,getApplicationContext()).getConsumequantity();

        view_Medicine.setText(medicine_name);
        view_Quantity.setText(Integer.toString(quantity));

        //listener is added to clear error when input is given
        clearErrorOnTextInput();

    }

    private void clearErrorOnTextInput() {


        etTime.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    l_Time.setError(null);
            }
        });

    }

    public void onClick(View v) {
        final Calendar calender;
        int hour,minute;

        if(v == etTime)
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
            saveConsumption();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveConsumption() {

        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        int threshold = healthmanager.getMedicine(medicine_id,getApplicationContext()).getThreshold();
        int enteredQuantity = Integer.valueOf(view_Quantity.getText().toString());
        int totalQuantity = healthmanager.getMedicine(medicine_id, getApplicationContext()).getQuantity();

        if (input_validate(enteredQuantity,totalQuantity,date,time)) {

            final String date_time = date + " " + time;

            ConsumptionDAO consumptionDAO = new ConsumptionDAO(this);

            Calendar c = Calendar.getInstance();
            int day, month, year;
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);
            String consumedDate = day + "-" + (month + 1) + "-" + year;

            ConsumptionManager consumptionManager = new ConsumptionManager(this);

            int consumptionCount = consumptionDAO.getConsumptionCount(Integer.toString(medicine_id), consumedDate);

            if (consumptionCount == 0) {
                    consumptionManager.addConsumption(0, medicine_id, enteredQuantity, date_time);
            } else {
                int MinConsumptionId = consumptionDAO.getMinConsumptionId(Integer.toString(medicine_id), consumedDate);
                consumptionDAO.close();

                if (MinConsumptionId == 0) {
                    consumptionManager.addConsumption(0, medicine_id, enteredQuantity, date_time);
                } else {
                    consumptionManager.updateConsumption(MinConsumptionId, medicine_id, enteredQuantity, date_time);
                }
            }


            Medicine medicine = healthmanager.getMedicine(medicine_id, getApplicationContext());
            int newQuantity = totalQuantity - enteredQuantity;
            medicine.setQuantity((newQuantity));

            healthmanager.updateMedicineQuantity(medicine,getApplicationContext());

            if ( newQuantity <= threshold) {

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Calendar calendar =Calendar.getInstance(Locale.getDefault());
                calendar.setTimeInMillis(System.currentTimeMillis());

                Intent intent = new Intent(getApplicationContext(), RemindAlarmReceiver.class);
                //Set the reminderID and startTime in Intent data for scheduled repeat reminder
                intent.putExtra("ReplenishReminderId",medicine.getId());
                intent.putExtra("ReplenishNotification",medicine.getMedicine_name()+" need to be replenished");


                PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),medicine.getId(), intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_CANCEL_CURRENT);

                //Set up a reminder for replenish
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }

            final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Saving...");
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast toast = Toast.makeText(AddConsumption.this, "consumption added Successfully!", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }, 1000);
        }
    }

    public boolean input_validate(int quantity,int totalQuantity,String date,String time) {
        boolean validate_status = true;
        if(totalQuantity == 0) {
            l_Time.setError("Current quantity is 0. Please replenish the medicine");
            validate_status = false;
            Toast toast = Toast.makeText(AddConsumption.this, "Please Reo", Toast.LENGTH_SHORT);
            toast.show();
        } else
        if(quantity > totalQuantity){
            validate_status = false;
            l_Time.setError("Current quantity is "+totalQuantity+". . Please replenish the medicine.");
            Toast toast = Toast.makeText(AddConsumption.this, "Consumption added Successfully!", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (time.isEmpty()) {
            l_Time.setError("please select a time");
            validate_status = false;
        } else if (MediPalUtility.isValidTime(date,time)) {
            l_Time.setError("please select a past/current time");
            validate_status = false;
        }
        return validate_status;
    }

}
