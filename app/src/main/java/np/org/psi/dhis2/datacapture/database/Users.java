package np.org.psi.dhis2.datacapture.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Users extends DBHandler {

    private static final String TAG = "database.Users";

    public Users(Context context) { super(context); }

    public Boolean userExist() {
        int count = 0;
        String select = "SELECT * FROM "+TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        count = cursor.getCount();
        db.close();
        if(count == 0) { return false; }
        return true;
    }

    public void saveUser(String username, String credentials, String firstName, String lastName, String email, String phoneNumber, String employer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_ID,"1");
        val.put(KEY_USERNAME, username);
        val.put(KEY_CREDENTIALS, credentials);
        val.put(KEY_FIRSTNAME, firstName);
        val.put(KEY_LASTNAME, lastName);
        val.put(KEY_EMAIL, email);
        val.put(KEY_PHONENO, phoneNumber);
        val.put(KEY_EMPLOYER, employer);

        db.insert(TABLE_USER, null, val);
        db.close();
        Log.i(TAG, "Stored Successfully");
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER);
        db.close();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT id,firstName,lastName,email FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("firstName", cursor.getString(1));
            user.put("lastName", cursor.getString(2));
            user.put("email", cursor.getString(3));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }

    public ArrayList<String> getUser() {
        ArrayList<String> user = new ArrayList<>();
        String select = "SELECT firstName,lastName,email FROM " + TABLE_USER;
        Log.e("Query: ",select);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select, null);
        if(cursor.getColumnCount() == 1) {
            cursor.moveToFirst();
            user.add(0, cursor.getString(0));
            user.add(1, cursor.getString(1));
            user.add(2,cursor.getString(2));
        }
        cursor.close();
        db.close();
        return user;
    }

    public String getCredentials() {
        String credentials = null;
        String selectQuery = "SELECT credentials FROM " + TABLE_USER;
        Log.e("Query: ",selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getColumnCount() == 1) {
            cursor.moveToFirst();
            credentials = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return credentials;
    }

}
