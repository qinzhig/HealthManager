package sg.edu.nus.iss.medipal.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sg.edu.nus.iss.medipal.manager.DataBaseManager;

/**
 * Created by : Navi on 06-03-2017.
 * Description : This method will be used by all DAO classes to open a connection to database instance and subsequently to close it
 * Modified by :
 * Reason for modification :
 *
 */

public class DataBaseUtility {
    protected SQLiteDatabase database;
    private DataBaseManager dbManager;
    private Context mContext;

    //Constructor
    public DataBaseUtility(Context context) {
        this.mContext = context;
        this.dbManager = DataBaseManager.getDBInstance(mContext);
        open();

    }

    //open a connection to db
    public void open() throws SQLException {
        if(dbManager == null)
            dbManager = DataBaseManager.getDBInstance(mContext);
        database = dbManager.getWritableDatabase();
        Log.d("inside open",Boolean.toString(database==null));
    }

    //close the connection
    public void close() {
        dbManager.close();
        database = null;
    }
}
