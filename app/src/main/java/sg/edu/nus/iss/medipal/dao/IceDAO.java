package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;
import sg.edu.nus.iss.medipal.pojo.Ice;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;

/**
 * Created by levis on 3/23/2017.
 */

public class IceDAO extends DataBaseUtility {

    private static final String WHERE_ID_EQUALS = DataBaseManager.ICE_ID + " =?";

    public IceDAO(Context context) {
        super(context);
    }

    public long insert(Ice ice) throws SQLException {
        long retCode = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseManager.ICE_NAME, ice.getName());
        contentValues.put(DataBaseManager.ICE_CONTACTNUMBER, ice.getContactNo());
        contentValues.put(DataBaseManager.ICE_CONTACTTYPE, ice.getContactType());
        contentValues.put(DataBaseManager.ICE_DESCRIPTION, ice.getDescription());
        contentValues.put(DataBaseManager.ICE_PRIORITY, ice.getPriority());

        try {
            retCode = database.insertOrThrow(DataBaseManager.ICE_TABLE, null, contentValues);
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }

    public long update(Ice ice) {
        long retCode = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseManager.ICE_NAME, ice.getName());
        contentValues.put(DataBaseManager.ICE_CONTACTNUMBER, ice.getContactNo());
        contentValues.put(DataBaseManager.ICE_CONTACTTYPE, ice.getContactType());
        contentValues.put(DataBaseManager.ICE_DESCRIPTION, ice.getDescription());
        contentValues.put(DataBaseManager.ICE_PRIORITY, ice.getPriority());

        try {
            retCode = database.update(DataBaseManager.ICE_TABLE, contentValues, WHERE_ID_EQUALS, new String[]{String.valueOf(ice.getId())});
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            retCode = -1;
        }
        return retCode;
    }

    public List<Ice> retrieve() {
        Ice ice = null;
        List<Ice> iceList = new ArrayList<Ice>();

        try {
            Cursor cursor = database.query(DataBaseManager.ICE_TABLE,
                    new String[]{
                            DataBaseManager.ICE_ID,
                            DataBaseManager.ICE_NAME,
                            DataBaseManager.ICE_CONTACTNUMBER,
                            DataBaseManager.ICE_CONTACTTYPE,
                            DataBaseManager.ICE_DESCRIPTION,
                            DataBaseManager.ICE_PRIORITY}, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                String contractNo = cursor.getString(2);
                Integer contractType = cursor.getInt(3);
                String description = cursor.getString(4);
                Integer priority = cursor.getInt(5);

                ice = new Ice(id, name, contractNo, contractType, description);
                iceList.add(ice);
            }
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
        }

        return iceList;
    }

    public long delete(String id) {
        long retCode = 0;

        try {
            database.delete(DataBaseManager.ICE_TABLE, DataBaseManager.ICE_ID + "= ?", new String[]{id});
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            retCode = -1;
        }
        return retCode;
    }
}
