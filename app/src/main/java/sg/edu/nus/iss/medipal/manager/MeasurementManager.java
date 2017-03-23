package sg.edu.nus.iss.medipal.manager;

import android.content.Context;

import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.medipal.dao.MeasurementDAO;
import sg.edu.nus.iss.medipal.pojo.Measurement;

/**
 * Created by levis on 3/23/2017.
 */

public class MeasurementManager {
    MeasurementDAO _measurementDAO = null;

    public long addMeasurement(Integer systolic, Integer diastolic, Integer pulse, Float temperature, Integer weight, Date mesuredOn, Context context) {
        _measurementDAO = new MeasurementDAO(context);
        Measurement measurement = new Measurement(systolic, diastolic, pulse, temperature, weight, mesuredOn);
        return _measurementDAO.insert(measurement);
    }

    public List<Measurement> getMeasurements(Context context) {
        _measurementDAO = new MeasurementDAO(context);
        return _measurementDAO.retrieve();
    }

    public long deleteMeasurement(String id, Context context) {
        _measurementDAO = new MeasurementDAO(context);
        return _measurementDAO.delete(id);
    }

    public long updateMeasurement(String id, Integer systolic, Integer diastolic, Integer pulse, Float temperature, Integer weight, Date mesuredOn, Context context) {
        _measurementDAO = new MeasurementDAO(context);
        Measurement measurement = new Measurement(Integer.valueOf(id), systolic, diastolic, pulse, temperature, weight, mesuredOn);
        return _measurementDAO.update(measurement);
    }
}