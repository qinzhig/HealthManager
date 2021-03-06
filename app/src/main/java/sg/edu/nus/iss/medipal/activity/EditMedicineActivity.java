package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.pojo.Reminder;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

public class EditMedicineActivity extends AppCompatActivity {

    private EditText et_name,et_des,et_quanity,et_date_get,et_date_expire,et_cquantity,et_threshold,et_frequency,et_interval,et_stime;
    private Spinner spinner,spinner_dosage;

    TextView add_category;
    TextInputLayout lName,lDesc,lQuantity,lCQuantity,lThreshold,lGetDate,lExpireDate,lFrequency,lInterval,lStartTime;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
    Calendar currentDate = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();
    Calendar calender = Calendar.getInstance();

    //private static final String[] m_category = {"Supplement","Chronic","Incidental","Complete Course","Self Apply"};
    ArrayAdapter array_adpater;
    String[] m_list;
    Medicine medicine;
    Reminder reminder;

    private Switch switch_remind;
    private boolean remind_status;

    int position=0,dosage_position=0,expire_factor;
    String medcine_category;
    Date date_get,date_expire;
    private int hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Update Medicine");

        medicine = (Medicine) getIntent().getSerializableExtra("medicineInfo");
        reminder = App.hm.getReminder(medicine.getReminderId(),getApplicationContext());

        //Get the intentExtras data
        //Bundle intentExtras = getIntent().getExtras();


        //Align the medicine data from previous selected medicine item to current view correspondingly

        lName = (TextInputLayout)findViewById(R.id.tv_name);
        lDesc = (TextInputLayout)findViewById(R.id.tv_des);
        lQuantity = (TextInputLayout)findViewById(R.id.tv_quantity);
        lCQuantity = (TextInputLayout)findViewById(R.id.tv_dosage);
        lThreshold = (TextInputLayout)findViewById(R.id.tv_threshold);
        lGetDate = (TextInputLayout)findViewById(R.id.tv_date_get);
        lExpireDate = (TextInputLayout)findViewById(R.id.tv_date_expire);
        lFrequency = (TextInputLayout)findViewById(R.id.tv_frequency);
        lInterval = (TextInputLayout)findViewById(R.id.tv_interval);
        lStartTime = (TextInputLayout)findViewById(R.id.tv_stime);


        et_name = (EditText) findViewById(R.id.et_name);
        et_des = (EditText) findViewById(R.id.et_des);
        et_quanity = (EditText) findViewById(R.id.et_quantity);
        et_date_get = (EditText) findViewById(R.id.et_date_get);
        et_date_expire = (EditText) findViewById(R.id.et_date_expire);
        et_cquantity = (EditText) findViewById(R.id.et_cquantity);
        et_threshold = (EditText) findViewById(R.id.et_threshold);
        et_frequency = (EditText) findViewById(R.id.et_frequency);
        et_interval = (EditText) findViewById(R.id.et_interval);
        et_stime = (EditText) findViewById(R.id.et_stime);


        switch_remind = (Switch) findViewById(R.id.switch_remind);

        switch_remind.setChecked(medicine.isReminder());

        if(medicine.isReminder()){
            remind_status = true;

            et_frequency.setText(Integer.toString(reminder.getFrequency()));
            et_interval.setText(Integer.toString(reminder.getInterval()));
            et_stime.setText(reminder.getStartTime());

        }else{
            remind_status = false;

            et_frequency.setVisibility(View.INVISIBLE);
            et_interval.setVisibility(View.INVISIBLE);
            et_stime.setVisibility(View.INVISIBLE);
            lFrequency.setVisibility(View.INVISIBLE);
            lInterval.setVisibility(View.INVISIBLE);
            lStartTime.setVisibility(View.INVISIBLE);

        }


        //attach a listener to check for changes in state
        switch_remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch_remind.setText("Reminder ON ");
                    remind_status = true;

                    et_frequency.setVisibility(View.VISIBLE);
                    et_interval.setVisibility(View.VISIBLE);
                    et_stime.setVisibility(View.VISIBLE);
                    lFrequency.setVisibility(View.VISIBLE);
                    lInterval.setVisibility(View.VISIBLE);
                    lStartTime.setVisibility(View.VISIBLE);


                }else{
                    switch_remind.setText("Reminder OFF ");
                    remind_status = false;

                    et_frequency.setVisibility(View.INVISIBLE);
                    et_interval.setVisibility(View.INVISIBLE);
                    et_stime.setVisibility(View.INVISIBLE);
                    lFrequency.setVisibility(View.INVISIBLE);
                    lInterval.setVisibility(View.INVISIBLE);
                    lStartTime.setVisibility(View.INVISIBLE);

                }

            }
        });


        //Set the default data for every elements
        et_name.setText(medicine.getMedicine_name());
        et_des.setText(medicine.getMedicine_des());
        et_quanity.setText(Integer.toString(medicine.getQuantity()));
        et_cquantity.setText(Integer.toString(medicine.getConsumequantity()));
        et_threshold.setText(Integer.toString(medicine.getThreshold()));


        et_date_get.setText(medicine.getDateIssued());
        et_date_get.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                et_date_get.setText(dateFormatter.format(calendar.getTime()));
                                date_get=calendar.getTime();
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(EditMedicineActivity.this, onDateSetListener,
                                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
                                currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        String expire_date=restore_expireMonth(medicine.getDateIssued(),medicine.getExpireFactor());
        //et_date_expire.setText(medicine.getExpireFactor());
        et_date_expire.setText(expire_date);
        et_date_expire.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                et_date_expire.setText(dateFormatter.format(calendar.getTime()));
                                date_expire=calendar.getTime();
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(EditMedicineActivity.this, onDateSetListener,
                                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
                                currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });



        m_list = App.hm.getCategoryNameList(getApplicationContext());

        //Spinner action
        spinner= (Spinner) findViewById(R.id.spinner_category);
        array_adpater = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,m_list);
        spinner.setAdapter(array_adpater);
        spinner.setSelection((medicine.getCateId()-1));

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                medcine_category = m_list[arg2];
                arg0.setVisibility(View.VISIBLE);

                position=arg2;

                Toast toast = Toast.makeText(EditMedicineActivity.this,"Category Selected Item"+position,Toast.LENGTH_SHORT);
                toast.show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        add_category = (TextView) findViewById(R.id.button_add_category);
        add_category.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_list_category= new Intent(getApplicationContext(), ListCategory.class);
                startActivity(intent_list_category);


            }
        });


        spinner_dosage = (Spinner) findViewById(R.id.spinner_dosage);
        ArrayAdapter<CharSequence> adapter_dosage = ArrayAdapter.createFromResource(this,
                R.array.dosage, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_dosage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_dosage.setAdapter(adapter_dosage);
        spinner_dosage.setSelection(medicine.getDosage());


        spinner_dosage.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                arg0.setVisibility(View.VISIBLE);

                dosage_position=arg2;

                Toast toast = Toast.makeText(EditMedicineActivity.this,"Dosage Selected",Toast.LENGTH_SHORT);
                toast.show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Set Listener for start time TimePicker
        et_stime.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                calender.setTimeInMillis(System.currentTimeMillis());
                int mHour = calender.get(Calendar.HOUR_OF_DAY);
                int mMinute = calender.get(Calendar.MINUTE);
                new TimePickerDialog(EditMedicineActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                calender.setTimeInMillis(System.currentTimeMillis());
                                calender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calender.set(Calendar.MINUTE, minute);
                                calender.set(Calendar.SECOND, 0); // 设为 0
                                calender.set(Calendar.MILLISECOND, 0); // 设为 0

                                et_stime.setText(hourOfDay+":"+minute);
                            }
                        }, mHour, mMinute, true).show();
            }
        });


        //listener is added to clear error when input is given
        clearErrorOnTextInput();

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
            //Update the medicine if the save button is clicked
            boolean update_result = update_Medicine();

            if(update_result){
                final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Saving...");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        finish();
                    }
                }, 1000);
            }


        }

        return super.onOptionsItemSelected(item);
    }

    public String restore_expireMonth(String date_issued,int expire_factor){

        try {
            date_get = dateFormatter.parse(date_issued);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar_tmp = Calendar.getInstance();

        if(date_get != null){

            calendar_tmp.setTime(date_get);
            calendar_tmp.add(Calendar.MONTH,expire_factor);
        }

        String expire_date_restore = dateFormatter.format(calendar_tmp.getTime());

        return expire_date_restore;


    }

    public int caculate_expireFactor(String date_issued,String date_expire){

        String[] date1= date_issued.split(" ");
        String[] date2= date_expire.split(" ");

        int year2=Integer.valueOf(date2[2]);
        int year1=Integer.valueOf(date1[2]);
        int month2=Integer.valueOf(date2[1]);
        int month1=Integer.valueOf(date1[1]);

        expire_factor= (year2*12+month2) - (year1*12+month1);


        if(expire_factor < 1)
        {
            expire_factor = 0;
            lExpireDate.setError("Expire Month is more than Issue date month");
        }else if(expire_factor>24){
            expire_factor= 24;

        }

        return expire_factor;

    }


    public boolean input_validate_of_reminder(String frequency,String interval,String stime){

        boolean reminder_validate_status = true;

        String[] stime_hour_min = stime.split(":");

        if(frequency.isEmpty())
        {
            lFrequency.setError("Frequency is empty!");
            reminder_validate_status =false;
        }else if( (!frequency.isEmpty() && (Integer.valueOf(frequency) >24)))
        {
            lFrequency.setError("Frequency overlap than 24 per day !");
            reminder_validate_status =false;
        }

        if(interval.isEmpty())
        {
            lInterval.setError("Interval is empty!");
            reminder_validate_status =false;
        }else if( (!interval.isEmpty() && (Integer.valueOf(interval) >24)))
        {
            lInterval.setError("Interval overlap than 24 per day !");
            reminder_validate_status =false;
        }


        if(stime.isEmpty())
        {
            et_stime.setError("consumption Reminder StartTime is empty!");
            reminder_validate_status = false;
        }else if( (!stime.isEmpty()) && (!interval.isEmpty()) && (!stime.isEmpty()) &&
                ((Integer.valueOf(interval) * Integer.valueOf(frequency) +Integer.valueOf(stime_hour_min[0]) >24) ) ){

            lInterval.setError("Daily consumption schedule exceed than 24 hours!");
            lFrequency.setError("Daily consumption schedule exceed than 24 hours!");
            lStartTime.setError("Daily consumption schedule exceed than 24 hours!");

            reminder_validate_status = false;
        }

        return reminder_validate_status;

    }

    public boolean input_validate(String name,String des,String quantity,String cquantity,String threshold){

        boolean validate_status =true;

        if(name.isEmpty()){
            //lName.setError("Input a medicine name!");
            lName.setError("Name is empty");
            validate_status = false;
        }

        if(des.isEmpty()){
            lDesc.setError("Description is empty!");
            validate_status = false;
        }

        if(quantity.isEmpty() ){
            lQuantity.setError("Quantity is empty!");
            validate_status = false;
        }

        if(cquantity.isEmpty())
        {
            lCQuantity.setError("consumption is empty!");
            validate_status = false;
        }else if( (!cquantity.isEmpty()) && (!quantity.isEmpty()) && (Integer.valueOf(cquantity) > Integer.valueOf(quantity)))
        {
            lThreshold.setError("Threshold overlap Quantity");
            validate_status=false;
        }

        if(threshold.isEmpty()){
            lThreshold.setError("Threshold is empty!");
            validate_status = false;
        }else if( (!threshold.isEmpty()) && (!quantity.isEmpty()) && (Integer.valueOf(threshold) > Integer.valueOf(quantity)))
        {
            lThreshold.setError("Threshold overlap Quantity");
            validate_status=false;
        }

        if(et_date_get.getText().toString() != null ) {
            if (!MediPalUtility.isNotFutureDate(et_date_get.getText().toString().trim(),"yyyyMMdd")){
                lGetDate.setError("Future Date is entered ");
                validate_status=false;
            }
        }

        return validate_status;

    }


    public boolean update_Medicine(){

        int reminderid = (int)( (Math.random()*9 + 1) * 10000);

        boolean no_input_invalidate,no_reminder_input_invalidate;

        //Get the medicine related input info whether match the correct format
        no_input_invalidate = input_validate(et_name.getText().toString().trim(),et_des.getText().toString().trim(),
                et_quanity.getText().toString().trim(),et_cquantity.getText().toString().trim(), et_threshold.getText().toString().trim());


        //Get the reminder related input info whether match the correct format
        no_reminder_input_invalidate = input_validate_of_reminder(et_frequency.getText().toString().trim(),
                et_interval.getText().toString().trim(),et_stime.getText().toString().trim());

        expire_factor = caculate_expireFactor(et_date_get.getText().toString(),et_date_expire.getText().toString());

        if( no_input_invalidate  && (expire_factor > 0 )) {

            if( remind_status == true ){

                //If the reminder swither status is ON

                //If the reminder info passed the validation
                if(no_reminder_input_invalidate)
                {
                    if(medicine.isReminder())
                    {
                        //If the original meidicine has a reminder ON,and now update both "medicine" info and the frequency,interval and stime for "reminder"

                        App.hm.updateReminder(medicine.getReminderId(), Integer.valueOf(et_frequency.getText().toString().trim()), et_stime.getText().toString(), Integer.valueOf(et_interval.getText().toString().trim()), getApplicationContext());

                        App.hm.updateMedicine(medicine.getId(), et_name.getText().toString().trim(), et_des.getText().toString().trim(),
                                position + 1, medicine.getReminderId(), true, Integer.valueOf(et_quanity.getText().toString().trim()), spinner_dosage.getSelectedItemPosition(),
                                Integer.valueOf(et_cquantity.getText().toString().trim()), Integer.valueOf(et_threshold.getText().toString().trim()),
                                et_date_get.getText().toString(), expire_factor, getApplicationContext());

                        App.hm.setMeidicineReminder(true,et_stime.getText().toString(),Integer.valueOf(et_interval.getText().toString().trim()),
                                Integer.valueOf(et_frequency.getText().toString().trim()),medicine.getReminderId(),medicine.getId(),getApplicationContext());

                    }else{
                        //If the original meidicine has no reminder,then add a reminder for it
                        App.hm.addReminder(reminderid, Integer.valueOf(et_frequency.getText().toString().trim()), et_stime.getText().toString(), Integer.valueOf(et_interval.getText().toString().trim()), getApplicationContext());

                        App.hm.updateMedicine(medicine.getId(), et_name.getText().toString().trim(), et_des.getText().toString().trim(),
                                position + 1, reminderid, true, Integer.valueOf(et_quanity.getText().toString().trim()), spinner_dosage.getSelectedItemPosition(),
                                Integer.valueOf(et_cquantity.getText().toString().trim()), Integer.valueOf(et_threshold.getText().toString().trim()),
                                et_date_get.getText().toString(), expire_factor, getApplicationContext());

                        App.hm.setMeidicineReminder(true,et_stime.getText().toString(),Integer.valueOf(et_interval.getText().toString().trim()),
                                Integer.valueOf(et_frequency.getText().toString().trim()),reminderid,medicine.getId(),getApplicationContext());
                    }

                    return true;

                }else{
                    //If the reminder info doesn't pass the validation
                    Toast toast_error = Toast.makeText(EditMedicineActivity.this,"Some reminder related info incorrect,please check!",Toast.LENGTH_SHORT);
                    toast_error.show();

                    return false;
                }



            }else{
                //If the reminder switch status is OFF

                if(medicine.isReminder())
                {
                    //If the original meidicine has a reminder ON,then disable the reminder and update medicine

                    //App.hm.updateReminder(medicine.getReminderId(), Integer.valueOf(et_frequency.getText().toString().trim()), et_stime.getText().toString(), Integer.valueOf(et_interval.getText().toString().trim()), getApplicationContext());

                    App.hm.updateMedicine(medicine.getId(), et_name.getText().toString().trim(), et_des.getText().toString().trim(),
                            position + 1, medicine.getReminderId(), false, Integer.valueOf(et_quanity.getText().toString().trim()), spinner_dosage.getSelectedItemPosition(),
                            Integer.valueOf(et_cquantity.getText().toString().trim()), Integer.valueOf(et_threshold.getText().toString().trim()),
                            et_date_get.getText().toString(), expire_factor, getApplicationContext());

                    App.hm.setMeidicineReminder(false,et_stime.getText().toString(),Integer.valueOf(et_interval.getText().toString().trim()),
                            Integer.valueOf(et_frequency.getText().toString().trim()),medicine.getReminderId(),medicine.getId(), getApplicationContext());

                }else{
                    //If the original meidicine has no reminder,then just update medicine info will be fine

                    App.hm.updateMedicine(medicine.getId(), et_name.getText().toString().trim(), et_des.getText().toString().trim(),
                            position + 1, reminderid, false, Integer.valueOf(et_quanity.getText().toString().trim()), spinner_dosage.getSelectedItemPosition(),
                            Integer.valueOf(et_cquantity.getText().toString().trim()), Integer.valueOf(et_threshold.getText().toString().trim()),
                            et_date_get.getText().toString(), expire_factor, getApplicationContext());
                }

                return true;

            }

        }else{
            Toast toast_error = Toast.makeText(EditMedicineActivity.this,"Some Medicine Info incorrect,please check!",Toast.LENGTH_SHORT);
            toast_error.show();

            return false;
        }

    }
    private void clearErrorOnTextInput() {


        et_name.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lName.setError(null);
            }
        });

        et_des.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lDesc.setError(null);
            }
        });

        et_quanity.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lQuantity.setError(null);
            }
        });
        et_cquantity.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lCQuantity.setError(null);
            }
        });
        et_threshold.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lThreshold.setError(null);
            }
        });
        et_frequency.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lFrequency.setError(null);
            }
        });
        et_interval.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lInterval.setError(null);
            }
        });
        et_stime.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lStartTime.setError(null);
            }
        });
        et_date_expire.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lExpireDate.setError(null);
            }
        });
        et_date_get.addTextChangedListener(new MediPalUtility.CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0)
                    lGetDate.setError(null);
            }
        });

    }

}
