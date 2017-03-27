package sg.edu.nus.iss.medipal.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.adapter.ConsumptionRecyclerAdapter;
import sg.edu.nus.iss.medipal.interfaces.AdapterCallbackInterface;
import sg.edu.nus.iss.medipal.manager.ConsumptionManager;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.manager.HealthManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;

/**
 * Created by : Naval on 25-03-2017.
 * Description : fragment for consumption
 * Modified by :
 * Reason for modification :
 */

public class ConsumptionFragment extends Fragment implements AdapterCallbackInterface ,View.OnClickListener {

    RecyclerView lv;
    //MedicineAdapter m_adapter;
    private ConsumptionRecyclerAdapter consumptionAdapter;
    private ConsumptionManager consumptionManager;
    private HealthManager medicineManager;
    private List<Consumption> consumptionList;
    private List<Medicine> medicineList;
    private Context mContext;
    private View consumeFragment;
    private EditText date;

    int day,month,year;
    Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        consumeFragment = inflater.inflate(R.layout.consume_card_list, container, false);
        lv= (RecyclerView) consumeFragment.findViewById(R.id.lv_consume);

        date = (EditText)consumeFragment.findViewById(R.id.date);

        //get reference to medicine manager
        medicineManager = new HealthManager();
        //get reference to consumption manager
        consumptionManager = new ConsumptionManager(mContext);

        //get reference to the current context(
        mContext = consumeFragment.getContext();
        medicineList = medicineManager.getMedicines(mContext);
        medicineManager.getCategorys(mContext);
        medicineManager.getReminders(mContext);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        String dt=day+"-"+(month+1)+"-"+year;
        date.setText(dt);

        createRecyclerView(dt);

        date.setOnClickListener(this);


        return consumeFragment;
    }

    void createRecyclerView(String dt) {
        consumptionList = consumptionManager.getConsumptions(dt);

        if (consumptionList == null || consumptionList.isEmpty()) {
            //show the "no medicine" message
            refreshView("No Consumptions found");
        } else {

            populateConsumptionRecyclerView();
        }
    }

    private void populateConsumptionRecyclerView() {
        TextView txtView = (TextView) consumeFragment.findViewById(R.id.placeholdertext);
        txtView.setVisibility(View.GONE);
        lv.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        lv.setLayoutManager(mLayoutManager);
        //populate the adapter with appointments lists
        consumptionAdapter = new ConsumptionRecyclerAdapter(mContext,medicineManager,consumptionManager,medicineList,consumptionList, this);
        lv.setAdapter(consumptionAdapter);
    }

    @Override
    public void refreshView(String message) {
        //if no health are found then the following message will be shown
        LinearLayout linearLayout = (LinearLayout) consumeFragment.findViewById(R.id.consume_layout);
        linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        TextView txtView = (TextView) consumeFragment.findViewById(R.id.placeholdertext);
        txtView.setVisibility(View.VISIBLE);
        lv.setVisibility(View.GONE);
        txtView.setText(message);
    }

    @Override
    public void onClick(View view) {

        if (view == date) {
            date.setError(null);
            DatePickerDialog datePicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String dt = dayOfMonth+"-"+(month+1)+"-"+year;
                    date.setText(dt);
                    createRecyclerView(dt);

                }
            }, day, month, year);
            datePicker.updateDate(year, month, day);
            datePicker.show();

        }
    }
}
