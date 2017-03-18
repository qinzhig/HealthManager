package sg.edu.nus.iss.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.Date;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;
import sg.edu.nus.iss.medipal.pojo.PersonalBio;
import sg.edu.nus.iss.medipal.utils.DataBaseUtility;
import sg.edu.nus.iss.medipal.utils.MediPalUtility;

/**
 * Created by Divahar on 3/15/2017.
 */

public class PersonalBioDAO extends DataBaseUtility {

    private static final String WHERE_ID_EQUALS = DataBaseManager.PERSONALBIO_ID + " =?";

    public PersonalBioDAO(Context context) {
        super(context);
    }

    public long insert(PersonalBio personalBio) throws SQLException {
        long retCode = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseManager.PERSONALBIO_NAME, personalBio.getName());
        contentValues.put(DataBaseManager.PERSONALBIO_DOB, MediPalUtility.covertDateToString(personalBio.getDob()));
        contentValues.put(DataBaseManager.PERSONALBIO_IDNO, personalBio.getIdNo());
        contentValues.put(DataBaseManager.PERSONALBIO_ADDRESS, personalBio.getAddress());
        contentValues.put(DataBaseManager.PERSONALBIO_POSTALCODE, personalBio.getPostalCode());
        contentValues.put(DataBaseManager.PERSONALBIO_HEIGHT, personalBio.getHeight());
        contentValues.put(DataBaseManager.PERSONALBIO_BLOODTYPE, personalBio.getBloodType());

        try {
            retCode = database.insertOrThrow(DataBaseManager.PERSONALBIO_TABLE, null, contentValues);
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }


    public long update(PersonalBio personalBio) {

        long retCode = 0;
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseManager.PERSONALBIO_NAME, personalBio.getName());
        contentValues.put(DataBaseManager.PERSONALBIO_DOB, MediPalUtility.covertDateToString(personalBio.getDob()));
        contentValues.put(DataBaseManager.PERSONALBIO_IDNO, personalBio.getIdNo());
        contentValues.put(DataBaseManager.PERSONALBIO_ADDRESS, personalBio.getAddress());
        contentValues.put(DataBaseManager.PERSONALBIO_POSTALCODE, personalBio.getPostalCode());
        contentValues.put(DataBaseManager.PERSONALBIO_HEIGHT, personalBio.getHeight());
        contentValues.put(DataBaseManager.PERSONALBIO_BLOODTYPE, personalBio.getBloodType());

        try {
            retCode = database.update(DataBaseManager.PERSONALBIO_TABLE, contentValues,
                    WHERE_ID_EQUALS,
                    new String[]{String.valueOf(personalBio.getId())});
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace(); //unexpected error while inserting.
            retCode = -1; //set return value to error code so that caller can handle error
        }
        return retCode;
    }

    public PersonalBio retrieve() {

        PersonalBio personalBio = null;

        Cursor cursor = database.query(DataBaseManager.PERSONALBIO_TABLE,
                new String[]{
                        DataBaseManager.PERSONALBIO_ID,
                        DataBaseManager.PERSONALBIO_NAME,
                        DataBaseManager.PERSONALBIO_DOB,
                        DataBaseManager.PERSONALBIO_IDNO,
                        DataBaseManager.PERSONALBIO_ADDRESS,
                        DataBaseManager.PERSONALBIO_POSTALCODE,
                        DataBaseManager.PERSONALBIO_HEIGHT,
                        DataBaseManager.PERSONALBIO_BLOODTYPE}, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Date dob = MediPalUtility.covertStringToDate(cursor.getString(2));
            String idNo = cursor.getString(3);
            String address = cursor.getString(4);
            int postalCode = Integer.valueOf(cursor.getString(5));
            int height = Integer.valueOf(cursor.getString(6));
            String bloodType = cursor.getString(7);

            personalBio = new
                    PersonalBio(id, name, dob, idNo, address, postalCode, height, bloodType);
        }

        return personalBio;
    }
}
