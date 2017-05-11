package net.cloudapp.avsite.sqlitesample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBhelper extends SQLiteOpenHelper {

    final static String TABLE_NAME = "artists";
    final static String f_ARTIST_NAME = "name";
    final static String f_ID = "_id";
    final static String[] columns = { f_ID, f_ARTIST_NAME };

    final private static String CREATE_ARTIST_TABLE =
            "CREATE TABLE artists (" +
                    f_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    f_ARTIST_NAME + " TEXT NOT NULL)";

    final private static String DB_NAME = "artist_db";
    final private static Integer VERSION = 1;
    final private Context mContext;

    public MyDBhelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    void deleteDatabase() {
        mContext.deleteDatabase(DB_NAME);
    }
}
