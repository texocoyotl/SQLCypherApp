package com.texocoyotl.w7d3sqlcypherapp;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(this);

    }

    public void onSave(View view) {
        UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = usersDatabaseHelper.getWritableDatabase(FirstPart.part + SecondPart.part + ThirdPart.part);

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(UsersDatabaseHelper.COLUMN_NAME, "Ben");
            values.put(UsersDatabaseHelper.COLUMN_RANK, 1);

            db.insertOrThrow(UsersDatabaseHelper.TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }

    }

    public void onLoad(View view) {
        UsersDatabaseHelper usersDatabaseHelper = new UsersDatabaseHelper(getApplicationContext());
        SQLiteDatabase db = usersDatabaseHelper.getWritableDatabase(FirstPart.part + SecondPart.part + ThirdPart.part);

        Cursor cursor = db.query(UsersDatabaseHelper.TABLE, null, null, null, null, null, null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            do {
                Log.d(TAG, "loadMagic: " + cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.COLUMN_ID)));
                Log.d(TAG, "loadMagic: " + cursor.getString(cursor.getColumnIndex(UsersDatabaseHelper.COLUMN_NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

    }
}
