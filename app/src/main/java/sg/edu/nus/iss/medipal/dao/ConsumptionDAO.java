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
        values.put(DataBaseManager.CONSUMEDON, consumption.getConsumedOn());

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
    //method to delete the consumption table
    public long delete(Consumption consumption) throws SQLException
    {
        long retCode=0;
        try {
            retCode = database.delete(DataBaseManager.CONSUMPTION_TABLE, WHERE_ID_EQUALS, new String[]{String.valueOf(consumption.getId())});
        }catch (SQLException sqlE){
            sqlE.printStackTrace();
            retCode = -1;
        }
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
            String CONSUMEDON = cursor.getString(3);
            Consumption consumption = new Consumption(id, CONSUMPTION_MEIDICINE_ID, CONSUMPTION_QUANTITY, CONSUMEDON);
            consumptions.add(consumption);
        }
        return consumptions;
    }

    //added by naval
    public Integer getConsumptionCount(String medicineId,String consumedOn) {

        String selection = "medicine_id = ? AND trim(substr(consumedOn,1,10)) = ?";
        String[] selectionArgs = {medicineId, consumedOn};
        Cursor cursor = database.query(DataBaseManager.CONSUMPTION_TABLE,
                null,selection,selectionArgs,null,null,null);

        int count = cursor.getCount();
        return count;
    }

    public int getMinConsumptionId(String medicineId,String consumedOn) {

        String selection = "medicine_id = ? AND trim(substr(consumedOn,1,10)) = ? AND quantity = ?";
        String[] selectionArgs = {medicineId, consumedOn,"0"};
        Cursor cursor = database.query(DataBaseManager.CONSUMPTION_TABLE,
                new String[]{
                        "min(id)",
                        },selection,selectionArgs,null,null,null);
        int id=-1;
        //loop through each result set to populate the appointment pojo and add to the list each time
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    public int[] getConsumptionQuantities(String medicineId,String consumedOn) {
        String selection = "medicine_id = ? AND trim(substr(consumedOn,1,10)) = ? AND quantity = ?";
        String[] selectionArgs = {medicineId, consumedOn,"0"};
        Cursor cursor = database.query(DataBaseManager.CONSUMPTION_TABLE,
                new String[]{
                        "quantity",
                },selection,selectionArgs,null,null,null);
        int[] id= new int[cursor.getCount()];
        int cnt=0;
        //loop through each result set to populate the appointment pojo and add to the list each time
        while (cursor.moveToNext()) {
            id[cnt] =Integer.parseInt(cursor.getString(0));
        }
        return id;
    }
}
