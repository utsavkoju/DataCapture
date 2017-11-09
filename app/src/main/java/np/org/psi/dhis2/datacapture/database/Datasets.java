package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by utsav on 3/8/2016.
 */
public class Datasets extends DBHandler{
    public static final String TAG = "database.Datasets";

    public Datasets(Context context) { super(context);}

    public Boolean datasetExist() {
        int count = 0;
        String select = "SELECT * FROM "+TABLE_DATASET;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public void saveDataset(String id, String name, String shortName, String periodType, String dataEntryForm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ID, id);
        val.put(KEY_NAME, name);
        val.put(KEY_SHORTNAME, shortName);
        val.put(KEY_PERIODTYPE, periodType);
        val.put(KEY_DATAENTRYFORM, dataEntryForm);

        db.insert(TABLE_DATASET, null, val);
        db.close();
        Log.i(TAG, "Stored Successfully");
    }

    public void deleteDataset() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DATASET);
        db.close();
    }

}
