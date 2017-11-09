package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import np.org.psi.dhis2.datacapture.objects.DidiReport;

/**
 * Created by utsav on 3/9/2016.
 */
public class DidiReports extends DBHandler {
    public DidiReports(Context context) { super(context); }

    private static String TAG = "database.DidiReports";

    public Boolean didiReportExist() {
        int count = 0;
        String select = "SELECT * FROM "+TABLE_DIDIREPORT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public Integer needSync() {
        int count = 0;
        String select = "SELECT * FROM " + TABLE_DIDIREPORT + " WHERE " + KEY_ISDIRTY + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        return count;
    }

    public Boolean saveDidiReport(String orgUnit_id, String period, String locality, String wardNo, String newContact, String previousContact, String okIUCD, String nonNetworkIUCD, String okImplant, String nonNetworkImplant, String pills, String depo, String sterilization, String ma)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ORGUNIT_ID, orgUnit_id);
        val.put(KEY_PERIOD, "2016W"+period);
        val.put(KEY_LOCALITY, locality);
        val.put(KEY_WARDNO, wardNo);
        val.put(KEY_NEWCONTACT, newContact);
        val.put(KEY_PREVIOUSCONTACT, previousContact);
        val.put(KEY_OKIUCD, okIUCD);
        val.put(KEY_NONNETWORKIUCD, nonNetworkIUCD);
        val.put(KEY_OKIMPLANT, okImplant);
        val.put(KEY_NONNETWORKIMPLANT, nonNetworkImplant);
        val.put(KEY_PILLS, pills);
        val.put(KEY_DEPO, depo);
        val.put(KEY_STERILIZATION, sterilization);
        val.put(KEY_MA, ma);
        val.put(KEY_ISDIRTY, "1");

        db.insert(TABLE_DIDIREPORT, null, val);
        db.close();
        Log.i(TAG, "Stored Successfully");
        return true;
    }

    public Boolean updateDidiReport(String id, String orgUnit_id, String period, String locality, String wardNo, String newContact, String previousContact, String okIUCD, String nonNetworkIUCD, String okImplant, String nonNetworkImplant, String pills, String depo, String sterilization, String ma){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ORGUNIT_ID, orgUnit_id);
        val.put(KEY_PERIOD, period);
        val.put(KEY_LOCALITY, locality);
        val.put(KEY_WARDNO, wardNo);
        val.put(KEY_NEWCONTACT, newContact);
        val.put(KEY_PREVIOUSCONTACT, previousContact);
        val.put(KEY_OKIUCD, okIUCD);
        val.put(KEY_NONNETWORKIUCD, nonNetworkIUCD);
        val.put(KEY_OKIMPLANT, okImplant);
        val.put(KEY_NONNETWORKIMPLANT, nonNetworkImplant);
        val.put(KEY_PILLS, pills);
        val.put(KEY_DEPO, depo);
        val.put(KEY_STERILIZATION, sterilization);
        val.put(KEY_MA, ma);
        val.put(KEY_ISDIRTY, "1");

        db.update(TABLE_DIDIREPORT, val, "id = "+id, null);
        db.close();
        Log.i(TAG, "Stored Successfully");
        return true;
    }

    public ArrayList<DidiReport> getAllDidiReports() {
        ArrayList<DidiReport> report = new ArrayList<DidiReport>();
        String Query = "SELECT tb_orgUnit.name, period, locality, wardNo, newContact, previousContact, okIUCD, isDirty, tb_didiReport.id FROM "+TABLE_DIDIREPORT+" JOIN tb_orgUnit ON tb_orgUnit.id = tb_didiReport.orgUnit_Id ORDER BY tb_didiReport.id";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst()){
            do{
                report.add( new DidiReport(Integer.parseInt(cursor.getString(8)),cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return report;
    }

    public JsonObject getAllDidiRpts(){
        JsonObject drs = new JsonObject();
        JsonArray fnl = new JsonArray();
        String[] deStr = {"kAqXMCUOcOV","pum5lhlReLP","r10X2nqpmYr","r10X2nqpmYr","CXvwx87FieU","CXvwx87FieU","CXvwx87FieU","CXvwx87FieU","CXvwx87FieU","CXvwx87FieU","CXvwx87FieU","CXvwx87FieU"};
        String[] ccStr = {"","","Cxy71sRr0FD","tQiEwFB5boF","Qcxl43rt017","lEMJ8B8POq5","NFu9gK8nKqe","L2NpxfpaSuw","ALx6xj34dwl","PtftJwN5N4l","FrbmiTp3vwR","cRCN1yKhO10"};
        //Gson gson = new GsonBuilder().create();
        String select = "SELECT * FROM " + TABLE_DIDIREPORT + " WHERE " + KEY_ISDIRTY + " = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        if(cursor.moveToFirst()) {
            do {
                drs.addProperty("dataSet","cbxSBjBW45G");
                drs.addProperty("orgUnit",cursor.getString(1));
                drs.addProperty("period",cursor.getString(2));
                JsonArray deArray = new JsonArray();
                for(int i = 3; i<=14; i++) {
                    if(!cursor.getString(i).isEmpty()) {
                        JsonObject result = putFieldValuesInJson(deStr[i - 3], ccStr[i - 3], cursor.getString(i));
                        deArray.add(result);
                        }
                }
                drs.add("dataValues",deArray);
            } while(cursor.moveToNext());
        }
        db.close();
        return drs;
    }

    private static JsonObject putFieldValuesInJson(String de, String cc, String val) {
        JsonObject jField = new JsonObject();
        jField.addProperty("dataElement", de);
        if(!cc.isEmpty()) jField.addProperty("categoryOptionCombo", cc);
        jField.addProperty("value", val);
        return jField;
    }

    public Boolean setStatusSync() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ISDIRTY, "0");
        db.update(TABLE_DIDIREPORT, val, "isDirty = 1", null);
        db.close();
        Log.i(TAG, "Stored Successfully");
        return true;
    }

    public Boolean remove (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_DIDIREPORT, KEY_ID + " = " +id, null)>0;
    }

    public List<String> getDidiReport(Integer id) {
        List<String> result = new ArrayList<String>();
        String Query = "SELECT tb_orgUnit.name, period, locality, wardNo, newContact, previousContact, okIUCD, nonNetworkIUCD, okImplant, nonNetworkImplant, pills, depo, sterilization, medicalAbortion FROM " + TABLE_DIDIREPORT + " JOIN tb_orgUnit ON tb_orgUnit.id = tb_didiReport.orgUnit_Id WHERE tb_didiReport.id ="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.moveToFirst())
        {
            for(int i = 0; i < 14; i++)
            {
                result.add(i, cursor.getString(i));
            }
        }
        cursor.close();
        db.close();
        return result;
    }
}
