package sg.edu.nus.iss.medipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.ConsumptionAdapter;
import sg.edu.nus.iss.medipal.adapter.MedicineAdapter;

public class ConsumptionActivity extends AppCompatActivity {

    ListView listView;
    ConsumptionAdapter consumptionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                startActivity(new Intent(ConsumptionActivity.this, AddConsumption.class));

            }
        });

        listView= (ListView) findViewById(R.id.showConsumptionall);
        consumptionAdapter = new ConsumptionAdapter(getApplicationContext());
        listView.setAdapter(consumptionAdapter);

    }


    public void OnclickDetail (View view) {
        Intent i = new Intent(getApplicationContext(),ConsumptionDetail.class);
        startActivity(i);
    }



}
