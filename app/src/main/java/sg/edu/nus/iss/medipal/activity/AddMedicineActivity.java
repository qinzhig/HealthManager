package sg.edu.nus.iss.medipal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import sg.edu.nus.iss.medipal.R;

/**
 * Created by : Qin Zhi Guo on 10-03-2017.
 * Description : Activity Class to add medicine
 * Modified by :
 * Reason for modification :
 */

public class AddMedicineActivity extends AppCompatActivity {

    int year_expire,month_expire,day_expire;
    Button button_save;
    private static final String[] m_category = {"Supplement","Chronic","Incidental","Complete Course","Self Apply"};
    ArrayAdapter array_adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        button_save = (Button) findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(AddMedicineActivity.this,"Add Medicine Successfully!",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        //Spinner action
        Spinner spn= (Spinner) findViewById(R.id.spinner);
        array_adpater = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,m_category);
        spn.setAdapter(array_adpater);

        spn.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String medcine_category = m_category[arg2];
                arg0.setVisibility(View.VISIBLE);

                Toast toast = Toast.makeText(AddMedicineActivity.this,"Category Selected :"+medcine_category,Toast.LENGTH_SHORT);
                toast.show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Expire date setting
        DatePicker datepicker_expire=(DatePicker)findViewById(R.id.datePicker);
        Calendar ca=Calendar.getInstance();
        year_expire=ca.get(Calendar.YEAR);
        month_expire=ca.get(Calendar.MONTH);
        day_expire=ca.get(Calendar.DAY_OF_MONTH);

        datepicker_expire.init(year_expire,month_expire,day_expire,new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                // TODO Auto-generated method stub
                year_expire=year;
                month_expire=monthOfYear+1;
                day_expire=dayOfMonth;
                Toast.makeText(getApplicationContext(), "Expire Date：" + year_expire + "-" + month_expire + "-" + day_expire,
                        Toast.LENGTH_SHORT).show();
            }
        });



    }
}
