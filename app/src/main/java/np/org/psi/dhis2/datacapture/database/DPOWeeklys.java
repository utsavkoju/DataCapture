package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import np.org.psi.dhis2.datacapture.objects.DPOWeekly;

/**
 * Created by utsav on 3/18/2016.
 */
public class DPOWeeklys extends DBHandler {

    public DPOWeeklys(Context context) { super(context); }

    private static String TAG = "database.DPOWeeklys";

    public Boolean dpoWeeklysExist() {
        int count = 0;
        String select = "SELECT * FROM "+TABLE_DPOWEEKLY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public Boolean saveDPOWeekly(String orgUnit_Id, String period, String contact, String referral, String IUCD) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ORGUNIT_ID, orgUnit_Id);
        val.put(KEY_PERIOD, period);
        val.put(KEY_CONTACT, contact);
        val.put(KEY_REFERRAL, referral);
        val.put(KEY_IUCD, IUCD);

        db.insert(TABLE_DPOWEEKLY, null, val);
        db.close();
        Log.i(TAG, "Stored Successfully");
        return true;
    }

    public ArrayList<DPOWeekly> getAllDPOWeeklys() {
        ArrayList<DPOWeekly> report = new ArrayList<DPOWeekly>();
        String Query = "SELECT tb_orgUnit.name 'orgUnit', tb_dpoWeekly.period, tb_dpoWeekly.contact, tb_dpoWeekly.referral, tb_dpoWeekly.iucd FROM tb_dpoWeekly JOIN tb_orgUnit ON tb_orgUnit.id = tb_dpoWeekly.orgUnit_Id ORDER BY tb_dpoWeekly.id desc";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()){
            do{
                report.add( new DPOWeekly(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return report;
    }

}
