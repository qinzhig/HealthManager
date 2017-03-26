package sg.edu.nus.iss.medipal.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.pojo.Reminder;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

import static java.lang.Integer.valueOf;

public class AddConsumption extends AppCompatActivity implements View.OnClickListener {

    private EditText etDate, etTime;
    TextInputLayout l_Quantity,l_Date,l_Time;
    private TextView view_Medicine;
    private EditText view_Quantity;
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
        l_Quantity = (TextInputLayout)findViewById(R.id.Quantity);
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
        view_Quantity = (EditText) findViewById(R.id.Dosage);
        etTime.setOnClickListener(this);


        Bundle bundleMedicine = getIntent().getExtras();
        medicine_id = bundleMedicine.getInt("medicine_id");

        Intent transferMedicineId = new Intent(getApplicationContext(),ConsumptionDetail.class);
        transferMedicineId.putExtra("medicine_id",medicine_id);
        Intent transferMedicineIdtoUnconsumption = new Intent(getApplicationContext(),UncomsumedActivity.class);
        transferMedicineIdtoUnconsumption.putExtra("medicine_id",medicine_id);

        medicine_name = healthmanager.getMedicine(medicine_id,getApplicationContext()).getMedicine_name();
        quantity = healthmanager.getMedicine(medicine_id,getApplicationContext()).getConsumequantity();

        view_Medicine.setText(medicine_name);
        view_Quantity.setText(Integer.toString(quantity));

        //listener is added to clear error when input is given
        clearErrorOnTextInput();

        //Log.v("MT","------------------------Cquantity="+medicine.getConsumequantity());

        Log.v("mateng","***************************name"+medicine_name);
        Log.v("mateng","***************************quantity"+quantity);
        Log.v("mateng","***************************bundle"+bundleMedicine);

        //view_Quantity.setText(Integer.toString(medicine.getConsumequantity()));
    }

    private void clearErrorOnTextInput() {

        view_Quantity.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    l_Quantity.setError(null);
            }
        });

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
        int day,month,year,hour,minute;

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
        Log.v("MATENG ADDCONSUMPTION","_+_+_++_+_+_+_+_+_+_+_+_+_+_+_+"+medicine_id);

        if (input_validate(enteredQuantity,totalQuantity,date,time)) {

            final String date_time = date + " " + time;
            Intent transferDate_time = new Intent(getApplicationContext(), ConsumptionDetail.class);
            transferDate_time.putExtra("ConsumedOn", date_time);

            ConsumptionDAO consumptionDAO = new ConsumptionDAO(this);

            Calendar c = Calendar.getInstance();
            int day, month, year;
            day = c.get(Calendar.DAY_OF_MONTH);
            month = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);
            String consumedDate = day + "-" + (month + 1) + "-" + year;

            ConsumptionManager consumptionManager = new ConsumptionManager(this);

            Log.d("consumed Date", consumedDate);
            int consumptionCount = consumptionDAO.getConsumptionCount(Integer.toString(medicine_id), consumedDate);
            //int MinConsumptionId = consumptionDAO.getMinConsumptionId(Integer.toString(medicine_id),consumedDate);


            int ReminderId = healthmanager.getMedicine(medicine_id, getApplicationContext()).getReminderId();
            Log.d("medicine id",Integer.toString(medicine_id));
            Reminder reminder = healthmanager.getReminder(ReminderId, getApplicationContext());

            if (consumptionCount == 0) {
               // if (reminder != null) {
                 //   int FrequentNum = reminder.getFrequency();
                    consumptionManager.addConsumption(0, medicine_id, enteredQuantity, date_time);
                   // for (int i = 0; i < (FrequentNum - 1); i++) {
                   //     consumptionManager.addConsumption(0, medicine_id, 0, date_time);
                   // }
                //} else {
                  //  consumptionManager.addConsumption(0, medicine_id, enteredQuantity, date_time);
               // }
            } else {
                int MinConsumptionId = consumptionDAO.getMinConsumptionId(Integer.toString(medicine_id), consumedDate);
                consumptionDAO.close();
                Log.d("min consumption id",Integer.toString(MinConsumptionId));

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

            //have to change this to notification
            if ( newQuantity <= threshold) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("Notice");
                dlg.setMessage("Dear, you need to replenish the medicine");
                dlg.setPositiveButton("OK", null);
                dlg.show();
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
                    Intent i = new Intent(getApplicationContext(), ConsumptionActivity.class);
                    i.putExtra("medicine_id", medicine_id);
                    i.putExtra("ConsumedOn", date_time);
                   // startActivity(i);
                    Toast toast = Toast.makeText(AddConsumption.this, "Consumption added Successfully!", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
            }, 1000);
        }
    }

    public boolean input_validate(int quantity,int totalQuantity,String date,String time) {
        boolean validate_status = true;
        if (quantity == 0)
        {
            l_Quantity.setError("please input a quantity");
            validate_status = false;
        }else if(totalQuantity == 0) {
            l_Quantity.setError("Current quantity is 0. Please replenish the medicine");
            validate_status = false;
        } else
        if(quantity > totalQuantity){
            validate_status = false;
            l_Quantity.setError("Current quantity is "+totalQuantity+". Please enter within this value.");
        }

       /* if (date.isEmpty()) {
            l_Date.setError("please select a date");
            validate_status = false;
        } else if (!MediPalUtility.isValidDate(date)) {
            etDate.setError("please select a right date");
            validate_status = false;
        }
*/
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
