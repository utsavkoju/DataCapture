package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by utsav on 3/9/2016.
 */
public class Options extends DBHandler {
    public Options(Context context) { super(context); }
    private static final String TAG="database.Options";

    public Boolean optionExist() {
        int count = 0;
        String select = "SELECT * FROM "+TABLE_OPTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public void saveOptions(String id, String name, String code, String optionSet_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ID, id);
        val.put(KEY_NAME, name);
        val.put(KEY_CODE, code);
        val.put(KEY_OPTIONSSET_ID, optionSet_id);

        db.insert(TABLE_OPTIONS, null, val);
        db.close();
        Log.i(TAG, "Stored Successfully");
    }

    public void deleteOption() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_OPTIONS);
        db.close();
        Log.i(TAG,"DELETED");
    }
}
