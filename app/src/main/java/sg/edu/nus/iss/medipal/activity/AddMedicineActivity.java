package sg.edu.nus.iss.medipal.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.application.App;

/**
 * Created by : Qin Zhi Guo on 10-03-2017.
 * Description : Activity Class to add medicine
 * Modified by :
 * Reason for modification :
 */

public class AddMedicineActivity extends AppCompatActivity {

    private EditText et_name,et_des,et_quanity,et_date_get,et_date_expire,et_frequency,et_interval,et_stime,et_cquantity,et_threshold;
    private Spinner spinner,spinner_dosage;
    Button button_save;
    ImageButton button_add_category;
    TextView tv_reminder,tv_reminder_sw;

    private Switch switch_remind;
    private boolean remind_status;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
    Calendar currentDate = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();
    Calendar calender = Calendar.getInstance();

   // private static final String[] m_category = {"Supplement","Chronic","Incidental","Complete Course","Self Apply"};

    ArrayAdapter array_adpater;
    int position=0,dosage_position=0,expire_factor;
    String medcine_category;
    Date date_get,date_expire;
    String[] m_list;
    private int hour,minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_name = (EditText) findViewById(R.id.et_name);
        et_des = (EditText) findViewById(R.id.et_des);
        et_quanity = (EditText) findViewById(R.id.et_quantity);

        m_list = App.hm.getCategoryNameList(getApplicationContext());
        //Spinner action
        spinner= (Spinner) findViewById(R.id.spinner_category);
        array_adpater = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,m_list);
        spinner.setAdapter(array_adpater);

        //Reminder setting for medicine
        tv_reminder_sw = (TextView) findViewById(R.id.tv_reminder_sw);
        tv_reminder = (TextView) findViewById(R.id.tv_reminder);
        et_frequency = (EditText) findViewById(R.id.et_frequency);
        et_interval = (EditText) findViewById(R.id.et_interval);
        et_stime = (EditText) findViewById(R.id.et_stime);

        //Default category is SUP and switch for it is ON
        switch_remind = (Switch) findViewById(R.id.switch_remind);


        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //medcine_category = m_list[arg2];
                arg0.setVisibility(View.VISIBLE);

                position=arg2;

                if(position>0 && position <4){

                    switch_remind.setChecked(true);

                    tv_reminder_sw.setVisibility(View.INVISIBLE);
                    switch_remind.setVisibility(View.INVISIBLE);


                    tv_reminder.setVisibility(View.VISIBLE);
                    et_frequency.setVisibility(View.VISIBLE);
                    et_interval.setVisibility(View.VISIBLE);
                    et_stime.setVisibility(View.VISIBLE);

                    remind_status = true;

                }else{
                    tv_reminder_sw.setVisibility(View.VISIBLE);
                    switch_remind.setVisibility(View.VISIBLE);
                    switch_remind.setChecked(false);

                    tv_reminder.setVisibility(View.INVISIBLE);
                    et_frequency.setVisibility(View.INVISIBLE);
                    et_interval.setVisibility(View.INVISIBLE);
                    et_stime.setVisibility(View.INVISIBLE);

                    et_frequency.setText("0");
                    et_interval.setText("0");

                    remind_status = false;

                }

                Toast toast = Toast.makeText(AddMedicineActivity.this,"Category Selected Item"+position,Toast.LENGTH_SHORT);
                toast.show();
                //position= Arrays.asList(m_category).indexOf(medcine_category);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        et_cquantity = (EditText) findViewById(R.id.et_cquantity);
        et_threshold = (EditText) findViewById(R.id.et_threshold);

        spinner_dosage = (Spinner) findViewById(R.id.spinner_dosage);
        ArrayAdapter<CharSequence> adapter_dosage = ArrayAdapter.createFromResource(this,
                R.array.dosage, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_dosage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_dosage.setAdapter(adapter_dosage);

        spinner_dosage.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                arg0.setVisibility(View.VISIBLE);

                dosage_position=arg2;

                Toast toast = Toast.makeText(AddMedicineActivity.this,"Dosage Selected",Toast.LENGTH_SHORT);
                toast.show();
                //position= Arrays.asList(m_category).indexOf(medcine_category);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        //Expire date setting
        et_date_get = (EditText) findViewById(R.id.et_date_get);

        et_date_get.setText(dateFormatter.format(selectedDate.getTime()));
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
                        new DatePickerDialog(AddMedicineActivity.this, onDateSetListener,
                                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
                                currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        //Setting  Medicine issuedDate and Exipre Date to caculate out the ExpireFactor for store
        et_date_expire = (EditText) findViewById(R.id.et_date_expire);

        et_date_expire.setText(dateFormatter.format(selectedDate.getTime()));
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
                        new DatePickerDialog(AddMedicineActivity.this, onDateSetListener,
                                currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
                                currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        if(date_expire == null){
            c1.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH);
        }else{
            c1.setTime(date_expire);
        }

        if(date_get == null){
            c2.set(Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH);
        }else{
            c2.setTime(date_get);
        }

        expire_factor = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);

        if(expire_factor < 0){
            expire_factor = 0;
        }else if(expire_factor > 23){
            expire_factor = 24;
        }else{
            expire_factor = expire_factor + 1;
        }
        //End of computing the expireFactor


        //attach a listener to check for changes in state
        switch_remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch_remind.setText(" ON ");
                    remind_status = true;

                    tv_reminder.setVisibility(View.VISIBLE);
                    et_frequency.setVisibility(View.VISIBLE);
                    et_interval.setVisibility(View.VISIBLE);
                    et_stime.setVisibility(View.VISIBLE);

                }else{
                    switch_remind.setText(" OFF ");
                    remind_status = false;

                    tv_reminder.setVisibility(View.INVISIBLE);
                    et_frequency.setVisibility(View.INVISIBLE);
                    et_interval.setVisibility(View.INVISIBLE);
                    et_stime.setVisibility(View.INVISIBLE);

                    et_frequency.setText("0");
                    et_interval.setText("0");

                }

            }
        });

        //Set Listener for start time TimePicker
        et_stime.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    calender.setTimeInMillis(System.currentTimeMillis());
                    int mHour = calender.get(Calendar.HOUR_OF_DAY);
                    int mMinute = calender.get(Calendar.MINUTE);
                    new TimePickerDialog(AddMedicineActivity.this,
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


        //Save the medicine Infomation to SQLite

        button_save = (Button) findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                int reminderid = (int)( (Math.random()*9 + 1) * 10000);

                boolean no_input_empty;

                if((et_cquantity.getText().length() >0 && et_quanity.getText().length()>0 && et_threshold.getText().length()>0)){
                    no_input_empty = true;
                }else{
                    no_input_empty = false;
                }

                if(no_input_empty && input_validate(et_name.getText().toString().trim(),et_des.getText().toString().trim(),
                        Integer.valueOf(et_quanity.getText().toString().trim()),Integer.valueOf(et_cquantity.getText().toString().trim()),
                        Integer.valueOf(et_threshold.getText().toString().trim()),Integer.valueOf(et_frequency.getText().toString().trim()),
                        Integer.valueOf(et_interval.getText().toString().trim())) ) {


                    if(remind_status){
                        App.hm.addReminder(reminderid,Integer.valueOf(et_frequency.getText().toString().trim()),et_stime.getText().toString(),
                                Integer.valueOf(et_interval.getText().toString().trim()),getApplicationContext());

                        App.hm.setMeidicineReminder(et_stime.getText().toString(),Integer.valueOf(et_interval.getText().toString().trim()),Integer.valueOf(et_frequency.getText().toString().trim()),reminderid,getApplicationContext());
                    }

                    App.hm.addMedicine(0,et_name.getText().toString().trim(),et_des.getText().toString().trim(),
                            position+1,reminderid,remind_status,Integer.valueOf(et_quanity.getText().toString().trim()),
                            spinner_dosage.getSelectedItemPosition(),Integer.valueOf(et_cquantity.getText().toString().trim()),
                            Integer.valueOf(et_threshold.getText().toString().trim()),et_date_get.getText().toString(),expire_factor,getApplicationContext());

//                App.hm.addMedicine(0,"m1","m1_des",1,0,false,20,1,"14 03 2017",10,getApplicationContext());

                    Toast toast = Toast.makeText(AddMedicineActivity.this,"Add Medicine Successfully!",Toast.LENGTH_SHORT);
                    toast.show();


                    finish();

                }else{

                    Toast toast_error = Toast.makeText(AddMedicineActivity.this,"Some input incorrect,please check!",Toast.LENGTH_SHORT);
                    toast_error.show();
                }



            }
        });

        //End of saving


        //If doesn't found the category in the List,choose to add a new one
        button_add_category = (ImageButton) findViewById(R.id.button_add_category);

        button_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_add_category= new Intent(getApplicationContext(), AddCategoryActivity.class);
                startActivity(intent_add_category);

                finish();

            }
        });
    }

    @Override
    public void onResume(){
        //This activity get focus again and refresh the category List

        super.onResume();

        if(array_adpater != null)
        {
            array_adpater.notifyDataSetChanged();
            m_list = App.hm.getCategoryNameList(getApplicationContext());


        }
    }

    public boolean input_validate(String name,String des,int quantity,int cquantity,int threshold,int frequency,int interval){

        boolean validate_status =true;

        if(name.isEmpty()){
            et_name.setError("Please input a Medicine Name!");
            validate_status = false;
        }

        if(des.isEmpty()){
            et_des.setError("Please input a description for this medicine!");
            validate_status = false;
        }

        if(quantity < 0){
            et_quanity.setError("Please input a correct quantitu for this Medicine! ");
            validate_status = false;
            Log.v("DEBUG","------------Quantity = "+et_quanity.getText().toString().trim());
        }

        if(threshold >= quantity )
        {
            et_threshold.setError("Threshold medicine number overlap the Medicine Quantity!");
            validate_status = false;
            Log.v("DEBUG","------------Threshold = "+et_threshold.getText().toString().trim());
        }
        if(cquantity > quantity ){
            et_cquantity.setError("Consume Quantity every time is more the Medicine Quantity,Try to replenish!");
            validate_status = false;
        }

        if(frequency < 0 || frequency > 24 )
        {
            et_frequency.setError("Too Much Consumpotion Frequency set here!");
            validate_status = false;

            Log.v("DEBUG","------------Frequency = "+et_frequency.getText().toString().trim());
        }

        if(interval < 0 || interval >24 ){

            et_interval.setError("The interval hour exceed than 24 hours!");
            validate_status = false;

            Log.v("DEBUG","------------Interval = "+et_interval.getText().toString().trim());
        }

        if( frequency*interval >24 )
        {
            et_frequency.setError("Consumpition Setting exceed in one day!");
            et_interval.setError("Consumpition Setting exceed in one day!");
            validate_status = false;
        }

        return validate_status;

    }

}
