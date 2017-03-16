package sg.edu.nus.iss.medipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.MedicineAdapter;

/**
 * Created by : Qin Zhi Guo on 10-03-2017.
 * Description : Activity Class to control and show the medicine info
 * Modified by :
 * Reason for modification :
 */

public class MedicineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_medicine);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Medicine");

        //Set the back array in toolbar to available
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView lv= (ListView) findViewById(R.id.lv_medicine);

        MedicineAdapter m_adapter = new MedicineAdapter(this);
        lv.setAdapter(m_adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                startActivity(new Intent(MedicineActivity.this, AddMedicineActivity.class));

            }
        });
    }

}
