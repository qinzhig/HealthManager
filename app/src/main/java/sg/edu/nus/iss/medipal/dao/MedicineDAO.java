package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;

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
    public long insert(Medicine medicine) throws SQLException {
        //return value
        long retCode = 0;
        //use the medicine pojo to populate the table column values
        ContentValues values = new ContentValues();
        values.put(DataBaseManager.MEDICINE_NAME, medicine.getMedicine_name());
        values.put(DataBaseManager.MEDICINE_DES, medicine.getMedicine_des());
        values.put(DataBaseManager.MEDICINE_CATID, medicine.getCateId());
        values.put(DataBaseManager.MEDICINE_REMID, medicine.getReminderId());
        values.put(DataBaseManager.MEDICINE_REM, medicine.isReminder() ? 1 : 0);
        values.put(DataBaseManager.MEDICINE_QUANTITY, medicine.getQuantity());
        values.put(DataBaseManager.MEDICINE_DOSAGE, medicine.getDosage());
        values.put(DataBaseManager.MEDICINE_CQUANTITY, medicine.getConsumequantity());
        values.put(DataBaseManager.MEDICINE_THRESHOLD, medicine.getThreshold());
        values.put(DataBaseManager.MEDICINE_DATEISSUED, medicine.getDateIssued());
        values.put(DataBaseManager.MEDICINE_EXPIREFACTOR, medicine.getExpireFactor());


        //Insert data into Medicine table. If insertion is successfull then the method returns the row ID, else -1
        try {
            retCode = database.insertOrThrow(DataBaseManager.MEDICINE_TABLE, null, values);
        } catch (SQLException sqlE) {
            sqlE.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }

    public long delete(Medicine medicine) throws SQLException {
        long retCode = 0;

        //Delete the medicine from SQLite
        try {
            retCode = database.delete(DataBaseManager.MEDICINE_TABLE, WHERE_ID_EQUALS, new String[]{String.valueOf(medicine.getId())});
        } catch (SQLException sqlE) {
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
        values.put(DataBaseManager.MEDICINE_REM, medicine.isReminder() ? 1 : 0);
        values.put(DataBaseManager.MEDICINE_QUANTITY, medicine.getQuantity());
        values.put(DataBaseManager.MEDICINE_DOSAGE, medicine.getDosage());
        values.put(DataBaseManager.MEDICINE_CQUANTITY, medicine.getConsumequantity());
        values.put(DataBaseManager.MEDICINE_THRESHOLD, medicine.getThreshold());
        values.put(DataBaseManager.MEDICINE_DATEISSUED, medicine.getDateIssued());
        values.put(DataBaseManager.MEDICINE_EXPIREFACTOR, medicine.getExpireFactor());

        //method returns number of rows affected. so if it is zero some error handling needs to be done by caller
        try {
            retCode = database.update(DataBaseManager.MEDICINE_TABLE, values,
                    WHERE_ID_EQUALS,
                    new String[]{String.valueOf(medicine.getId())});
        } catch (SQLException sqlE) {
            sqlE.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }

        return retCode;

    }

    //get list of medicines
    public ArrayList<Medicine> getMedicines() {
        ArrayList<Medicine> medicines = new ArrayList<Medicine>();

        //similiar to query "select * from medicines"
        Cursor cursor = database.query(DataBaseManager.MEDICINE_TABLE,
                new String[]{
                        DataBaseManager.MEDICINE_ID,
                        DataBaseManager.MEDICINE_NAME,
                        DataBaseManager.MEDICINE_DES,
                        DataBaseManager.MEDICINE_CATID,
                        DataBaseManager.MEDICINE_REMID,
                        DataBaseManager.MEDICINE_REM,
                        DataBaseManager.MEDICINE_QUANTITY,
                        DataBaseManager.MEDICINE_DOSAGE,
                        DataBaseManager.MEDICINE_CQUANTITY,
                        DataBaseManager.MEDICINE_THRESHOLD,
                        DataBaseManager.MEDICINE_DATEISSUED,
                        DataBaseManager.MEDICINE_EXPIREFACTOR,
                }, null, null, null, null, null);

        //loop through each result set to populate the appointment pojo and add to the list each time
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String des = cursor.getString(2);
            int catid = cursor.getInt(3);
            int remid = cursor.getInt(4);
            boolean rem;
            if (cursor.getInt(5) == 0) {
                rem = false;
            } else {
                rem = true;
            }
            int quantity = cursor.getInt(6);
            int dosage = cursor.getInt(7);
            int cquantity = cursor.getInt(8);
            int threshold = cursor.getInt(9);
            String date = cursor.getString(10);
            int expirefactor = cursor.getInt(11);

            Medicine medicinenode = new Medicine(id, name, des, catid, remid, rem, quantity, dosage, cquantity, threshold, date, expirefactor);

            Log.v("DEBUG", "-------------------------DAO++++++++++++++++++++++ " + medicinenode.toString());

            medicines.add(medicinenode);

        }
        return medicines;
    }

    //Get Medicine name based on ID for report
    public String getMedicineName(int medicineId) {

        String name = "";

        Cursor cursor = database.query(DataBaseManager.MEDICINE_TABLE,
                new String[]{
                        DataBaseManager.MEDICINE_NAME,
                }, DataBaseManager.MEDICINE_ID + "=" + medicineId, null, null, null, null);

        while (cursor.moveToNext()) {
            name = cursor.getString(0);
        }
        return name;
    }
}
