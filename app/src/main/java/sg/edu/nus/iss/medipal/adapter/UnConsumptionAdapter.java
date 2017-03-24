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

/**
 * Created by apple on 24/03/2017.
 */

public class UnConsumptionAdapter extends ArrayAdapter<Consumption> {
    private Context context;
    private List<Consumption> Unconsumption = new ArrayList<Consumption>();
    ConsumptionManager consumptionManager;


    public UnConsumptionAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public UnConsumptionAdapter(Context context) {
        super(context, R.layout.medicine_category_row_layout);
        this.context = context;
        consumptionManager = new ConsumptionManager(context);
        refreshUnconsumption();
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
        UncomsumedActivity unconsumned = new UncomsumedActivity();
        int acceptFrequentCount = unconsumned.getFrequentCount();

        Log.v("ACCEPTTAGTAGTAGACCEPT","_+_+_+_+_+_+_+_++++_+_+_+_+_+ACCEPTCOUNT"+acceptFrequentCount);





        return convertView;

    }
}
