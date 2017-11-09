package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import np.org.psi.dhis2.datacapture.objects.DashboardItem;

/**
 * Created by utsav on 5/12/2016.
 */
public class DashboardItems extends DBHandler {
    public static final String TAG = "database.dashItems";
    public DashboardItems(Context context) {
        super(context);
    }

    public Boolean dashItemExist() {
        int count = 0;
        String select = "SELECT * FROM "+TABLE_DASHBOARDITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public void saveDashItems(String id, String dashboard_id, String report_type, String Chart_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ID, id);
        val.put(KEY_DASHBOARD_ID, dashboard_id);
        val.put(KEY_REPORT_TYPE, report_type);
        val.put(KEY_CHART_ID, Chart_id);

        db.insert(TABLE_DASHBOARDITEMS, null, val);
        db.close();
        Log.i(TAG, "Stored Successfully");
    }

    public ArrayList<DashboardItem> getItems_by_dashboard_Id(String dashboard_id) {
        ArrayList<DashboardItem> report = new ArrayList<DashboardItem>();
        String Query = "SELECT id, dashboard_id, report_type, chart_id FROM tb_dashboard_items where dashboard_id = "+'"'+dashboard_id+'"';
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            do {
                report.add(new DashboardItem(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  report;
    }

    public void deleteDashboardItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DASHBOARDITEMS);
        db.close();
    }
}
