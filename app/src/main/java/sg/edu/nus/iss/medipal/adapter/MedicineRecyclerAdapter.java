package sg.edu.nus.iss.medipal.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.activity.AddEditAppointmentActivity;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.AppointmentManager;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.Appointment;
import sg.edu.nus.iss.medipal.pojo.Category;
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

        public MedicineViewHolder(View view) {
            super(view);
            //get reference to the card view elements
            cardView = (CardView)view.findViewById(R.id.card_view);
            medicineName = (TextView) view.findViewById(R.id.medicine_name);
            medicineDesc = (TextView) view.findViewById(R.id.medicine_desc);
            medicineCategory = (TextView) view.findViewById(R.id.medicine_category);
            remainderFrequency = (TextView) view.findViewById(R.id.medicine_frequency);
            consumeQuantity = (TextView) view.findViewById(R.id.medicine_consumequantity);
            dateIssued = (TextView) view.findViewById(R.id.medicine_date);

        }
    }
    //constructor for adapter
    public MedicineRecyclerAdapter(Context mContext, HealthManager healthManager, AdapterCallbackInterface mCallback) {
        this.mContext = mContext;
        this.healthManager = healthManager;
        medicinePreference = new PreferenceManager(mContext);
        this.mCallback = mCallback;
        medicineList = healthManager.getMedicines(mContext);
        healthManager.getCategorys(mContext);
        healthManager.getReminders(mContext);
    }

    //called once in beginning to load the view
    @Override
    public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_card_list, parent, false);
        View popUp = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_card_single, parent, false);

        return new MedicineViewHolder(itemView);
    }

    //used to populate the view elements with adapter data
    @Override
    public void onBindViewHolder(final MedicineViewHolder holder, int position) {

        //get appointment data from list using current position as index


        Medicine medicine = medicineList.get(position);


        //populate the view elements
            holder.medicineName.setText(medicine.getMedicine_name());
            holder.medicineName.setTag(medicine.getId());
            holder.medicineDesc.setText(medicine.getMedicine_des());
            String categoryName = healthManager.getCategory(medicine.getCateId(),mContext).getCategory_name();
            Integer remainderFrequency = healthManager.getReminder(medicine.getReminderId(),mContext).getFrequency();
            Integer consumeQuantity = medicine.getConsumequantity();
            String[] dosageArray = mContext.getResources().getStringArray(R.array.dosage);
            holder.medicineCategory.setText(categoryName);
            holder.dateIssued.setText(medicine.getDateIssued());
            holder.remainderFrequency.setText("Medicine should be taken "+remainderFrequency.toString()+" per day");
            holder.consumeQuantity.setText("Dosage for each consumption is "+ consumeQuantity.toString()+dosageArray[medicine.getDosage()]);

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

}
