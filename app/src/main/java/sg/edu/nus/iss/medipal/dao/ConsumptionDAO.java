package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;
import sg.edu.nus.iss.medipal.pojo.Consumption;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;

/**
 * Created by : Ma teng on 07-03-2017.
 * Description : This is the main view for Appointment
 * Modified by : Naval on 24-03-2017
 * Reason for modification :
 */

public class ConsumptionDAO extends DataBaseUtility {
    public ConsumptionDAO(Context context) { super(context); }

    private static final String WHERE_ID_EQUALS = DataBaseManager.CONSUMPTION_ID + " =?";
    private static final String WHERE_MEDICINEID_EQUALS = DataBaseManager.CONSUMPTION_MEIDICINE_ID + " =?";
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

    //method to delete the consumption table
    public long delete(int medicineId) throws SQLException
    {
        long retCode=0;
        try {
            retCode = database.delete(DataBaseManager.CONSUMPTION_TABLE, WHERE_MEDICINEID_EQUALS, new String[]{String.valueOf(medicineId)});
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


    //get list of consumption
    public ArrayList<Consumption> getConsumptions(String date) {
        ArrayList<Consumption> consumptions = new ArrayList<Consumption>();

        String selection = "trim(substr(consumedOn,1,10)) = ?";
        String[] selectionArgs = {date};
        Cursor cursor = database.query(DataBaseManager.CONSUMPTION_TABLE,
                new String[]{
                        DataBaseManager.CONSUMPTION_ID,
                        DataBaseManager.CONSUMPTION_MEIDICINE_ID,
                        DataBaseManager.CONSUMPTION_QUANTITY,
                        DataBaseManager.CONSUMEDON},selection,selectionArgs,null,null,DataBaseManager.CONSUMEDON);

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

    public Integer getConsumptionCount(String medicineId,String consumedOn) {

        String selection = "medicine_id = ? AND trim(substr(consumedOn,1,10)) = ?";
        String[] selectionArgs = {medicineId, consumedOn};
        Cursor cursor = database.query(DataBaseManager.CONSUMPTION_TABLE,
                null,selection,selectionArgs,null,null,null);

        int count = cursor.getCount();
        return count;
    }

    public Integer getCurrentConsumptionCount(String medicineId,String consumedOn) {

        String selection = "medicine_id = ? AND trim(substr(consumedOn,1,10)) = ? AND quantity <> ?";
        String[] selectionArgs = {medicineId, consumedOn,"0"};
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
        Log.d("count",Integer.toString(cursor.getCount()));
        //loop through each result set to populate the appointment pojo and add to the list each time
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    public int[] getConsumptionQuantities(String medicineId,String consumedOn) {
        String selection = "medicine_id = ? AND trim(substr(consumedOn,1,10)) = trim(substr(?,1,10)) AND quantity = ?";
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

    //get list of consumption
    public HashMap<String,Double> getPieChartConsumptions(String date) {
        HashMap<String,Double> data = new HashMap<>();

        String query = "select count(c.id),c1.category from medicine m,category c1, consumption c where m.id=c.medicine_id and m.catid=c1.id and c.quantity=0 and trim(substr(consumedOn,1,10)) = ? group by category";
        Cursor c = database.rawQuery(query,new String[]{date});

        //loop through each result set to populate the appointment pojo and add to the list each time
        while (c.moveToNext()) {
            int count = c.getInt(0);
            String category = c.getString(1);

            data.put(category,Double.valueOf(count));
        }
        return data;
    }

}
