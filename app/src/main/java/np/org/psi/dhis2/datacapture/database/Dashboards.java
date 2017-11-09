package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by utsav on 5/12/2016.
 */
public class Dashboards extends DBHandler{
    public static final String TAG = "database.Dashboard";
    public Dashboards(Context context) {
        super(context);
    }

    public Boolean DashboardExist() {
        int count = 0;
        String select = "SELECT * FROM " + TABLE_DASHBOARD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public void saveDash(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ID, id);
        val.put(KEY_NAME, name);
        db.insert(TABLE_DASHBOARD, null, val);
        db.close();
        Log.i(TAG, "Stored Successfully");
    }

    public void deleteDashboard() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DASHBOARD);
        db.close();
    }

    public List<String> getDashboard() {
        List<String> result = new ArrayList<String>();
        String Query = "SELECT name FROM tb_dashboard";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            do {
                result.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

    public String getId_byName(String Name) {
        String result = null;
        String Query = "SELECT id FROM tb_dashboard WHERE name ="+'"'+Name+'"';
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

}
