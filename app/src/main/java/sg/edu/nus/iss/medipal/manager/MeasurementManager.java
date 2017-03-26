package sg.edu.nus.iss.medipal.manager;

import android.content.Context;

import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.medipal.dao.MeasurementDAO;
import sg.edu.nus.iss.medipal.pojo.BloodPressure;
import sg.edu.nus.iss.medipal.pojo.Pulse;
import sg.edu.nus.iss.medipal.pojo.Temperature;
import sg.edu.nus.iss.medipal.pojo.Weight;

/**
 * Created by levis on 3/23/2017.
 */

public class MeasurementManager {
    private MeasurementDAO measurementDAO = null;
    private BloodPressure bloodPressureObj = null;
    private Pulse pulseObj = null;
    private Temperature temperatureObj = null;
    private Weight weightObj = null;

    public long addMeasurement(int systolic, int diastolic, int pulse, float temperature, int weight, Date measuredOn, Context context) {
        measurementDAO = new MeasurementDAO(context);
        if(systolic>0 &&
                diastolic>0) {
            bloodPressureObj = new BloodPressure(measuredOn, systolic, diastolic);
        }
        if(pulse>0) {
            pulseObj = new Pulse(measuredOn, pulse);
        }
        if(temperature>0) {
            temperatureObj = new Temperature(measuredOn, temperature);
        }
        if(weight>0) {
            weightObj = new Weight(measuredOn, weight);
        }

        return measurementDAO.insert(bloodPressureObj,
                pulseObj, weightObj, temperatureObj);
    }

    public List<Object> getMeasurements(Context context) {
        measurementDAO = new MeasurementDAO(context);
        return measurementDAO.retrieve();
    }
}