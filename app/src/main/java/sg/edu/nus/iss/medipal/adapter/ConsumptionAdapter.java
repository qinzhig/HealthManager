package sg.edu.nus.iss.medipal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.AddConsumption;
import sg.edu.nus.iss.medipal.activity.EditConsumptionActivity;
import sg.edu.nus.iss.medipal.activity.EditMedicineActivity;
import sg.edu.nus.iss.medipal.activity.MainActivity;
import sg.edu.nus.iss.medipal.activity.UncomsumedActivity;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.pojo.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by apple on 22/03/2017.
 */

public class ConsumptionAdapter extends ArrayAdapter<Consumption> {

    private Context context;
    private List<Consumption>consumptions = new ArrayList<Consumption>();
    ConsumptionManager consumptionManager;
    String showName = null;


    public ConsumptionAdapter(Context context){

        super(context,R.layout.medicine_category_row_layout);
        this.context=context;
        consumptionManager = new ConsumptionManager(context);
        refreshConsumptions();

    }

    public void refreshConsumptions() {
        consumptions.clear();
        /*
        final HealthManager healthManager = new HealthManager();
        ArrayList<Consumption> getC = new ArrayList<Consumption>();
        HashMap<Integer,String> hashMap = new HashMap<>();
        int[] getMedicine_id = new int[50];
        getC = consumptionManager.getConsumptions();
        Consumption consumptionitem = new Consumption();
        Iterator<Consumption> ite  = getC.iterator();
        int i = 0;
        while (ite.hasNext()) {
            consumptionitem = ite.next();
            getMedicine_id[i] = consumptionitem.getMedicineId();
        }

        for (i = 0; i < getMedicine_id.length; i++) {
            if (getMedicine_id[i] == getMedicine_id[i++])
            {
                showName = healthManager.getMedicine(getMedicine_id[i],context).getMedicine_name();
            }
        }
        */

        consumptions.addAll(consumptionManager.getConsumptions());


        notifyDataSetChanged();
    }
    public int getCount(){
        return  consumptions.size();
    }

    static class ViewHolder{
        TextView tvName;
        Button btnUpdate,btnRemove;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        final ConsumptionAdapter.ViewHolder viewHolder;

        Log.v("mateng i am in just in the view","+_+_+_+_+_+_+_+_+_+_+");

        if (convertView == null)
        {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medicine_category_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.btnUpdate = (Button) convertView.findViewById(R.id.btn_update);
            viewHolder.btnRemove = (Button) convertView.findViewById(R.id.btn_remove);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ConsumptionAdapter.ViewHolder) convertView.getTag();
        }
        final Consumption consumption = consumptions.get(position);
        final int medicine_id = consumption.getMedicineId();
        final HealthManager healthManager = new HealthManager();

        viewHolder.tvName.setText(healthManager.getMedicine(medicine_id,context).getMedicine_name());
        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent updateConsumption = new Intent(context,EditConsumptionActivity.class);

                updateConsumption.putExtra("medicine_name",viewHolder.tvName.getText().toString());
                updateConsumption.putExtra("quantity",healthManager.getMedicine(medicine_id,context).getConsumequantity());
                updateConsumption.putExtra("medicine_id",medicine_id);
                Log.v("TAGdate_timeTAG","_+_+_+_+_+_+_+_+_+_()()()*(*&*&*&*&*&*&*&*&*&"+consumption.getConsumedOn());
                updateConsumption.putExtra("date_time",consumption.getConsumedOn());

                updateConsumption.putExtra("id",consumption.getId());
                context.startActivity(updateConsumption);

            }
        });
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("TAG","----------------------------+++++++++++++++++++++++++> Consumpition Adapter Removed!");
                consumptionManager.deleteConsumption(consumption.getId());
                refreshConsumptions();

            }
        });

        return convertView;




    }


}
