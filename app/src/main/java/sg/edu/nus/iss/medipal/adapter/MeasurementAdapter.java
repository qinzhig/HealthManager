package sg.edu.nus.iss.medipal.adapter;

/**
 * Created by levis on 3/23/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.manager.MeasurementManager;
import sg.edu.nus.iss.medipal.pojo.Measurement;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder>{

    private Context _context;
    private List<Measurement> _measurementList;

    public class MeasurementViewHolder extends RecyclerView.ViewHolder {
        public TextView _systolic;
        public TextView _diastolic;
        public TextView _pulse;
        public TextView _temperature;
        public TextView _weight;
        public TextView _measuredOn;
        public ImageView _edit;
        public ImageView _delete;

        public MeasurementViewHolder(View view) {
            super(view);

            _systolic = (TextView) view.findViewById(R.id.measurementlistitem_systolic);
            _diastolic = (TextView) view.findViewById(R.id.measurementlistitem_diastolic);
            _pulse = (TextView) view.findViewById(R.id.measurementlistitem_pulse);
            _temperature = (TextView) view.findViewById(R.id.measurementlistitem_temperature);
            _weight = (TextView) view.findViewById(R.id.measurementlistitem_weight);
            _measuredOn = (TextView) view.findViewById(R.id.measurementlistitem_date);

            _edit = (ImageView) view.findViewById(R.id.measurement_edit);
            _delete = (ImageView) view.findViewById(R.id.measurement_delete);

            /*
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addEditHealthBio = new Intent(_context, AddEditHealthBioActivity.class);
                    addEditHealthBio.putExtra("id",condition.getTag().toString());
                    addEditHealthBio.putExtra("condition",condition.getText().toString());
                    addEditHealthBio.putExtra("startDate",startDate.getText().toString());
                    addEditHealthBio.putExtra("conditionType",conditionType.getText().toString());
                    addEditHealthBio.putExtra("isEdit",true);
                    ((Activity)_context).startActivityForResult(addEditHealthBio,2);
                }
            });
            */

            _delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Are you sure you want to delete this Contact?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    MeasurementManager measurementManager = new MeasurementManager();
                                    //measurementManager.deleteMeasurement(condition.getTag().toString(), _context);
                                    delete(getAdapterPosition());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            });
        }
    }

    public MeasurementAdapter(Context context, List<Measurement> measurementList) {
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
        Measurement measurement = _measurementList.get(position);

        holder._systolic.setText(measurement.getSystolic().toString());
        holder._diastolic.setText(measurement.getDiastolic().toString());
        holder._pulse.setText(measurement.getPulse().toString());
        holder._temperature.setText(measurement.getTemperature().toString());
        holder._weight.setText(measurement.getWeight().toString());
        holder._measuredOn.setText(measurement.getMeasuredOn().toString());
    }

    @Override
    public int getItemCount() {
        return _measurementList.size();
    }

    public void delete(int position) {
        _measurementList.remove(position);
        notifyItemRemoved(position);
    }
}
