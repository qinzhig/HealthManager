package sg.edu.nus.iss.medipal.adapter;

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
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.UncomsumedActivity;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.pojo.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by apple on 24/03/2017.
 */

public class UnConsumptionAdapter extends ArrayAdapter<Consumption> {
    private Context context;
    private List<Consumption> Unconsumption = new ArrayList<Consumption>();
    ConsumptionManager consumptionManager;
    String consumeQuantity;
    Integer medicineId;

    public UnConsumptionAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public UnConsumptionAdapter(Context context,Integer medicineId,String consumeQuantity) {
        super(context, R.layout.medicine_category_row_layout);
        this.context = context;
        consumptionManager = new ConsumptionManager(context);
        refreshUnconsumption();
        this.medicineId=medicineId;
        this.consumeQuantity=consumeQuantity;
    }
    public void refreshUnconsumption() {
        Unconsumption.clear();
        Unconsumption.addAll(consumptionManager.getConsumptions(context));
    }

    public int getCount(){
        return  Unconsumption.size();
    }

    static class ViewHolder{
        TextView tvName;
        Button btnUpdate,btnRemove;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        Log.v("ADAPTERADAPTER","_+_+_+_+_+_I AM IN THE UnConsumptionAdapter+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_");

        final UnConsumptionAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medicine_category_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.btnUpdate = (Button) convertView.findViewById(R.id.btn_update);
            viewHolder.btnRemove = (Button) convertView.findViewById(R.id.btn_remove);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (UnConsumptionAdapter.ViewHolder) convertView.getTag();
        }
        final Consumption consumption = Unconsumption.get(position);
        HealthManager healthManager = new HealthManager();
        UncomsumedActivity unconsumned = new UncomsumedActivity();
        String getConsumptionQuantity =consumeQuantity;
        String getIem[] = getConsumptionQuantity.split(" ");
        int medicine_id = medicineId;
        for (int i = 0;i < getIem.length; i++)
        {
            if (Integer.valueOf(getIem[i]) != 0) {
                viewHolder.tvName.setVisibility(View.INVISIBLE);
                viewHolder.btnUpdate.setVisibility(View.INVISIBLE);
                viewHolder.btnUpdate.setVisibility(View.VISIBLE);
            }

            if (Integer.valueOf(getIem[i]) == 0) {
                viewHolder.tvName.setText(healthManager.getMedicine(medicine_id,context).getMedicine_name());
                viewHolder.btnUpdate.setVisibility(View.INVISIBLE);
                viewHolder.btnUpdate.setVisibility(View.VISIBLE);
            }
        }



        return convertView;

    }
}
