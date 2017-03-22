package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;

/**
 * Created by apple on 07/03/2017.
 */

public class ConsumptionDAO extends DataBaseUtility {
    public ConsumptionDAO(Context context) { super(context); }

    private static final String WHERE_ID_EQUALS = DataBaseManager.CONSUMPTION_ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("d-MMM-yyyy H:mm", Locale.CHINA);


    //method to insert into consumption table
    public long insert(Consumption consumption)throws SQLException
    {
        long retCode = 0;

        ContentValues values = new ContentValues();
        values.put(DataBaseManager.CONSUMPTION_MEIDICINE_ID,consumption.getMedicineId());
        values.put(DataBaseManager.CONSUMPTION_QUANTITY,consumption.getQuality());
        values.put(DataBaseManager.CONSUMEDON, String.valueOf(consumption.getConsumedOn()));

        //Insert into consumption table. If insertion is successfull then the method returns the row ID, else -1
        try {
            retCode = database.insertOrThrow(DataBaseManager.CONSUMPTION_TABLE, null, values);
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace();
            retCode = -1;
        }

        return retCode;
    }

    //method to update the consumption table
    public long update(Consumption consumption)
    {
        long retCode = 0;

        ContentValues values = new ContentValues();
        if (consumption.getMedicineId() != null)
            values.put(DataBaseManager.CONSUMPTION_MEIDICINE_ID,consumption.getMedicineId());
        if (consumption.getQuality() != null)
            values.put(DataBaseManager.CONSUMPTION_QUANTITY,consumption.getQuality());
        if (consumption.getConsumedOn() != null) {
            values.put(DataBaseManager.CONSUMEDON, String.valueOf(consumption.getConsumedOn()));
        }

        retCode = database.update(DataBaseManager.CONSUMPTION_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(consumption.getId())});
        return retCode;
    }

    //get list of consumption
    public ArrayList<Consumption> getConsumptions() {
        ArrayList<Consumption> consumptions = new ArrayList<Consumption>();

        Cursor cursor = database.query(DataBaseManager.CONSUMPTION_TABLE,
                new String[]{
                        DataBaseManager.CONSUMPTION_ID,
                        DataBaseManager.CONSUMPTION_MEIDICINE_ID,
                        DataBaseManager.CONSUMPTION_QUANTITY,
                        DataBaseManager.CONSUMEDON},null,null,null,null,null);

        //loop through each result set to populate the appointment pojo and add to the list each time
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int CONSUMPTION_MEIDICINE_ID = cursor.getInt(1);
            int CONSUMPTION_QUANTITY = cursor.getInt(2);
            Date CONSUMEDON = null;
            try {
                CONSUMEDON = formatter.parse(cursor.getString(3));
            }catch (ParseException e) {
                e.printStackTrace();
            }

            Consumption consumption = new Consumption(id, CONSUMPTION_MEIDICINE_ID, CONSUMPTION_QUANTITY, CONSUMEDON);
            consumptions.add(consumption);
        }
        return consumptions;
    }







}
