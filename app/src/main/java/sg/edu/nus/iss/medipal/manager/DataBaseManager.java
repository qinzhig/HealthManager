package sg.edu.nus.iss.medipal.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by : Navi on 06-03-2017.
 * Description : This is the database manager class which will have the db creation and modification methods
 * Modified by :
 * Reason for modification :
 */

public class DataBaseManager extends SQLiteOpenHelper {

    //Database name
    private static final String DATABASE_NAME = "medipalFT01DB";
    private static final int DATABASE_VERSION = 1;

    //variables for Personal Bio table
    public static final String PERSONALBIO_TABLE = "personalbio";
    public static final String PERSONALBIO_ID = "id";
    public static final String PERSONALBIO_NAME = "name";
    public static final String PERSONALBIO_DOB = "dob";
    public static final String PERSONALBIO_IDNO = "idno";
    public static final String PERSONALBIO_ADDRESS = "address";
    public static final String PERSONALBIO_POSTALCODE = "postalcode";
    public static final String PERSONALBIO_HEIGHT = "height";
    public static final String PERSONALBIO_BLOODTYPE = "bloodtype";

    // Create query for Personal Bio table
    public static final String CREATE_PERSONALBIO_TABLE = "CREATE TABLE "
            + PERSONALBIO_TABLE + "(" + PERSONALBIO_ID + " INTEGER PRIMARY KEY, "
            + PERSONALBIO_NAME + " TEXT, " + PERSONALBIO_DOB + " DATE, "
            + PERSONALBIO_IDNO + " TEXT, " + PERSONALBIO_ADDRESS+ " TEXT, "
            + PERSONALBIO_POSTALCODE + " INTEGER, " + PERSONALBIO_HEIGHT + " INTEGER, "
            + PERSONALBIO_BLOODTYPE + " TEXT "+")";

    //variables for Health Bio table
    public static final String HEALTHBIO_TABLE = "healthbio";
    public static final String HEALTHBIO_ID = "id";
    public static final String HEALTHBIO_CONDITION = "condition";
    public static final String HEALTHBIO_STARTDATE = "startdate";
    public static final String HEALTHBIO_CONDITIONTYPE = "conditiontype";

    // Create query for Health Bio table
    public static final String CREATE_HEALTHBIO_TABLE = "CREATE TABLE "
            + HEALTHBIO_TABLE + "(" + HEALTHBIO_ID + " INTEGER PRIMARY KEY, "
            + HEALTHBIO_CONDITION + " TEXT, " + HEALTHBIO_STARTDATE + " DATE, "
            + HEALTHBIO_CONDITIONTYPE + " TEXT "+")";

    //variables used to create medicine table query
    public static final String MEDICINE_TABLE = "medicine";
    public static final String MEDICINE_ID = "id";
    public static final String MEDICINE_NAME = "medicine";
    public static final String MEDICINE_DES = "description";
    public static final String MEDICINE_CATID = "catid";
    public static final String MEDICINE_REMID = "reminderid";
    public static final String MEDICINE_REM = "remind";
    public static final String MEDICINE_QUANTITY = "quantity";
    public static final String MEDICINE_DOSAGE = "dosage";
    public static final String MEDICINE_DATEISSUED = "dateissued";
    public static final String MEDICINE_EXPIREFACTOR = "expirefactor";

    //medicine SQLite table creation SQL
    public static final String CREATE_MEDICINE_TABLE = "CREATE TABLE "
            + MEDICINE_TABLE + "(" + MEDICINE_ID + " INTEGER PRIMARY KEY, "
            + MEDICINE_NAME + " TEXT, " + MEDICINE_DES + " TEXT, "
            + MEDICINE_CATID + " INTEGER, " + MEDICINE_REMID+ " INTEGER, "
            + MEDICINE_REM + " INTEGER, " + MEDICINE_QUANTITY + " INTEGER, "
            + MEDICINE_DOSAGE + " INTEGER, " + MEDICINE_DATEISSUED + " TEXT, "
            + MEDICINE_EXPIREFACTOR + " INTEGER "+")";

    //variables used to create category table query
    public static final String CATEGORY_TABLE = "category";
    public static final String CATEGORY_ID = "id";
    public static final String CATEGORY_NAME = "category";
    public static final String CATEGORY_CODE = "code";
    public static final String CATEGORY_DES = "description";

    //category SQLite table creating SQL
    public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "
            + CATEGORY_TABLE + "(" + CATEGORY_ID + " INTEGER PRIMARY KEY, "
            + CATEGORY_NAME+ " TEXT, " + CATEGORY_CODE + " TEXT, "
            + CATEGORY_DES + " TEXT" + ")";

    //variables used for to form create appointment table query
    public static final String APPOINTMENT_TABLE = "appointment";
    public static final String APPMNT_ID = "id";
    public static final String APPMNT_LOCATION = "location";
    public static final String APPMNT_DATETIME = "appointment";
    public static final String APPMNT_DESCRIPTION = "description";


    //appointment table create query
    public static final String CREATE_APPOINTMENT_TABLE = "CREATE TABLE "
            + APPOINTMENT_TABLE + "(" + APPMNT_ID + " INTEGER PRIMARY KEY, "
            + APPMNT_LOCATION + " TEXT, " + APPMNT_DATETIME + " TEXT, "
            + APPMNT_DESCRIPTION + " TEXT" + ")";

    //variables used to create ice table query
    public static final String ICE_TABLE = "ice";
    public static final String ICE_ID = "id";
    public static final String ICE_NAME = "name";
    public static final String ICE_CONTACTNUMBER = "contactnumber";
    public static final String ICE_CONTACTTYPE = "contacttype";
    public static final String ICE_DESCRIPTION = "description";

    //appointment table create query
    public static final String CREATE_ICE_TABLE = "CREATE TABLE "
            + ICE_TABLE + "(" + ICE_ID + " INTEGER PRIMARY KEY, "
            + ICE_NAME + " TEXT, " + ICE_CONTACTNUMBER + " TEXT, "
            + ICE_CONTACTTYPE + " INTEGER, "+ ICE_DESCRIPTION + " TEXT" + ")";

    //variables used to create measurement table query
    public static final String MEASUREMENT_TABLE = "measurement";
    public static final String MEASUREMENT_ID = "id";
    public static final String MEASUREMENT_SYSTOLIC = "systolic";
    public static final String MEASUREMENT_DIASTOLIC = "diastolic";
    public static final String MEASUREMENT_PULSE = "pulse";
    public static final String MEASUREMENT_TEMPERATURE = "temperature";
    public static final String MEASUREMENT_WEIGHT = "weight";
    public static final String MEASUREMENT_MEASUREDON = "measuredon";

    public static final String CREATE_MEASUREMENT_TABLE = "CREATE TABLE "
            + MEASUREMENT_TABLE + "(" + MEASUREMENT_ID + " INTEGER PRIMARY KEY, "
            + MEASUREMENT_SYSTOLIC + " INTEGER, " + MEASUREMENT_DIASTOLIC + " INTEGER, "
            + MEASUREMENT_PULSE + " INTEGER, " + MEASUREMENT_TEMPERATURE + " FLOAT, "
            + MEASUREMENT_WEIGHT + " INTEGER, "+ MEASUREMENT_MEASUREDON + " DATETIME" + ")";

    //To store current DB instance
    private static DataBaseManager instance;

    //returns current DB instance.(if not present, then creates an instance(during app installation))
    public static synchronized DataBaseManager getDBInstance(Context context) {
        if (instance == null)
            instance = new DataBaseManager(context);
        return instance;
    }

    //constructor
    private DataBaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //is this required? have to check
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //Will be called whenever database instance is created (will be done during installation of app)
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.d("oncreate",CREATE_HEALTHBIO_TABLE);
        db.execSQL(CREATE_PERSONALBIO_TABLE);
        db.execSQL(CREATE_HEALTHBIO_TABLE);
        db.execSQL(CREATE_MEDICINE_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_APPOINTMENT_TABLE);
        db.execSQL(CREATE_ICE_TABLE);
        db.execSQL(CREATE_MEASUREMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DataBaseManager.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                );

        //the following code makes sure that the tables are recreated each time when db is upgraded.
        //If existing data needs to be preserved then following code needs to be changed accordingly
        db.execSQL("DROP TABLE IF EXISTS " + PERSONALBIO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + HEALTHBIO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MEDICINE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + APPOINTMENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_ICE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_MEASUREMENT_TABLE);
        onCreate(db);
    }}
