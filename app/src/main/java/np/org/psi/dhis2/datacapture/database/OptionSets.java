package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import np.org.psi.dhis2.datacapture.objects.Option;

/**
 * Created by utsav on 3/9/2016.
 */
public class OptionSets extends DBHandler {
    private static final String TAG = "database.Optionsets";
    public OptionSets(Context context) { super(context); }

    public Boolean OptionSetExist() {
        int count = 0;
        String select = "SELECT * FROM "+TABLE_OPTIONSETS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public void saveOptionSet(String id, String name, String displayName, String code)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ID, id);
        val.put(KEY_NAME, name);
        val.put(KEY_DISPLAY_NAME, displayName);
        val.put(KEY_CODE, code);

        db.insert(TABLE_OPTIONSETS, null, val);

        db.close();
        Log.i(TAG, "Stored Successfully");
    }

    public List<Option> getOptionSet(String module) {
        List <Option> options = new ArrayList<Option>();
        String Query = "SELECT code, name FROM tb_options WHERE tb_options.optionSet_Id = (SELECT id FROM tb_optionsets WHERE name = '"+module+"')";
        Log.e(TAG, Query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()) {
            do {
                options.add(new Option(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return options;
    }

    public void deleteOptionset() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_OPTIONSETS);
        db.close();
        Log.i(TAG,"DELETED");
    }
}
