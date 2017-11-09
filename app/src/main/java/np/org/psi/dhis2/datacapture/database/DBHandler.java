package np.org.psi.dhis2.datacapture.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "datacapture";

    //LIST OF TABLES
    protected static final String TABLE_USER = "tb_user";
    protected static final String TABLE_ORGUNIT = "tb_orgUnit";
    protected static final String TABLE_DATASET = "tb_dataset";
    protected static final String TABLE_DATASET_ORGUNIT = "tb_dataset_orgUnit";
    protected static final String TABLE_OPTIONSETS = "tb_optionsets";
    protected static final String TABLE_OPTIONS = "tb_options";
    protected static final String TABLE_DIDIREPORT = "tb_didiReport";
    protected static final String TABLE_BCCSUMMARY = "tb_bccSummary";
    protected static final String TABLE_DPOWEEKLY = "tb_dpoWeekly";
    protected static final String TABLE_DASHBOARD = "tb_dashboard";
    protected static final String TABLE_DASHBOARDITEMS = "tb_dashboard_items";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_SYNCDATE = "syncDate";
    public static final String KEY_SERVER_ID = "serverId";
    public static final String KEY_ISDIRTY = "isDirty";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_CREDENTIALS = "credentials";
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENO = "phoneNo";
    public static final String KEY_EMPLOYER = "employer";
    public static final String KEY_DISPLAY_NAME = "displayName";

    public static final String KEY_SHORTNAME = "shortName";
    public static final String KEY_CODE = "code";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_PARENTID = "parentId";
    public static final String KEY_COORDINATES = "coordinates";

    public static final String KEY_PERIODTYPE = "daily";
    public static final String KEY_DATAENTRYFORM = "dataEntryForm";

    public static final String KEY_DATASET_ID = "dataset_Id";
    public static final String KEY_ORGUNIT_ID = "orgUnit_Id";
    public static final String KEY_OPTIONSSET_ID = "optionSet_Id";

    public static final String KEY_PERIOD = "period";
    public static final String KEY_LOCALITY = "locality";
    public static final String KEY_WARDNO = "wardNo";
    public static final String KEY_NEWCONTACT = "newContact";
    public static final String KEY_PREVIOUSCONTACT = "previousContact";
    public static final String KEY_OKIUCD = "okIUCD";
    public static final String KEY_NONNETWORKIUCD = "nonNetworkIUCD";
    public static final String KEY_OKIMPLANT = "okImplant";
    public static final String KEY_NONNETWORKIMPLANT = "nonNetworkImplant";
    public static final String KEY_PILLS = "pills";
    public static final String KEY_DEPO = "depo";
    public static final String KEY_STERILIZATION = "sterilization";
    public static final String KEY_MA = "medicalAbortion";

    public static final String KEY_REPORTED_BY = "reported_By";
    public static final String KEY_TOLE = "tole";
    public static final String KEY_ACTIVITY_TYPE = "activityType";
    public static final String KEY_MALE = "male";
    public static final String KEY_FEMALE = "female";
    public static final String KEY_CHILDREN = "children";
    public static final String KEY_REFERRAL = "referral";
    public static final String KEY_PC = "pc";

    public static final String KEY_ONLINESYNC = "onlineSync";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_IUCD = "iucd";

    public static final String KEY_DASHBOARD_ID = "dashboard_id";
    public static final String KEY_REPORT_TYPE = "report_type";
    public static final String KEY_CHART_ID = "chart_id";


    private DBHandler dbHandler;
    private SQLiteHelper sqliteHelper;
    private Context context;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ("
                                    + KEY_ID + " INTEGER PRIMARY KEY, "
                                    + KEY_USERNAME + " TEXT, "
                                    + KEY_CREDENTIALS + " TEXT, "
                                    + KEY_FIRSTNAME + " TEXT, "
                                    + KEY_LASTNAME + " TEXT, "
                                    + KEY_EMAIL + " TEXT, "
                                    + KEY_PHONENO + " TEXT, "
                                    + KEY_EMPLOYER + " TEXT )";

        String CREATE_ORGUNIT_TABLE = "CREATE TABLE " + TABLE_ORGUNIT + " ("
                                    + KEY_ID + " TEXT, "
                                    + KEY_NAME + " TEXT, "
                                    + KEY_SHORTNAME + " TEXT, "
                                    + KEY_CODE + " TEXT, "
                                    + KEY_LEVEL + " TEXT, "
                                    + KEY_COORDINATES + " TEXT, "
                                    + KEY_PARENTID + " TEXT )";

        String CREATE_DATASET_TABLE = "CREATE TABLE " + TABLE_DATASET + " ("
                                    + KEY_ID + " TEXT, "
                                    + KEY_NAME + " TEXT, "
                                    + KEY_SHORTNAME + " TEXT, "
                                    + KEY_PERIODTYPE + " TEXT, "
                                    + KEY_DATAENTRYFORM + " TEXT )";

        String CREATE_DATASET_ORGUNIT_TABLE = "CREATE TABLE " + TABLE_DATASET_ORGUNIT + " ("
                                    + KEY_DATASET_ID + " TEXT, "
                                    + KEY_ORGUNIT_ID + " TEXT )";

        String CREATE_OPTIONSET_TABLE = "CREATE TABLE " + TABLE_OPTIONSETS + " ("
                                    + KEY_ID + " TEXT, "
                                    + KEY_NAME + " TEXT, "
                                    + KEY_DISPLAY_NAME + " TEXT, "
                                    + KEY_CODE + " TEXT )";

        String CREATE_OPTION_TABLE = "CREATE TABLE " + TABLE_OPTIONS + " ("
                                    + KEY_ID + " TEXT, "
                                    + KEY_NAME + " TEXT, "
                                    + KEY_CODE + " TEXT, "
                                    + KEY_OPTIONSSET_ID + " TEXT )";

        String CREATE_DIDIREPORT_TABLE = "CREATE TABLE " + TABLE_DIDIREPORT + " ("
                                    + KEY_ID + "  INTEGER PRIMARY KEY, "
                                    + KEY_ORGUNIT_ID + " TEXT, "
                                    + KEY_PERIOD + " TEXT, "
                                    + KEY_LOCALITY + " TEXT, "
                                    + KEY_WARDNO + " TEXT, "
                                    + KEY_NEWCONTACT + " TEXT, "
                                    + KEY_PREVIOUSCONTACT + " TEXT, "
                                    + KEY_OKIUCD + " TEXT, "
                                    + KEY_NONNETWORKIUCD + " TEXT, "
                                    + KEY_OKIMPLANT + " TEXT, "
                                    + KEY_NONNETWORKIMPLANT + " TEXT, "
                                    + KEY_PILLS + " TEXT, "
                                    + KEY_DEPO + " TEXT, "
                                    + KEY_STERILIZATION + " TEXT, "
                                    + KEY_MA + " TEXT, "
                                    + KEY_SERVER_ID + " TEXT, "
                                    + KEY_ISDIRTY + " TEXT, "
                                    + KEY_SYNCDATE + " TEXT )";

        String CREATE_BCCSUMMARY_TABLE = "CREATE TABLE " + TABLE_BCCSUMMARY + " ("
                                    + KEY_ID + " INTEGER PRIMARY KEY, "
                                    + KEY_ORGUNIT_ID + " TEXT, "
                                    + KEY_PERIOD + " TEXT, "
                                    + KEY_REPORTED_BY + " TEXT, "
                                    + KEY_TOLE + " TEXT, "
                                    + KEY_ACTIVITY_TYPE + " TEXT, "
                                    + KEY_MALE + " TEXT, "
                                    + KEY_FEMALE + " TEXT, "
                                    + KEY_CHILDREN + " TEXT, "
                                    + KEY_REFERRAL + " TEXT, "
                                    + KEY_PC + " TEXT, "
                                    + KEY_SERVER_ID + " TEXT, "
                                    + KEY_ISDIRTY + " TEXT, "
                                    + KEY_SYNCDATE + " )";

        String CREATE_DPOWEEKLY_TABLE = "CREATE TABLE " + TABLE_DPOWEEKLY + " ("
                                    + KEY_ID + " INTEGER PRIMARY KEY, "
                                    + KEY_ORGUNIT_ID + " TEXT, "
                                    + KEY_PERIOD + " TEXT, "
                                    + KEY_CONTACT + " TEXT, "
                                    + KEY_REFERRAL + " TEXT, "
                                    + KEY_IUCD + " TEXT)";

        String CREATE_DASHBOARD_TABLE = "CREATE TABLE " + TABLE_DASHBOARD + " ("
                                    + KEY_ID + " TEXT, "
                                    + KEY_NAME + " TEXT )";

        String CREATE_DASHBOARD_ITEMS_TABLE = "CREATE TABLE " + TABLE_DASHBOARDITEMS + " ("
                                    + KEY_ID + " TEXT, "
                                    + KEY_DASHBOARD_ID + " TEXT, "
                                    + KEY_REPORT_TYPE + " TEXT, "
                                    + KEY_CHART_ID + " )";


        //RUN SYNTAX
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ORGUNIT_TABLE);
        db.execSQL(CREATE_DATASET_TABLE);
        db.execSQL(CREATE_DATASET_ORGUNIT_TABLE);
        db.execSQL(CREATE_OPTIONSET_TABLE);
        db.execSQL(CREATE_OPTION_TABLE);
        db.execSQL(CREATE_DIDIREPORT_TABLE);
        db.execSQL(CREATE_BCCSUMMARY_TABLE);
        db.execSQL(CREATE_DPOWEEKLY_TABLE);
        db.execSQL(CREATE_DASHBOARD_TABLE);
        db.execSQL(CREATE_DASHBOARD_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_ORGUNIT);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_DATASET);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_DATASET_ORGUNIT);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_OPTIONSETS);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_OPTIONS);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_DIDIREPORT);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_BCCSUMMARY);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_DPOWEEKLY);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_DASHBOARD);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_DASHBOARDITEMS);
        onCreate(db);
    }

    public DBHandler open() throws SQLException {
        this.getWritableDatabase();
        return this;
    }

    public void close() {
        if(dbHandler != null) {
            dbHandler.close();
        }
    }

    //SQLiteHelper
    public class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name,
                            CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }

    }
}
