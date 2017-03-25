package sg.edu.nus.iss.medipal.adapter;

/**
 * Created by levis on 3/23/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

import java.util.List;

import sg.edu.nus.iss.medipal.R;
import sg.edu.nus.iss.medipal.pojo.Measurement;
import sg.edu.nus.iss.medipal.manager.MeasurementManager;
import sg.edu.nus.iss.medipal.activity.MeasurementActivity;

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
        //public ImageView _edit;
        public ImageView _delete;

        public MeasurementViewHolder(View view) {
            super(view);

            _systolic = (TextView) view.findViewById(R.id.measurementlistitem_systolic);
            _diastolic = (TextView) view.findViewById(R.id.measurementlistitem_diastolic);
            _pulse = (TextView) view.findViewById(R.id.measurementlistitem_pulse);
            _temperature = (TextView) view.findViewById(R.id.measurementlistitem_temperature);
            _weight = (TextView) view.findViewById(R.id.measurementlistitem_weight);
            _measuredOn = (TextView) view.findViewById(R.id.measurementlistitem_date);

            //_edit = (ImageView) view.findViewById(R.id.measurement_edit);
            _delete = (ImageView) view.findViewById(R.id.measurement_delete);
/*
            _edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MeasurementManager measurementManager = new MeasurementManager();
                    Measurement measurement = measurementManager.getMeasurement(getAdapterPosition(), _context);

                    Intent measurementIntent = new Intent(_context, MeasurementActivity.class);
                    measurementIntent.putExtra("id", measurement.getId());
                    measurementIntent.putExtra("systolic", measurement.getSystolic());
                    measurementIntent.putExtra("diastolic", measurement.getDiastolic());
                    measurementIntent.putExtra("pulse", measurement.getPulse());
                    measurementIntent.putExtra("temperature", measurement.getTemperature());
                    measurementIntent.putExtra("weight", measurement.getWeight());
                    measurementIntent.putExtra("measuredon", measurement.getMeasuredOn());
                    measurementIntent.putExtra("isEdit", true);
                    ((Activity)_context).startActivityForResult(measurementIntent, 503);
                }
            });
*/
            _delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(view.getContext())
                            .setMessage("Are you sure you want to delete this Measurement?")
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
        MeasurementManager measurementManager = new MeasurementManager();
        Measurement measurement = (Measurement)_measurementList.get(position);
        measurementManager.deleteMeasurement(measurement.getId().toString(), _context);

        _measurementList.remove(position);
        notifyItemRemoved(position);
    }
}
