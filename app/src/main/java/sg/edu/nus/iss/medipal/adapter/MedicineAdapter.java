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
import sg.edu.nus.iss.medipal.activity.AddConsumption;
import sg.edu.nus.iss.medipal.activity.EditMedicineActivity;
import sg.edu.nus.iss.medipal.application.App;
import sg.edu.nus.iss.medipal.pojo.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.pojo.Reminder;

/**
 * Created by zhiguo on 15/3/17.
 */

public class MedicineAdapter extends ArrayAdapter<Medicine>{

    private Context context;
    private List<Medicine> medicines = new ArrayList<>();

    public MedicineAdapter(Context context){

        super(context,R.layout.medicine_category_row_layout);
        this.context=context;
        refreshMedicines();
    }

    public void refreshMedicines() {

        medicines.clear();

        medicines.addAll(App.hm.getMedicines(this.context));

        Log.v("DEBUG",".............MedicineAdapter+++++++++++++++++++++++++++++++++++++++++++++ Size = "+medicines.size());

        notifyDataSetChanged();
    }

    public int getCount(){
        return  medicines.size();
    }

    static class ViewHolder{
        TextView tvName;
        Button btnUpdate,btnRemove;
    }



    public View getView(final int position, View convertView, ViewGroup parent){
        final ViewHolder viewHolder;



        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medicine_category_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.btnUpdate = (Button) convertView.findViewById(R.id.btn_update);
          //  viewHolder.btnRemove = (Button) convertView.findViewById(R.id.btn_remove);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Medicine medicine = medicines.get(position);
        final HealthManager healthManager = new HealthManager();


        viewHolder.tvName.setText(medicine.getMedicine_name());
        viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addConsumption = new Intent(context, AddConsumption.class);

                addConsumption.putExtra("isEdit",true);
                addConsumption.putExtra("medicine_name",viewHolder.tvName.getText().toString());
                addConsumption.putExtra("quantity",medicine.getConsumequantity());
                addConsumption.putExtra("medicine_id",medicine.getId());
                //get Frequency
                addConsumption.putExtra("frequency",healthManager.getReminder(medicine.getReminderId(),context).getFrequency());
                ((Activity)context).startActivity(addConsumption);

            }
        });

        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener(){

            @Override public void onClick(View v) {

                Intent updateMedicine = new Intent(context, EditMedicineActivity.class);

                Log.v("Tag","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+updateMedicine);

                Bundle bundle = new Bundle();
                bundle.putSerializable("medicineInfo",medicine);

                updateMedicine.setClass(context,EditMedicineActivity.class);
                updateMedicine.putExtras(bundle);

                Log.v("TAG","--------------------MedicineAdapter Update   Object " + medicine.toString());
                ((Activity)context).startActivity(updateMedicine);


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

        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                App.hm.deleteMedicine(medicine.getId(),context);
                refreshMedicines();
            }
        });


        Log.v("DEBUG",".............++++++++++++++++++++++++++++++++++++++++++++++ Size = "+medicines.size());

        return convertView;

    }


}