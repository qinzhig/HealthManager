package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;
import sg.edu.nus.iss.medipal.pojo.HealthBio;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by Divahar on 3/15/2017.
 */

public class HealthBioDAO extends DataBaseUtility {

    private static final String WHERE_ID_EQUALS = DataBaseManager.HEALTHBIO_ID + " =?";

    public HealthBioDAO(Context context) {
        super(context);
    }

    public long insert(HealthBio healthBio) throws SQLException {

        long retCode = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseManager.HEALTHBIO_CONDITION, healthBio.getCondition());
        contentValues.put(DataBaseManager.HEALTHBIO_STARTDATE, MediPalUtility.convertDateToString(healthBio.getStartDate(),
                "dd MMM yyyy"));
        contentValues.put(DataBaseManager.HEALTHBIO_CONDITIONTYPE, String.valueOf(healthBio.getConditionType()));

        try {
            retCode = database.insertOrThrow(DataBaseManager.HEALTHBIO_TABLE, null, contentValues);
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }


    public long update(HealthBio healthBio) {

        long retCode = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseManager.HEALTHBIO_CONDITION, healthBio.getCondition());
        contentValues.put(DataBaseManager.HEALTHBIO_STARTDATE, MediPalUtility.convertDateToString(healthBio.getStartDate(),
                "dd MMM yyyy"));
        contentValues.put(DataBaseManager.HEALTHBIO_CONDITIONTYPE, String.valueOf(healthBio.getConditionType()));

        try {
            retCode = database.update(DataBaseManager.HEALTHBIO_TABLE, contentValues,
                    WHERE_ID_EQUALS,
                    new String[]{String.valueOf(healthBio.getId())});
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }

    public List<HealthBio> retrieve() {

        HealthBio healthBio = null;
        List<HealthBio> healthBioList = new ArrayList<HealthBio>();

        try {
            Cursor cursor = database.query(DataBaseManager.HEALTHBIO_TABLE,
                    new String[]{
                            DataBaseManager.HEALTHBIO_ID,
                            DataBaseManager.HEALTHBIO_CONDITION,
                            DataBaseManager.HEALTHBIO_STARTDATE,
                            DataBaseManager.HEALTHBIO_CONDITIONTYPE}, null, null, null, null, null);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String condition = cursor.getString(1);
                Date startDate = MediPalUtility.convertStringToDate(cursor.getString(2),
                        "dd MMM yyyy");
                char conditionType = cursor.getString(3).charAt(0);

                healthBio = new
                        HealthBio(id, condition, startDate, conditionType);

                healthBioList.add(healthBio);
            }
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
        }

        return healthBioList;
    }


    public long delete(String healthBioId) {

        long retCode = 0;

        try {
            database.delete(DataBaseManager.HEALTHBIO_TABLE,
                    DataBaseManager.HEALTHBIO_ID + "= ?", new String[]{healthBioId});
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }
}
