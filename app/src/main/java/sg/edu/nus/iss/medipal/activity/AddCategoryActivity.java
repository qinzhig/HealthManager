package sg.edu.nus.iss.medipal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.application.App;

/**
 * Created by : Qin Zhi Guo on 12-03-2017.
 * Description : Activity Class to add category
 * Modified by :
 * Reason for modification :
 */

public class AddCategoryActivity extends AppCompatActivity{

    private EditText et_name,et_code,et_des;
    private Button button_save;
    private Switch switch_remind;
    private boolean remind_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        et_name = (EditText) findViewById(R.id.et_name);
        et_code = (EditText) findViewById(R.id.et_code);
        et_des = (EditText) findViewById(R.id.et_des);
        switch_remind = (Switch) findViewById(R.id.switch_remind);

        //set the switch to ON
        switch_remind.setChecked(true);
        //attach a listener to check for changes in state
        switch_remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    switch_remind.setText(" ON ");
                    remind_status = true;
                }else{
                    switch_remind.setText(" OFF ");
                    remind_status = false;
                }

            }
        });


        button_save = (Button)findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(input_validate(et_name.getText().toString().trim(),et_code.getText().toString().trim(),et_des.getText().toString().trim())){
                    App.hm.addCategory(0,et_name.getText().toString().trim(),et_code.getText().toString().trim(),et_des.getText().toString().trim(),remind_status,getApplicationContext());

                    Toast toast = Toast.makeText(AddCategoryActivity.this,"Add Category Successfully!",Toast.LENGTH_SHORT);
                    toast.show();

                    finish();
                }else{
                    Toast toast = Toast.makeText(AddCategoryActivity.this,"Something incorrect with your input,please have a check!",Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });
    }

    public boolean input_validate(String name,String code,String des){

        boolean validate_status =true;

        if(name.isEmpty()){
            et_name.setError("Please input a Category Name!");
            validate_status = false;
        }

        if(code.isEmpty() || code.length() != 3){
            et_code.setError("Please input a 3  letter as Category Code!");
            validate_status = false;
        }

        if(des.isEmpty()){
            et_des.setError("Please input a Category Description!");
            validate_status = false;
        }


        return validate_status;

    }

}
