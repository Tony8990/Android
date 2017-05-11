package net.cloudapp.avsite.sqlitesample;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {

    private SQLiteDatabase mDB = null;
    private MyDBhelper mDbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mDbHelper = new MyDBhelper(this);
        mDB = mDbHelper.getWritableDatabase();

        // start with an empty database
        mDB.delete(MyDBhelper.TABLE_NAME, null, null);

        // Insert records
        insertArtists();

        // Create a cursor
        Cursor c = readArtists();
        final SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.list_layout, c,
                MyDBhelper.columns, new int[] { R.id.artist_id, R.id.artist_name }, 0);

        setListAdapter(mAdapter);

        Button fixButton = (Button) findViewById(R.id.fix_button);
        fixButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // execute database operations
                modifyRecords();

                // Redisplay data
                //mAdapter.getCursor().requery();
                mAdapter.swapCursor(readArtists());
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    // Insert several artist records
    private void insertArtists() {

        ContentValues values = new ContentValues();
        values.put(MyDBhelper.f_ARTIST_NAME, "Frank Sinatra");
        mDB.insert(MyDBhelper.TABLE_NAME, null, values);

        values.clear();
        values.put(MyDBhelper.f_ARTIST_NAME, "Lady Gaga");
        mDB.insert(MyDBhelper.TABLE_NAME, null, values);

        values.clear();
        values.put(MyDBhelper.f_ARTIST_NAME, "Jawny Cash");
        mDB.insert(MyDBhelper.TABLE_NAME, null, values);

        values.clear();
        values.put(MyDBhelper.f_ARTIST_NAME, "Ludwig van Beethoven");
        mDB.insert(MyDBhelper.TABLE_NAME, null, values);
    }

    // Returns all artist records in the database
    private Cursor readArtists() {
        return mDB.query(MyDBhelper.TABLE_NAME,
                MyDBhelper.columns, null, new String[] {}, null, null,
                null);
    }

    // Modify the contents of the database
    private void modifyRecords() {

        // Sorry Lady Gaga :-(
        mDB.delete(MyDBhelper.TABLE_NAME,
                MyDBhelper.f_ARTIST_NAME + "=?",
                new String[] { "Lady Gaga" });

        // fix the Man in Black
        ContentValues values = new ContentValues();
        values.put(MyDBhelper.f_ARTIST_NAME, "Johnny Cash");
        mDB.update(MyDBhelper.TABLE_NAME, values,
                MyDBhelper.f_ARTIST_NAME + "=?",
                new String[] { "Jawny Cash" });

    }


    // Close database
    @Override
    protected void onDestroy() {

        mDB.close();
        mDbHelper.deleteDatabase();

        super.onDestroy();

    }
}
