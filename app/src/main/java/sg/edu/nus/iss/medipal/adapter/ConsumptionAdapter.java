package sg.edu.nus.iss.medipal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import sg.edu.nus.iss.medipal.activity.EditMedicineActivity;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by apple on 22/03/2017.
 */

public class ConsumptionAdapter extends ArrayAdapter<Consumption> {
    private Context context;
    private List<Consumption>consumptions = new ArrayList<Consumption>();
    ConsumptionManager consumptionManager ;
    private List<Medicine> medicines = new ArrayList<>();

 public ConsumptionAdapter(Context context) {
     super(context, R.layout.consumption_item);
     consumptionManager = new ConsumptionManager(context);
     this.context=context;
     refreshConsumptions();
 }

 public void refreshConsumptions(){
     consumptions.clear();
     consumptions.addAll(consumptionManager.getConsumptions(this.context));

     Log.v("DEBUG",".............Consumptiondapter+++++++++++++++++++++++++++++++++++++++++++++ Size = "+consumptions.size());

     notifyDataSetChanged();
 }

    public int getCount(){
        return  consumptions.size();
    }

    static class ViewHolder{
        TextView item_name;
        Button btn_updateconsumption,btn_removeconsumption;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        ConsumptionAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.consumption_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item_name = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.btn_updateconsumption = (Button) convertView.findViewById(R.id.btn_updateconsumption);
            viewHolder.btn_removeconsumption = (Button) convertView.findViewById(R.id.btn_removeconsumption);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ConsumptionAdapter.ViewHolder) convertView.getTag();
        }

        final Medicine medicine = medicines.get(position);
        final Consumption consumption = consumptions.get(position);
        viewHolder.item_name.setText(medicine.getMedicine_name());

        viewHolder.btn_updateconsumption.setOnClickListener(new View.OnClickListener(){

            @Override public void onClick(View v) {

                Intent updateConsumption = new Intent(context, EditMedicineActivity.class);

                Bundle bundle = new Bundle();
               // bundle.putSerializable("medicineInfo",medicine);

                updateConsumption.setClass(context,EditMedicineActivity.class);
                updateConsumption.putExtras(bundle);

                Log.v("TAG","--------------------ConsumotionAdapter Update   Object " + "Good night!");

//                Log.v("TAG","--------------------MedicineAdapter Update   ID " + medicine.getId() );
//                Log.v("TAG","--------------------MedicineAdapter Update  name " + medicine.getMedicine_name() );
//                Log.v("TAG","--------------------MedicineAdapter Update   catId " + medicine.getCateId() );
//                Log.v("TAG","--------------------MedicineAdapter Update   Quantity " + medicine.getQuantity() );



                ((Activity)context).startActivity(updateConsumption);


//                updateMedicine.putExtra("id",medicine.getId());
//                updateMedicine.putExtra("name",medicine.getMedicine_name());
//                updateMedicine.putExtra("description",medicine.getMedicine_des());
//                updateMedicine.putExtra("catId",medicine.getCateId());
//                updateMedicine.putExtra("reminderId",medicine.getReminderId());
//                updateMedicine.putExtra("reminder",medicine.isReminder());
//                updateMedicine.putExtra("quantity",medicine.getQuantity());
//                updateMedicine.putExtra("dosage",medicine.getDosage());
//                updateMedicine.putExtra("dateIssued",medicine.getDateIssued());
//                updateMedicine.putExtra("expireFactor",medicine.getExpireFactor());




                //((Activity)context).startActivityForResult(updateMedicine,201);
            }
        });

        viewHolder.btn_removeconsumption.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                consumptionManager.deleteConsumption(consumption.getId(),context);
                refreshConsumptions();
            }
        });


        Log.v("DEBUG",".............++++++++++++++++++++++++++++++++++++++++++++++ Size = "+consumptions.size());

        return convertView;

    }


}
