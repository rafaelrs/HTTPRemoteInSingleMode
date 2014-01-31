package ru.rafaelrs.httpremotesingle.db;

import android.provider.BaseColumns;

public interface SendLogs extends BaseColumns {
    public static final String TABLE_NAME = "sendpackets";
    public static final String COL_MESSAGE = "message";
    public static final String COL_EVENTTIME = "eventtime";
}
