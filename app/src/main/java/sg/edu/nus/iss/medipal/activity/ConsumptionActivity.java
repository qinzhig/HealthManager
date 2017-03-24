package sg.edu.nus.iss.medipal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.ConsumptionAdapter;

public class ConsumptionActivity extends AppCompatActivity {

    ListView listView;
    ConsumptionAdapter consumptionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption);

        Log.v("mateng i am in the consumption actovity","_++_+_++_+_+_+_+_+_matneg i am in the consumption activity _+_+_+_+_+_+_+_+_+_+_+_+");



        listView= (ListView) findViewById(R.id.showConsumptionall);
        consumptionAdapter = new ConsumptionAdapter(getApplicationContext());
        listView.setAdapter(consumptionAdapter);

    }


    public void OnclickDetail (View view) {
        Intent i = new Intent(getApplicationContext(),ConsumptionDetail.class);
        startActivity(i);
    }



}
