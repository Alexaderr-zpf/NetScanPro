package com.example.netscanpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * ScanDatabase — historial de escaneos en SQLite
 * Patrón idéntico al DatabaseHelper del Lab 15
 */
public class ScanDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME    = "netscan.db";
    private static final int    DB_VERSION = 1;

    private static final String TABLE      = "scans";
    private static final String COL_ID     = "id";
    private static final String COL_FECHA  = "fecha";
    private static final String COL_SUBNET = "subnet";
    private static final String COL_TOTAL  = "total_scanned";
    private static final String COL_ALIVE  = "hosts_alive";

    public ScanDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " ("
                + COL_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_FECHA  + " TEXT, "
                + COL_SUBNET + " TEXT, "
                + COL_TOTAL  + " INTEGER, "
                + COL_ALIVE  + " INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void insertScan(String fecha, String subnet, int total, int alive) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_FECHA,  fecha);
        cv.put(COL_SUBNET, subnet);
        cv.put(COL_TOTAL,  total);
        cv.put(COL_ALIVE,  alive);
        db.insert(TABLE, null, cv);
        db.close();
    }

    public List<String> getAllScans() {
        List<String> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE, null, null, null,
                null, null, COL_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                String fecha  = cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA));
                String subnet = cursor.getString(cursor.getColumnIndexOrThrow(COL_SUBNET));
                int    total  = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TOTAL));
                int    alive  = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ALIVE));
                result.add(fecha + "  |  " + subnet + ".x/24  |  "
                        + alive + "/" + total + " activos");
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE);
        db.close();
    }
}
