package sg.edu.nus.iss.medipal.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.manager.PreferenceManager;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.manager.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.pojo.Reminder;

/**
 * Created by : Navi on 24-03-2017.
 * Description : This is the recycler adapter for showing consumption
 * Modified by :
 * Reason for modification :
 */

public class ConsumptionRecyclerAdapter extends RecyclerView.Adapter<ConsumptionRecyclerAdapter.ConsumptionViewHolder> {
    private PreferenceManager consumptionPreference;
    private Context mContext;
    private HealthManager healthManager ;
    private ConsumptionManager consumeManager;
    private List<Medicine> medicineList;
    private List<Consumption> consumptionList;

    AdapterCallbackInterface mCallback;

    //custom view holder to show the appointment details as card
    public class ConsumptionViewHolder extends RecyclerView.ViewHolder {
        public TextView medicineName;
        public TextView medicineCategory;
        public TextView consumeQuantity;
        public TextView consumedDateTime;
        public CardView cardView;

        public ConsumptionViewHolder(View view) {
            super(view);
            //get reference to the card view elements
            cardView = (CardView)view.findViewById(R.id.card_view);
            medicineName = (TextView) view.findViewById(R.id.medicine_name);
            medicineCategory = (TextView) view.findViewById(R.id.medicine_category);
            consumeQuantity = (TextView) view.findViewById(R.id.medicine_consumequantity);
            consumedDateTime = (TextView) view.findViewById(R.id.medicine_date);
        }
    }

    //constructor for adapter
    public ConsumptionRecyclerAdapter(Context mContext, HealthManager healthManager,ConsumptionManager consumeManager, List<Medicine> medicineList, List<Consumption> consumptionList, AdapterCallbackInterface mCallback) {
        this.mContext = mContext;
        this.healthManager = healthManager;
        this.consumeManager = consumeManager;
        consumptionPreference = new PreferenceManager(mContext);
        this.medicineList = medicineList;
        this.consumptionList = consumptionList;
        this.mCallback = mCallback;
    }

    //called once in beginning to load the view
    @Override
    public ConsumptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.consumption_card, parent, false);

        return new ConsumptionViewHolder(itemView);
    }

    //used to populate the view elements with adapter data
    @Override
    public void onBindViewHolder(final ConsumptionViewHolder holder, int position) {

        Consumption consumption = consumptionList.get(position);
        //get appointment data from list using current position as index
        Medicine medicine = healthManager.getMedicine(consumption.getMedicineId(),mContext);

        Integer consumeQuantity = medicine.getConsumequantity();
        String[] dosageArray = mContext.getResources().getStringArray(R.array.dosage);

        if(consumption.getQuality()==0) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.unconsumed));

            holder.consumeQuantity.setText("Dosage to be consumed is "+ consumeQuantity.toString()+" "+dosageArray[medicine.getDosage()]);
            holder.consumedDateTime.setText("Medicine needs to be consumed on "+consumption.getConsumedOn());
        }
        else {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.consumed));
            holder.consumeQuantity.setText("Dosage consumed is "+ consumeQuantity.toString()+" "+dosageArray[medicine.getDosage()]);
            holder.consumedDateTime.setText("Medicine consumed on "+consumption.getConsumedOn());
        }

        //populate the view elements
            holder.medicineName.setText(medicine.getMedicine_name());
            String categoryName = healthManager.getCategory(medicine.getCateId(),mContext).getCategory_name();

            holder.medicineCategory.setText(categoryName);

    }

    @Override
    public int getItemCount() {
        return consumptionList.size();
    }

}
