package ru.rafaelrs.httpremotesingle.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SendData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "senddata.db";
    private static final int DATABASE_VERSION = 1;

    public SendData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SendLogs.TABLE_NAME + " (" + SendLogs._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SendLogs.COL_MESSAGE + " TEXT,"
                + SendLogs.COL_EVENTTIME + " LONG NOT NULL "
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SendLogs.TABLE_NAME);
        onCreate(db);
    }

    public void addEvent(String message, long eventTime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SendLogs.COL_MESSAGE, message);
        values.put(SendLogs.COL_EVENTTIME, eventTime);
        db.insertOrThrow(SendLogs.TABLE_NAME, null, values);
        db.close();
    }

    public void dismissEvent(long eventTime) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(SendLogs.TABLE_NAME, SendLogs.COL_EVENTTIME + "=?", new String[] { String.valueOf(eventTime) });
        db.close();
    }

    public class EventPacket {
        public String message;
        public long evenTime;
    }

    public EventPacket getPacketFromQueue() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("" +
                "SELECT " +
                "   " + SendLogs.COL_MESSAGE + " AS message, " +
                "   " + SendLogs.COL_EVENTTIME + " AS time " +
                "FROM " + SendLogs.TABLE_NAME + " " +
                "ORDER BY " +
                "   time " +
                "LIMIT 1 "
                , null);
        EventPacket resData = null;
        if (cursor.moveToNext()) {
            resData = new EventPacket();
            resData.message = cursor.getString(0);
            resData.evenTime = cursor.getLong(1);
        }

        return resData;
    }

    public ArrayList<EventPacket> getPacketsFromQueue() {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("" +
                "SELECT " +
                "   " + SendLogs.COL_MESSAGE + " AS message, " +
                "   " + SendLogs.COL_EVENTTIME + " AS time " +
                "FROM " + SendLogs.TABLE_NAME + " " +
                "ORDER BY " +
                "   time "
                , null);
        ArrayList<EventPacket> resData = new ArrayList<EventPacket>();
        while (cursor.moveToNext()) {
            EventPacket currData = new EventPacket();
            currData.message = cursor.getString(0);
            currData.evenTime = cursor.getLong(1);
            resData.add(currData);
        }

        return resData;
    }
}
