package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import np.org.psi.dhis2.datacapture.objects.BccSummary;

/**
 * Created by utsav on 3/13/2016.
 */
public class BccSummarys extends DBHandler {
    public BccSummarys(Context context) { super(context); }
    private static String TAG = "database.BccSummarys";

    public Boolean bccSummarysExist() {
        int count = 0;
        String select = "SELECT * FROM "+TABLE_DIDIREPORT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public Boolean saveBccReport(String orgUnit_Id, String period, String report_by, String tole, String activityType, String male, String female, String children, String referral, String pc)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ORGUNIT_ID, orgUnit_Id);
        val.put(KEY_PERIOD, period);
        val.put(KEY_REPORTED_BY, report_by);
        val.put(KEY_TOLE, tole);
        val.put(KEY_ACTIVITY_TYPE, activityType);
        val.put(KEY_MALE, male);
        val.put(KEY_FEMALE, female);
        val.put(KEY_CHILDREN, children);
        val.put(KEY_REFERRAL, referral);
        val.put(KEY_PC, pc);

        db.insert(TABLE_BCCSUMMARY, null, val);
        db.close();
        Log.i(TAG, "Stored Sucessfully");
        return true;
    }

    public Boolean updateBCCReport(String id, String orgUnit_Id, String period, String report_by, String tole, String activityType, String male, String female, String children, String referral, String pc)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ORGUNIT_ID, orgUnit_Id);
        val.put(KEY_PERIOD, period);
        val.put(KEY_REPORTED_BY, report_by);
        val.put(KEY_TOLE, tole);
        val.put(KEY_ACTIVITY_TYPE, activityType);
        val.put(KEY_MALE, male);
        val.put(KEY_FEMALE, female);
        val.put(KEY_CHILDREN, children);
        val.put(KEY_REFERRAL, referral);
        val.put(KEY_PC, pc);
        db.update(TABLE_BCCSUMMARY, val, "id = "+id,null);
        db.close();
        Log.i(TAG, "UPDATED Successfully");
        return true;
    }

    public ArrayList<BccSummary> getAllBccSummary() {
        ArrayList<BccSummary> report = new ArrayList<BccSummary>();
        String Query = "SELECT tb_orgUnit.name, tb_bccSummary.period, tb_options.name,  male, female, referral, tb_bccSummary.id FROM tb_bccSummary JOIN tb_orgUnit ON tb_orgUnit.id = tb_bccSummary.orgUnit_Id JOIN tb_options ON tb_options.code = tb_bccSummary.activityType ORDER BY tb_bccSummary.id DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst())
        {
            do {
                report.add(new BccSummary(Integer.parseInt(cursor.getString(6)),cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return report;
    }

    public Boolean remove(Integer id)
    {
        Log.e("ID: ", String.valueOf(id));
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BCCSUMMARY, KEY_ID + "=" + id, null) > 0;
    }

    public List<String> getBCCSummary(Integer id) {
        List<String> result = new ArrayList<String>();
        String Query = "SELECT tb_orgUnit.name, period, reported_By, tole, tb_options.name, male, female, children, referral, pc FROM " + TABLE_BCCSUMMARY + " JOIN tb_orgUnit ON tb_orgUnit.id = tb_bccSummary.orgUnit_Id JOIN tb_options ON tb_options.code = tb_bccSummary.activityType WHERE tb_bccSummary.id ="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst())
        {
            for(int i = 0; i <10; i++)
            {
                result.add(i, cursor.getString(i));
            }
        }
        cursor.close();
        db.close();
        return result;
    }

}
