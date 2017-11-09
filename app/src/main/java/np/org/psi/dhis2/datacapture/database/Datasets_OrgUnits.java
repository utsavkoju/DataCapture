package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import np.org.psi.dhis2.datacapture.objects.OrgUnit;

/**
 * Created by utsav on 3/8/2016.
 */
public class Datasets_OrgUnits extends DBHandler {
    public static final String TAG = "database.Datasets_OU";

    public Datasets_OrgUnits(Context context) { super(context); }

    public Boolean Datasets_OrgUnitsExist() {
        int count = 0;
        String select = "SELECT * FROM "+TABLE_DATASET_ORGUNIT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public void saveDataset_orgUnit(String dataset_id, String orgUnit_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_DATASET_ID, dataset_id);
        val.put(KEY_ORGUNIT_ID, orgUnit_id);

        db.insert(TABLE_DATASET_ORGUNIT, null, val);
        db.close();
        Log.i(TAG, "Stored");
    }

    public List<OrgUnit> getOrgUnit(String name) {
        List <OrgUnit> ounits = new ArrayList<OrgUnit>();
        String Query = "SELECT tb_orgUnit.id as id, tb_orgUnit.name as name, shortName, code, level, coordinates FROM tb_orgUnit JOIN tb_dataset_orgUnit ON tb_dataset_orgUnit.orgUnit_Id = tb_orgUnit.id WHERE tb_dataset_orgUnit.dataset_Id = (SELECT id FROM tb_dataset WHERE name  = '"+name+"') ORDER BY tb_orgUnit.name";
        Log.e(TAG,Query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            do {
                ounits.add(new OrgUnit(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return ounits;
    }

    public void deleteDataset_orgUnit() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_DATASET_ORGUNIT);
        db.close();
        Log.i(TAG,"DELETED");
    }
}
