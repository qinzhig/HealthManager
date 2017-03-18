package sg.edu.nus.iss.medipal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sg.edu.nus.iss.medipal.R;

/**
 * Created by : Qin Zhi Guo on 12-03-2017.
 * Description : Activity Class to add category
 * Modified by :
 * Reason for modification :
 */

public class AddCategoryActivity extends AppCompatActivity {

    private EditText et_name,et_code,et_des;
    private Button button_save;

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

        button_save = (Button)findViewById(R.id.button_save);

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(AddCategoryActivity.this,"Add Category Successfully!",Toast.LENGTH_SHORT);
                toast.show();

                finish();
            }
        });
    }
}
