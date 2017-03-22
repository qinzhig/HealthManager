package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;
import sg.edu.nus.iss.medipal.pojo.Measurement;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by levis on 3/23/2017.
 */

public class MeasurementDAO extends DataBaseUtility {

    private static final String WHERE_ID_EQUALS = DataBaseManager.MEASUREMENT_ID + " =?";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    public MeasurementDAO(Context context) {
        super(context);
    }

    public long insert(Measurement measurement) throws SQLException {
        long retCode = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseManager.MEASUREMENT_SYSTOLIC, measurement.getSystolic());
        contentValues.put(DataBaseManager.MEASUREMENT_DIASTOLIC, measurement.getDiastolic());
        contentValues.put(DataBaseManager.MEASUREMENT_PULSE, measurement.getPulse());
        contentValues.put(DataBaseManager.MEASUREMENT_TEMPERATURE, measurement.getTemperature());
        contentValues.put(DataBaseManager.MEASUREMENT_WEIGHT, measurement.getWeight());
        contentValues.put(DataBaseManager.MEASUREMENT_TEMPERATURE, measurement.getTemperature());
        contentValues.put(DataBaseManager.MEASUREMENT_MEASUREDON, MediPalUtility.convertDateToString(measurement.getMeasuredOn(), "dd MMM yyyy"));

        try {
            retCode = database.insertOrThrow(DataBaseManager.MEASUREMENT_TABLE, null, contentValues);
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }


    public long update(Measurement measurement) {
        long retCode = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseManager.MEASUREMENT_SYSTOLIC, measurement.getSystolic());
        contentValues.put(DataBaseManager.MEASUREMENT_DIASTOLIC, measurement.getDiastolic());
        contentValues.put(DataBaseManager.MEASUREMENT_PULSE, measurement.getPulse());
        contentValues.put(DataBaseManager.MEASUREMENT_TEMPERATURE, measurement.getTemperature());
        contentValues.put(DataBaseManager.MEASUREMENT_WEIGHT, measurement.getWeight());
        contentValues.put(DataBaseManager.MEASUREMENT_TEMPERATURE, measurement.getTemperature());
        contentValues.put(DataBaseManager.MEASUREMENT_MEASUREDON, MediPalUtility.convertDateToString(measurement.getMeasuredOn(), "dd MMM yyyy"));

        try {
            retCode = database.update(DataBaseManager.ICE_TABLE, contentValues, WHERE_ID_EQUALS, new String[]{String.valueOf(measurement.getId())});
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }

    public List<Measurement> retrieve() {
        Measurement measurement = null;
        List<Measurement> measurementList = new ArrayList<Measurement>();

        try {
            Cursor cursor = database.query(DataBaseManager.MEASUREMENT_TABLE,
                    new String[]{
                            DataBaseManager.MEASUREMENT_ID,
                            DataBaseManager.MEASUREMENT_SYSTOLIC,
                            DataBaseManager.MEASUREMENT_DIASTOLIC,
                            DataBaseManager.MEASUREMENT_PULSE,
                            DataBaseManager.MEASUREMENT_TEMPERATURE,
                            DataBaseManager.MEASUREMENT_WEIGHT,
                            DataBaseManager.MEASUREMENT_TEMPERATURE,
                            DataBaseManager.MEASUREMENT_MEASUREDON}, null, null, null, null, null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                int systolic = cursor.getInt(1);
                int diastolic = cursor.getInt(2);
                int pulse = cursor.getInt(3);
                float temperature = cursor.getFloat(4);
                int weight = cursor.getInt(5);
                Date measuredon = null;
                try {
                    measuredon = dateFormatter.parse(cursor.getString(6));
                }catch (ParseException e) {
                    e.printStackTrace();
                }

                measurement = new Measurement(id, systolic, diastolic, pulse, temperature, weight, measuredon);
                measurementList.add(measurement);
            }
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
        }

        return measurementList;
    }


    public long delete(String iceId) {
        long retCode = 0;

        try {
            database.delete(DataBaseManager.MEASUREMENT_TABLE, DataBaseManager.MEASUREMENT_ID + "= ?", new String[]{iceId});
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }
}