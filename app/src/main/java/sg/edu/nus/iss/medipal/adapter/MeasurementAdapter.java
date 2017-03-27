package sg.edu.nus.iss.medipal.adapter;

/**
 * Created by levis on 3/23/2017.
 * Modified by: Divahar on 3/25/2017 - Get any combination of inputs for measurement, handling inheritance
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.pojo.BloodPressure;
import sg.edu.nus.iss.medipal.pojo.Pulse;
import sg.edu.nus.iss.medipal.pojo.Temperature;
import sg.edu.nus.iss.medipal.pojo.Weight;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

    private Context _context;
    private List<Object> _measurementList;

    public class MeasurementViewHolder extends RecyclerView.ViewHolder {
        public TextView field1;
        public TextView field2;
        public TextView _measuredOn;


        public MeasurementViewHolder(View view) {
            super(view);

            field1 = (TextView) view.findViewById(R.id.measurementlistitem_field1);
            field2 = (TextView) view.findViewById(R.id.measurementlistitem_field2);
            _measuredOn = (TextView) view.findViewById(R.id.measurementlistitem_date);
        }
    }

    public MeasurementAdapter(Context context, List<Object> measurementList) {
        this._context = context;
        this._measurementList = measurementList;
    }

    @Override
    public MeasurementAdapter.MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_measurement_listitem, parent, false);

        return new MeasurementAdapter.MeasurementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MeasurementAdapter.MeasurementViewHolder holder, int position) {
        Object measurement = _measurementList.get(position);

        if (measurement instanceof BloodPressure) {
            BloodPressure bloodPressure
                    = (BloodPressure) measurement;
            holder.field1.setText("Systolic: " + String.valueOf(bloodPressure.getSystolic()));
            holder.field2.setText("Diastolic: " + String.valueOf(bloodPressure.getDiastolic()));
            holder._measuredOn.setText(MediPalUtility
                    .convertDateToString(bloodPressure.getMeasuredOn(), "yyyy MMM dd HH:mm"));
        } else if (measurement instanceof Pulse) {
            Pulse pulse =
                    (Pulse) measurement;
            holder.field1.setText("Pulse: " + String.valueOf(pulse.getPulse()));
            holder._measuredOn.setText(MediPalUtility
                    .convertDateToString(pulse.getMeasuredOn(), "yyyy MMM dd HH:mm"));
        } else if (measurement instanceof Temperature) {
            Temperature temperature =
                    (Temperature) measurement;
            holder.field1.setText("Temperature: " + String.valueOf(temperature.getTemperature()));
            holder._measuredOn.setText(MediPalUtility
                    .convertDateToString(temperature.getMeasuredOn(), "yyyy MMM dd HH:mm"));
        } else if (measurement instanceof Weight) {
            Weight weight =
                    (Weight) measurement;
            holder.field1.setText("Weight: " + String.valueOf(weight.getWeight()));
            holder._measuredOn.setText(MediPalUtility
                    .convertDateToString(weight.getMeasuredOn(), "yyyy MMM dd HH:mm"));
        }
    }

    @Override
    public int getItemCount() {
        return _measurementList.size();
    }
}
