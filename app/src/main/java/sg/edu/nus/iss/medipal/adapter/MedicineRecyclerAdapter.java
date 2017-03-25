package sg.edu.nus.iss.medipal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import sg.edu.nus.iss.medipal.R;


import sg.edu.nus.iss.medipal.activity.AddConsumption;
import sg.edu.nus.iss.medipal.activity.AddEditAppointmentActivity;


import sg.edu.nus.iss.medipal.activity.AddConsumption;

import sg.edu.nus.iss.medipal.activity.EditMedicineActivity;
import sg.edu.nus.iss.medipal.dao.ConsumptionDAO;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.pojo.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.pojo.Reminder;

/**
 * Created by : Navi on 22-03-2017.
 * Description : This is the recycler adapter for showing medicines
 * Modified by :
 * Reason for modification :
 */

public class MedicineRecyclerAdapter extends RecyclerView.Adapter<MedicineRecyclerAdapter.MedicineViewHolder> {
    private PreferenceManager medicinePreference;
    private Context mContext;
    private HealthManager healthManager ;
    private List<Medicine> medicineList;
    private Boolean fromHomeFragment;

    //callback listener to communicate with the parent activity
    private AdapterCallbackInterface mCallback;

    //custom view holder to show the appointment details as card
    public class MedicineViewHolder extends RecyclerView.ViewHolder {
        public TextView medicineName;
        public TextView medicineDesc;
        public TextView medicineCategory;
        public TextView remainderFrequency;
        public TextView consumeQuantity;
        public TextView dateIssued;
        public CardView cardView;
        public ImageView edit;
        public ImageView delete;


        ConsumptionDAO consumptionDAO;

        public ImageView consume;
        //private PopupWindow cardPopUp;

        public MedicineViewHolder(View view, final View popUp) {
            super(view);
            //get reference to the card view elements
            cardView = (CardView)view.findViewById(R.id.card_view);
            medicineName = (TextView) view.findViewById(R.id.medicine_name);
            medicineDesc = (TextView) view.findViewById(R.id.medicine_desc);
            medicineCategory = (TextView) view.findViewById(R.id.medicine_category);
            remainderFrequency = (TextView) view.findViewById(R.id.medicine_frequency);
            consumeQuantity = (TextView) view.findViewById(R.id.medicine_consumequantity);
            dateIssued = (TextView) view.findViewById(R.id.medicine_date);
            delete = (ImageView) view.findViewById(R.id.delete);
            edit = (ImageView) view.findViewById(R.id.edit);
            consume = (ImageView) view.findViewById(R.id.consume);


            if(fromHomeFragment != null && fromHomeFragment) {
                delete.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //passing the current information to the activity for showing in view
                    Intent updateMedicine = new Intent(mContext, EditMedicineActivity.class);
                    Medicine medicine = medicineList.get(getAdapterPosition());
                    Log.v("Tag","_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_+_"+updateMedicine);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("medicineInfo",medicine);
                    updateMedicine.setClass(mContext,EditMedicineActivity.class);
                    updateMedicine.putExtras(bundle);
                    Log.v("TAG","--------------------MedicineAdapter Update   Object " + medicine.toString());
                    ((Activity)mContext).startActivityForResult(updateMedicine,202);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Are you sure you want to delete this Medicine?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Medicine medicine = medicineList.get(getAdapterPosition());
                                    healthManager.deleteMedicine(medicine.getId(),mContext);
                                    healthManager.deleteReminder(medicine.getReminderId(),mContext);
                                    ConsumptionManager consumptionManager= new ConsumptionManager(mContext);
                                    consumptionManager.deleteConsumption(medicine.getId());
                                    delete(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if(fromHomeFragment != null && fromHomeFragment) {
                        Medicine medicine = medicineList.get(getAdapterPosition());
                        Intent addConsumption = new Intent(mContext, AddConsumption.class);
                        addConsumption.putExtra("medicine_id",medicine.getId());
                        consumptionDAO = new ConsumptionDAO(mContext);
                        //int getCount =  consumptionDAO.getConsumptionCount(Integer.toString(medicine.getId(),)
                        ((Activity)mContext).startActivityForResult(addConsumption,301);
                    }*/
                }

            });

            consume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Medicine medicine = medicineList.get(getAdapterPosition());
                        int id= medicine.getId();
                        Intent addConsumption = new Intent(mContext, AddConsumption.class);
                        addConsumption.putExtra("medicine_id", id);
                    if(fromHomeFragment != null && fromHomeFragment) {
                        ((Activity) mContext).startActivityForResult(addConsumption, 301);
                    }
                    else
                    {
                        ((Activity) mContext).startActivityForResult(addConsumption, 302);
                    }

                }
            });
        }
    }

    private boolean isConsumptionAvailable(int id) {
       boolean retval=true;
        Calendar c = Calendar.getInstance();
        int day, month, year;
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        String consumedDate = day + "-" + (month + 1) + "-" + year;
        ConsumptionDAO consumptionDAO = new ConsumptionDAO(mContext);
        int totalConsumeCount=consumptionDAO.getConsumptionCount(Integer.toString(id),consumedDate);
        int currentConsumeCount = consumptionDAO.getCurrentConsumptionCount(Integer.toString(id),consumedDate);
        consumptionDAO.close();

        if(totalConsumeCount == 0){
            retval = true;
        }
        else if(currentConsumeCount >= totalConsumeCount)
        {
            retval = false;
        }

        return retval;
    }

    //constructor for adapter
    public MedicineRecyclerAdapter(Context mContext, HealthManager healthManager,List<Medicine> medicineList,Boolean fromHomeFragment, AdapterCallbackInterface mCallback) {
        this.mContext = mContext;
        this.healthManager = healthManager;
        medicinePreference = new PreferenceManager(mContext);
        this.mCallback = mCallback;
        this.medicineList = medicineList;
        this.fromHomeFragment = fromHomeFragment;

    }

    //called once in beginning to load the view
    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_card_list, parent, false);
        View popUp = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_consume_popup, parent, false);

        return new MedicineViewHolder(itemView, popUp);
    }

    //used to populate the view elements with adapter data
    @Override
    public void onBindViewHolder(final MedicineViewHolder holder, int position) {

        //get appointment data from list using current position as index
        int categoryId = medicineList.get(position).getCateId();
        if(!isConsumptionAvailable(medicineList.get(position).getId()) ) {
            holder.consume.setVisibility(View.INVISIBLE);
        }

        String remainderString;
        Medicine medicine = medicineList.get(position);
        Reminder reminder = healthManager.getReminder(medicine.getReminderId(),mContext);
        if(reminder != null) {
            Integer remainderFrequency = reminder.getFrequency();
            remainderString = "Medicine should be taken "+remainderFrequency.toString()+" times per day";
        }
        else
            remainderString="No remainder set";

        //populate the view elements
            holder.medicineName.setText(medicine.getMedicine_name());
            holder.medicineName.setTag(medicine.getId());
            holder.medicineDesc.setText(medicine.getMedicine_des());
            String categoryName = healthManager.getCategory(medicine.getCateId(),mContext).getCategory_name();
            Integer consumeQuantity = medicine.getConsumequantity();
            String[] dosageArray = mContext.getResources().getStringArray(R.array.dosage);
            holder.medicineCategory.setText(categoryName);
            holder.dateIssued.setText("Issued Date is " + medicine.getDateIssued());
            holder.remainderFrequency.setText(remainderString);
            holder.consumeQuantity.setText("Dosage for each consumption is "+ consumeQuantity.toString()+" "+dosageArray[medicine.getDosage()]);

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    //refresh recycler view
    public void delete(int position) {
        medicineList.remove(position);
        notifyItemRemoved(position);
        //callback to main activity if all the adapter data is deleted
        if(medicineList.size() == 0)
        {
            mCallback.refreshView("No medications found");
        }
    }

}
