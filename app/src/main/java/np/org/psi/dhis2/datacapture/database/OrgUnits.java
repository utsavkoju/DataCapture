package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import np.org.psi.dhis2.datacapture.objects.OrgUnit;

/**
 * Created by utsav on 2/24/2016.
 */
public class OrgUnits extends DBHandler{

    private static final String TAG = "database.OrgUnits";

    public OrgUnits(Context context) { super(context); }

    public Boolean orgUnitExist() {
        int count = 0;
        String select = "SELECT * FROM " + TABLE_ORGUNIT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public Boolean facilityExist() {
        int count = 0;
        String select = "SELECT * FROM " + TABLE_ORGUNIT + " WHERE level = 7";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public void saveOrgUnit(String id, String name, String shortName, String code, String level, String coordinates, String parentId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ID, id);
        val.put(KEY_NAME, name);
        val.put(KEY_SHORTNAME, shortName);
        val.put(KEY_CODE, code);
        val.put(KEY_LEVEL, level);
        val.put(KEY_COORDINATES, coordinates);
        val.put(KEY_PARENTID, parentId);
        db.insert(TABLE_ORGUNIT, null, val);
        db.close();
    }

    public List<OrgUnit> getOrgUnits () {
        List<OrgUnit> orgUnits = new ArrayList<OrgUnit>();
        String Query = "SELECT id, name, shortName, code, level, coordinates FROM tb_orgUnit WHERE level = 7";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst())
        {
            do {
                orgUnits.add(new OrgUnit(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orgUnits;
    }

    public void deleteOrgUnit() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ORGUNIT);
        db.close();
    }

}
