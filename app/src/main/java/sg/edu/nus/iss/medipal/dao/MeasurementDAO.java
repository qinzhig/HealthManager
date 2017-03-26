package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;
import sg.edu.nus.iss.medipal.pojo.BloodPressure;
import sg.edu.nus.iss.medipal.pojo.Pulse;
import sg.edu.nus.iss.medipal.pojo.Temperature;
import sg.edu.nus.iss.medipal.pojo.Weight;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by levis on 3/23/2017.
 */

public class MeasurementDAO extends DataBaseUtility {

    private static final String WHERE_ID_EQUALS = DataBaseManager.MEASUREMENT_ID + " =?";

    public MeasurementDAO(Context context) {
        super(context);
    }

    public long insert(BloodPressure bloodPressure,
                       Pulse pulse, Weight weight, Temperature temperature) throws SQLException {
        long retCode = 0;
        ContentValues contentValues = new ContentValues();
        if (null != bloodPressure) {
            contentValues.put(DataBaseManager.MEASUREMENT_SYSTOLIC, bloodPressure.getSystolic());
            contentValues.put(DataBaseManager.MEASUREMENT_DIASTOLIC, bloodPressure.getDiastolic());
            contentValues.put(DataBaseManager.MEASUREMENT_MEASUREDON, MediPalUtility.
                    convertDateToString(bloodPressure.getMeasuredOn(), "yyyy MMM dd HH:mm"));
        }
        if (null != pulse) {
            contentValues.put(DataBaseManager.MEASUREMENT_PULSE, pulse.getPulse());
            contentValues.put(DataBaseManager.MEASUREMENT_MEASUREDON,
                    MediPalUtility.
                            convertDateToString(pulse.getMeasuredOn(), "yyyy MMM dd HH:mm"));
        }
        if (null != temperature) {
            contentValues.put(DataBaseManager.MEASUREMENT_TEMPERATURE, temperature.getTemperature());
            contentValues.put(DataBaseManager.MEASUREMENT_MEASUREDON,
                    MediPalUtility.
                            convertDateToString(temperature.getMeasuredOn(), "yyyy MMM dd HH:mm"));
        }
        if (null != weight) {
            contentValues.put(DataBaseManager.MEASUREMENT_WEIGHT, weight.getWeight());
            contentValues.put(DataBaseManager.MEASUREMENT_MEASUREDON,
                    MediPalUtility.
                            convertDateToString(weight.getMeasuredOn(), "yyyy MMM dd HH:mm"));
        }

        try {
            retCode = database.insertOrThrow(DataBaseManager.MEASUREMENT_TABLE, null, contentValues);
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }

    public List<Object> retrieve(String selectedDate) {
        List<Object>
                measurementList = new ArrayList<Object>();
        BloodPressure bloodPressureObj = null;
        Pulse pulseObj = null;
        Weight weightObj = null;
        Temperature temperatureObj = null;
        Cursor cursor = null;

        try {

            if (null != selectedDate
                    && !selectedDate.equals("")) {
                cursor = database.query(DataBaseManager.MEASUREMENT_TABLE,
                        new String[]{
                                DataBaseManager.MEASUREMENT_ID,
                                DataBaseManager.MEASUREMENT_SYSTOLIC,
                                DataBaseManager.MEASUREMENT_DIASTOLIC,
                                DataBaseManager.MEASUREMENT_PULSE,
                                DataBaseManager.MEASUREMENT_TEMPERATURE,
                                DataBaseManager.MEASUREMENT_WEIGHT,
                                DataBaseManager.MEASUREMENT_MEASUREDON}, "measuredon>= ? AND measuredon<= ?",
                        new String[]{selectedDate + " 00:00", selectedDate + " 23:59"}, null, null, null);
            } else {
                cursor = database.query(DataBaseManager.MEASUREMENT_TABLE,
                        new String[]{
                                DataBaseManager.MEASUREMENT_ID,
                                DataBaseManager.MEASUREMENT_SYSTOLIC,
                                DataBaseManager.MEASUREMENT_DIASTOLIC,
                                DataBaseManager.MEASUREMENT_PULSE,
                                DataBaseManager.MEASUREMENT_TEMPERATURE,
                                DataBaseManager.MEASUREMENT_WEIGHT,
                                DataBaseManager.MEASUREMENT_MEASUREDON}, null,
                        null, null, null, null);
            }

            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                int systolic = cursor.getInt(1);
                int diastolic = cursor.getInt(2);
                int pulse = cursor.getInt(3);
                float temperature = cursor.getFloat(4);
                int weight = cursor.getInt(5);
                String measuredOn = cursor.getString(6);

                if (systolic > 0 && diastolic > 0) {
                    bloodPressureObj
                            = new BloodPressure(MediPalUtility.convertStringToDate(measuredOn, "yyyy MMM dd HH:mm"),
                            systolic, diastolic);
                    measurementList.add(bloodPressureObj);
                }

                if (pulse > 0) {
                    pulseObj
                            = new Pulse(MediPalUtility.convertStringToDate(measuredOn, "yyyy MMM dd HH:mm"),
                            pulse);
                    measurementList.add(pulseObj);
                }

                if (temperature > 0) {
                    temperatureObj =
                            new Temperature(MediPalUtility.convertStringToDate(measuredOn, "yyyy MMM dd HH:mm"),
                                    temperature);
                    measurementList.add(temperatureObj);
                }

                if (weight > 0) {
                    weightObj =
                            new Weight(MediPalUtility.convertStringToDate(measuredOn, "yyyy MMM dd HH:mm"),
                                    weight);
                    measurementList.add(weightObj);
                }
            }
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
        }

        return measurementList;
    }
}