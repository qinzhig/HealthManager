package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.Date;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;
import sg.edu.nus.iss.medipal.pojo.Medicine;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;

/**
 * Created by zhiguo on 13/3/17.
 */

public class MedicineDAO extends DataBaseUtility {
    private static final String WHERE_ID_EQUALS = DataBaseManager.MEDICINE_ID + " =?";

    public MedicineDAO(Context context) {

        super(context);
    }

    //Method to insert data to medicine table
    public long insert(Medicine medicine)throws SQLException
    {
        //return value
        long retCode = 0;
        //use the medicine pojo to populate the table column values
        ContentValues values =  new ContentValues();
        values.put(DataBaseManager.MEDICINE_NAME, medicine.getMedicine_name());
        values.put(DataBaseManager.MEDICINE_DES, medicine.getMedicine_des());
        values.put(DataBaseManager.MEDICINE_CATID, medicine.getCateId());
        values.put(DataBaseManager.MEDICINE_REMID, medicine.getReminderId());
        values.put(DataBaseManager.MEDICINE_REM, medicine.isReminder() ? 1:0);
        values.put(DataBaseManager.MEDICINE_QUANTITY, medicine.getQuantity());
        values.put(DataBaseManager.MEDICINE_DOSAGE, medicine.getDosage());
        values.put(DataBaseManager.MEDICINE_DATEISSUED, medicine.getDateIssued().toString());
        values.put(DataBaseManager.MEDICINE_EXPIREFACTOR,medicine.getExpireFactor());


        //Insert data into Medicine table. If insertion is successfull then the method returns the row ID, else -1
        try {
            retCode = database.insertOrThrow(DataBaseManager.MEDICINE_TABLE, null, values);
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }

    //method to update the medicine table
    public long update(Medicine medicine) {
        //return value
        long retCode = 0;

        //use the medicine pojo to populate the table column values
        ContentValues values = new ContentValues();

        values.put(DataBaseManager.MEDICINE_NAME, medicine.getMedicine_name());
        values.put(DataBaseManager.MEDICINE_DES, medicine.getMedicine_des());
        values.put(DataBaseManager.MEDICINE_CATID, medicine.getCateId());
        values.put(DataBaseManager.MEDICINE_REMID, medicine.getReminderId());
        values.put(DataBaseManager.MEDICINE_REM, medicine.isReminder() ? 1:0);
        values.put(DataBaseManager.MEDICINE_QUANTITY, medicine.getQuantity());
        values.put(DataBaseManager.MEDICINE_DOSAGE, medicine.getDosage());
        values.put(DataBaseManager.MEDICINE_DATEISSUED, medicine.getDateIssued().toString());
        values.put(DataBaseManager.MEDICINE_EXPIREFACTOR,medicine.getExpireFactor());

        //method returns number of rows affected. so if it is zero some error handling needs to be done by caller
        retCode = database.update(DataBaseManager.MEDICINE_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(medicine.getId()) });

        return retCode;

    }

    //get list of medicines
    public ArrayList<Medicine> getMedicines() {
        ArrayList<Medicine> medicines = new ArrayList<Medicine>();

        //similiar to query "select * from medicines"
        Cursor cursor = database.query(DataBaseManager.MEDICINE_TABLE,
                new String[] {
                        DataBaseManager.MEDICINE_ID,
                        DataBaseManager.MEDICINE_NAME,
                        DataBaseManager.MEDICINE_DES,
                        DataBaseManager.MEDICINE_CATID,
                        DataBaseManager.MEDICINE_REMID,
                        DataBaseManager.MEDICINE_REM,
                        DataBaseManager.MEDICINE_QUANTITY,
                        DataBaseManager.MEDICINE_DOSAGE,
                        DataBaseManager.MEDICINE_DATEISSUED,
                        DataBaseManager.MEDICINE_EXPIREFACTOR,
                        }, null, null, null, null, null);

        //loop through each result set to populate the appointment pojo and add to the list each time
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String des = cursor.getString(2);
            int catid = cursor.getInt(3);
            int remid=cursor.getInt(4);
            boolean rem;
            if(cursor.getInt(5) == 0) { rem = false; } else{ rem = true; }
            int quantity=cursor.getInt(6);
            int dosage=cursor.getInt(7);
            Date date = new Date(cursor.getString(8));
            int expirefactor=cursor.getInt(9);

            Medicine medicinenode = new Medicine(id,name,des,catid,remid,rem,quantity,dosage,date,expirefactor);
            medicines.add(medicinenode);

        }
        return medicines;
    }
}
